package PresentationLayer.GUI.SupplierGUI;

import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static PresentationLayer.GUI.SupplierGUI.SupplierGUI.run;

public class AllSupplierFrame extends JFrame {

    private ServiceFactory sf;
    private SupplierManager supplierManager;
    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;
    private JTable supplierTable;
    private DefaultTableModel tableModel;





    public AllSupplierFrame(SupplierManager supplierManager, ServiceFactory sf) {
        this.sf=sf;
        this.supplierManager=supplierManager;
        // Set up the frame properties
        setTitle("All Suppliers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the table
        initializeTable();

        int width = 1500; // Adjust the width as needed
        int height = 1000; // Adjust the height as needed
        setSize(width, height);
        // Create a panel for the buttons
        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1)); // Adjust the number of rows as needed

        // Create the buttons and add them to the button panel
        createOrderButton(buttonPanel);

        // Create a panel for the table and additional button
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Set the preferred size of the table
        supplierTable.setPreferredScrollableViewportSize(new Dimension(300, 200)); // Adjust the dimensions as needed

        // Add the table to the table panel
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create the additional button
        createAddSupplierButton(tablePanel);

        // Add the button panel to the left side (WEST) of the frame
        add(buttonPanel, BorderLayout.WEST);

        // Add the table panel to the right side (CENTER) of the frame
        add(tablePanel, BorderLayout.CENTER);

        pack();

    }

    private void initializeTable() {
        // Create column names
        String[] columnNames = {"Supplier Name", "Address", "Supplier Number", "Bank Account Number", "Payment Terms"};

        // Create table model with the column names
        tableModel = new DefaultTableModel(columnNames, 0);

        // Retrieve the list of suppliers from the business layer
        List<String> suppliersData = sf.supplierService.getSuppliers();
        List<String> suppliersNumber = new ArrayList<>();
        // Add each supplier as a row in the table
        for (String supplierData : suppliersData) {
            // Use regular expressions to extract supplier information
            String supplierName = extractValueForKey(supplierData, "Supplier Name");
            String address = extractValueForKey(supplierData, "Address");
            String supplierNumber = extractValueForKey(supplierData, "Supplier Number");
            String bankAccountNumber = extractValueForKey(supplierData, "Bank Account Number");
            String paymentTerms = extractValueForKey(supplierData, "Payment Terms").split("}")[0];
            suppliersNumber.add(supplierNumber);
            Object[] rowData = {supplierName, address, supplierNumber, bankAccountNumber, paymentTerms};
            tableModel.addRow(rowData);
        }

        // Create the JTable with the populated model
        supplierTable = new JTable(tableModel);

        // Create a custom cell renderer to center the cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set the custom cell renderer for all columns
        for (int i = 0; i < supplierTable.getColumnCount(); i++) {
            supplierTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        supplierTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = supplierTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected supplier from the table model

                    String selectedSupplier = suppliersNumber.get(selectedRow);

                    // Open the SupplierFrame and pass the selected supplier
                    SupplierFrame supplierFrame = new SupplierFrame(selectedSupplier);
                    supplierFrame.setVisible(true);
                }
            }
        });
    }
    private String extractValueForKey(String supplierData, String key) {
        if (key.equals("Supplier Name") || key.equals("Address")) {
            return extractValueForKeyWithoutDelimiter(supplierData, key);
        } else {
            return extractValueForKeyWithDelimiter(supplierData, key);
        }
    }

    private String extractValueForKeyWithoutDelimiter(String supplierData, String key) {
        int startIndex = supplierData.indexOf(key);
        if (startIndex != -1) {
            startIndex += key.length() + 1;  // Move the index after the key and the colon
            int endIndex = supplierData.indexOf("'", startIndex);
            if (endIndex != -1) {
                return supplierData.substring(startIndex, endIndex).trim();
            }
        }
        return "";
    }

    private String extractValueForKeyWithDelimiter(String supplierData, String key) {
        String[] keyValuePairs = supplierData.split(",\\s*");
        for (String pair : keyValuePairs) {
            String[] parts = pair.split(":\\s*");
            if (parts.length == 2) {
                String currentKey = parts[0].trim();
                String value = parts[1].trim();
                if (currentKey.equals(key)) {
                    return value;
                }
            }
        }
        return "";
    }

    private void createOrderButton(JPanel buttonPanel) {
        JButton ordersView = new JButton("View All orders");
        ordersView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    dispose();
                    run(new OrdersFrame(sf,supplierManager));
            }
        });
        buttonPanel.add(ordersView);

    }
    private void createAddSupplierButton(JPanel buttonPanel) {
        JButton addSupplier = new JButton("Add Supplier");
        addSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                run(new AddSupplierProcess(sf,supplierManager));
            }
        });
        addSupplier.setPreferredSize(new Dimension(100, 40)); // Adjust the dimensions as needed
        buttonPanel.add(addSupplier, BorderLayout.SOUTH);
    }
}