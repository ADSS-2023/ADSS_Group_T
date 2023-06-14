import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockGUI2 extends JFrame {
    private ServiceFactory sf;
    private JPanel mainPanel;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField percentageField;

    public StockGUI2(ServiceFactory sf) {
        this.sf = sf;
        initializeComponents();
        createGUI();
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Set Discount");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel);

        mainPanel.add(new JLabel("Product:"));
        JTextField productField = new JTextField();
        mainPanel.add(productField);

        mainPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        startDateField = new JTextField();
        mainPanel.add(startDateField);

        mainPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        endDateField = new JTextField();
        mainPanel.add(endDateField);

        mainPanel.add(new JLabel("Percentage Amount:"));
        percentageField = new JTextField();
        mainPanel.add(percentageField);

        JButton setDiscountButton = new JButton("Set Discount");
        setDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product = productField.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                double percentageAmount = Double.parseDouble(percentageField.getText());

                sf.inventoryService.set_discount(product, percentageAmount, endDate, startDate);

                // Show a success message or perform any other actions after setting the discount
                JOptionPane.showMessageDialog(StockGUI2.this, "Discount has been set successfully!");
            }
        });
        mainPanel.add(setDiscountButton);
    }

    private void createGUI() {
        setTitle("Stock Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);

        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of the ServiceFactory and pass it to the StockGUI constructor
        ServiceFactory sf = new ServiceFactory();
        StockGUI2 stockGUI = new StockGUI2(sf);
    }
}
