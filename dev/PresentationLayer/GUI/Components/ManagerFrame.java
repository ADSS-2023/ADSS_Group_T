package PresentationLayer.GUI.Components;

import BusinessLayer.Stock.Util.Util;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
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

public class ManagerFrame extends JFrame {
    private StockUI stockUI;
    private ServiceFactory sf;
    private SupplierManager supplierManager;
    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;
    private JLabel messageField;

    public ManagerFrame(StockUI stockUI, SupplierManager supplierManager, ServiceFactory sf) {
        this.stockUI = stockUI;
        this.sf = sf;

        setTitle("Manager window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        //TODO : split the error and the string value
        createToolbar();
        createEmptyBoxPanel();
        createContentPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createLineBorder(Color.BLACK)));
        messagePanel.add(messageField);

        mainPanel.add(messagePanel, BorderLayout.NORTH);
        messageField.setText("Welcome to manager system");

        mainPanel.add(emptyBoxPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        messageField = new JLabel();
        topPanel.add(messageField, BorderLayout.NORTH);
        contentPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void createEmptyBoxPanel() {
        cardLayout = new CardLayout();
        emptyBoxPanel = new JPanel(cardLayout);
        emptyBoxPanel.setBackground(Color.WHITE);
        add(emptyBoxPanel, BorderLayout.CENTER);
    }

    private void createToolbar() {
        JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
        toolbar.setFloatable(false);

        addButtonToToolbar(toolbar, "Generate Inventory Report", this::inventoryReport);
        addButtonToToolbar(toolbar, "Generate Damage Item Report", () -> {
            String damagedReport = sf.damagedService.produce_damaged_report();
            showDamageReportDialog(damagedReport);
        });
        addButtonToToolbar(toolbar, "Generate Shortage Report", () -> {
            String shortageReport = sf.inventoryService.produce_shortage_report();
            showShortageReportDialog(shortageReport);
        });
        addButtonToToolbar(toolbar, "Edit Regular Order", this::editRegularItemOrder);
        addButtonToToolbar(toolbar, "Show All Orders", this::show_all_orders);
        addButtonToToolbar(toolbar, "Create Regular Order - TESTING ONLY", this::createRegularOrder);
        addButtonToToolbar(toolbar, "Show New Items", () -> {
            String newItems = sf.manageOrderService.show_new_items();
            JTextArea textArea = new JTextArea(newItems);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "New Items", JOptionPane.PLAIN_MESSAGE);
        });

        add(toolbar, BorderLayout.WEST);
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

    private void showDamageReportDialog(String damagedReport) {
        try {
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

            JOptionPane.showMessageDialog(null, scrollPane, "Damage Item Report", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }
    }

    private void showShortageReportDialog(String shortageReport) {
        try {
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

            JOptionPane.showMessageDialog(null, scrollPane, "Shortage Report", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }
    }

    //TODO : fix when click on final category
    public String presentCategories() {
        try {
            String data = sf.inventoryService.show_data();
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
                        String toShow = sf.categoryService.show_data(nextIndex);
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
                        messageField.setText(e.getMessage()); // Update the messageField with the error message
                        return "exit";
                    }
                }
            }

            return nextIndex;
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
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
                String inventoryReport = sf.inventoryService.produce_inventory_report(categories);
                showInventoryReportDialog(inventoryReport);
            }
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }
    }

    private void showInventoryReportDialog(String inventoryReport) {
        try {
            JTextArea textArea = new JTextArea(formatInventoryReport(inventoryReport));
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Inventory Report", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
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
            messageField.setText(e.getMessage()); // Update the messageField with the error message
            return ""; // Return an empty string if an exception occurs
        }
    }

    private void editRegularItemOrder() {
        try {
            JTextField dayField = new JTextField(10);
            JTextField idField = new JTextField(10);
            JTextField amountField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(new JLabel("Day of the week:"));
            panel.add(dayField);
            panel.add(new JLabel("Product ID:"));
            panel.add(idField);
            panel.add(new JLabel("New amount:"));
            panel.add(amountField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Regular Item Order", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String day = dayField.getText();
                int id = Integer.parseInt(idField.getText());
                int amount = Integer.parseInt(amountField.getText());

                try {
                    DayOfWeek cur_day = DayOfWeek.valueOf(day.toUpperCase());
                    String itemDetails = sf.manageOrderService.presentItemsById(cur_day);
                    String message = "Item Details:\n" + itemDetails + "\n\nConfirm editing the order with the new amount: " + amount;

                    int confirmResult = JOptionPane.showConfirmDialog(null, message, "Confirm Edit", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        String editResult = sf.manageOrderService.editRegularOrder(id, cur_day, amount);
                        messageField.setText(editResult); // Update the messageField with the result
                    } else {
                        messageField.setText("Edit operation canceled."); // Update the messageField
                    }
                } catch (IllegalArgumentException e) {
                    messageField.setText("Invalid day of the week. Please enter a valid day in capital letters."); // Update the messageField
                }
            } else {
                messageField.setText("Edit operation canceled."); // Update the messageField
            }
        } catch (NumberFormatException e) {
            messageField.setText("Invalid input. Please enter numeric values for ID and amount."); // Update the messageField
        }
    }

    private void show_all_orders() {
        try {
            String allOrders = sf.manageOrderService.show_all_orders();

            if (allOrders.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No orders available.", "All Orders", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            StringBuilder sb = new StringBuilder();

            for (String day : days) {
                sb.append("---------").append(day).append("---------\n");

                String regularOrders = sf.manageOrderService.presentItemsById(DayOfWeek.valueOf(day));
                if (regularOrders.isEmpty()) {
                    sb.append("Regular orders:\n");
                    sb.append("\tNo orders on this day\n");
                } else {
                    sb.append("Regular orders:\n");
                    sb.append(regularOrders);
                }

                String specialOrders = ""; // Replace with the actual special orders implementation
                if (specialOrders.isEmpty()) {
                    sb.append("Special orders:\n");
                    sb.append("\tNo orders on this day\n");
                } else {
                    sb.append("Special orders:\n");
                    sb.append(specialOrders);
                }

                sb.append("\n");
            }

            JTextArea textArea = new JTextArea(sb.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            textArea.setEditable(false);

            JOptionPane.showMessageDialog(null, scrollPane, "All Orders", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }
    }

    //just for testing
    private void createRegularOrder() {
        boolean isActive = true;
        HashMap<Integer, Integer> products = new HashMap<>();

        while (isActive) {
            JTextField idField = new JTextField(10);
            JTextField amountField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(new JLabel("Insert item ID:"));
            panel.add(idField);
            panel.add(new JLabel("Insert amount desired:"));
            panel.add(amountField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Create Regular Order", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int amount = Integer.parseInt(amountField.getText());

                    products.put(id, amount);

                    int choice = JOptionPane.showConfirmDialog(null, "Add more products?", "Create Regular Order", JOptionPane.YES_NO_OPTION);
                    isActive = choice == JOptionPane.YES_OPTION;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format. Please enter numeric values for ID and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isActive = false;
            }
        }

        try {
            String message = sf.manageOrderService.createRegularOrder(products);
            JOptionPane.showMessageDialog(null, message, "Create Regular Order", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }
    }

}



