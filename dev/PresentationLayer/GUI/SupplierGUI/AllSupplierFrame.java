package PresentationLayer.GUI.SupplierGUI;

import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
        setTitle("Supplier List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the table
        initializeTable();

        // Add the table to the frame
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        getContentPane().add(scrollPane);

        pack();
    }

    private void initializeTable() {
        // Create column names
        String[] columnNames = {"Supplier Name", "Address", "Contact"};

        // Create table model with the column names
        tableModel = new DefaultTableModel(columnNames, 0);

        // Retrieve the list of suppliers from the business layer
        List<String> suppliers = sf.supplierService.getSuppliers();

        // Add each supplier as a row in the table
        for (String s : suppliers) {
            Object[] rowData = {s};
            tableModel.addRow(rowData);
        }

        // Create the JTable with the populated model
        supplierTable = new JTable(tableModel);
        supplierTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = supplierTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected supplier from the table model
                    String selectedSupplier = suppliers.get(selectedRow);

                    // Open the SupplierFrame and pass the selected supplier
                    SupplierFrame supplierFrame = new SupplierFrame(selectedSupplier);
                    supplierFrame.setVisible(true);
                }
            }
        });
    }

}
