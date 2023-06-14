package PresentationLayer.GUI.SupplierGUI;

import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static PresentationLayer.GUI.SupplierGUI.SupplierGUI.run;

public class OrderProductsFrame extends JFrame {
    private ServiceFactory sf;
    private SupplierManager supplierManager;
    private JTable productsTable;
    private DefaultTableModel tableModel;

    public OrderProductsFrame(ServiceFactory sf, int orderId)  {
        this.sf=sf;
        setTitle("Order Number: "+ orderId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1)); // Adjust the number of rows as needed
        add(buttonPanel,BorderLayout.SOUTH);
        // Create the buttons and add them to the button panel
        createOrderButton(buttonPanel);


        Response res= sf.orderService.getProductsByOrder(orderId);
        String[] columnNames = {"Product Name", "Product Number", "Quantity","Initial Price","Discount","Final Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table with the model
        productsTable = new JTable(tableModel);
// Create a custom cell renderer to center the cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set the custom cell renderer for all columns
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        List<String> products = getOrdersFromBusinessLayer(orderId);
        updateTable(products);

        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeTable() {
        tableModel = new DefaultTableModel();
        productsTable = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Number");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Initial Price");
        tableModel.addColumn("Discount");
        tableModel.addColumn("Final Price");
    }

    private void updateTable(List<String> orders) {
        DefaultTableModel tableModel = (DefaultTableModel) productsTable.getModel();

        // Clear existing data
        tableModel.setRowCount(0);

        // Add new data to the table model
        for (String order : orders) {
            tableModel.addRow(extractValues(order));
        }
    }

    private void createOrderButton(JPanel buttonPanel) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                run(new OrdersFrame(sf,supplierManager));
            }
        });
        buttonPanel.add(backButton);
    }
    private List<String> getOrdersFromBusinessLayer(int orderId) {
        Response res=sf.orderService.getProductsByOrder(orderId);
        if(res.isError()){
            return null;
        }
        else{
            return (List<String>)res.getValue();
        }
    }

    public static String[] extractValues(String input) {
        String[] values = new String[6];

        // Split the input string by the delimiter "|"
        String[] productData = input.split("\\|");

        // Iterate over the split values
        for (int i = 0; i < productData.length; i++) {
            // Split each value by the delimiter ":" to get the field name and value
            String[] fieldData = productData[i].split(":");

            // Extract the field name and value
            String fieldName = fieldData[0].trim();
            String fieldValue = fieldData[1].trim();

            // Assign the value to the corresponding index in the values array based on the field name
            switch (fieldName) {
                case "productName":
                    values[0] = fieldValue;
                    break;
                case "productNumber":
                    values[1] = fieldValue;
                    break;
                case "quantity":
                    values[2] = fieldValue;
                    break;
                case "initialPrice":
                    values[3] = fieldValue;
                    break;
                case "discount":
                    values[4] = fieldValue;
                    break;
                case "finalPrice":
                    values[5] = fieldValue;
                    break;
            }
        }

        return values;
    }
}
