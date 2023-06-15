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
import java.util.ArrayList;
import java.util.List;

import static PresentationLayer.GUI.SupplierGUI.SupplierGUI.run;

public class OrdersFrame extends JFrame {
    private JTable orderTable;
    private ServiceFactory sf;

    public OrdersFrame(ServiceFactory sf) {
        setTitle("Orders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.sf=sf;
        // Create a table model with column names and empty data
        String[] columnNames = {"Order Number", "Supplier Number", "Order Date","Destination Address","Contact Name","Contact Number"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table with the model
        orderTable = new JTable(tableModel);
// Create a custom cell renderer to center the cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set the custom cell renderer for all columns
        for (int i = 0; i < orderTable.getColumnCount(); i++) {
            orderTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        List<String> orders = getOrdersFromBusinessLayer();
        updateTable(orders);


        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1)); // Adjust the number of rows as needed

        // Create the buttons and add them to the button panel
        createOrderButton(buttonPanel);

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected supplier from the table model

                    // Open the SupplierFrame and pass the selected supplier
                    dispose();
                    OrderProductsFrame orderProductsFrame = new OrderProductsFrame(sf,Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString()));
                    orderProductsFrame.setVisible(true);
                }
            }
        });




        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(orderTable);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Fetch the orders from the business layer and update the table

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<String> getOrdersFromBusinessLayer() {
       Response res=sf.orderService.getOrders();
       if(res.isError()){
           JOptionPane.showMessageDialog(this, res.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            run(new OrdersFrame(sf));
           return null;
       }
       else{
           return (List<String>)res.getValue();
       }
    }

    private void updateTable(List<String> orders) {
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();

        // Clear existing data
        tableModel.setRowCount(0);

        // Add new data to the table model
        for (String order : orders) {
            tableModel.addRow(extractValues(order));
        }
    }

    public static String[] extractValues(String input) {
        String[] values = new String[7];

        int[] fieldIndexes = new int[7];
        String[] fieldNames = {"Order Number:", "Supplier Number:", "Order Date:", "Destination Address:",
                "Contact Name:", "Contact Number:", "Products:"};

        for (int i = 0; i < fieldIndexes.length; i++) {
            fieldIndexes[i] = input.indexOf(fieldNames[i]);
        }

        for (int i = 0; i < fieldIndexes.length; i++) {
            int fieldStartIndex = fieldIndexes[i] + fieldNames[i].length();
            int fieldEndIndex = (i < fieldIndexes.length - 1) ? fieldIndexes[i + 1] : input.length();
            values[i] = input.substring(fieldStartIndex, fieldEndIndex).replace("|", "").trim();
        }

        return values;
    }

    private void createOrderButton(JPanel buttonPanel) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                run(new AllSupplierFrame(sf));
            }
        });
        buttonPanel.add(backButton);
    }
}
