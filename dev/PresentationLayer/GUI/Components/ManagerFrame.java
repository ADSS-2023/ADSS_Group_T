package PresentationLayer.GUI.Components;

import BusinessLayer.Stock.Util.Util;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
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

    public ManagerFrame(StockUI stockUI, SupplierManager supplierManager, ServiceFactory sf) {
        this.stockUI = stockUI;
        this.sf = sf;

        setTitle("Manager window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        createToolbar();
        createContentPanel();
        createEmptyBoxPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
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

        JButton inventoryButton = new JButton("Generate Inventory Report");
        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventoryReport();
            }
        });
        toolbar.add(inventoryButton);
        JButton damageReportButton = new JButton("Generate Damage Item Report");
        damageReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String damagedReport = sf.damagedService.produce_damaged_report();
                showDamageReportDialog(damagedReport);
            }
        });
        toolbar.add(damageReportButton);

        JButton shortageReportButton = new JButton("Generate Shortage Report");
        shortageReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String shortageReport = sf.inventoryService.produce_shortage_report();
                showShortageReportDialog(shortageReport);
            }
        });
        toolbar.add(shortageReportButton);

        JButton editRegularOrderButton = new JButton("Edit Regular Order");
        editRegularOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editRegularItemOrder();
            }
        });
        toolbar.add(editRegularOrderButton);
        JButton showOrdersButton = new JButton("Show All Orders");
        showOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                show_all_orders();
            }
        });
        toolbar.add(showOrdersButton);
        JButton btnCreateRegularOrder = new JButton("Create Regular Order - TESTING ONLY");
        btnCreateRegularOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRegularOrder();
            }
        });
        toolbar.add(btnCreateRegularOrder);

        JButton showNewItemsButton = new JButton("Show New Items");
        showNewItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newItems = sf.manageOrderService.show_new_items();
                JTextArea textArea = new JTextArea(newItems);
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setEditable(false);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "New Items", JOptionPane.PLAIN_MESSAGE);
            }
        });
        toolbar.add(showNewItemsButton);

        add(toolbar, BorderLayout.WEST);

    }



    private void showDamageReportDialog(String damagedReport) {
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
    }

    private void showShortageReportDialog(String shortageReport) {
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
    }

    public String presentCategories2() {
        String data = sf.inventoryService.show_data();
        String[] categories = data.split(", ");
        String[] options = new String[categories.length + 1];

        for (int i = 0; i < categories.length; i++) {
            String[] parts = categories[i].split(" : ");
            String index = parts[0].trim();
            String categoryName = parts[1].trim();
            options[i] = index + " : " + categoryName;
        }

        options[categories.length] = "Choose";

        String message = "Press Choose Current to select the current category";

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
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return nextIndex;
    }

    public String presentCategories() {
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return nextIndex;
    }


    public void inventoryReport() {
        LinkedList<String> categories = new LinkedList<>();
        boolean is_active = true;

        while (is_active) {
            String result = presentCategories();
            if (result.equals("exit")) {
                return;
            }
            categories.add(result);

            int choice = JOptionPane.showOptionDialog(null, "Would you like another category?", "Choose Option",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");

            if (choice == 1) {
                is_active = false;
            }
        }

        if (categories.isEmpty()) {
            System.out.println("You didn't choose any category.");
        } else {
            String inventoryReport = sf.inventoryService.produce_inventory_report(categories);
            showInventoryReportDialog(inventoryReport);
        }
    }

    private void showInventoryReportDialog(String inventoryReport) {
        JTextArea textArea = new JTextArea(formatInventoryReport(inventoryReport));
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Inventory Report", JOptionPane.PLAIN_MESSAGE);
    }

    private String formatInventoryReport(String inventoryReport) {
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
    }

    private void editRegularItemOrder() {
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
                String message = "Item Details:\n" + itemDetails + "\n\nConfirm editing the order with the new amount?";

                int confirmResult = JOptionPane.showConfirmDialog(null, message, "Confirm Edit", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    sf.manageOrderService.editRegularOrder(id, cur_day, amount);
                    System.out.println("Regular item order successfully edited.");
                } else {
                    System.out.println("Edit operation canceled.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day of the week. Please enter a valid day in capital letters.");
            }
        } else {
            System.out.println("Edit operation canceled.");
        }
    }


    private void show_all_orders() {
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
                sb.append("\tNo orders in this day\n");
            } else {
                sb.append("Regular orders:\n");
                sb.append(regularOrders);
            }

            String specialOrders = "";
            if (specialOrders.isEmpty()) {
                sb.append("Special orders:\n");
                sb.append("\tNo orders in this day\n");
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
    }

    private String formatOrderSection(String section) {
        StringBuilder formattedSection = new StringBuilder();
        String[] lines = section.split("\\r?\\n");
        for (String line : lines) {
            if (!line.startsWith("-")) {
                if (line.startsWith("Regular orders") || line.startsWith("Special orders")) {
                    formattedSection.append(line).append("\n");
                } else if (!line.equals("No items to present")) {
                    formattedSection.append("\t").append(line).append("\n");
                }
            }
        }
        return formattedSection.toString();
    }

    private void showOrdersDialog(String orders) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        textArea.setText(orders);

        JOptionPane.showMessageDialog(null, scrollPane, "All Orders", JOptionPane.PLAIN_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isActive = false;
            }
        }

        String message = sf.manageOrderService.createRegularOrder(products);
        JOptionPane.showMessageDialog(null, message, "Create Regular Order", JOptionPane.INFORMATION_MESSAGE);
    }



}



