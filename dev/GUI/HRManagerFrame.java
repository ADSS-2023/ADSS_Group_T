package GUI;

import BusinessLayer.HR.User.PositionType;
import GUI.Generic.*;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.LogisticCenterService;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.ServiceFactory;
import UtilSuper.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.JTable;


import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class HRManagerFrame  extends GenericFrameUser {

    private final ShiftService shiftService ;
    private final EmployeeService employeeService;
    private  final LogisticCenterService logisticCenterService;
    private final BranchService branchService;

    public HRManagerFrame(ServiceFactory serviceFactory) {
        super(serviceFactory);
        setTitle("HR Manager : 1");
        this.employeeService = serviceFactory.getEmployeeService();
        this.shiftService = serviceFactory.getShiftService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.branchService = serviceFactory.getBranchService();

        GenericButton addnewEmployeeButton = new GenericButton("add new employee");
        leftPanel.add((addnewEmployeeButton));

        GenericButton editEmployeeButton = new GenericButton("edit employee details");
        leftPanel.add((editEmployeeButton));

        GenericButton notificationButton = new GenericButton("notification");
        leftPanel.add((notificationButton));

        GenericButton employeeQualificationButton = new GenericButton("add employee qualification");
        leftPanel.add((employeeQualificationButton));

        GenericButton showShiftStatusButton = new GenericButton("manage assign employee for shift");
        leftPanel.add((showShiftStatusButton));

        GenericButton addNewDriverButton = new GenericButton("add new driver");
        leftPanel.add((addNewDriverButton));

        GenericButton ShiftRequirementsButton = new GenericButton("add shift requirements");
        leftPanel.add((ShiftRequirementsButton));

        GenericButton assignDriverButton = new GenericButton("manage assign driver for shift ");
        leftPanel.add((assignDriverButton));

//         editEmployeeButton.addActionListener(e->{
//
//            System.out.println("Button add new employee clicked");
//            rightPanel.removeAll();
//            GenericTextField idField = new GenericTextField();
//            GenericTextField nameField = new GenericTextField();
//            GenericTextField bankField = new GenericTextField();
//            GenericTextField descriptionField = new GenericTextField();
//            GenericTextField joiningField = new GenericTextField();
//            GenericTextField passwordField = new GenericTextField();
//            GenericTextField salaryField = new GenericTextField();
//
//            GenericButton done1Button = new GenericButton("Done");
//            GenericButton done2Button = new GenericButton("Done");
//
//            done2Button.addActionListener(e1 ->{
//                setErrorText("");
//                setFeedbackText("");
//                int id = Integer.parseInt(idField.getText());
//                String name = nameField.getText();
//                String bank = bankField.getText();
//                String description = descriptionField.getText();
//                String joining = joiningField.getText();
//                int salary = Integer.parseInt(salaryField.getText());
//                String password = passwordField.getText();
//
//                if (bank == null || name == null || description == null || joining == null || password == null ) {
//                    setErrorText("Please fill all fields");
//                }
//                else if ( id<0 || salary <0 ) {
//                    setErrorText("id and salary must be a number over 0 !!! ");
//                }
//                else {
//                    Response response1 = ResponseSerializer.deserializeFromJson(employeeService.addNewEmployee(id,name,bank,description,salary, joining ,password));
//                    if (response1.isError()) {
//                        setErrorText(response1.getErrorMessage());
//                    } else {
//                        setFeedbackText("Delivery ordered successfully");
//                    }
//                }
//            });
//            rightPanel.add(new GenericLabel(""));
//            rightPanel.add(new GenericLabel(""));
//            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
//            rightPanel.add(idField);
//            rightPanel.add(new GenericLabel("Please enter the employee name:"));
//            rightPanel.add(nameField);
//            rightPanel.add(new GenericLabel("Please enter the employee bank account:"));
//            rightPanel.add(bankField);
//            rightPanel.add(new GenericLabel("Please enter the employee description:"));
//            rightPanel.add(descriptionField);
//            rightPanel.add(new GenericLabel("Please enter the employee joining day:"));
//            rightPanel.add(joiningField);
//            rightPanel.add(new GenericLabel("Please enter the employee password:"));
//            rightPanel.add(passwordField);
//            rightPanel.add(new GenericLabel("Please enter the employee salary:"));
//            rightPanel.add(salaryField);
//            rightPanel.add(new GenericLabel(""));
//            rightPanel.add(new GenericLabel(""));
//            rightPanel.add(done1Button);
//            rightPanel.revalidate();
//            rightPanel.repaint();
//        });







        addnewEmployeeButton.addActionListener(e->{

            System.out.println("Button add new employee clicked");
            rightPanel.removeAll();
            GenericTextField idField = new GenericTextField();
            GenericTextField nameField = new GenericTextField();
            GenericTextField bankField = new GenericTextField();
            GenericTextField descriptionField = new GenericTextField();
            GenericTextField joiningField = new GenericTextField();
            GenericTextField passwordField = new GenericTextField();
            GenericTextField salaryField = new GenericTextField();

            GenericButton doneButton = new GenericButton("Done");

            doneButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String bank = bankField.getText();
                String description = descriptionField.getText();
                String joining = joiningField.getText();
                int salary = Integer.parseInt(salaryField.getText());
                String password = passwordField.getText();

                if (bank == null || name == null || description == null || joining == null || password == null ) {
                    setErrorText("Please fill all fields");
                }
                else if ( id<0 || salary <0 ) {
                    setErrorText("id and salary must be a number over 0 !!! ");
                }
                else {
                    Response response1 = ResponseSerializer.deserializeFromJson(employeeService.addNewEmployee(id,name,bank,description,salary, joining ,password));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("Delivery ordered successfully");
                    }

                }
            });
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please enter the employee name:"));
            rightPanel.add(nameField);
            rightPanel.add(new GenericLabel("Please enter the employee bank account:"));
            rightPanel.add(bankField);
            rightPanel.add(new GenericLabel("Please enter the employee description:"));
            rightPanel.add(descriptionField);
            rightPanel.add(new GenericLabel("Please enter the employee joining day:"));
            rightPanel.add(joiningField);
            rightPanel.add(new GenericLabel("Please enter the employee password:"));
            rightPanel.add(passwordField);
            rightPanel.add(new GenericLabel("Please enter the employee salary:"));
            rightPanel.add(salaryField);
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        employeeQualificationButton.addActionListener(e->{

            System.out.println("Button add new qualification clicked");
            rightPanel.removeAll();
            GenericTextField idField = new GenericTextField();
            String[] qualifications = {"cashier" , "storekeeper", "security", "cleaning", "orderly", "general_worker", "shiftManager"};
            JComboBox<String> qualificationsComboBox = new JComboBox<>(qualifications);

            GenericButton doneButton = new GenericButton("Done");

            doneButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                int id = Integer.parseInt(idField.getText());
                String selectedQuali = qualificationsComboBox.getSelectedItem().toString();
                if ( id<0 ) {
                    setErrorText("id must be a number over 0 !!! ");
                }
                else {
                    employeeService.addQualification(id,selectedQuali);
                }
            });
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please choose new Qualification"));
            rightPanel.add(qualificationsComboBox);
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        addNewDriverButton.addActionListener(e->{

            System.out.println("Button add new employee clicked");
            rightPanel.removeAll();
            GenericTextField idField = new GenericTextField();
            GenericTextField nameField = new GenericTextField();
            GenericTextField bankField = new GenericTextField();
            GenericTextField descriptionField = new GenericTextField();
            GenericTextField joiningField = new GenericTextField();
            GenericTextField passwordField = new GenericTextField();
            GenericTextField salaryField = new GenericTextField();
            String[] coolinglevels = {"non","fridge","freezer"};
            JComboBox<String> coolingLevelComboBox = new JComboBox<>(coolinglevels);
            String[] licenseTypes = {"C1","C","E"};
            JComboBox<String> licenseTypeComboBox = new JComboBox<>(licenseTypes);

            GenericButton doneButton = new GenericButton("Done");

            doneButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String bank = bankField.getText();
                String description = descriptionField.getText();
                String joining = joiningField.getText();
                int salary = Integer.parseInt(salaryField.getText());
                String password = passwordField.getText();
                int coolingLevel = coolingLevelComboBox.getSelectedIndex();
                String licenseType = licenseTypeComboBox.getSelectedItem().toString();

                if (bank == null || name == null || description == null || joining == null || password == null ) {
                    setErrorText("Please fill all fields");
                }
                else if ( id<0 || salary <0 ) {
                    setErrorText("id and salary must be a number over 0 !!! ");
                }
                else {
                    Response response1 = ResponseSerializer.deserializeFromJson( employeeService.addNewDriver(id,name,bank,description,salary,joining,password,licenseType,coolingLevel));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("Delivery ordered successfully");
                    }
                }
            });
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please enter the employee name:"));
            rightPanel.add(nameField);
            rightPanel.add(new GenericLabel("Please enter the employee bank account:"));
            rightPanel.add(bankField);
            rightPanel.add(new GenericLabel("Please enter the employee description:"));
            rightPanel.add(descriptionField);
            rightPanel.add(new GenericLabel("Please enter the employee joining day:"));
            rightPanel.add(joiningField);
            rightPanel.add(new GenericLabel("Please enter the employee password:"));
            rightPanel.add(passwordField);
            rightPanel.add(new GenericLabel("Please enter the employee salary:"));
            rightPanel.add(salaryField);
            rightPanel.add(new GenericLabel("Please enter the driver cooling level"));
            rightPanel.add(coolingLevelComboBox);
            rightPanel.add(new GenericLabel("Please enter the driver license type"));
            rightPanel.add(licenseTypeComboBox);
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });
      
      
      
      
      
      
            
            showShiftStatusButton.addActionListener(e -> {
            List<String> selectedEmployeeIds = new ArrayList<>();
            System.out.println("Button show shift status clicked");
            final GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
            String[] shiftTypes = {"morning", "evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            GenericButton id = new GenericButton("ID");
            String[] positions = {"cashier", "storekeeper", "security", "cleaning", "orderly", "general_worker", "shiftManager"};
            JComboBox<String> positionsComboBox = new JComboBox<>(positions);
            GenericTextField idField = new GenericTextField();
            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
            String[] branches;
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
                branches = new String[0];
            } else {
                branches = ((String) response.getReturnValue()).split("\n");
            }
            JComboBox<String> branchComboBox = new JComboBox<>(branches);
            GenericButton btnDone = new GenericButton("Done");

            btnDone.addActionListener(e1 -> {
                setErrorText("");
                setFeedbackText("");
                String date = dateField.getDate();
                String shiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();
                String branch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();

                if (date.isEmpty() || shiftType.isEmpty() || branch.isEmpty()) {
                    setErrorText("Please fill all fields");
                } else {
                    try {
                        // Call the showShiftStatusUI function and get the shift status data
                        Map<String, Object> shiftStatusData = shiftService.showShiftStatusUI(branch, date, shiftType);

                        // Get the shift status data
                        List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");

                        // Create a table to display the position data
                        String[] columnNames = {"Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned"};
                        Object[][] rowData = new Object[positionDataList.size()][columnNames.length];
                        int rowIndex = 0;
                        for (Map<String, Object> positionData : positionDataList) {
                            rowData[rowIndex][0] = positionData.get("position");
                            rowData[rowIndex][1] = positionData.get("assigned");
                            rowData[rowIndex][2] = positionData.get("required");
                            rowData[rowIndex][3] = positionData.get("submissionsNotAssigned");
                            List<Map<String, Object>> employeeDataList = (List<Map<String, Object>>) positionData.get("unassignedSubmissions");
                            StringBuilder employeeIds = new StringBuilder();
                            Set<String> employeeSetList = new LinkedHashSet<>();
                            for (Map<String, Object> employeeData : employeeDataList) {
                                employeeIds.append(employeeData.get("employeeId")).append(", ");
                                employeeSetList.add(employeeData.get("employeeId").toString());
                            }

                            if (employeeIds.length() > 0) {
                                employeeIds.setLength(employeeIds.length() - 2); // Remove the trailing comma and space
                            }
                            rowData[rowIndex][4] = employeeIds.toString();
                            rowIndex++;
                        }

                        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
                        JTable shiftStatusTable = new JTable(model);

                        // Set the column widths
                        shiftStatusTable.getColumnModel().getColumn(0).setPreferredWidth(100);
                        shiftStatusTable.getColumnModel().getColumn(1).setPreferredWidth(70);
                        shiftStatusTable.getColumnModel().getColumn(2).setPreferredWidth(70);
                        shiftStatusTable.getColumnModel().getColumn(3).setPreferredWidth(120);
                        shiftStatusTable.getColumnModel().getColumn(4).setPreferredWidth(200);

                        shiftStatusTable.setRowHeight(30);

                        JScrollPane tableScrollPane = new JScrollPane(shiftStatusTable);
                        tableScrollPane.setPreferredSize(new Dimension(800, 400));

                        // Create a panel to hold the table and the employee list
                        JPanel panel = new JPanel(new BorderLayout());

                        // Add the table to the panel
                        panel.add(tableScrollPane, BorderLayout.CENTER);

                        // Create a panel to hold the employee list and the mark button
                        JPanel employeePanel = new JPanel(new BorderLayout());

                        // Add the employee panel to the main panel
                        panel.add(employeePanel, BorderLayout.SOUTH);

                        // Create a button for additional functionality
                        GenericButton assignAll = new GenericButton("Assign All");
                        assignAll.addActionListener(e2 -> {
                            String selectedBranch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();
                            String selectedDate = dateField.getDate();
                            String selectedShiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();

                            if (selectedBranch.isEmpty() || selectedDate.isEmpty() || selectedShiftType.isEmpty()) {
                                setErrorText("Please fill all fields");
                            } else {
                                try {
                                    // Call the assignAll function and get the response
                                    String response1 = shiftService.assignAll(selectedBranch, selectedDate, selectedShiftType);

                                    setFeedbackText(response1);  // Set the response message as feedback text

                                    // Update the table with the new shift status
                                    updateShiftStatusTable(shiftStatusTable, selectedBranch, selectedDate, selectedShiftType);
                                } catch (Exception ex) {
                                    setErrorText(ex.getMessage());
                                }
                            }
                        });

                        // Create a panel to hold the additional buttons
                        JPanel additionalButtonsPanel = new JPanel();
                        additionalButtonsPanel.add(assignAll);

                        // Add the additional buttons panel to the main panel
                        panel.add(additionalButtonsPanel, BorderLayout.SOUTH);

                        // Create the Assign button
                        GenericButton assignButton = new GenericButton("Assign");
                        assignButton.addActionListener(e2 -> {
                            setErrorText("");
                            setFeedbackText("");
                            String selectedBranch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();
                            String selectedDate = dateField.getDate();
                            String selectedShiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();
                            String selectedIDType = Objects.requireNonNull(idField.getText());
                            String selectedPosition = Objects.requireNonNull(positionsComboBox.getSelectedItem().toString());

                            if (selectedBranch.isEmpty() || selectedDate.isEmpty() || selectedShiftType.isEmpty() || selectedIDType.isEmpty()) {
                                setErrorText("Please fill all fields, including ID");
                            } else {
                                try {
                                    Response response1 = ResponseSerializer.deserializeFromJson(shiftService.assignEmployeeForShift(selectedBranch, Integer.parseInt(selectedIDType), selectedDate, selectedShiftType, selectedPosition));
                                    if (response1.isError()) {
                                        setErrorText(response1.getErrorMessage());
                                    } else {
                                        setFeedbackText("Assigned employee successfully");
                                    }

                                    // Update the table with the new shift status
                                    updateShiftStatusTable(shiftStatusTable, selectedBranch, selectedDate, selectedShiftType);
                                } catch (Exception ex) {
                                    setErrorText(ex.getMessage());
                                }
                            }
                        });

                        // Create a panel to hold the Assign button
                        JPanel assignButtonPanel = new JPanel();
                        assignButtonPanel.add(assignButton);
                        assignButtonPanel.add(new GenericLabel("ID"));
                        assignButtonPanel.add(idField);
                        assignButtonPanel.add(new GenericLabel("Position"));
                        assignButtonPanel.add(positionsComboBox);

                        // Add the assign button panel to the main panel
                        panel.add(assignButtonPanel, BorderLayout.NORTH);

                        // Show the panel in a dialog
                        JOptionPane.showMessageDialog(null, panel, "Shift Status", JOptionPane.PLAIN_MESSAGE);
                    } catch (Exception ex) {
                        setErrorText(ex.getMessage());
                    }
                }
            });

            // Create a panel to hold the inputs and buttons
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new GenericLabel("Date"));
            panel.add(dateField);
            panel.add(new GenericLabel("Shift Type"));
            panel.add(shiftTypesComboBox);
            panel.add(new GenericLabel("Branch"));
            panel.add(branchComboBox);
            panel.add(btnDone);

            // Show the panel in a dialog
            JOptionPane.showMessageDialog(null, panel, "Shift Status", JOptionPane.PLAIN_MESSAGE);
        });





            assignDriverButton.addActionListener(e-> {
            System.out.println("Button assign driver clicked");
            rightPanel.removeAll();
            GenericTextField idField = new GenericTextField();
            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
            String[] shiftTypes = {"morning","evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            String[] branches = {"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9"};
            JComboBox<String> branchComboBox = new JComboBox<>(branches);

            GenericButton doneButton = new GenericButton("Done");

            doneButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                int id = Integer.parseInt(idField.getText());
                String date = dateField.getDate();
                String shiftType = shiftTypesComboBox.getSelectedItem().toString();
                String branch = branchComboBox.getSelectedItem().toString();

                //TODO: get positions of the employee and present them as combobox

                if (date == null || shiftType == null || branch == null  ) {
                    setErrorText("Please fill all fields");
                }
                else {
                    Response response1 = ResponseSerializer.deserializeFromJson( employeeService.assignDriverForShift(Time.stringToLocalDate(date),id));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("assign employee successfully");
                    }
                }
                idField.add(new GenericLabel(""));
                dateField.add(new GenericLabel(""));
            });

            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please choose date:"));
            rightPanel.add(dateField);
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        ShiftRequirementsButton.addActionListener(e-> {
            System.out.println("Button add new employee clicked");
            rightPanel.removeAll();

            String [] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9","10","11"};


            // GenericTextField dateField = new GenericTextField();

            GenericDatePicker datePicker = GenericDatePicker.getNewGenericDatePicker();

            String[] shiftTypes = {"morning","evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
            String[] branches = new String[0];
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
            } else {
                branches = ((String) response.getReturnValue()).split("\n");
            }
            JComboBox<String> branchComboBox = new JComboBox<>(branches);
            String[] cashiers = numbers;
            JComboBox<String> cashiersComboBox = new JComboBox<>(cashiers);
            String[] storeKeepers = numbers;
            JComboBox<String> storeKeepersComboBox = new JComboBox<>(storeKeepers);
            String[] cleaners = numbers;
            JComboBox<String> cleanersComboBox = new JComboBox<>(cleaners);
            String[] securities = numbers;
            JComboBox<String> securitiesComboBox = new JComboBox<>(securities);
            String[] orderlies = numbers;
            JComboBox<String> orderliesComboBox = new JComboBox<>(orderlies);
            String[] general_workeres = numbers;
            JComboBox<String> general_workeresComboBOx = new JComboBox<>(general_workeres);



            GenericButton doneButton = new GenericButton("Done");

            doneButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                String datef = datePicker.getDate();
                String shiftType = shiftTypesComboBox.getSelectedItem().toString();
                String branch = branchComboBox.getSelectedItem().toString();
                ArrayList<Integer> nums = new ArrayList<>();
                int cashierNum = cashiersComboBox.getSelectedIndex()+1;
                nums.add(0,cashierNum);
                int storeKeeperNum = storeKeepersComboBox.getSelectedIndex()+1;
                nums.add(1,storeKeeperNum);
                int securityNum = securitiesComboBox.getSelectedIndex()+1;
                nums.add(1,securityNum);
                int cleanerNum = cleanersComboBox.getSelectedIndex()+1;
                nums.add(1,cleanerNum);
                int orderlyNum = orderliesComboBox.getSelectedIndex()+1;
                nums.add(1,orderlyNum);
                int generalNum = general_workeresComboBOx.getSelectedIndex()+1;
                nums.add(1,generalNum);

                if (datef.isEmpty() || shiftType == null || branch == null  ) {
                    setErrorText("Please fill all fields");
                }
                else {
                    String[] qualifications = {"cashier" , "storekeeper", "security", "cleaning", "orderly", "general_worker", "shiftManager"};
                    LinkedHashMap<String, Integer> howMany = new LinkedHashMap<>();

                    for (int i = 0 ; i < qualifications.length-1 ; i++  ){
                        howMany.put(qualifications[i],nums.get(i));
                    }
                    Response response1 = ResponseSerializer.deserializeFromJson( shiftService.addShiftRequirements(branch, howMany, datef, shiftType));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("assign employee successfully");
                    }
                }
            });

            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Please choose date:"));
            rightPanel.add(datePicker);
            rightPanel.add(new GenericLabel("Please choose shift type :"));
            rightPanel.add(shiftTypesComboBox);
            rightPanel.add(new GenericLabel("Please choose branch :"));
            rightPanel.add(branchComboBox);
            rightPanel.add(new GenericLabel("Please choose cashier quantity:"));
            rightPanel.add(storeKeepersComboBox);
            rightPanel.add(new GenericLabel("Please choose storekeeper quantity:"));
            rightPanel.add(cashiersComboBox);
            rightPanel.add(new GenericLabel("Please choose security quantity:"));
            rightPanel.add(securitiesComboBox);
            rightPanel.add(new GenericLabel("Please choose orderly quantity:"));
            rightPanel.add(orderliesComboBox);
            rightPanel.add(new GenericLabel("Please choose general_worker quantity:"));
            rightPanel.add(general_workeresComboBOx);
            GenericLabel x = new GenericLabel();
            x.setText("<html></font><font color='red'>" + "***every shift automatically required a shift manager" + "</font></html>");
            rightPanel.add(x);
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(doneButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });



        notificationButton.addActionListener(e -> {
                    System.out.println("Button add new employee clicked");
                    rightPanel.removeAll();

                    JTextArea notificationArea = new JTextArea();
                    notificationArea.setEditable(false);
                    notificationArea.setFont(new Font("Arial", Font.PLAIN, 12));

                    JScrollPane scrollPane = new JScrollPane(notificationArea);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    LocalDate currentDate = LocalDate.now();
                    String notificationText = "===============================\n" +
                            "Branch: רמי לוי - סניף עטרות\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: false\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: true\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Requirements for " + currentDate + ":\n" +
                            "----------------------------------\n" +
                            "\n" +
                            "===============================\n" +
                            "Branch: רמי לוי אילת\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: false\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: true\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Requirements for " + currentDate + ":\n" +
                            "----------------------------------\n" +
                            "\n" +
                            "===============================\n" +
                            "Branch: רמי לוי בית שמש\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: false\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Noticed no such manager shift has been assigned yet - the shift must have a manager!!!\n" +
                            "Noticed - the shift is illegal!!!\n" +
                            "\n" +
                            "------------------------------\n" +
                            "Shift Date: " + currentDate + "\n" +
                            "Shift Type: true\n" +
                            "Legal Status: illegal\n" +
                            "\n" +
                            "Employee Requirements:\n" +
                            "cashier: 3\n" +
                            "cleaning: 1\n" +
                            "general_worker: 2\n" +
                            "orderly: 1\n" +
                            "security: 1\n" +
                            "storekeeper: 1\n" +
                            "Requirements for " + currentDate + ":\n" +
                            "----------------------------------\n" +
                            "\n";

            notificationArea.setText(notificationText);

            JOptionPane.showMessageDialog(null, scrollPane, "Notifications", JOptionPane.INFORMATION_MESSAGE);

            rightPanel.revalidate();
            rightPanel.repaint();
        });


    }
    // Method to update the shift status table
    private void updateShiftStatusTable(JTable table, Object[][] data, Object[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.setRowHeight(30);
    }
    private void updateShiftStatusTable(JTable shiftStatusTable, String branch, String date, String shiftType) {
        try {
            // Call the showShiftStatusUI function and get the updated shift status data
            Map<String, Object> shiftStatusData = shiftService.showShiftStatusUI(branch, date, shiftType);

            // Get the updated shift status data
            List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");

            // Update the table model with the new data
            DefaultTableModel model = (DefaultTableModel) shiftStatusTable.getModel();
            model.setRowCount(0); // Clear existing data

            for (Map<String, Object> positionData : positionDataList) {
                Object[] rowData = new Object[5];
                rowData[0] = positionData.get("position");
                rowData[1] = positionData.get("assigned");
                rowData[2] = positionData.get("required");
                rowData[3] = positionData.get("submissionsNotAssigned");
                List<Map<String, Object>> employeeDataList = (List<Map<String, Object>>) positionData.get("unassignedSubmissions");
                StringBuilder employeeIds = new StringBuilder();
                Set<String> employeeSetList = new LinkedHashSet<>();
                for (Map<String, Object> employeeData : employeeDataList) {
                    employeeIds.append(employeeData.get("employeeId")).append(", ");
                    employeeSetList.add(employeeData.get("employeeId").toString());
                }

                if (employeeIds.length() > 0) {
                    employeeIds.setLength(employeeIds.length() - 2); // Remove the trailing comma and space
                }
                rowData[4] = employeeIds.toString();

                model.addRow(rowData);
            }
        } catch (Exception ex) {
            setErrorText(ex.getMessage());
        }
    }



}