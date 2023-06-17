package PresentationLayer.GUI.Components;

import PresentationLayer.GUI.SupplierGUI.SupplierFrame;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
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


        String[] options = {"Load data", "Empty system", "Set data to the system"};
        int action = JOptionPane.showOptionDialog(null, "Welcome to Superly inventory and supplier system", "Superly",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        try {
            if (action == 0) {
                // Read from DB
                loadData(sf);


            } else if (action == 1) {
                // Delete all the DB

            } else if (action == 2) {

                sf.uss.setUpDate();

            }
        } catch (Exception c) {

        }
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("ID:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        String[] options2 = {"Login", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Login",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);

        if (choice == 0) {
            // Login button clicked
            String id = idField.getText();
            // Perform login logic
            Response res = sf.userService.login(id);
            if (res.isError()){
                JOptionPane.showMessageDialog(null, "Login Failed: " + res.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                continueToFrame(res,sf);
            }

        } else {
            // Cancel button clicked or dialog closed
        }
//        if (frameChoice == 0) {
//            // Open ManagerFrame
//            // Create an instance of ManagerFrame and activate it
//            run(new ManagerFrame(sf));
//        } else if (frameChoice == 1) {
//            // Open StockFrame
//            run(new StockFrame(stockUI , supplierManager , sf));
//        }

    }

    private static void loadData(ServiceFactory sf) {
        sf.userService.loadData();
        sf.inventoryService.loadData();
        ///TODO ask goz what need to be loaded
        sf.orderService.loadOrders();
    }

    private static void continueToFrame(Response res,ServiceFactory sf) {
        if (((String) res.getValue().toString()).equals("WareHouse"))
            run(new StockFrame(sf));
        else if(((String) res.getValue().toString()).equals("Suppliers"))
            run(new ManagerFrame(sf));
//        else
//            run(new SupplierFrame(sf));
    }



    private static void run(JFrame curFrame) {
        curFrame.setVisible(true);
    }
}

