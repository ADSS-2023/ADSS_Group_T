package PresentationLayer.GUI.SupplierGUI;
import PresentationLayer.GUI.SupplierGUI.SupplierFrame;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierProductFrame extends JFrame {

    private ServiceFactory sf;
    private int supplierNum;
    private String supplierName;
    private int productNum;

    // Swing components
    private JLabel lblSupplierNum;
    private JLabel lblProductName;
    private JLabel lblProductNum;
    private JLabel lblManufacturer;
    private JLabel lblPrice;
    private JLabel lblMaxAmount;
    private JLabel lblExpiryDate;
    private JTable tblProductDiscounts;
    private JButton btnBack;
    private JButton btnDeleteProduct;
    private JButton btnEditProduct;
    private JButton btnAddDiscount;
    private JButton btnDeleteDiscount;
    private JButton btnEditDiscount;

    public SupplierProductFrame(ServiceFactory sf, int supplierNum, int productNum, String supplierName) {
        this.sf = sf;
        this.supplierNum = supplierNum;
        this.supplierName = supplierName;
        this.productNum = productNum;
        initializeComponents();
        setupLayout();
        setupListeners();
        loadProductDetails();
        loadProductDiscounts();
    }

    private void initializeComponents() {
        lblSupplierNum = new JLabel("Supplier Number: " + supplierNum);
        lblProductName = new JLabel();
        lblProductNum = new JLabel();
        lblManufacturer = new JLabel();
        lblPrice = new JLabel();
        lblMaxAmount = new JLabel();
        lblExpiryDate = new JLabel();
        tblProductDiscounts = new JTable();
        btnBack = new JButton("Back");
        btnDeleteProduct = new JButton("Delete");
        btnEditProduct = new JButton("Edit");
        btnAddDiscount = new JButton("Add Discount");
        btnDeleteDiscount = new JButton("Delete Discount");
        btnEditDiscount = new JButton("Edit Discount");
    }

    private void setupLayout() {
        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Create panel for product details
        JPanel pnlProductDetails = new JPanel();
        pnlProductDetails.setLayout(new GridLayout(7, 2));
        pnlProductDetails.add(new JLabel("Product Name:"));
        pnlProductDetails.add(lblProductName);
        pnlProductDetails.add(new JLabel("Product Number:"));
        pnlProductDetails.add(lblProductNum);
        pnlProductDetails.add(new JLabel("Manufacturer:"));
        pnlProductDetails.add(lblManufacturer);
        pnlProductDetails.add(new JLabel("Price:"));
        pnlProductDetails.add(lblPrice);
        pnlProductDetails.add(new JLabel("Max Amount:"));
        pnlProductDetails.add(lblMaxAmount);
        pnlProductDetails.add(new JLabel("Expiry Date:"));
        pnlProductDetails.add(lblExpiryDate);

        // Create panel for buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnBack);
        pnlButtons.add(btnDeleteProduct);
        pnlButtons.add(btnEditProduct);
        pnlButtons.add(btnAddDiscount);
        pnlButtons.add(btnDeleteDiscount);
        pnlButtons.add(btnEditDiscount);

        // Add panels to the frame
        add(lblSupplierNum, BorderLayout.NORTH);
        add(pnlProductDetails, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Supplier Product");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupListeners() {
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SupplierFrame(sf, supplierNum, supplierName);
            }
        });

        btnDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform delete product operation
                // Add your logic here
            }
        });

        btnEditProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform edit product operation
                // Add your logic here
            }
        });

        btnAddDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform add discount operation
                // Add your logic here
            }
        });

        btnDeleteDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform delete discount operation
                // Add your logic here
            }
        });

        btnEditDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform edit discount operation
                // Add your logic here
            }
        });
    }

    private void loadProductDetails() {
        // Load product details based on supplierNum and productNum
        // Add your logic here

        // Set the loaded details in the labels
        lblProductName.setText("Product Name: ...");
        lblProductNum.setText("Product Number: ...");
        lblManufacturer.setText("Manufacturer: ...");
        lblPrice.setText("Price: ...");
        lblMaxAmount.setText("Max Amount: ...");
        lblExpiryDate.setText("Expiry Date: ...");
    }

    private void loadProductDiscounts() {
        // Load product discounts based on supplierNum and productNum
        // Add your logic here

        // Set the loaded discounts in the table
        // You can use a TableModel to populate the table
        // For simplicity, let's assume the table has two columns: Discount Name and Discount Percentage
        String[] columnNames = {"Discount Name", "Discount Percentage"};
        Object[][] data = {
                {"Discount 1", "10%"},
                {"Discount 2", "15%"},
                {"Discount 3", "20%"}
        };
        tblProductDiscounts.setModel(new DefaultTableModel(data, columnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplierProductFrame(new ServiceFactory(), 2, 456, "Sapak2");
            }
        });
    }
}
