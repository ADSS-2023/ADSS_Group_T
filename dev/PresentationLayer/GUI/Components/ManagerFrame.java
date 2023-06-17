package PresentationLayer.GUI.Components;

import BusinessLayer.Stock.Util.Util;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class ManagerFrame extends JFrame {
    private ServiceFactory sf;
    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;
    private JLabel messageField;


    public ManagerFrame(ServiceFactory sf) {
        this.sf = sf;

        setTitle("Manager window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        //TODO: split the error and the string value
        createToolbar();
        createEmptyBoxPanel();
        createErrorOkMessages();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createErrorOkMessages() {
        messageField = new JLabel("Welcome to inventory system");
        Font currentFont = messageField.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() + 2f);
        messageField.setFont(newFont);
        add(messageField,BorderLayout.NORTH);
    }

    private void handleErrorOrOk(Response res) {
        if (res.isError())
            updateError(res.getErrorMassage());
        else
            updateOkMessage((String) res.getValue());
    }

    public void updateError(String msg){
        messageField.setText(msg);
        messageField.setForeground(Color.RED);
    }
    public void updateOkMessage(String msg){
        messageField.setText(msg);
        messageField.setForeground(Color.GREEN);
    }

    private void createEmptyBoxPanel() {
        emptyBoxPanel = new JPanel(new GridLayout());
        emptyBoxPanel.setBackground(Color.WHITE);

        // Add labels and text fields to the panel


        add(emptyBoxPanel);
    }

    private void createToolbar() {
        JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
        toolbar.setFloatable(false);

        addButtonToToolbar(toolbar, "Generate Inventory Report", this::inventoryReport);
        addButtonToToolbar(toolbar, "Generate Damage Item Report", () -> {showDamageReportDialog(sf.damagedService.produce_damaged_report());});
        addButtonToToolbar(toolbar, "Generate Shortage Report", () -> {
            showShortageReportDialog(sf.inventoryService.produce_shortage_report());
        });
        addButtonToToolbar(toolbar, "Edit Regular Order", this::editRegularItemOrder);
        addButtonToToolbar(toolbar, "Show All Orders", this::show_all_orders);
        addButtonToToolbar(toolbar, "Create Regular Order - TESTING ONLY", this::createRegularOrder);
        addButtonToToolbar(toolbar, "Show New Items", this::showNewItems);

        add(toolbar, BorderLayout.WEST);
    }

    private void showNewItems() {
        emptyBoxPanel.removeAll(); // Clear the empty box panel

        try {
            Response newItemsData = sf.manageOrderService.show_new_items();
            if (newItemsData.isError()) throw new Exception(newItemsData.getErrorMassage());
            String newItems = (String) newItemsData.getValue();
            newItems = newItems.replace( "\u001B[31m", "ATTENTION : ");
            newItems = newItems.replace( "\u001B[0m", "");
            JTextArea textArea = new JTextArea(newItems);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            emptyBoxPanel.add(scrollPane); // Add the scroll pane to the empty box panel
            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            updateOkMessage("Items viewed");
        } catch (Exception exp) {
            updateError(exp.getMessage());
        }
    }

    private void addButtonToToolbar(JToolBar toolbar, String label, Runnable action) {
        JButton button = new JButton("<html><center>" + label + "</center></html>");
        button.addActionListener(e -> action.run());
        button.setPreferredSize(new Dimension(250, 100));

        // Apply custom styling
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 16));

        toolbar.add(button);
    }

    private void showDamageReportDialog(Response damagedReportData) {
        emptyBoxPanel.removeAll(); // Clear the empty box panel
        try {
            if (damagedReportData.isError())
                throw new Exception(damagedReportData.getErrorMassage());
            String damagedReport = (String) damagedReportData.getValue();
            String[] items = damagedReport.split(",,\n");

            StringBuilder formattedReport = new StringBuilder();
            for (String item : items) {
                formattedReport.append("------------------------------\n");
                String[] fields = item.split(" : ");
                for (String field : fields) {
                    formattedReport.append(field).append(": ");
                }
                formattedReport.append("\n");
            }

            JTextArea textArea = new JTextArea(formattedReport.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Clear the empty box and add the report
            emptyBoxPanel.setLayout(new BorderLayout());
            emptyBoxPanel.add(scrollPane, BorderLayout.CENTER);

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            updateOkMessage("Report exported");
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
        }
    }

    private void showShortageReportDialog(Response shortageReportData) {
        try {
            if (shortageReportData.isError())
                throw new Exception(shortageReportData.getErrorMassage());
            String shortageReport = (String) shortageReportData.getValue();
            if(shortageReport == "no shortage") throw new Exception("No shortage items");
            String[] items = shortageReport.split("-------------------------------------------");

            StringBuilder formattedReport = new StringBuilder();
            for (String item : items) {
                String[] lines = item.split("\n");
                if (lines.length >= 3) {
                    formattedReport.append(lines[0].trim()).append("\n");
                    formattedReport.append(lines[1].trim()).append("\n");
                    formattedReport.append(lines[2].trim()).append("\n");
                    formattedReport.append("-------------------------------------------\n");
                }
            }

            JTextArea textArea = new JTextArea(formattedReport.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Clear the empty box and add the report
            emptyBoxPanel.removeAll();
            emptyBoxPanel.setLayout(new BorderLayout());
            emptyBoxPanel.add(scrollPane, BorderLayout.CENTER);

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            updateOkMessage("Report exported");
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
        }
    }

    //TODO : fix when click on final category
    public String presentCategories() {
        try {
            Response dataResponse = sf.inventoryService.show_data();
            if (dataResponse.isError()) throw new Exception(dataResponse.getErrorMassage());
            String data = (String) dataResponse.getValue();
            String[] categories = data.split(", ");
            String[] options = new String[categories.length + 1];

            for (int i = 0; i < categories.length; i++) {
                String[] parts = categories[i].split(" : ");
                String index = parts[0].trim();
                String categoryName = parts[1].trim();
                options[i] = index + " : " + categoryName;
            }

            options[categories.length] = "Choose Current Category";

            String message = "Press Choose Current Category to select the current category";

            String nextIndex = "";
            boolean isActive = true;

            int choice = JOptionPane.showOptionDialog(
                    null,
                    message,
                    "Choose Category",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            while (isActive) {
                if (choice == JOptionPane.CLOSED_OPTION || choice == -1) {
                    isActive = false;
                    nextIndex = "exit";
                } else if (choice == categories.length) {
                    isActive = false;
                    return nextIndex;
                } else {
                    nextIndex += "." + (Integer.parseInt(options[choice].split(" : ")[0]) - 1);
                    try {
                        Response dataToShow = sf.categoryService.show_data(nextIndex);
                        if (dataResponse.isError()) throw new Exception(dataToShow.getErrorMassage());
                        String toShow = (String) dataToShow.getValue();
                        categories = toShow.split(", ");
                        options = new String[categories.length + 1];

                        for (int i = 0; i < categories.length; i++) {
                            String[] parts = categories[i].split(" : ");
                            String index = parts[0].trim();
                            String categoryName = parts[1].trim();
                            options[i] = index + " : " + categoryName;
                        }

                        options[categories.length] = "Choose Current Category";

                        choice = JOptionPane.showOptionDialog(
                                null,
                                message,
                                "Choose Category",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                options,
                                options[0]
                        );
                    } catch (Exception e) {
                        updateError(e.getMessage()); // Update the messageField with the error message
                        return "exit";
                    }
                }
            }
            return nextIndex;
        } catch (Exception e) {
            updateOkMessage(e.getMessage()); // Update the messageField with the error message
            return "exit";
        }
    }

    public void inventoryReport() {
        LinkedList<String> categories = new LinkedList<>();
        boolean isActive = true;

        try {
            while (isActive) {
                String result = presentCategories();
                if (result.equals("exit")) {
                    return;
                }
                categories.add(result);

                int choice = JOptionPane.showOptionDialog(null, "Would you like another category?", "Choose Option",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");

                if (choice == 1) {
                    isActive = false;
                }
            }

            if (categories.isEmpty()) {
                System.out.println("You didn't choose any category.");
            } else {
                Response inventoryReportData = sf.inventoryService.produce_inventory_report(categories);
                if (inventoryReportData.isError()) throw new Exception(inventoryReportData.getErrorMassage());
                String inventoryReport = (String) inventoryReportData.getValue();
                showInventoryReportDialog(inventoryReport);
            }
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
        }
    }

    private void showInventoryReportDialog(String inventoryReport) {
        try {
            emptyBoxPanel.removeAll(); // Clear the empty box panel
            JTextArea textArea = new JTextArea(formatInventoryReport(inventoryReport));
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Clear the empty box and add the report
            emptyBoxPanel.setLayout(new BorderLayout());
            emptyBoxPanel.add(scrollPane, BorderLayout.CENTER);

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            updateOkMessage("Report exported");
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
        }
    }

    private String formatInventoryReport(String inventoryReport) {
        try {
            StringBuilder formattedReport = new StringBuilder();

            String[] lines = inventoryReport.split("\n");
            for (String line : lines) {
                if (line.startsWith("Category")) {
                    formattedReport.append(line).append("\n\n");
                } else if (line.startsWith("\t")) {
                    formattedReport.append("\t").append(line.trim()).append("\n");
                } else {
                    formattedReport.append(line).append("\n");
                }
            }

            return formattedReport.toString();
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
            return ""; // Return an empty string if an exception occurs
        }
    }

    private void editRegularItemOrder() {
        emptyBoxPanel.removeAll(); // Clear the empty box panel

        try {
            JTextField dayField = new JTextField(10);
            JTextField idField = new JTextField(10);
            JTextField amountField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));
            panel.add(new JLabel("Day of the week:"));
            panel.add(dayField);
            panel.add(new JLabel("Product ID:"));
            panel.add(idField);
            panel.add(new JLabel("New amount:"));
            panel.add(amountField);

            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            panel.add(okButton);
            panel.add(cancelButton);

            emptyBoxPanel.add(panel); // Add the panel to the empty box panel

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            // Add ActionListener to the OK button
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Continue with the rest of the code when OK button is clicked
                    String day = dayField.getText();
                    int id = Integer.parseInt(idField.getText());
                    int amount = Integer.parseInt(amountField.getText());

                    try {
                        DayOfWeek cur_day = DayOfWeek.valueOf(day.toUpperCase());
                        Response itemDetailsData = sf.manageOrderService.presentItemsById(cur_day);
                        if (itemDetailsData.isError()) throw new Exception(itemDetailsData.getErrorMassage());
                        String itemDetails = (String) itemDetailsData.getValue();
                        if(itemDetails == "No items to present\n") throw new Exception("No items to this ID");
                        String message = "Item Details:\n" + itemDetails + "\n\nConfirm editing the order with the new amount: " + amount;

                        int confirmResult = JOptionPane.showConfirmDialog(null, message, "Confirm Edit", JOptionPane.YES_NO_OPTION);
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            Response editResultData = sf.manageOrderService.editRegularOrder(id, cur_day, amount);
                            if (editResultData.isError()) throw new Exception(editResultData.getErrorMassage());
                            String editResult = (String) editResultData.getValue();
                            updateOkMessage(editResult); // Update the messageField with the result
                        } else {
                            updateOkMessage("Edit operation canceled."); // Update the messageField
                        }
                    } catch (Exception ex) {
                        updateError(ex.getMessage()); // Update the messageField
                    }
                }
            });
        } catch (NumberFormatException e) {
            updateError("Invalid input. Please enter numeric values for ID and amount."); // Update the messageField
        }
    }

    private void show_all_orders() {
        emptyBoxPanel.removeAll(); // Clear the empty box panel

        try {
            Response allOrdersData = sf.manageOrderService.show_all_orders();
            if (allOrdersData.isError()) throw new Exception(allOrdersData.getErrorMassage());
            String allOrders = (String) allOrdersData.getValue();

            if (allOrders.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No orders available.", "All Orders", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            JTextArea textArea = new JTextArea(allOrders);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            textArea.setEditable(false);

            emptyBoxPanel.add(scrollPane); // Add the scroll pane to the empty box panel

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();

            updateOkMessage("Report exported");
        } catch (Exception e) {
            updateError(e.getMessage()); // Update the messageField with the error message
        }
    }

    //just for testing
    private void createRegularOrder() {
        emptyBoxPanel.removeAll(); // Clear the empty box panel

        JTextField idField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Insert item ID:"));
        panel.add(idField);
        panel.add(new JLabel("Insert amount desired:"));
        panel.add(amountField);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        panel.add(okButton);
        panel.add(cancelButton);

        emptyBoxPanel.add(panel); // Add the panel to the empty box panel
        emptyBoxPanel.revalidate();
        emptyBoxPanel.repaint();

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int amount = Integer.parseInt(amountField.getText());

                    HashMap<Integer, Integer> products = new HashMap<>();
                    products.put(id, amount);

                    int choice = JOptionPane.showConfirmDialog(null, "Add more products?", "Create Regular Order", JOptionPane.YES_NO_OPTION);
                    boolean isActive = choice == JOptionPane.YES_OPTION;

                    if (isActive) {
                        idField.setText(""); // Clear the ID field
                        amountField.setText(""); // Clear the amount field
                    } else {
                        try {
                            Response messageData = sf.manageOrderService.createRegularOrder(products);
                            if (messageData.isError()) throw new Exception(messageData.getErrorMassage());
                            String message = (String) messageData.getValue();

                            JTextArea textArea = new JTextArea(message);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new Dimension(400, 300));
                            textArea.setEditable(false);

                            emptyBoxPanel.removeAll(); // Clear the empty box panel
                            emptyBoxPanel.add(scrollPane); // Add the scroll pane to the empty box panel
                            emptyBoxPanel.revalidate();
                            emptyBoxPanel.repaint();

                            updateOkMessage(message);
                        } catch (Exception ex) {
                            updateError(ex.getMessage()); // Update the messageField with the error message
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format. Please enter numeric values for ID and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                emptyBoxPanel.removeAll(); // Clear the empty box panel
                emptyBoxPanel.revalidate();
                emptyBoxPanel.repaint();
            }
        });
    }
}



