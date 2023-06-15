package PresentationLayer.GUI.SupplierGUI;

import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import ServiceLayer.Supplier_Stock.Response;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

//import static PresentationLayer.GUI.Components.StockGUI.run;

public class SupplierFrame extends JFrame {
    private ServiceFactory sf;
    private int supplierNumber;
    private String supplierName;

    private JTable productsTable;
    private JTable discountsTable;
    private DefaultTableModel productsTableModel;
    private DefaultTableModel discountsTableModel;
    private JButton btnViewProducts;
    private JButton btnViewDiscounts;
    private JButton btnEditSupplier;
    private JButton btnDeleteSupplier;
    private JButton btnAddProduct;
    private JButton btnEditDiscount;
    private JButton btnDeleteDiscount;
    private JButton btnBack;
    private JButton btnAddDiscount;



    public SupplierFrame(ServiceFactory sf, int supplierNumber, String supplierName) {
        this.sf = sf;
        this.supplierNumber = supplierNumber;
        this.supplierName = supplierName;
        sf.supplierService.loadSuppliers();
        setTitle("Supplier Name: " + supplierName + "    Supplier Number: " + supplierNumber);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1400, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());



//        JPanel headerPanel = new JPanel();
//        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        headerPanel.setLayout(new BorderLayout());
//
//        JLabel nameLabel = new JLabel("Supplier Name: " + supplierName + "   Supplier Number: " + supplierNumber);
//        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        headerPanel.add(nameLabel, BorderLayout.NORTH);
//        add(headerPanel);



        createHeader();
        createProductsTable();
        createDiscountsTable();
        createButtons();
        setVisible(true);
    }

    private void createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Supplier Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel supplierLabel = new JLabel("Supplier Number: " + supplierNumber);
        supplierLabel.setFont(new Font("Arial", Font.BOLD, 14));
        supplierLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(supplierLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void showSupplierProducts() {
        if (productsTable.isVisible()) {
            productsTable.setVisible(false);
            productsTableModel.setRowCount(0);
            productsTableModel.setColumnCount(0);
            btnViewProducts.setText("View Products");
        } else {
            productsTable.setVisible(true);
            btnViewProducts.setText("Hide Products");
            Response response = sf.supplierService.getProducts(supplierNumber);
            if (response.isError()) {
                JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> products = (List<String>) response.getValue();
                displayProducts(products);
            }
        }
    }

    private void showSupplierDiscounts() {
        if (discountsTable.isVisible()) {
            discountsTable.setVisible(false);
            discountsTableModel.setRowCount(0);
            discountsTableModel.setColumnCount(0);
            btnViewDiscounts.setText("View Discounts");
        } else {
            discountsTable.setVisible(true);
            btnViewDiscounts.setText("Hide Discounts");
            Response response = sf.supplierService.getSupplierDiscounts(supplierNumber);
            if (response.isError()) {
                JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> discounts = (List<String>) response.getValue();
                displayDiscounts(discounts);
            }
        }
    }

    private void createProductsTable() {
        productsTableModel = new DefaultTableModel();
        productsTable = new JTable(productsTableModel);



        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedSupplierNumber = productsTable.getValueAt(selectedRow, 1);
                    SupplierFrame.this.dispose();
                    SupplierProductFrame supplierFrame = new SupplierProductFrame(sf, supplierNumber, (Integer.parseInt(selectedSupplierNumber.toString())), supplierName);
                    supplierFrame.setVisible(true);
                }
            }
        });




        JScrollPane productsScrollPane = new JScrollPane(productsTable);
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Products");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        productsPanel.setBorder(titledBorder);
        productsTable.setPreferredScrollableViewportSize(new Dimension(600, 1000));
        productsPanel.add(productsScrollPane, BorderLayout.CENTER);
        add(productsPanel, BorderLayout.EAST);
    }


    private void createDiscountsTable() {
        discountsTableModel = new DefaultTableModel();
        discountsTable = new JTable(discountsTableModel);
        JScrollPane discountsScrollPane = new JScrollPane(discountsTable);
        JPanel discountsPanel = new JPanel();
        discountsPanel.setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Discounts");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        discountsPanel.setBorder(titledBorder);
        discountsTable.setPreferredScrollableViewportSize(new Dimension(600, 1000));
        discountsPanel.add(discountsScrollPane, BorderLayout.CENTER);
        add(discountsPanel, BorderLayout.WEST);
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        buttonPanel.add(btnBack);

        btnViewProducts = new JButton("View Products");
        btnViewProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSupplierProducts();
            }
        });
        buttonPanel.add(btnViewProducts);

        btnViewDiscounts = new JButton("View Discounts");
        btnViewDiscounts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSupplierDiscounts();
            }
        });
        buttonPanel.add(btnViewDiscounts);

        btnAddProduct = new JButton("Add Product");
        btnAddProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        buttonPanel.add(btnAddProduct);

        btnDeleteSupplier = new JButton("Delete Supplier");
        btnDeleteSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
            }
        });
        buttonPanel.add(btnDeleteSupplier);

        btnEditSupplier = new JButton("Edit Supplier");
        btnEditSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSupplier();
            }
        });
        buttonPanel.add(btnEditSupplier);

        btnAddDiscount = new JButton("Add Discount");
        btnAddDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDiscount();
            }
        });
        buttonPanel.add(btnAddDiscount);

        btnDeleteDiscount = new JButton("Delete Discount");
        btnDeleteDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteDiscount();
            }
        });
        buttonPanel.add(btnDeleteDiscount);

        btnEditDiscount = new JButton("Edit Discount");
        btnEditDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDiscount();
            }
        });
        buttonPanel.add(btnEditDiscount);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void back(){
        SupplierFrame.this.dispose();

//        AllSupplierFrame allSupplierFrame = new AllSupplierFrame(, sf);
//        allSupplierFrame.setVisible(true);
        //JOptionPane.showMessageDialog(this, response.getValue(), "Delete Supplier", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayProducts(List<String> products) {
        productsTableModel.setColumnCount(0);
        productsTableModel.setRowCount(0);

        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Add column headers
        productsTableModel.addColumn("Product Name");
        productsTableModel.addColumn("Product Number");
        productsTableModel.addColumn("Manufacturer");
        productsTableModel.addColumn("Price");
        productsTableModel.addColumn("Max Amount");
        productsTableModel.addColumn("Expiry date");


        for (String product : products) {
            String[] productData = product.split(",");
            String[] modifiedProductData = new String[productData.length - 1]; // Exclude the Supplier Number column
            int modifiedIndex = 0;

            for (int i = 0; i < productData.length; i++) {
                String[] columnData = productData[i].split(":");
                if (columnData.length == 2) {
                    if (columnData[0].trim().equals("Supplier Number")) {
                        continue; // Skip adding the Supplier Number column
                    }
                    modifiedProductData[modifiedIndex] = columnData[1].trim(); // Get the value after the colon
                    modifiedIndex++;
                }
            }
            productsTableModel.addRow(modifiedProductData);
        }
    }

    private void editSupplier() {
        String address = JOptionPane.showInputDialog("Enter the new supplier address:");
        String bankAccountStr = JOptionPane.showInputDialog("Enter the new supplier bank account number:");
        int bankAccount;
        try {
            bankAccount = Integer.parseInt(bankAccountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bank account number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selfDeliveryChoiceStr = JOptionPane.showInputDialog("Is the supplier delivering by themselves?\nEnter 1 for Yes, 2 for No:");
        int selfDeliveryChoice;
        try {
            selfDeliveryChoice = Integer.parseInt(selfDeliveryChoiceStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for self delivery.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean selfDelivery = selfDeliveryChoice == 1;

        String paymentTermsChoiceStr = JOptionPane.showInputDialog("What is the payment terms of the supplier?\n" +
                "1. Shotef+30\n" +
                "2. Shotef+45\n" +
                "3. Shotef+60\n" +
                "4. Shotef+90\n");
        int paymentChoose;
        try {
            paymentChoose = Integer.parseInt(paymentTermsChoiceStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for payment terms.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PaymentTerms paymentTerms;
        switch (paymentChoose) {
            case 1:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_30;
                break;
            case 2:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_45;
                break;
            case 3:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_60;
                break;
            case 4:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_90;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid choice for payment terms.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        Response response = sf.supplierService.editSupplier(supplierName, address, supplierNumber, bankAccount, selfDelivery, paymentTerms);
        if(response.isError())
            JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteSupplier(){
        Response response = sf.supplierService.deleteSupplier(supplierNumber);
        if (response.isError())
            JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
        else {
            JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
            SupplierFrame.this.dispose();
        }

        //AllSupplierFrame allSupplierFrame = new AllSupplierFrame(supplierManager, sf);
        //allSupplierFrame.setVisible(true);
        //JOptionPane.showMessageDialog(this, response.getValue(), "Delete Supplier", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addDiscount(){
        String discountEnumNumStr = JOptionPane.showInputDialog("Enter the type of the discount: press 1 to Total products purchased, press 2 to Total Price discount:");
        int discountEnumNum;
        try {
            discountEnumNum = Integer.parseInt(discountEnumNumStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for kind of the discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        Discounts discountEnum;
        if (discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;

        String productAmountStr = JOptionPane.showInputDialog("Enter the amount of products/price discount:");
        int productAmount;
        try {
            productAmount = Integer.parseInt(productAmountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String discountStr = JOptionPane.showInputDialog("Enter the discount for that amount:");
        int discount;
        try {
            discount = Integer.parseInt(discountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid discount for that amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isPercentageStr = JOptionPane.showInputDialog("Is the discount is by percentage or by shekels? press 1 to Percetage, press 2 to Shekels:");
        int isPercentage;
        try {
            isPercentage = Integer.parseInt(isPercentageStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isPercentageBool = isPercentage == 1;
        Response response = sf.supplierService.addSupplierDiscount(supplierNumber, discountEnum, productAmount, discount, isPercentageBool);
        if(response.isError())
            JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteDiscount(){
        String discountEnumNumStr = JOptionPane.showInputDialog("Enter the type of the discount: press 1 to Total products purchased, press 2 to Total Price discount:");
        int discountEnumNum;
        try {
            discountEnumNum = Integer.parseInt(discountEnumNumStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for kind of the discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        Discounts discountEnum;
        if (discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;

        String productAmountStr = JOptionPane.showInputDialog("Enter the amount of products/price discount to be deleted:");
        int productAmount;
        try {
            productAmount = Integer.parseInt(productAmountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isPercentageStr = JOptionPane.showInputDialog("Is the discount is by percentage or by shekels? press 1 to Percetage, press 2 to Shekels:");
        int isPercentage;
        try {
            isPercentage = Integer.parseInt(isPercentageStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isPercentageBool = isPercentage == 1;
        Response response = sf.supplierService.deleteSupplierDiscount(supplierNumber, discountEnum, productAmount, isPercentageBool);
        if (response.isError())
            JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editDiscount(){

        String discountEnumNumStr = JOptionPane.showInputDialog("Enter the type of the discount: press 1 to Total products purchased, press 2 to Total Price discount:");
        int discountEnumNum;
        try {
            discountEnumNum = Integer.parseInt(discountEnumNumStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for kind of the discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        Discounts discountEnum;
        if (discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;

        String productAmountStr = JOptionPane.showInputDialog("Enter the amount of products/price discount to be edited:");
        int productAmount;
        try {
            productAmount = Integer.parseInt(productAmountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount of products/price discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String discountStr = JOptionPane.showInputDialog("Enter the discount for that amount:");
        int discount;
        try {
            discount = Integer.parseInt(discountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid discount for that amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isPercentageStr = JOptionPane.showInputDialog("Is the discount is by percentage or by shekels? press 1 to Percetage, press 2 to Shekels:");
        int isPercentage;
        try {
            isPercentage = Integer.parseInt(isPercentageStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid choice for is Percentage.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isPercentageBool = isPercentage == 1;
        Response response = sf.supplierService.editSupplierDiscount(supplierNumber, discountEnum, productAmount, discount, isPercentageBool);
        if (response.isError())
            JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayDiscounts(List<String> discounts) {
        discountsTableModel.setColumnCount(0);
        discountsTableModel.setRowCount(0);

        if (discounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No discounts found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Add column headers
        discountsTableModel.addColumn("Amount");
        discountsTableModel.addColumn("Discount");
        discountsTableModel.addColumn("Percentage discount?");
        discountsTableModel.addColumn("Total Price/Total Quantity");
        boolean totalQuantity = false;
        for (String discount : discounts) {
            if (discount.toLowerCase().contains("Total Quantity Discounts".toLowerCase()))
                totalQuantity = true;
            String[] discountData = discount.split(",");
            String[] modifiedDiscountData = new String[discountData.length+1];
            boolean hasMissingDiscount = false;

            for (int i = 0; i < discountData.length; i++) {
                String[] columnData = discountData[i].split(":");
                if (columnData.length == 2) {
                    modifiedDiscountData[i] = columnData[1].trim(); // Get the value after the colon
                } else {
                    hasMissingDiscount = true;
                    break; // Skip adding the row if there is a missing discount
                }
            }
            if (hasMissingDiscount) {
                continue; // Skip this iteration and move to the next discount
            }
            if(totalQuantity)
                modifiedDiscountData[3] = "Total Quantity";
            else
                modifiedDiscountData[3] = "Total Price";

            discountsTableModel.addRow(modifiedDiscountData);
        }
    }

    private void addProduct() {
        String productNumberStr = JOptionPane.showInputDialog("Enter product number:");
        String productName = JOptionPane.showInputDialog("Enter product name:");
        String manufacturer = JOptionPane.showInputDialog("Enter manufacturer:");
        String priceStr = JOptionPane.showInputDialog("Enter price:");
        String maxAmountStr = JOptionPane.showInputDialog("Enter max amount:");
        String expiryDateStr = JOptionPane.showInputDialog("Enter expiry date (YYYY-MM-DD):");


        try {
            float price = Float.parseFloat(priceStr);
            int maxAmount = Integer.parseInt(maxAmountStr);
            LocalDate expiryDate = LocalDate.parse(expiryDateStr);
            int productNumber = Integer.parseInt(productNumberStr);

            Response response = sf.supplierService.addProduct(supplierNumber, productNumber, productName, manufacturer, price, maxAmount, expiryDate);
            if (response.isError()) {
                JOptionPane.showMessageDialog(this, response.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, response.getValue(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price or max amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid expiry date.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void run(JFrame curFrame) {
        curFrame.setVisible(true);
    }

    public static void main(String[] args) {
        run(new SupplierFrame(new ServiceFactory(), 2, "Sapak2"));
    }
}


