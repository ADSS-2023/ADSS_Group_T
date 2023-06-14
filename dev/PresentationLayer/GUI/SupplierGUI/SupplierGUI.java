package PresentationLayer.GUI.SupplierGUI;

import PresentationLayer.GUI.Components.ManagerFrame;
import PresentationLayer.GUI.Components.StockFrame;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;

import static PresentationLayer.GUI.Components.StockGUI.run;

public class SupplierGUI {
    public static void main(String[] args) {
        ServiceFactory sf = new ServiceFactory();
        StockUI stockUI = new StockUI(sf);
        SupplierManager supplierManager = new SupplierManager(sf);
        stockUI.setPreviousCallBack(() -> run(new StockFrame(stockUI , supplierManager , sf)));
        supplierManager.setPreviousCallBack(() -> run(new StockFrame(stockUI , supplierManager , sf)));

        String[] options = {"Load data", "Empty system", "Set data to the system"};
        int action = JOptionPane.showOptionDialog(null, "Welcome to Superly inventory and supplier system", "Superly",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        try {
            if (action == 0) {
                // Read from DB
                sf.uss.loadDate();
                stockUI.loadData();
                supplierManager.loadData();

            } else if (action == 1) {
                // Delete all the DB
                supplierManager.deleteAll();
                stockUI.deleteData();
            } else if (action == 2) {
                stockUI.deleteData();
                sf.uss.setUpDate();
                stockUI.setUpData();
                supplierManager.setUpData();
            }
        } catch (Exception c) {

        }
        String[] frameOptions = {"ManagerFrame", "StockFrame"};
        int frameChoice = JOptionPane.showOptionDialog(null, "Choose a frame to open", "Select Frame",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, frameOptions, frameOptions[0]);
        if (frameChoice == 0) {
            // Open ManagerFrame
            // Create an instance of ManagerFrame and activate it
            run(new ManagerFrame(stockUI , supplierManager , sf));
        } else if (frameChoice == 1) {
            // Open StockFrame
            run(new StockFrame(stockUI , supplierManager , sf));
        }

    }
}
