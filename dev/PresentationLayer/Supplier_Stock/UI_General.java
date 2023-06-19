package PresentationLayer.Supplier_Stock;

import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Util.DAO;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class UI_General {
    public void run2(StockUI stockUI,SupplierManager supplierManager,ServiceFactory sf,String user){
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        while (isActive) {
            if (user.equals("Supplier")) {
                System.out.println("What would like to do?\n" +
                        "1.Enter suppliers system\n2.Skip day\n3.exit");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        supplierManager.start();
                        break;
                    case 2:
                        stockUI.moveToNextDay();
                        sf.nextDay();
                        supplierManager.nextDay();
                        break;
                    case 3:
                        isActive = false;
                        break;
                }
            }
            else if (user.equals("Stock")){
                System.out.println("What would like to do?\n" +
                        "1.Enter inventory system\n2.Skip day\n3.exit");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        stockUI.run();
                        break;
                    case 2:
                        stockUI.moveToNextDay();
                        sf.nextDay();
                        supplierManager.nextDay();
                        break;
                    case 3:
                        isActive = false;
                        break;
                }
            }
            else {
                System.out.println("What would like to do?\n" +
                        "1.Enter suppliers system\n2.Enter Inventory system\n3.Skip day\n4.Exit");
                int action = scanner.nextInt();
                scanner.nextLine();
                switch (action) {
                    case 1:
                        supplierManager.start();
                        break;
                    case 2:
                        stockUI.run();
                        break;
                    case 3:
                        stockUI.moveToNextDay();
                        sf.nextDay();
                        supplierManager.nextDay();
                        break;
                    case 4:
                        isActive = false;
                        break;
                }
            }
        }
    }

    public  void run(String user) throws Exception {
        ServiceFactory sf = new ServiceFactory();
        //sf.sc.loadSuppliers();
        StockUI stockUI = new StockUI(sf);
        SupplierManager supplierManager = new SupplierManager(sf);
        stockUI.setPreviousCallBack(()->run2(stockUI,supplierManager,sf,user));
        supplierManager.setPreviousCallBack(()->run2(stockUI,supplierManager,sf,user));
        Scanner scanner = new Scanner(System.in);
        System.out.println("\033[1mWelcome to Superly inventory and supplier system\033[0m\n\u001B[32m" +
                "Would you like to load data or continue on an empty system?\n" +
                "1.Load data\n2.Empty system\n3.Set data to the system\u001B[0m");
        int action = scanner.nextInt();
        scanner.nextLine();
        if(action==1) {
            //read from DB
            sf.uss.loadDate();
            stockUI.loadData();
            supplierManager.loadData();
        }
        else if(action == 2){
            //delete all the DB
            supplierManager.deleteAll();
            stockUI.deleteData();
        }
        else if(action == 3){
            stockUI.deleteData();
            sf.uss.setUpDate();
            stockUI.setUpData();
            supplierManager.setUpData();
        }
        run2(stockUI,supplierManager,sf,user);
    }
}
