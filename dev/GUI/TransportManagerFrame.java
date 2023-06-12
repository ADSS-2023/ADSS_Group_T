package GUI;

import GUI.Generic.*;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.ServiceFactory;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransportManagerFrame extends GenericFrameUser {
    private final LogisticCenterService logisticCenterService;
    private final DeliveryService deliveryService;
    private final SupplierService supplierService;
    private final BranchService branchService;

    public TransportManagerFrame(ServiceFactory serviceFactory) {
        super(serviceFactory);
        setTitle("Transport Manager");
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.supplierService = serviceFactory.getSupplierService();
        this.branchService = serviceFactory.getBranchService();
        this.serviceFactory.setTransportManagerFrame(this);
        serviceFactory.callbackEnterWeight(this::enterWeightFunction);
        serviceFactory.callbackEnterOverWeight(this::enterOverWeightAction);


        try {
            deliveryService.initCounters();
        }
        catch (Exception exception){
            setErrorText(exception.toString());
        }

        GenericButton skipDayButton = new GenericButton("Skip day");
        leftPanel.add((skipDayButton));

        GenericButton enterNewDeliveryButton = new GenericButton("Enter new delivery");
        leftPanel.add((enterNewDeliveryButton));

        GenericButton enterNewTruckButton = new GenericButton("Enter new truck");
        leftPanel.add((enterNewTruckButton));

        GenericButton enterNewSupplierButton = new GenericButton("Enter new supplier");
        leftPanel.add((enterNewSupplierButton));

        GenericButton enterNewBranchButton = new GenericButton("Enter new branch");
        leftPanel.add((enterNewBranchButton));

        GenericButton showLogicCenterProductsButton = new GenericButton("Show logic center products");
        leftPanel.add((showLogicCenterProductsButton));

        GenericButton showAllDeliveriesButton = new GenericButton("Show all deliveries");
        leftPanel.add((showAllDeliveriesButton));

        GenericButton addNewProductsButton = new GenericButton("Add new products");
        leftPanel.add((addNewProductsButton));


        //TODO: implement this button
        skipDayButton.addActionListener(e -> {
            System.out.println("Button skip day clicked");
            rightPanel.removeAll();
            Response nextDayDetailsResponse = ResponseSerializer.deserializeFromJson(deliveryService.getNextDayDetails());
            if (nextDayDetailsResponse.isError()) {
                setErrorText(nextDayDetailsResponse.getErrorMessage());
            } else {
                //show next day details in the right panel in big textBox
                JTextArea nextDayDetailsTextArea = new JTextArea();
                nextDayDetailsTextArea.setText((String) nextDayDetailsResponse.getReturnValue());
                rightPanel.add(nextDayDetailsTextArea);
                updateDateTime();
            }
            Response skipDayResponse = ResponseSerializer.deserializeFromJson(deliveryService.skipDay());
            if (skipDayResponse.isError()) {
                setErrorText(skipDayResponse.getErrorMessage());
            } else {
                //show next day details in the right panel in big textBox

                JTextArea skipDayTextArea = new JTextArea();
                skipDayTextArea.setText((String) skipDayResponse.getReturnValue());
                rightPanel.add(skipDayTextArea);
            }
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        enterNewDeliveryButton.addActionListener(e -> {
            System.out.println("Button enter new delivery clicked");
            rightPanel.removeAll();
            //----------Destination combobox----------------
            JComboBox<String> destinationComboBox = new JComboBox<>();
            Response response = ResponseSerializer.deserializeFromJson(logisticCenterService.getAddress());
            //list of addresses
            List<String> addresses = new ArrayList<>();
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
            } else {
                addresses.add((String) response.getReturnValue());
                response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
                if (response.isError()) {
                    setErrorText(response.getErrorMessage());
                } else {
                    //response.getReturnValue() is string of all branches each branch separated by \n
                    //add all branches to addresses
                    String[] branches = ((String) response.getReturnValue()).split("\n");
                    addresses.addAll(Arrays.asList(branches));
                    //add to right panel a combobox with all addresses
                    destinationComboBox = new JComboBox<>(addresses.toArray(new String[0]));
                }
            }
            //----------Supplier and products combobox----------------
            LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersAndProducts = new LinkedHashMap<>();
            JComboBox<String> supplierComboBox = new JComboBox<>();
            JComboBox<String> productComboBox = new JComboBox<>();
            JTextField amountTextField = new JTextField();
            GenericButton doneButton = new GenericButton("Done");

            List<String> suppliers = new ArrayList<>();
            response = ResponseSerializer.deserializeFromJson(supplierService.getAllSuppliers());
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
            } else {
                String[] suppliersArray = ((String) response.getReturnValue()).split("\n");
                suppliers.addAll(Arrays.asList(suppliersArray));
                supplierComboBox = new JComboBox<>(suppliers.toArray(new String[0]));
            }
            JComboBox<String> finalSupplierComboBox = supplierComboBox;
            supplierComboBox.addActionListener(e1 -> {
                String supplierName = (String) finalSupplierComboBox.getSelectedItem();
                if (supplierName != null) {
                    Response response1 = ResponseSerializer.deserializeFromJson(supplierService.getSupplierProducts(supplierName));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        //response1.getReturnValue() is string of all products of this supplier each product separated by \n
                        //add all products to products combobox
                        String[] products = ((String) response1.getReturnValue()).split("\n");
                        productComboBox.removeAllItems();
                        productComboBox.addItem("");
                        for (String product : products) {
                            productComboBox.addItem(product);
                        }
                    }
                }
            });
            GenericButton addProductButton = new GenericButton("Add product");
            //add productButton will be twice the size of the other buttons
            addProductButton.setPreferredSize(new Dimension(200, 50));
            addProductButton.addActionListener(e1 -> {
                String supplierName = (String) finalSupplierComboBox.getSelectedItem();
                String productName = (String) productComboBox.getSelectedItem();
                String amountString = amountTextField.getText();
                if (supplierName == null || productName == null || amountString == null || amountString.equals("")) {
                    setErrorText("Please fill all fields");
                } else {
                    int amount = Integer.parseInt(amountString);
                    if (amount <= 0) {
                        setErrorText("Amount must be positive");
                    } else {
                        if (suppliersAndProducts.containsKey(supplierName)) {
                            if (suppliersAndProducts.get(supplierName).containsKey(productName)) {
                                setErrorText("This product already exists");
                            } else {
                                suppliersAndProducts.get(supplierName).put(productName, amount);
                                setFeedbackText("Product added successfully");
                            }
                        } else {
                            LinkedHashMap<String, Integer> products = new LinkedHashMap<>();
                            products.put(productName, amount);
                            suppliersAndProducts.put(supplierName, products);
                            setFeedbackText("Product added successfully");
                        }
                    }
                }
            });
            //add date text field
            JTextField dateTextField = new JTextField();
            //button to send the hashmap suppliersAndProducts to the server
            GenericButton sendButton = new GenericButton("Send");
            JComboBox<String> finalDestinationComboBox = destinationComboBox;
            sendButton.addActionListener(e1 -> {
                String destination = (String) finalDestinationComboBox.getSelectedItem();
                if (destination == null) {
                    setErrorText("Please choose destination");
                } else {
                    if (suppliersAndProducts.isEmpty()) {
                        setErrorText("Please add products");
                    } else {
                        Response response1 = ResponseSerializer.deserializeFromJson(deliveryService.orderDelivery(destination, suppliersAndProducts, dateTextField.getText()));
                        if (response1.isError()) {
                            setErrorText(response1.getErrorMessage());
                        } else {
                            setFeedbackText("Delivery ordered successfully");
                        }
                    }
                }
            });
            rightPanel.add(new GenericLabel("choose destination:"));
            rightPanel.add(destinationComboBox);
            rightPanel.add(new GenericLabel("Choose supplier:"));
            rightPanel.add(supplierComboBox);
            rightPanel.add(new GenericLabel("Choose product:"));
            rightPanel.add(productComboBox);
            rightPanel.add(new GenericLabel("Enter amount:"));
            rightPanel.add(amountTextField);
            rightPanel.add(addProductButton);
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Choose date:"), "cell 0 6");
            rightPanel.add(dateTextField, "cell 1 6");
            rightPanel.add(sendButton, "cell 0 7");
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        enterNewTruckButton.addActionListener(e -> {
            rightPanel.removeAll();
            GenericTextField licensePlateTextField = new GenericTextField();
            GenericTextField modelTextField = new GenericTextField();
            GenericTextField netoWeightTextField = new GenericTextField();
            GenericTextField maxWeightTextField = new GenericTextField();
            String[] coolinglevels = {"non","fridge","freezer"};
            JComboBox<String> coolingLevelComboBox = new JComboBox<>(coolinglevels);
            GenericButton doneButton = new GenericButton("Done");
            doneButton.addActionListener(e1 -> {
                int licensePlate = Integer.parseInt(licensePlateTextField.getText());
                String model = modelTextField.getText();
                String netoWeightString = netoWeightTextField.getText();
                String maxWeightString = maxWeightTextField.getText();
                //coolingLevel = 0 if non, 1 if fridge, 2 if freezer
                int coolingLevel= coolingLevelComboBox.getSelectedIndex();
                if (model == null || netoWeightString == null || maxWeightString == null  ) {
                    setErrorText("Please fill all fields");
                } else {
                    int netoWeight = Integer.parseInt(netoWeightString);
                    int maxWeight = Integer.parseInt(maxWeightString);
                    if (netoWeight <= 0 || maxWeight <= 0) {
                        setErrorText("Weight must be positive");
                    } else {
                        if (netoWeight > maxWeight) {
                            setErrorText("Neto weight must be smaller than max weight");
                        } else {
                            Response response1 = ResponseSerializer.deserializeFromJson(logisticCenterService.addTruck(licensePlate, model, netoWeight, maxWeight, coolingLevel));
                            if (response1.isError()) {
                                setErrorText(response1.getErrorMessage());
                            } else {
                                setErrorText("Truck added successfully");
                            }
                        }
                    }
                }
            });
            rightPanel.add(new GenericLabel("Enter license plate:"));
            rightPanel.add(licensePlateTextField);
            rightPanel.add(new GenericLabel("Enter model:"));
            rightPanel.add(modelTextField);
            rightPanel.add(new GenericLabel("Enter neto weight:"));
            rightPanel.add(netoWeightTextField);
            rightPanel.add(new GenericLabel("Enter max weight:"));
            rightPanel.add(maxWeightTextField);
            rightPanel.add(new GenericLabel("Choose cooling level:"));
            rightPanel.add(coolingLevelComboBox);
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        enterNewSupplierButton.addActionListener(e -> {
            rightPanel.removeAll();
            GenericTextField addressTextField = new GenericTextField();
            GenericTextField telephone = new GenericTextField();
            GenericTextField contactName = new GenericTextField();
            GenericTextField x = new GenericTextField();
            GenericTextField y = new GenericTextField();
            GenericButton doneButton = new GenericButton("Done");
            doneButton.addActionListener(e1 -> {
                String address = addressTextField.getText();
                String telephoneString = telephone.getText();
                String contactNameString = contactName.getText();
                String xString = x.getText();
                String yString = y.getText();
                if (address == null || telephoneString == null || contactNameString == null || xString == null || yString == null) {
                    setErrorText("Please fill all fields");
                } else {
                    int xInt = Integer.parseInt(xString);
                    int yInt = Integer.parseInt(yString);
                    if ( xInt <= 0 || yInt <= 0) {
                        setErrorText("Please enter positive numbers");
                    } else {
                        Response response1 = ResponseSerializer.deserializeFromJson(supplierService.addSupplier(address, telephoneString, contactNameString, xInt, yInt));
                        if (response1.isError()) {
                            setErrorText(response1.getErrorMessage());
                        } else {
                            setErrorText("Supplier added successfully");
                        }
                    }
                }
            });
            rightPanel.add(new GenericLabel("Enter address:"));
            rightPanel.add(addressTextField);
            rightPanel.add(new GenericLabel("Enter telephone:"));
            rightPanel.add(telephone);
            rightPanel.add(new GenericLabel("Enter contact name:"));
            rightPanel.add(contactName);
            rightPanel.add(new GenericLabel("Enter x:"));
            rightPanel.add(x);
            rightPanel.add(new GenericLabel("Enter y:"));
            rightPanel.add(y);
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        enterNewBranchButton.addActionListener(e -> {
            rightPanel.removeAll();
            GenericTextField addressTextField = new GenericTextField();
            GenericTextField telephone = new GenericTextField();
            GenericTextField contactName = new GenericTextField();
            GenericTextField x = new GenericTextField();
            GenericTextField y = new GenericTextField();
            GenericButton doneButton = new GenericButton("Done");
            doneButton.addActionListener(e1 -> {
                String address = addressTextField.getText();
                String telephoneString = telephone.getText();
                String contactNameString = contactName.getText();
                String xString = x.getText();
                String yString = y.getText();
                if (address == null || telephoneString == null || contactNameString == null || xString == null || yString == null) {
                    setErrorText("Please fill all fields");
                } else {

                    int xInt = Integer.parseInt(xString);
                    int yInt = Integer.parseInt(yString);
                    if ( xInt <= 0 || yInt <= 0) {
                        setErrorText("Please enter positive numbers");
                    } else {
                        Response response1 = ResponseSerializer.deserializeFromJson(branchService.addBranch(address, telephoneString, contactNameString, xInt, yInt));
                        if (response1.isError()) {
                            setErrorText(response1.getErrorMessage());
                        } else {
                            setErrorText("Branch added successfully");
                        }
                    }
                }
            });
            rightPanel.add(new GenericLabel("Enter address:"));
            rightPanel.add(addressTextField);
            rightPanel.add(new GenericLabel("Enter telephone:"));
            rightPanel.add(telephone);
            rightPanel.add(new GenericLabel("Enter contact name:"));
            rightPanel.add(contactName);
            rightPanel.add(new GenericLabel("Enter x:"));
            rightPanel.add(x);
            rightPanel.add(new GenericLabel("Enter y:"));
            rightPanel.add(y);
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        showLogicCenterProductsButton.addActionListener(e -> {
            rightPanel.removeAll();
                Response response1 = ResponseSerializer.deserializeFromJson(logisticCenterService.getProductsInStock());
                if (response1.isError()) {
                    setErrorText(response1.getErrorMessage());
                } else {
                    String products = (String) response1.getReturnValue();
                    //the products string of products separated by \n
                    String[] productsArray = products.split("\n");
                    for (String product : productsArray) {
                        rightPanel.add(new GenericLabel(product));
                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                }
                rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericTextField());
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        //TODO: implement this button
        showAllDeliveriesButton.addActionListener(e -> {
            rightPanel.removeAll();
            Response response1 = ResponseSerializer.deserializeFromJson(deliveryService.showAllDeliveries());
            if (response1.isError()) {
                setErrorText(response1.getErrorMessage());
            } else {
                String deliveries = (String) response1.getReturnValue();
                //the deliveries string of deliveries separated by "Delivery ID: "
                //create a jcobmo box with all the deliveries id and when the user chooses one, show the details of the delivery




                rightPanel.add(new GenericLabel(deliveries));
                rightPanel.revalidate();
                rightPanel.repaint();
            }
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericTextField());

            rightPanel.revalidate();
            rightPanel.repaint();
        });


        addNewProductsButton.addActionListener(e -> {
                    rightPanel.removeAll();
                    //supplier JComboBox
                    JComboBox<String> supplierComboBox = new JComboBox<>();
                    Response response1 = ResponseSerializer.deserializeFromJson(supplierService.getAllSuppliers());
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        String suppliers = (String) response1.getReturnValue();
                        //the suppliers string of suppliers separated by \n
                        String[] suppliersArray = suppliers.split("\n");
                        supplierComboBox = new JComboBox<>(suppliersArray);
                    }
                    GenericTextField nameTextField = new GenericTextField();
                    String[] coolinglevels = {"non","fridge","freezer"};
                    JComboBox<String> coolingLevelComboBox = new JComboBox<>(coolinglevels);
                    GenericButton doneButton = new GenericButton("Done");
            JComboBox<String> finalSupplierComboBox = supplierComboBox;
            doneButton.addActionListener(e1 -> {
                        String name = nameTextField.getText();
                        String supplier = (String) finalSupplierComboBox.getSelectedItem();
                        int coolingLevel = coolingLevelComboBox.getSelectedIndex();
                        if (name == null || supplier == null) {
                            setErrorText("Please fill all fields");
                        } else {
                            //creat LinkedHashMap<String,Integer> products with the product name and the cooling level\
                            LinkedHashMap<String,Integer> products = new LinkedHashMap<>();
                            products.put(name,coolingLevel);
                            Response response2 = ResponseSerializer.deserializeFromJson(supplierService.addProducts(supplier,products));
                            if (response2.isError()) {
                                setErrorText(response2.getErrorMessage());
                            } else {
                                setFeedbackText("Product added successfully");
                            }
                        }
                    });
                    rightPanel.add(new GenericLabel("Enter name:"));
                    rightPanel.add(nameTextField);
                    rightPanel.add(new GenericLabel("Enter cooling level:"));
                    rightPanel.add(coolingLevelComboBox);
                    rightPanel.add(new GenericLabel("Enter supplier:"));
                    rightPanel.add(supplierComboBox);
                    rightPanel.add(doneButton);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                });

        leftPanel.revalidate();
        leftPanel.repaint();
        setVisible(true);
    }
    //CallBack-Functions:
    public int enterWeightFunction1(String address, int deliveryID) {
        Response response = ResponseSerializer.deserializeFromJson(deliveryService.getLoadedProducts(deliveryID, address));
        int[] newWieghtInt;
        if (response.isError()) {
            setErrorText(response.getErrorMessage());
        } else {
            newWieghtInt = new int[]{-1};
            JTextField deliveryDetails = new JTextField("The truck is in: " + address + "." +
                    "\nThe following products are loaded:" +
                    "\n" + response.getReturnValue());
            deliveryDetails.setEditable(false);
            JTextField newWeight = new JTextField();
            JButton done = new JButton("Done");
            JPanel panel = new JPanel(new GridLayout(3, 1));
            panel.add(deliveryDetails);
            panel.add(newWeight);
            panel.add(done);
            rightPanel.add(panel);
            int[] finalNewWieghtInt = newWieghtInt;
            done.addActionListener(e -> {
                finalNewWieghtInt[0] = Integer.parseInt(newWeight.getText());
            });
            return newWieghtInt[0];
            }
        return 0;
    }
    public int enterWeightFunction(String address, int deliveryID) {
        JTextField newWeightTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(newWeightTextField);

        Response response = ResponseSerializer.deserializeFromJson(deliveryService.getLoadedProducts(deliveryID, address));
        if (response.isError()) {
            setErrorText(response.getErrorMessage());
        } else {
            JTextField deliveryDetails = new JTextField("The truck is in: " + address + "." +
                    "\nThe following products are loaded:" +
                    "\n" + response.getReturnValue());
            deliveryDetails.setEditable(false);
            panel.add(deliveryDetails);
        }

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optionPane.createDialog("Enter new weight");
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Disable the close button
        dialog.setVisible(true);

        int result = 0; // Set default value to 0
        String newWeightText = newWeightTextField.getText();
        if (!newWeightText.isEmpty()) {
            try {
                result = Integer.parseInt(newWeightText);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }








    public int enterOverWeightAction(int deliveryID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("There is overweight in delivery " + deliveryID + ".");
        System.out.println("Please choose an action to handle the overweight:");
        System.out.println("1.drop site");
        System.out.println("2.replace truck");
        System.out.println("3.unload products");
        return scanner.nextInt();//overweight action
    }
}
