package PresentationLayer.Supplier_Stock;

import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import java.util.Scanner;

public class UI_General {
    public static void run(StockUI stockUI,SupplierManager supplierManager,ServiceFactory sf){
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        while (isActive) {
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

    public static void main(String[] args) {
        ServiceFactory sf = new ServiceFactory();
        StockUI stockUI = new StockUI(sf);
        SupplierManager supplierManager = new SupplierManager(sf);
        stockUI.setPreviousCallBack(()->run(stockUI,supplierManager,sf));
        supplierManager.setPreviousCallBack(()->run(stockUI,supplierManager,sf));
        Scanner scanner = new Scanner(System.in);
        System.out.println("\033[1mWelcome to Superly inventory and supplier system\033[0m\n\u001B[32m" +
                "Would you like to load data or continue on an empty system?\n" +
                "1.Load data\n2.Empty system\u001B[0m");
        int action = scanner.nextInt();
        scanner.nextLine();
        if(action==1) {
            stockUI.loadData();
            supplierManager.setUpData();
        }
        run(stockUI,supplierManager,sf);
    }
}
