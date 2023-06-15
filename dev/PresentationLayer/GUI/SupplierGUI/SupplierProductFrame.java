package PresentationLayer.GUI.SupplierGUI;

import BusinessLayer.Stock.Util.Util;
import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import PresentationLayer.GUI.SupplierGUI.SupplierFrame;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SupplierProductFrame extends JFrame {

    private ServiceFactory sf;
    private int supplierNum;
    private int productNum;
    private String supplierName;
    private String productName;
    private String manufacturer;
    private float price;
    private int maxAmount;
    private String expiryDate;

    // Swing components
    private JLabel lblSupplierNum;
    private JLabel lblProductName;
    private JLabel lblProductNum;
    private JLabel lblManufacturer;
    private JLabel lblProductPrice;
    private JLabel lblProductMaxAmount;
    private JLabel lblProductExpiryDate;

    private JTable tblProductDiscounts;
    private JButton btnBack;
    private JButton btnDeleteProduct;
    private JButton btnEditProduct;
    private JButton btnAddDiscount;
    private JButton btnDeleteDiscount;
    private JButton btnEditDiscount;

    public SupplierProductFrame(ServiceFactory sf, int supplierNum,
                                String supplierName, int productNum,
                                String productName, String manufacturer,
                                float price,int maxAmount, String expiryDate) {
        this.sf = sf;
        this.supplierNum = supplierNum;
        this.productNum = productNum;
        this.supplierName = supplierName;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.price=price;
        this.maxAmount=maxAmount;
        this.expiryDate=expiryDate;

        initializeComponents();
        setupLayout();
        setupListeners();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {


        String[] columnNames = {"Amount", "Discount", "is a Percent discount"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table with the model
        tblProductDiscounts = new JTable(tableModel);
        // Create a custom cell renderer to center the cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tblProductDiscounts.getColumnCount(); i++) {
            tblProductDiscounts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        Response res = sf.supplierService.getProductDiscounts(supplierNum, productNum);
        if (res.isError())
            JOptionPane.showMessageDialog(this, res.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        else {
            List<String> discounts = (List<String>) res.getValue();
            updateTable(discounts);

            btnBack = new JButton("Back");
            btnDeleteProduct = new JButton("Delete Product");
            btnEditProduct = new JButton("Edit Product");
            btnAddDiscount = new JButton("Add Discount");
            btnDeleteDiscount = new JButton("Delete Discount");
            btnEditDiscount = new JButton("Edit Discount");
        }
    }

    private void updateTable(List<String> discounts) {
        DefaultTableModel tableModel = (DefaultTableModel) tblProductDiscounts.getModel();

        // Clear existing data
        tableModel.setRowCount(0);

        // Add new data to the table model
        for (String discount : discounts) {
            tableModel.addRow(extractValues(discount));
        }
    }

    private void setupLayout() {
        // Set layout manager for the frame
        setLayout(new BorderLayout());

        lblSupplierNum = new JLabel("Supplier Number: " + supplierNum);
        lblProductName = new JLabel("Product Name: " + productName);
        lblProductNum = new JLabel("Product Number: " + productNum);
        lblManufacturer = new JLabel("Manufacturer: " + manufacturer);
        lblProductPrice = new JLabel("Price: " + price);
        lblProductMaxAmount = new JLabel("Max Amount: " + maxAmount);
        lblProductExpiryDate = new JLabel("Expiry Date: " + expiryDate);


        JPanel pnlProductDetails = new JPanel();
        pnlProductDetails.setLayout(new GridLayout(7, 2));
        pnlProductDetails.add(lblProductName);
        pnlProductDetails.add(lblProductNum);
        pnlProductDetails.add(lblManufacturer);
        pnlProductDetails.add(lblSupplierNum);
        pnlProductDetails.add(lblProductPrice);
        pnlProductDetails.add(lblProductExpiryDate);
        pnlProductDetails.add(lblProductMaxAmount);

        // Create panel for buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnBack);
        pnlButtons.add(btnDeleteProduct);
        pnlButtons.add(btnEditProduct);
        pnlButtons.add(btnAddDiscount);
        pnlButtons.add(btnDeleteDiscount);
        pnlButtons.add(btnEditDiscount);

        // Add panels to the frame

        add(pnlButtons, BorderLayout.SOUTH);
        add(pnlProductDetails, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tblProductDiscounts);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

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
                Response res =sf.supplierService.deleteProduct(supplierNum,productNum);
                if(res.isError())
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, res.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, res.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new SupplierFrame(sf, supplierNum, supplierName);
            }
        });

        btnEditProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expiryDate = JOptionPane.showInputDialog("Enter the new expiryDate (yyyy-MM-dd):");
                String priceSTR = JOptionPane.showInputDialog("Enter the new price:");
                float price;
                try {
                    price = Float.parseFloat(priceSTR);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid price number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String maxAmountSTR = JOptionPane.showInputDialog("Enter the new max Amount:");
                int maxAmount;
                try {
                    maxAmount = Integer.parseInt(maxAmountSTR);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid max amount number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate date = Util.stringToDate(expiryDate);
                Response response = sf.supplierService.editProduct(supplierNum, productName,manufacturer,price,maxAmount,date);
                if(response.isError())
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        btnAddDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productAmountStr = JOptionPane.showInputDialog(SupplierProductFrame.this, "Enter the amount of products/price discount:");
                int productAmount;
                try {
                    productAmount = Integer.parseInt(productAmountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String discountStr = JOptionPane.showInputDialog(SupplierProductFrame.this, "Enter the discount for that amount:");
                int discount;
                try {
                    discount = Integer.parseInt(discountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid discount for that amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String isPercentageStr = JOptionPane.showInputDialog(SupplierProductFrame.this, "Is the discount by percentage or by shekels?\n1. Percentage\n2. Shekels");
                int isPercentage;
                try {
                    isPercentage = Integer.parseInt(isPercentageStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean isPercentageBool = (isPercentage == 1);
                Response response = sf.supplierService.addProductDiscount(supplierNum, productNum, productAmount, discount, isPercentageBool);
                if (response.isError()) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        btnDeleteDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String productAmountStr = JOptionPane.showInputDialog("Enter the amount of products of the discount:");
                int productAmount;
                try {
                    productAmount = Integer.parseInt(productAmountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid amount number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String productDiscountStr = JOptionPane.showInputDialog("Enter the discount rate:");
                float productDiscount;
                try {
                    productDiscount = Float.parseFloat(productDiscountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String isPercentageStr = JOptionPane.showInputDialog("Is the discount is by percentage or by shekels? press 1 to Percetage, press 2 to Shekels:");
                int isPercentage;
                try {
                    isPercentage = Integer.parseInt(isPercentageStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean isPercentageBool = isPercentage == 1;
                Response response = sf.supplierService.deleteProductDiscount(supplierNum, productNum, productAmount,productDiscount, isPercentageBool);
                if (response.isError())
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnEditDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String productAmountStr = JOptionPane.showInputDialog("Enter the amount discount to be edited:");
                int productAmount;
                try {
                    productAmount = Integer.parseInt(productAmountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String discountStr = JOptionPane.showInputDialog("Enter the new discount for that amount:");
                float discount;
                try {
                    discount = Float.parseFloat(discountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid discount for that amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String isPercentageStr = JOptionPane.showInputDialog("Is the discount is by percentage or by shekels? press 1 to Percetage, press 2 to Shekels:");
                int isPercentage;
                try {
                    isPercentage = Integer.parseInt(isPercentageStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean isPercentageBool = isPercentage == 1;
                Response response = sf.supplierService.editProductDiscount(supplierNum,productNum,productAmount,discount,isPercentageBool);
                if (response.isError())
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(SupplierProductFrame.this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    public static String[] extractValues(String input) {
        String[] values = new String[3];

        int[] fieldIndexes = new int[3];
        String[] fieldNames = {"Amount:", "Discount:", "is a Percent discount:"};

        for (int i = 0; i < fieldIndexes.length; i++) {
            fieldIndexes[i] = input.indexOf(fieldNames[i]);
        }

        for (int i = 0; i < fieldIndexes.length; i++) {
            int fieldStartIndex = fieldIndexes[i] + fieldNames[i].length();
            int fieldEndIndex = (i < fieldIndexes.length - 1) ? fieldIndexes[i + 1] : input.length();
            values[i] = input.substring(fieldStartIndex, fieldEndIndex).replace(",", "").trim();
        }

        return values;
    }
}
