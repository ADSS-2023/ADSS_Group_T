package PresentationLayer.GUI.SupplierGUI;

import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;

import static PresentationLayer.GUI.SupplierGUI.SupplierGUI.run;

public class AddSupplierProcess extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ServiceFactory sf;
    private String name;
    private String address;
    private int supplierNum;
    private int bankAccount;
    private int daysToDeliver;
    private HashMap<String, String> contacts;
    private LinkedList<DayOfWeek> constDeliveryDays;
    private boolean selfDelivery;
    private PaymentTerms paymentTerm;


    public AddSupplierProcess(ServiceFactory sf) {
        daysToDeliver=-1;
        selfDelivery=false;
        constDeliveryDays=new LinkedList<>();
        contacts=new HashMap<>();
        this.sf = sf;
        setTitle("Add New Supplier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int width = 500; // Adjust the width as needed
        int height = 500; // Adjust the height as needed
        setSize(width, height);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Step 1: Fill Supplier Information
        JPanel step1Panel = createStep1Panel();
        cardPanel.add(step1Panel, "step1");

        // Step 2: Enter Contact Details
        JPanel step2Panel = createStep2Panel();
        cardPanel.add(step2Panel, "step2");

        // Step 3: Delivery Information
        JPanel step3Panel = createStep3Panel();
        cardPanel.add(step3Panel, "step3");

        setLocationRelativeTo(null);
        setVisible(true);
        add(cardPanel);
        pack();
    }

    private JPanel createStep1Panel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Supplier Name
        JTextField nameField = new JTextField(30);
        panel.add(new JLabel("Supplier Name:"), gbc);

        gbc.gridy++;
        panel.add(nameField, gbc);

        // Supplier Number
        gbc.gridy++;
        panel.add(new JLabel("Supplier Number:"), gbc);

        gbc.gridy++;
        JTextField numberField = new JTextField(30);
        panel.add(numberField, gbc);

        // Supplier Number
        gbc.gridy++;
        panel.add(new JLabel("Supplier Address:"), gbc);

        gbc.gridy++;
        JTextField addressField = new JTextField(30);
        panel.add(addressField, gbc);

        // Bank Account Number
        gbc.gridy++;
        panel.add(new JLabel("Bank Account Number:"), gbc);

        gbc.gridy++;
        JTextField accountField = new JTextField(15);
        panel.add(accountField, gbc);

        // Payment Terms
        gbc.gridy++;
        panel.add(new JLabel("Payment Terms:"), gbc);

        gbc.gridy++;
        String[] paymentTerms = {"SHOTEF_PLUS_30", "SHOTEF_PLUS_45", "SHOTEF_PLUS_60", "SHOTEF_PLUS_90"};
        JComboBox<String> paymentComboBox = new JComboBox<>(paymentTerms);
        panel.add(paymentComboBox, gbc);

        // Next Button
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name=nameField.getText();
                supplierNum=Integer.parseInt(numberField.getText());
                address=addressField.getText();
                bankAccount=Integer.parseInt(accountField.getText());
                paymentTerm = PaymentTerms.valueOf((String)paymentComboBox.getSelectedItem()) ;
                cardLayout.show(cardPanel, "step2");
            }
        });
        panel.add(nextButton, gbc);

        return panel;
    }

    private JPanel createStep2Panel() {
        JPanel panel = new JPanel(new GridLayout(4, 0));

        // Contact Name
        panel.add(new JLabel("Contact Name:"));
        JTextField contactNameField = new JTextField(20);
        panel.add(contactNameField);

        // Contact Number
        panel.add(new JLabel("Contact Number:"));
        JTextField contactNumberField = new JTextField(10);
        panel.add(contactNumberField);

        // Add Another Contact Details
        JCheckBox addAnotherCheckBox = new JCheckBox("Add Another Contact Details");
        panel.add(addAnotherCheckBox,BorderLayout.CENTER);

        JCheckBox deliverBySupplierCheckbox = new JCheckBox("Supplier Delivers by Himeself?");
        panel.add(deliverBySupplierCheckbox, BorderLayout.SOUTH);

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(deliverBySupplierCheckbox.isSelected())
                    selfDelivery=true;
                contacts.put(contactNameField.getText(),contactNumberField.getText());
                if (addAnotherCheckBox.isSelected()) {
                    // Go to Step 2 again to add another contact
                    contactNameField.setText("");
                    contactNumberField.setText("");
                    addAnotherCheckBox.setSelected(false);
                    cardLayout.show(cardPanel, "step2");
                } else {
                    cardLayout.show(cardPanel, "step3");
                }
            }
        });
        panel.add(nextButton);

        return panel;
    }

    private JPanel createStep3Panel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Delivery Information
        JLabel deliveryLabel = new JLabel("Delivery Information:");
        panel.add(deliveryLabel, BorderLayout.NORTH);

        // Radio Button Group
        JRadioButton constantDaysRadio = new JRadioButton("Deliver on Constant Days");
        JRadioButton expectedDaysRadio = new JRadioButton("Expected Delivery Time (in days)");

        // Create a button group for the radio buttons
        ButtonGroup deliveryGroup = new ButtonGroup();
        deliveryGroup.add(constantDaysRadio);
        deliveryGroup.add(expectedDaysRadio);

        // Add radio buttons to a panel
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(constantDaysRadio);
        radioPanel.add(expectedDaysRadio);
        panel.add(radioPanel, BorderLayout.CENTER);

        // Days of the Week (Constant Days)
        String[] daysOfWeek = {"SUNDAY","MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        JList<String> daysList = new JList<>(daysOfWeek);
        JScrollPane daysScrollPane = new JScrollPane(daysList);

        // Expected Delivery Time (in days)
        JTextField expectedDaysField = new JTextField(10);

        // Panel for constant days and expected days components
        JPanel deliveryOptionsPanel = new JPanel(new BorderLayout());
        deliveryOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        // Add components based on selected radio button
        constantDaysRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deliveryOptionsPanel.removeAll();
                deliveryOptionsPanel.add(daysScrollPane, BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        });

        expectedDaysRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deliveryOptionsPanel.removeAll();
                deliveryOptionsPanel.add(expectedDaysField, BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        });
        panel.add(deliveryOptionsPanel, BorderLayout.NORTH);


        // Next Button
        JButton nextButton = new JButton("Finish");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(expectedDaysRadio.isSelected())
                    daysToDeliver = Integer.parseInt(expectedDaysField.getText());
                else{
                    for (String day:daysList.getSelectedValuesList()) {
                        constDeliveryDays.add(DayOfWeek.valueOf(day));
                    }
                }
                sf.supplierService.addSupplier(name,address,supplierNum,bankAccount,daysToDeliver,
                        contacts,constDeliveryDays,selfDelivery,paymentTerm);
                dispose();
                run(new AllSupplierFrame(sf));
            }
        });
        panel.add(nextButton, BorderLayout.EAST);
        return panel;
    }

}
