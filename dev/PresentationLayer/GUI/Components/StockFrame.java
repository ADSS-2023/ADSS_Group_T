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
import java.util.HashMap;

public class StockFrame extends JFrame {
    private StockUI stockUI;
    private ServiceFactory sf;
    private SupplierManager supplierManager;
    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;
    private JLabel messageField;

    public StockFrame(StockUI stockUI, SupplierManager supplierManager, ServiceFactory sf) {
        this.stockUI = stockUI;
        this.sf = sf;

        setTitle("Stock Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        createToolbar();
        createContentPanel();
        createEmptyBoxPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createLineBorder(Color.BLACK)));
        messagePanel.add(messageField);

        mainPanel.add(messagePanel, BorderLayout.NORTH);
        messageField.setText("Welcome to stock system");

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

        addButtonToToolbar(toolbar, "Set Discount", this::setDiscount);
        addButtonToToolbar(toolbar, "Receive Order", this::receiveOrder);
        addButtonToToolbar(toolbar, "Add Item", this::addItem);
        addButtonToToolbar(toolbar, "Report Damaged Item", this::damagedItem);
        addButtonToToolbar(toolbar, "Add Category", this::addCategory);
        addButtonToToolbar(toolbar, "See Categories", this::presentCategories);
        addButtonToToolbar(toolbar, "Set Minimal Amount", this::setMinimalAmount);
        addButtonToToolbar(toolbar, "Place Waiting Items", this::placeWaitingItems);
        addButtonToToolbar(toolbar, "Create Special Order", this::createSpecialOrder);
        addButtonToToolbar(toolbar, "Create Regular Order", this::createRegularOrder);
        addButtonToToolbar(toolbar, "Edit Regular Item Order", this::editRegularItemOrder);
        addButtonToToolbar(toolbar, "Move to Next Day", this::nextDay);

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

    private void nextDay(){
        sf.manageOrderService.nextDay();
        JOptionPane.showMessageDialog(null, "Moved to the next day successfully.", "Move to Next Day", JOptionPane.INFORMATION_MESSAGE);
    }

    //TODO : make only one function here and in stock
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

    private void setDiscount() {
        String product = presentCategories();
        JTextField productField = new JTextField(10);
        JTextField startDateField = new JTextField(10);
        JTextField endDateField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateField);
        panel.add(new JLabel("Percentage Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Discount", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                double amount = Double.parseDouble(amountField.getText());
                // Send the data to set discount
                // TODO: Implement and take the error message
                sf.inventoryService.set_discount(product, amount, startDate, endDate);
            } catch (NumberFormatException ex) {
                messageField.setText("Invalid input format");
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
    }

    private void damagedItem() {
        JTextField itemIdField = new JTextField(10);
        JTextField orderIdField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField descriptionField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Item ID:"));
        panel.add(itemIdField);
        panel.add(new JLabel("Order ID:"));
        panel.add(orderIdField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Report Damaged Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int item_id = Integer.parseInt(itemIdField.getText());
                int order_id = Integer.parseInt(orderIdField.getText());
                int amount = Integer.parseInt(amountField.getText());
                String description = descriptionField.getText();
                messageField.setText(sf.damagedService.report_damaged_item(item_id, order_id, amount, description));
            } catch (NumberFormatException ex) {
                messageField.setText("Invalid input format");
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
    }

    //TODO : change from an alert to just a string of error
    private void setMinimalAmount() {
        JTextField itemIdField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Item ID:"));
        panel.add(itemIdField);
        panel.add(new JLabel("Minimal Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Minimal Amount", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int item_id = Integer.parseInt(itemIdField.getText());
                int amount = Integer.parseInt(amountField.getText());

                String message = sf.itemService.setMinimalAmount(item_id, amount);
                JOptionPane.showMessageDialog(null, message, "Set Minimal Amount", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                messageField.setText("Invalid input format");
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
    }

    private void addItem() {
        String categoryId = presentCategories();

        JTextField itemIdField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField manufacturerField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        JPanel addItemPanel = new JPanel();
        addItemPanel.setLayout(new GridLayout(6, 2));


        addItemPanel.add(new JLabel("Item ID:"));
        addItemPanel.add(itemIdField);
        addItemPanel.add(new JLabel("Name:"));
        addItemPanel.add(nameField);
        addItemPanel.add(new JLabel("Alert Amount:"));
        addItemPanel.add(amountField);
        addItemPanel.add(new JLabel("Manufacturer:"));
        addItemPanel.add(manufacturerField);
        addItemPanel.add(new JLabel("Price:"));
        addItemPanel.add(priceField);

        emptyBoxPanel.add(addItemPanel, "addItemPanel");
        cardLayout.show(emptyBoxPanel, "addItemPanel");

        int result = JOptionPane.showConfirmDialog(emptyBoxPanel, addItemPanel, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int itemId = Integer.parseInt(itemIdField.getText());
                String name = nameField.getText();
                int amount = Integer.parseInt(amountField.getText());
                String manufacturer = manufacturerField.getText();
                double price = Double.parseDouble(priceField.getText());
                sf.itemService.addItem(categoryId, itemId, name, amount, manufacturer, price);
                messageField.setText("Item " + name + " added successfully");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
        emptyBoxPanel.removeAll();
        emptyBoxPanel.revalidate();
        emptyBoxPanel.repaint();
    }

    public void receiveOrder() {
        JTextField orderIdField = new JTextField(10);
        JTextField itemIdField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField locationField = new JTextField(10);
        JTextField validityField = new JTextField(10);
        JTextField costPriceField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Order ID:"));
        panel.add(orderIdField);
        panel.add(new JLabel("Item ID:"));
        panel.add(itemIdField);
        panel.add(new JLabel("Amount Received:"));
        panel.add(amountField);
        panel.add(new JLabel("Location in Store:"));
        panel.add(locationField);
        panel.add(new JLabel("Validity Date:"));
        panel.add(validityField);
        panel.add(new JLabel("Cost Price:"));
        panel.add(costPriceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Receive Order", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                int itemId = Integer.parseInt(itemIdField.getText());
                int amount = Integer.parseInt(amountField.getText());
                String location = locationField.getText();
                String validity = validityField.getText();
                double costPrice = Double.parseDouble(costPriceField.getText());

                String message = sf.itemService.receive_order(orderId, itemId, amount, location, Util.stringToDate(validity), costPrice);
                JOptionPane.showMessageDialog(null, message, "Receive Order", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
    }

    private void addCategory() {
        String index = presentCategories();
        JTextField nameField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Index ID:"));
        panel.add(new JLabel(index));
        panel.add(new JLabel("Category Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Category", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String message = sf.categoryService.add_category(index, name);
                JOptionPane.showMessageDialog(null, message, "Add Category", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                messageField.setText(ex.getMessage());
            }
        }
    }

    private void placeWaitingItems() {
        boolean isActive = true;

        while (isActive) {
            JTextField choiceField = new JTextField(10);
            JTextField locationField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(new JLabel("Choose item to be placed:"));
            panel.add(choiceField);
            panel.add(new JLabel("Where to place the item? (e.g., ile:'ile number' shelf:'shelf number')"));
            panel.add(locationField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Place Waiting Items", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int choice = Integer.parseInt(choiceField.getText());
                    String location = locationField.getText();

                    String message = sf.manageOrderService.placeNewArrival(choice, location);
                    JOptionPane.showMessageDialog(null, message, "Place Waiting Items", JOptionPane.INFORMATION_MESSAGE);

                    int confirmChoice = JOptionPane.showConfirmDialog(null, "Would you like to place another item?", "Continue", JOptionPane.YES_NO_OPTION);
                    isActive = confirmChoice == JOptionPane.YES_OPTION;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    messageField.setText(ex.getMessage());
                }
            } else {
                isActive = false;
            }
        }
    }

    //orders
    private void createSpecialOrder() {
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

            int result = JOptionPane.showConfirmDialog(null, panel, "Create Special Order", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int amount = Integer.parseInt(amountField.getText());

                    products.put(id, amount);

                    int choice = JOptionPane.showConfirmDialog(null, "Add more products?", "Create Special Order", JOptionPane.YES_NO_OPTION);
                    isActive = choice == JOptionPane.YES_OPTION;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isActive = false;
            }
        }

        int urgentChoice = JOptionPane.showConfirmDialog(null, "Mark this order as urgent?", "Create Special Order", JOptionPane.YES_NO_OPTION);
        boolean isUrgent = urgentChoice == JOptionPane.YES_OPTION;

        try {
            String message = sf.manageOrderService.createSpecialOrder(products, isUrgent);
            JOptionPane.showMessageDialog(null, message, "Create Special Order", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            messageField.setText(ex.getMessage());
        }
    }

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

        try {
            String message = sf.manageOrderService.createRegularOrder(products);
            JOptionPane.showMessageDialog(null, message, "Create Regular Order", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            messageField.setText(ex.getMessage());
        }
    }


    private void editRegularItemOrder() {
        show_all_orders();
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

    //TODO : make only one function
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

    // Create methods to handle other actions here
}


