package PresentationLayer.GUI.Components;

import PresentationLayer.GUI.SupplierGUI.AllSupplierFrame;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class GeneralFrame extends JFrame {
    private ServiceFactory sf;

    public GeneralFrame(ServiceFactory sf) {
        this.sf = sf;
        setTitle("Stock Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 3));
        setVisible(true);
    }

    public void run() {
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

    }

    private static void loadData(ServiceFactory sf) {
        try {
            sf.uss.loadDate();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"failed to load data");
        }

        sf.userService.loadData();
        sf.inventoryService.loadData();
        ///TODO ask goz what need to be loaded
        sf.orderService.loadOrders();
        sf.supplierService.loadSuppliers();
        sf.manageOrderService.loadData();

    }

    private static void continueToFrame(Response res,ServiceFactory sf) {
        if (((String) res.getValue().toString()).equals("WareHouse"))
            run(new StockFrame(sf));
        else if(((String) res.getValue().toString()).equals("Suppliers"))
            run(new AllSupplierFrame(sf));
        else
            run(new ManagerFrame(sf));
    }



    private static void run(JFrame curFrame) {
        curFrame.setVisible(true);

    }
}

