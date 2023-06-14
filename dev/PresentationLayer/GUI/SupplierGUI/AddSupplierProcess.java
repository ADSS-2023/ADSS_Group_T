package PresentationLayer.GUI.SupplierGUI;

import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSupplierProcess extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ServiceFactory sf;

    public AddSupplierProcess(ServiceFactory sf) {
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

        // Step 4: Confirmation
        JPanel step4Panel = createStep4Panel();
        cardPanel.add(step4Panel, "step4");


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

        // Previous Button
        JButton prevButton = new JButton("Previous");

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "step1");
            }
        });
        panel.add(prevButton,BorderLayout.SOUTH);

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
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

        panel.add(deliveryOptionsPanel, BorderLayout.SOUTH);

        // Previous Button
        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "step2");
            }
        });
        panel.add(prevButton, BorderLayout.WEST);

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "step4");
            }
        });
        panel.add(nextButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createStep4Panel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Supplier Confirmation
        JLabel confirmationLabel = new JLabel("Supplier Added Successfully!");
        panel.add(confirmationLabel, BorderLayout.CENTER);

        // Deliver by Supplier Checkbox
        JCheckBox deliverBySupplierCheckbox = new JCheckBox("Supplier Delivers by Themselves");
        panel.add(deliverBySupplierCheckbox, BorderLayout.SOUTH);

        // Previous Button
        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "step3");
            }
        });
        panel.add(prevButton, BorderLayout.WEST);

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(nextButton, BorderLayout.EAST);

        return panel;
    }

}
