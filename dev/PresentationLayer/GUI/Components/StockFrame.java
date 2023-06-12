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
import java.util.HashMap;

public class StockFrame extends JFrame {
    private StockUI stockUI;
    private ServiceFactory sf;
    private SupplierManager supplierManager;
    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;

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

        JButton discountButton = new JButton("Set Discount");
        discountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDiscount();
            }
        });
        toolbar.add(discountButton);

        JButton receiveOrderButton = new JButton("Receive Order");
        receiveOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveOrder();
            }
        });
        toolbar.add(receiveOrderButton);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        toolbar.add(addItemButton);

        JButton damagedItemButton = new JButton("Report Damaged Item");
        damagedItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                damagedItem();
            }
        });
        toolbar.add(damagedItemButton);

        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCategory();
            }
        });
        toolbar.add(addCategoryButton);

        JButton btnSeeCategories = new JButton("See Categories");
        btnSeeCategories.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                presentCategories();
            }
        });
        toolbar.add(btnSeeCategories);

        JButton btnSetMinimalAmount = new JButton("Set Minimal Amount");
        btnSetMinimalAmount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setMinimalAmount();
            }
        });
        toolbar.add(btnSetMinimalAmount);

        JButton btnPlaceWaitingItems = new JButton("Place Waiting Items");
        btnPlaceWaitingItems.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeWaitingItems();
            }
        });
        toolbar.add(btnPlaceWaitingItems);

        JButton btnCreateSpecialOrder = new JButton("Create Special Order");
        btnCreateSpecialOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createSpecialOrder();
            }
        });
        toolbar.add(btnCreateSpecialOrder);

        JButton btnCreateRegularOrder = new JButton("Create Regular Order");
        btnCreateRegularOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRegularOrder();
            }
        });
        toolbar.add(btnCreateRegularOrder);

        JButton btnEditRegularItemOrder = new JButton("Edit Regular Item Order");
        btnEditRegularItemOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editRegularItemOrder();
            }
        });
        toolbar.add(btnEditRegularItemOrder);

        JButton btnMoveToNextDay = new JButton("Move to Next Day");
        btnMoveToNextDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sf.manageOrderService.nextDay();
                JOptionPane.showMessageDialog(null, "Moved to the next day successfully.", "Move to Next Day", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        toolbar.add(btnMoveToNextDay);

        add(toolbar, BorderLayout.WEST);
    }


    public String presentCategories() {
        String data = sf.inventoryService.show_data();
        String[] categories = data.split(", ");
        String[] options = new String[categories.length];

        for (int i = 0; i < categories.length; i++) {
            String[] parts = categories[i].split(" : ");
            String index = parts[0].trim();
            String categoryName = parts[1].trim();
            options[i] = index + " : " + categoryName;
        }

        String message = "Press the index of the category/item to dive in,\nPress 0 to choose the current category,\nPress -1 to exit";

        String nextIndex = "";
        boolean isActive = true;
        String choice = (String) JOptionPane.showInputDialog(null, message, "Choose Category", JOptionPane.PLAIN_MESSAGE, null, options, null);

        while (isActive) {
            if (choice == null || choice.equals("-1")) {
                isActive = false;
                nextIndex = "exit";
            } else if (choice.equals("0")) {
                isActive = false;
            } else {
                nextIndex += "." + (Integer.parseInt(choice.split(" : ")[0]) - 1);
                try {
                    String toShow = sf.categoryService.show_data(nextIndex);
                    categories = toShow.split(", ");
                    options = new String[categories.length];

                    for (int i = 0; i < categories.length; i++) {
                        String[] parts = categories[i].split(" : ");
                        String index = parts[0].trim();
                        String categoryName = parts[1].trim();
                        options[i] = index + " : " + categoryName;
                    }

                    choice = (String) JOptionPane.showInputDialog(null, message, "Choose Category", JOptionPane.PLAIN_MESSAGE, null, options, null);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return nextIndex;
    }


    private void setDiscount() {
        JTextField productField = new JTextField(10);
        JTextField startDateField = new JTextField(10);
        JTextField endDateField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Product:"));
        panel.add(productField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateField);
        panel.add(new JLabel("Percentage Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Discount", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String product = productField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            double amount = Double.parseDouble(amountField.getText());
            stockUI.setDiscount();
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
            int item_id = Integer.parseInt(itemIdField.getText());
            int order_id = Integer.parseInt(orderIdField.getText());
            int amount = Integer.parseInt(amountField.getText());
            String description = descriptionField.getText();
            sf.damagedService.report_damaged_item(item_id, order_id, amount, description);
        }
    }

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
                JOptionPane.showMessageDialog(null, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addItem() {
        JTextField categoryIdField = new JTextField(10);
        JTextField itemIdField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField manufacturerField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        JPanel addItemPanel = new JPanel();
        addItemPanel.setLayout(new GridLayout(6, 2));
        addItemPanel.add(new JLabel("Category ID:"));
        addItemPanel.add(categoryIdField);
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
            String categoryId = categoryIdField.getText();
            int itemId = Integer.parseInt(itemIdField.getText());
            String name = nameField.getText();
            int amount = Integer.parseInt(amountField.getText());
            String manufacturer = manufacturerField.getText();
            double price = Double.parseDouble(priceField.getText());
            sf.itemService.addItem(categoryId, itemId, name, amount, manufacturer, price);
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
            int orderId = Integer.parseInt(orderIdField.getText());
            int itemId = Integer.parseInt(itemIdField.getText());
            int amount = Integer.parseInt(amountField.getText());
            String location = locationField.getText();
            String validity = validityField.getText();
            double costPrice = Double.parseDouble(costPriceField.getText());

            System.out.println(sf.itemService.receive_order(orderId, itemId, amount, location, Util.stringToDate(validity), costPrice));
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
            String name = nameField.getText();
            System.out.println(sf.categoryService.add_category(index, name));
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

        String message = sf.manageOrderService.createSpecialOrder(products, isUrgent);
        JOptionPane.showMessageDialog(null, message, "Create Special Order", JOptionPane.INFORMATION_MESSAGE);
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

        String message = sf.manageOrderService.createRegularOrder(products);
        JOptionPane.showMessageDialog(null, message, "Create Regular Order", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editRegularItemOrder() {
        String day = JOptionPane.showInputDialog(null, "Insert the day of the week (big letters only):");
        DayOfWeek curDay = DayOfWeek.valueOf(day);

        String itemsData = sf.manageOrderService.presentItemsById(curDay);
        String[] items = itemsData.split("\\r?\\n");
        String[] options = new String[items.length];

        for (int i = 0; i < items.length; i++) {
            String[] parts = items[i].split(" : ");
            String itemId = parts[0].trim();
            String itemName = parts[1].trim();
            options[i] = itemId + " : " + itemName;
        }

        String selectedItemId = (String) JOptionPane.showInputDialog(null, "Select the item to edit:", "Edit Regular Item Order", JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (selectedItemId != null && !selectedItemId.isEmpty()) {
            int itemId = Integer.parseInt(selectedItemId.split(" : ")[0].trim());

            String newAmountStr = JOptionPane.showInputDialog(null, "Enter the new amount of the product:", "Edit Regular Item Order", JOptionPane.PLAIN_MESSAGE);

            if (newAmountStr != null && !newAmountStr.isEmpty()) {
                int newAmount = Integer.parseInt(newAmountStr);
                sf.manageOrderService.editRegularOrder(itemId, curDay, newAmount);
                JOptionPane.showMessageDialog(null, "Regular item order has been updated successfully.", "Edit Regular Item Order", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //move to next day?

/*    private void addCategory2() {
        JTextField nameField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Category Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Category", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();

            System.out.println(sf.categoryService.add_category(".0" , name));
        }
    }

    public String presentCategories2() {
        String[] options = sf.inventoryService.show_data().split("\\r?\\n");
        String message = "Press the index of the category/item to dive in,\nPress 0 to choose the current category,\nPress -1 to exit";

        String nextIndex = "";
        boolean isActive = true;

        while (isActive) {
            String choice = (String) JOptionPane.showInputDialog(null, message, "Choose Category", JOptionPane.PLAIN_MESSAGE, null, options, null);

            if (choice == null || choice.equals("-1")) {
                isActive = false;
                nextIndex = "exit";
            } else if (choice.equals("0")) {
                isActive = false;
            } else {
                nextIndex += "." + (Integer.parseInt(choice) - 1);
                String toShow = sf.categoryService.show_data(nextIndex);
                JOptionPane.showMessageDialog(null, toShow, "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        return nextIndex;
    }

    public String presentCategories5() {
        String data = sf.inventoryService.show_data();
        String[] categories = data.split(", ");
        String[] options = new String[categories.length];

        for (int i = 0; i < categories.length; i++) {
            String[] parts = categories[i].split(" : ");
            String index = parts[0].trim();
            String categoryName = parts[1].trim();
            options[i] = index + " : " + categoryName;
        }

        String message = "Press the index of the category/item to dive in,\nPress 0 to choose the current category,\nPress -1 to exit";

        String nextIndex = "";
        boolean isActive = true;

        while (isActive) {
            String choice = (String) JOptionPane.showInputDialog(null, message, "Choose Category", JOptionPane.PLAIN_MESSAGE, null, options, null);

            if (choice == null || choice.equals("-1")) {
                isActive = false;
                nextIndex = "exit";
            } else if (choice.equals("0")) {
                isActive = false;
            } else {
                nextIndex += "." + (Integer.parseInt(choice.split(" : ")[0]) - 1);
                String toShow = sf.categoryService.show_data(nextIndex);
                JOptionPane.showMessageDialog(null, toShow, "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        return nextIndex;
    }



*/

    // Create methods to handle other actions here
}



