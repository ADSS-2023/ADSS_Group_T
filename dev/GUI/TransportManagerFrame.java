package GUI;
import DataLayer.HR_T_DAL.DB_init.SiteAddresses;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

public class TransportManagerFrame extends GenericFrameUser {
    private final LogisticCenterService logisticCenterService;
    private final DeliveryService deliveryService;
    private final SupplierService supplierService;
    private final BranchService branchService;

    private ArrayList<GenericButton> buttonList;

    public TransportManagerFrame(ServiceFactory serviceFactory) {
        super(serviceFactory);
        setTitle("Transport Manager");
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.supplierService = serviceFactory.getSupplierService();
        this.branchService = serviceFactory.getBranchService();
        this.serviceFactory.setTransportManagerFrame(this);
        this.buttonList = new ArrayList<>();
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
        buttonList.add(skipDayButton);

        GenericButton enterNewDeliveryButton = new GenericButton("Enter new delivery");
        leftPanel.add((enterNewDeliveryButton));
        buttonList.add(enterNewDeliveryButton);

        GenericButton enterNewTruckButton = new GenericButton("Enter new truck");
        leftPanel.add((enterNewTruckButton));
        buttonList.add(enterNewTruckButton);

        GenericButton enterNewSupplierButton = new GenericButton("Enter new supplier");
        leftPanel.add((enterNewSupplierButton));
        buttonList.add(enterNewSupplierButton);

        GenericButton enterNewBranchButton = new GenericButton("Enter new branch");
        leftPanel.add((enterNewBranchButton));
        buttonList.add(enterNewBranchButton);

        GenericButton showLogicCenterProductsButton = new GenericButton("Show logic center products");
        leftPanel.add((showLogicCenterProductsButton));
        buttonList.add(showLogicCenterProductsButton);

        GenericButton showAllDeliveriesButton = new GenericButton("Show all deliveries");
        leftPanel.add((showAllDeliveriesButton));
        buttonList.add(showAllDeliveriesButton);

        GenericButton addNewProductsButton = new GenericButton("Add new products");
        leftPanel.add((addNewProductsButton));
        buttonList.add(addNewProductsButton);



        skipDayButton.addActionListener(e -> {
            anyButtonPressed(skipDayButton);
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
            anyButtonPressed(enterNewDeliveryButton);
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
            //JTextField dateTextField = new JTextField();
            GenericDatePicker datePicker = GenericDatePicker.getNewGenericDatePicker();

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
                        Response response1 = ResponseSerializer.deserializeFromJson(deliveryService.orderDelivery(destination, suppliersAndProducts, datePicker.getDate()));
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
            rightPanel.add(datePicker, "cell 0 6");
            rightPanel.add(sendButton, "cell 0 7");
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        enterNewTruckButton.addActionListener(e -> {
            anyButtonPressed(enterNewTruckButton);
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
                                setFeedbackText("Truck added successfully");
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
            anyButtonPressed(enterNewSupplierButton);
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
                            setFeedbackText("Supplier added successfully");
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
            anyButtonPressed(enterNewBranchButton);
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
                            setFeedbackText("Branch added successfully");
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
            anyButtonPressed(showLogicCenterProductsButton);
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


        showAllDeliveriesButton.addActionListener(e -> {
            anyButtonPressed(showAllDeliveriesButton);
            rightPanel.removeAll();
            Response response1 = ResponseSerializer.deserializeFromJson(deliveryService.showAllDeliveries());
            if (response1.isError()) {
                setErrorText(response1.getErrorMessage());
            } else {
                String deliveries = (String) response1.getReturnValue();
                String[] deliveriesArray = deliveries.split("---------------------------------------");
                JComboBox<String> deliveryComboBox = new JComboBox<>();
                for (String delivery : deliveriesArray) {
                    deliveryComboBox.addItem(getDeliveryId(delivery));
                }


                GenericButton showDeliveryDetailsButton = new GenericButton("Show delivery details");

                //i want that the delivery text area will be with scroll bar and that the text area will be in the right panel
                // i want to be able to see the delivery details in the text area and scroll down to see all the if the delivery is long

                JTextArea deliveryDetailsTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(deliveryDetailsTextArea);
                GenericButton showDeliveryOnMapButton = new GenericButton("Show delivery on map");
                // Remove all components from the right panel
                rightPanel.removeAll();
                rightPanel.setLayout(new BorderLayout());

                rightPanel.add(scrollPane, BorderLayout.CENTER);
                rightPanel.add(deliveryComboBox, BorderLayout.NORTH);
                rightPanel.add(showDeliveryDetailsButton, BorderLayout.WEST);
                rightPanel.add(showDeliveryOnMapButton, BorderLayout.EAST);

                rightPanel.revalidate();
                rightPanel.repaint();




                showDeliveryDetailsButton.addActionListener(e1 -> {
                    deliveryDetailsTextArea.setText("");
                    String selectedDelivery = deliveriesArray[deliveryComboBox.getSelectedIndex()];
                    String[] deliveryDetails = selectedDelivery.split("\n");
                    for (String deliveryDetail : deliveryDetails) {
                        deliveryDetailsTextArea.append(deliveryDetail + "\n");
                    }
                });

                showDeliveryOnMapButton.addActionListener(e1 -> {
                    Response response2 = ResponseSerializer.deserializeFromJson(deliveryService.getDeliveryTrack(Integer.parseInt(deliveryComboBox.getSelectedItem().toString())));
                    if (response2.isError()) {
                        setErrorText(response2.getErrorMessage());
                    } else {


                        String track = (String) response2.getReturnValue();
                     //  String[] trackArray = track.split("\n");
                        List<String> trackArray = new java.util.ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            trackArray.add(SiteAddresses.getBranchAddress(i));
                        }

// Show track on Google Maps
                        StringBuilder urlBuilder = new StringBuilder("https://www.google.com/maps/dir/");
                        for (String trackPoint : trackArray) {
                            String encodedTrackPoint = null;
                            try {
                                encodedTrackPoint = URLEncoder.encode(trackPoint, StandardCharsets.UTF_8.toString());
                            } catch (UnsupportedEncodingException ee) {
                                ee.printStackTrace();
                            }
                            urlBuilder.append(encodedTrackPoint).append("/");
                        }
                        String url = urlBuilder.toString();

                        try {
                            // Open URL in default web browser
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException | URISyntaxException ee) {
                            ee.printStackTrace();
                        }



//קניון מול 7, HaAyin Lamed St, Be'er Sheva, 8485536

                    }
                });

            }
        });


        addNewProductsButton.addActionListener(e -> {
            anyButtonPressed(addNewProductsButton);
                    rightPanel.removeAll();
                    JComboBox<String> supplierComboBox = new JComboBox<>();
                    Response response1 = ResponseSerializer.deserializeFromJson(supplierService.getAllSuppliers());
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        String suppliers = (String) response1.getReturnValue();
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
    public int enterWeightFunction(String address, int deliveryID) {
        JTextField newWeightTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(newWeightTextField);
        Response response = ResponseSerializer.deserializeFromJson(deliveryService.getLoadedProducts(deliveryID, address));
        if (response.isError()) {
            setErrorText(response.getErrorMessage());
        } else {
            JTextArea deliveryDetails = new JTextArea("The truck is in: " + address + ".\n" + "\nThe following products are loaded:\n" + response.getReturnValue());
            deliveryDetails.setEditable(false);
            deliveryDetails.setLineWrap(true);
            deliveryDetails.setWrapStyleWord(true);
            panel.add(deliveryDetails);
        }

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optionPane.createDialog("Enter new weight");
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
        int result = 0;
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
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JTextArea deliveryDetails = new JTextArea("The truck is over weight.\n" + "\nThe delivery is:\n" + deliveryID);
        deliveryDetails.setEditable(false);
        deliveryDetails.setLineWrap(true); // Enable line wrapping
        deliveryDetails.setWrapStyleWord(true); // Wrap at word boundaries
        panel.add(deliveryDetails);
        JComboBox<String> overWeightActionComboBox = new JComboBox<>(new String[]{"1.drop site", "2.replace truck","3.unload products"});
        panel.add(overWeightActionComboBox);
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optionPane.createDialog("Enter over weight action");
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Disable the close button
        dialog.setVisible(true);
        int result = 0; // Set default value to 0
        result = overWeightActionComboBox.getSelectedIndex();
        return result;
    }
        public static String getDeliveryId(String deliveryDetails) {
            String[] lines = deliveryDetails.split("\\r?\\n");
            for (String line : lines) {
                if (line.startsWith("Delivery ID:")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        String idString = parts[1].trim();
                        return (idString);
                    }
                }
            }
            return "";
        }
    private void anyButtonPressed (GenericButton g){
        for (GenericButton x : buttonList) {
            if (!x.equals(g))
                x.setBackground(new Color(255, 255, 255));
        }
    }
}
