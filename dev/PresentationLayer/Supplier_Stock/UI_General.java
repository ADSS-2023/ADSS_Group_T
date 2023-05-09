package PresentationLayer.Supplier_Stock;

import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;

import java.util.Scanner;

public class UI_General {
    public static void run(StockUI stockUI,SupplierManager supplierManager){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which system would you like to proceed to?\n" +
                "1.Suppliers system 2.Inventory system");
        int action = scanner.nextInt();
        scanner.nextLine();
        switch (action){
            case 1:
                try {
                    supplierManager.start();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            case 2:
                stockUI.run();
        }
    }

    public static void main(String[] args) {
        StockUI stockUI = new StockUI();
        SupplierManager supplierManager = new SupplierManager();
        stockUI.setPreviousCallBack(()->run(stockUI,supplierManager));
        supplierManager.setPreviousCallBack(()->run(stockUI,supplierManager));
        Scanner scanner = new Scanner(System.in);
        System.out.println("\033[1mWelcome to Superly inventory and supplier system\033[0m\n\u001B[32m" +
                "Would you like to load data or continue on an empty system?\n" +
                "1.Load data\n2.Empty system\u001B[0m");
        int action = scanner.nextInt();
        scanner.nextLine();
        if(action==1) {
            stockUI.loadData();
            //supplierManager.setUpData();
        }
        run(stockUI,supplierManager);
    }

}