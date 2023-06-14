package PresentationLayer.GUI.Components;


import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketTimeoutException;

public class StockGUI extends JFrame {
    private ServiceFactory sf;
    public StockGUI(ServiceFactory sf) {
        this.sf = sf;
        setTitle("Stock Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 3));

//        createButtons();

        setVisible(true);
    }

//    private void createButtons() {
//        JButton btnSeeCategories = new JButton("See categories");
//        btnSeeCategories.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                stockUI.presentCategories();
//            }
//        });
//        add(btnSeeCategories);
//
//        JButton btnProduceInventoryReport = new JButton("Produce inventory report");
//        btnProduceInventoryReport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                stockUI.inventoryReport();
//            }
//        });
//        add(btnProduceInventoryReport);
//
//        JButton btnSetDiscount = new JButton("Set discount");
//        btnSetDiscount.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                stockUI.setDiscount();
//            }
//        });
//        add(btnSetDiscount);
//
//        // Add other buttons similarly
//
//        // ...
//
//        // Add more buttons as needed
//
//    }

    public static void main(String[] args) {
        ServiceFactory sf = new ServiceFactory();
        String[] options = {"Load data", "Empty system", "Set data to the system"};
        int action = JOptionPane.showOptionDialog(null, "Welcome to Superly inventory and supplier system", "Superly",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        try {
            if (action == 0) {
                // Read from DB
                sf.uss.loadDate();
                ///TODO activate service methods of load data for both modules
            } else if (action == 1) {
                ///TODO activate service methods of delete data for both modules
            } else if (action == 2) {

                sf.uss.setUpDate();
                ///TODO activate service methods of setup data for both modules
            }
        } catch (Exception c) {
            ///TODO what to do here?
        }
        String[] frameOptions = {"ManagerFrame", "StockFrame"};
        int frameChoice = JOptionPane.showOptionDialog(null, "Choose a frame to open", "Select Frame",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, frameOptions, frameOptions[0]);
        if (frameChoice == 0) {
            // Open ManagerFrame
            // Create an instance of ManagerFrame and activate it
//            run(new ManagerFrame(stockUI , supplierManager , sf));
        } else if (frameChoice == 1) {
            // Open StockFrame
            run(new StockFrame(sf));
        }

    }


    private static void run(JFrame curFrame) {
        curFrame.setVisible(true);
    }
}

