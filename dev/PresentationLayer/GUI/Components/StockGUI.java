package PresentationLayer.GUI.Components;

import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketTimeoutException;

public class StockGUI extends JFrame {
    private StockUI stockUI;
    private SupplierManager supplierManager;
    private ServiceFactory sf;

    public StockGUI(StockUI stockUI, SupplierManager supplierManager, ServiceFactory sf) {
        this.stockUI = stockUI;
        this.supplierManager = supplierManager;
        this.sf = sf;
        setTitle("Stock Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 3));
        setVisible(true);
    }

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
            run(new ManagerFrame(sf));
        } else if (frameChoice == 1) {
            // Open StockFrame
            run(new StockFrame(stockUI , supplierManager , sf));
        }

    }

    private static void run(JFrame curFrame) {
        curFrame.setVisible(true);
    }
}

