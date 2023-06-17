package PresentationLayer.GUI.Components;

import BusinessLayer.HR.Constraint;
import BusinessLayer.Stock.Util.Util;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import java.time.chrono.JapaneseDate;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.stream.Collectors;

public class StockFrame extends JFrame {
    private ServiceFactory sf;
    //    private JPanel contentPanel;
    private JPanel emptyBoxPanel;
    private CardLayout cardLayout;

    private JPanel mainPanel;
    private JLabel messageField;


    public StockFrame(ServiceFactory sf) {
        this.sf = sf;
        cardLayout = new CardLayout();
        setTitle("Stock");
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public void updateError(String msg){
        messageField.setText(msg);
        messageField.setForeground(Color.RED);
    }
    public void updateOkMessage(String msg){
        messageField.setText(msg);
        messageField.setForeground(Color.GREEN);
    }

    private void handleErrorOrOk(Response res){
        if (res.isError())
            updateError(res.getErrorMassage());
        else
            updateOkMessage((String) res.getValue());
    }
//    private void createEmptyBoxPanel() {
//
//        emptyBoxPanel = new JPanel(
//            new GridLayout(6,2)
//        );
//        emptyBoxPanel.setBackground(Color.WHITE);
//        add(emptyBoxPanel);
//    }
private void createEmptyBoxPanel() {
    emptyBoxPanel = new JPanel(new GridLayout(10, 2));
    emptyBoxPanel.setBackground(Color.WHITE);

    // Add labels and text fields to the panel


    add(emptyBoxPanel);
}

    private void refreshEmptyBox() {
        emptyBoxPanel.removeAll();
        emptyBoxPanel.setLayout(new GridLayout(10,2));

        emptyBoxPanel.revalidate();
        emptyBoxPanel.repaint();

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
        addButtonToToolbar(toolbar, "Show all orders", this::show_all_orders);
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
        createEmptyBoxPanel();
        Response res = sf.manageOrderService.nextDay();
        handleErrorOrOk(res);
//        JOptionPane.showMessageDialog(null, "Moved to the next day successfully.", "Move to Next Day", JOptionPane.INFORMATION_MESSAGE);
    }

    //TODO : make only one function here and in stock
    public String presentCategories() {
        emptyBoxPanel.removeAll();
        try {
            Response dataResponse = sf.inventoryService.show_data();
            if (dataResponse.isError()) {
                if(emptyCategory())
                    //TODO : Check
                    return "";
                else{
                    return "exit";
                }
            }
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
                        if (dataToShow.isError()) {
                            if(emptyCategory())
                                return nextIndex;
                            else{
                                return "exit";
                            }
                        }
                        else {
                            String toShow = (String) dataToShow.getValue();
                            if (toShow.startsWith("--")) {
                                isActive = false;
                                if (!presentItem(toShow)) {
                                    nextIndex = "exit";
                                }
                            }
                            else {
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
                            }
                        }
                    } catch (Exception e) {
                        nextIndex = nextIndex + emptyCategory();
                        return nextIndex;
                        //updateError(e.getMessage()); // Update the messageField with the error message
                    }
                }
            }
            return nextIndex;
        } catch (Exception e) {
            updateOkMessage(e.getMessage()); // Update the messageField with the error message
            return "exit";
        }
    }

    private boolean emptyCategory() {
        String[] options = {"Choose this category","Cancel"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "This category is empty",
                "Choose Category",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        return choice == 0;
    }

    private boolean presentItem(String toShow) {
        String[] options = {"Choose this category","Cancel"};
        int choise =  JOptionPane.showOptionDialog(
                null,
                toShow,
                "Choose Item",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                "cancel"
        );
        return choise == 0;
    }

    private void setDiscount() {
        refreshEmptyBox();
        String product = presentCategories();
        if (!product.equals("exit")) {
        try {


                JTextField amountField = new JTextField();
                emptyBoxPanel.add(new JLabel("Start Date:"), 0);
                JDatePicker startDate = addDate(emptyBoxPanel, 1);
                emptyBoxPanel.add(new JLabel("End Date:"), 2);
                JDatePicker endDate = addDate(emptyBoxPanel, 3);
                emptyBoxPanel.add(new JLabel("Percentage Amount:"), 4);
                emptyBoxPanel.add(amountField, 5);
                UtilDateModel model = (UtilDateModel) startDate.getModel();

                UtilDateModel model2 = (UtilDateModel) endDate.getModel();

                addButton(emptyBoxPanel, "Ok", () -> handleErrorOrOk(
                        sf.inventoryService.set_discount(
                                product, Double.parseDouble(amountField.getText()),
                                Util.DateToString(model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                                Util.DateToString(model2.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))));
                addButton(emptyBoxPanel, "Cancel", () -> {
                    emptyBoxPanel.removeAll(); // Remove all components from the panel
                    emptyBoxPanel.revalidate(); // Revalidate the panel
                    emptyBoxPanel.repaint(); // Repaint the panel
                });
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());

                emptyBoxPanel.revalidate();
                emptyBoxPanel.repaint();
                emptyBoxPanel.setVisible(true);
            }
            catch(NumberFormatException ex){
                updateError("Invalid input format");
            }
        }
    }

    private JDatePicker addDate(JPanel emptyBoxPanel,int index) {
        UtilDateModel model = new UtilDateModel();
//model.setDate(20,04,2014);
// Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
// Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        datePicker.setPreferredSize(new Dimension(100, 30));
        datePicker.setBackground(Color.white);
        emptyBoxPanel.add(datePicker,index);
        return datePicker;
    }

    private void addButton(JPanel panel,String name, Runnable runnable) {
        JButton button = new JButton("<html><center>" + name + "</center></html>");

        button.setSize(5,5);
        button.addActionListener((e)->runnable.run());
        // Apply custom styling
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 5;
        gbc.gridheight = 10;
        panel.add(button,gbc);
    }

    private void damagedItem() {
        refreshEmptyBox();
        try {
            JTextField itemIdField = new JTextField();
            JTextField orderIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField descriptionField = new JTextField();


            emptyBoxPanel.add(new JLabel("Item ID:"), 0);
            emptyBoxPanel.add(itemIdField, 1);
            emptyBoxPanel.add(new JLabel("Order ID:"), 2);
            emptyBoxPanel.add(orderIdField, 3);
            emptyBoxPanel.add(new JLabel("Amount:"), 4);
            emptyBoxPanel.add(amountField, 5);
            emptyBoxPanel.add(new JLabel("Description:"), 6);
            emptyBoxPanel.add(descriptionField, 7);

            addButton(emptyBoxPanel, "Ok", () -> handleErrorOrOk(
                    sf.damagedService.report_damaged_item(
                            Integer.parseInt(itemIdField.getText()),
                            Integer.parseInt(orderIdField.getText()),
                            Integer.parseInt(amountField.getText()),
                            descriptionField.getText())));
            addButton(emptyBoxPanel, "Cancel", () -> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint(); // Repaint the panel
            });
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);
        }
       catch (Exception ex) {
                updateError("invalid input");
            }
    }


    //TODO : change from an alert to just a string of error
    private void setMinimalAmount() {
        refreshEmptyBox();
        try{
        JTextField itemIdField = new JTextField(0);
        JTextField amountField = new JTextField(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        emptyBoxPanel.add(new JLabel("Item ID:"));
        emptyBoxPanel.add(itemIdField);
        emptyBoxPanel.add(new JLabel("Minimal Amount:"));
        emptyBoxPanel.add(amountField);
            addButton(emptyBoxPanel, "Ok", () -> handleErrorOrOk(
                    sf.itemService.setMinimalAmount(Integer.parseInt(itemIdField.getText()),
                            Integer.parseInt(amountField.getText()))));
            addButton(emptyBoxPanel, "Cancel", () -> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint(); // Repaint the panel
            });
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);
            } catch (NumberFormatException ex) {
                messageField.setText("Invalid input format");

        }
    }

    private void addItem() {
        refreshEmptyBox();
        String categoryId = presentCategories();
        if (categoryId != "exit") {
            JTextField itemIdField = new JTextField(10);
            JTextField nameField = new JTextField(10);
            JTextField amountField = new JTextField(10);
            JTextField manufacturerField = new JTextField(10);
            JTextField priceField = new JTextField(10);

            JPanel addItemPanel = new JPanel();
            addItemPanel.setLayout(new GridLayout(6, 2));


            emptyBoxPanel.add(new JLabel("Item ID:"),0);
            emptyBoxPanel.add(itemIdField,1);
            emptyBoxPanel.add(new JLabel("Name:"),2);
            emptyBoxPanel.add(nameField,3);
            emptyBoxPanel.add(new JLabel("Alert Amount:"),4);
            emptyBoxPanel.add(amountField,5);
            emptyBoxPanel.add(new JLabel("Manufacturer:"),6);
            emptyBoxPanel.add(manufacturerField,7);
            emptyBoxPanel.add(new JLabel("Price:"),8);
            emptyBoxPanel.add(priceField,9);


            addButton(emptyBoxPanel, "Ok", () -> handleErrorOrOk(
                    sf.itemService.addItem(
                            categoryId,Integer.parseInt(itemIdField.getText()),
                            nameField.getText(),Integer.parseInt(amountField.getText()),
                            manufacturerField.getText(),Double.parseDouble(priceField.getText())
                    )));

            addButton(emptyBoxPanel, "Cancel", () -> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint(); // Repaint the panel
            });
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);


        }
    }


    public void receiveOrder() {
        refreshEmptyBox();
        try {
            JTextField orderIdField = new JTextField();
            JTextField itemIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField locationField = new JTextField();


            JTextField costPriceField = new JTextField();

            emptyBoxPanel.add(new JLabel("Order ID:"),0);
            emptyBoxPanel.add(orderIdField,1);
            emptyBoxPanel.add(new JLabel("Item ID:"),2);
            emptyBoxPanel.add(itemIdField,3);
            emptyBoxPanel.add(new JLabel("Amount Received:"),4);
            emptyBoxPanel.add(amountField,5);
            emptyBoxPanel.add(new JLabel("Location in Store:"),6);
            emptyBoxPanel.add(locationField,7);
            emptyBoxPanel.add(new JLabel("Validity Date:"),8);
            JDatePicker validityField = addDate(emptyBoxPanel, 9);
            UtilDateModel model = (UtilDateModel) validityField.getModel();
            emptyBoxPanel.add(new JLabel("Cost Price:"),10);
            emptyBoxPanel.add(costPriceField,11);

            addButton(emptyBoxPanel, "Ok", () -> handleErrorOrOk(
                    sf.itemService.receive_order(
                            Integer.parseInt(orderIdField.getText()),
                            Integer.parseInt(itemIdField.getText()),
                            Integer.parseInt(amountField.getText()),
                            locationField.getText(),
                            Util.stringToDate(Util.DateToString(model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())),
                            Double.parseDouble(costPriceField.getText())
                            )));
            addButton(emptyBoxPanel, "Cancel", () -> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint(); // Repaint the panel
            });
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);
        }
            catch(NumberFormatException ex){
        updateError("Invalid input format");
        }

    }

    private void addCategory() {
        refreshEmptyBox();
        String index = presentCategories();
        try {

            if (!index.equals("exit")) {
                JTextField nameField = new JTextField();

                emptyBoxPanel.add(new JLabel("Category Name:"), 0);
                emptyBoxPanel.add(nameField, 1);
                addButton(emptyBoxPanel,"OK",()->handleErrorOrOk(sf.categoryService.add_category(index,nameField.getText())));
                addButton(emptyBoxPanel,"Cancel",()-> {
                    emptyBoxPanel.removeAll(); // Remove all components from the panel
                    emptyBoxPanel.revalidate(); // Revalidate the panel
                    emptyBoxPanel.repaint();
                });
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());
                emptyBoxPanel.add(new JLabel());


                emptyBoxPanel.revalidate();
                emptyBoxPanel.repaint();
                emptyBoxPanel.setVisible(true);

            }
        }

        catch (Exception ex) {
                    updateError("invalid input");
                }


    }

    private void placeWaitingItems() {
        refreshEmptyBox();
        try {
            Response res = sf.manageOrderService.presentItemsToBePlaced();
            if (res.isError()) {
                throw new Exception(res.getErrorMassage());
            }
            java.util.List<String> stringList = (java.util.List<String>) res.getValue();
            String itemsString = stringList.stream().collect(Collectors.joining(""));

            // Split the items string into individual items
            String[] items = itemsString.split("\\n");

            for (String item : items) {
                JLabel itemLabel = new JLabel(item);
                emptyBoxPanel.add(itemLabel);
                itemLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String selectedItem = itemLabel.getText();
                        int itemID = Character.getNumericValue(selectedItem.charAt(0));
                        openLocationInputDialog(itemID);
                        placeWaitingItems();
                    }
                });
            }
            addButton(emptyBoxPanel,"Cancel",()-> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint();
            });
            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);
        } catch (Exception ex) {
            messageField.setText(ex.getMessage());
        }
    }


    private void openLocationInputDialog(int itemId) {
            JTextField locationField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.add(new JLabel("Where to place item with ID: " + itemId + "? (e.g., ile:'ile number' shelf:'shelf number')"));
            panel.add(locationField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Item Location", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String location = locationField.getText();
                handleErrorOrOk(sf.manageOrderService.placeNewArrival(itemId, location));
            }
        }



    private void createSpecialOrder2(){
        try {
            refreshEmptyBox();
            HashMap<Integer, Integer> items = new HashMap<>();
            emptyBoxPanel.add(new JLabel("Item id"), 0);
            JTextField itemId = new JTextField(5);
            emptyBoxPanel.add(itemId, 1);

            emptyBoxPanel.add(new JLabel("Amount"), 2);
            JTextField amountField = new JTextField(5);
            emptyBoxPanel.add(amountField, 3);

            addButton(emptyBoxPanel, "add", () -> {
                addItemToEmptyBoxPanel(itemId.getText(), amountField.getText(), items)
                ;
                itemId.setText("");
                amountField.setText("");
            });

            JCheckBox isUrgent = new JCheckBox("Mark as urgent");
            addButton(emptyBoxPanel, "make order", () -> handleErrorOrOk(sf.manageOrderService.createSpecialOrder(items, isUrgent.isSelected())));
            emptyBoxPanel.add(isUrgent);
            emptyBoxPanel.add(new Label());
            emptyBoxPanel.add(new Label());
            emptyBoxPanel.add(new Label());

            JButton clearButton = new JButton("<html><center>" + "clear" + "</center></html>");
            clearButton.addActionListener((e) -> createSpecialOrder2());
            emptyBoxPanel.add(clearButton, 7);
            pack();
            emptyBoxPanel.revalidate();
            setVisible(true);
        }
        catch (Exception e){
            updateError(e.getMessage());
        }

    }




    private void addItemToEmptyBoxPanel(String itemId, String amount,HashMap<Integer,Integer> items) {
        // Create a panel to display the added item
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemPanel.add(new JLabel("Item ID: " + itemId));
        itemPanel.add(new JLabel("Amount: " + amount));

        items.put(Integer.parseInt(itemId),Integer.parseInt(amount));
        // Add the item panel to the emptyBoxPanel
        emptyBoxPanel.add(itemPanel);

        // Refresh the emptyBoxPanel to reflect the changes
        emptyBoxPanel.revalidate();
        emptyBoxPanel.repaint();
    }


    //orders
    private void createSpecialOrder() {
        refreshEmptyBox();
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
            handleErrorOrOk(sf.manageOrderService.createSpecialOrder(products, isUrgent));

        } catch (Exception ex) {
            messageField.setText(ex.getMessage());
        }
    }

    private void createRegularOrder() {
        try {
            refreshEmptyBox();
            HashMap<Integer, Integer> items = new HashMap<>();
            emptyBoxPanel.add(new JLabel("Item id"), 0);
            JTextField itemId = new JTextField(5);
            emptyBoxPanel.add(itemId, 1);

            emptyBoxPanel.add(new JLabel("Amount"), 2);
            JTextField amountField = new JTextField(5);
            emptyBoxPanel.add(amountField, 3);

            addButton(emptyBoxPanel, "add", () -> {
                addItemToEmptyBoxPanel(itemId.getText(), amountField.getText(), items)
                ;
                itemId.setText("");
                amountField.setText("");
            });


            addButton(emptyBoxPanel, "make order", () -> handleErrorOrOk(sf.manageOrderService.createRegularOrder(items)));

            emptyBoxPanel.add(new Label());
            emptyBoxPanel.add(new Label());
            emptyBoxPanel.add(new Label());
            emptyBoxPanel.add(new Label());

            JButton clearButton = new JButton("<html><center>" + "clear" + "</center></html>");
            clearButton.addActionListener((e) -> createRegularOrder());
            emptyBoxPanel.add(clearButton, 7);
            pack();
            emptyBoxPanel.revalidate();
            setVisible(true);
        }
        catch (Exception e){
            updateError(e.getMessage());
        }

    }


    private void editRegularItemOrder() {
        try {
           refreshEmptyBox();
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

            JComboBox<String> dayComboBox;
            String[] daysOfWeek = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            dayComboBox = new JComboBox<>(daysOfWeek);
            emptyBoxPanel.add(new JLabel("Day"),0);
            emptyBoxPanel.add(dayComboBox,1);
            emptyBoxPanel.add(new JLabel("Product ID:"),2);
            emptyBoxPanel.add(idField,3);
            emptyBoxPanel.add(new JLabel("New amount:"),4);
            emptyBoxPanel.add(amountField,5);

            addButton(emptyBoxPanel,"edit",()->handleErrorOrOk(
                    sf.manageOrderService.editRegularOrder(Integer.parseInt(idField.getText()),
                            DayOfWeek.of(dayComboBox.getSelectedIndex())
                            ,Integer.parseInt(amountField.getText()))));
            addButton(emptyBoxPanel,"Cancel",()-> {
                emptyBoxPanel.removeAll(); // Remove all components from the panel
                emptyBoxPanel.revalidate(); // Revalidate the panel
                emptyBoxPanel.repaint();
            });
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());
            emptyBoxPanel.add(new JLabel());

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
            emptyBoxPanel.setVisible(true);
        } catch (NumberFormatException e) {
            messageField.setText("Invalid input. Please enter numeric values for ID and amount."); // Update the messageField
        }
    }

    //TODO : make only one function
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

            emptyBoxPanel.setLayout(new BorderLayout());
            emptyBoxPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the empty box panel

            emptyBoxPanel.revalidate();
            emptyBoxPanel.repaint();
        } catch (Exception e) {
            messageField.setText(e.getMessage()); // Update the messageField with the error message
        }

    }




    // Create methods to handle other actions here
}