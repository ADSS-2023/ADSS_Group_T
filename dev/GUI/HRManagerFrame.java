package GUI;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import javax.swing.JTable;


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

        GenericButton notificationButton = new GenericButton("notification");
        leftPanel.add((notificationButton));

        GenericButton employeeQualificationButton = new GenericButton("add employee qualification");
        leftPanel.add((employeeQualificationButton));

        GenericButton showShiftStatusButton = new GenericButton("show shift status");
        leftPanel.add((showShiftStatusButton));

        GenericButton addNewDriverButton = new GenericButton("add new driver");
        leftPanel.add((addNewDriverButton));

        GenericButton assignEmployeeButton = new GenericButton("manage assign employee for shift");
        leftPanel.add((assignEmployeeButton));

        GenericButton ShiftRequirementsButton = new GenericButton("add shift requirements");
        leftPanel.add((ShiftRequirementsButton));

        GenericButton assignDriverButton = new GenericButton("manage assign driver for shift ");
        leftPanel.add((assignDriverButton));


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

        assignEmployeeButton.addActionListener(e->{
            System.out.println("Button add new employee clicked");
            rightPanel.removeAll();
            GenericTextField idField = new GenericTextField();
            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
            String[] shiftTypes = {"morning","evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            //TODO: get branches from service and not like this
            //String[] branches = {"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9"};
            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
            String[] branches = new String[0];
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
            } else {
                branches = ((String) response.getReturnValue()).split("\n");
            }
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
                    Response response1 = ResponseSerializer.deserializeFromJson( shiftService.assignEmployeeForShift(branch,id,date,shiftType,"cashier"));
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
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please enter date:"));
            rightPanel.add(dateField);
            rightPanel.add(new GenericLabel("Please choose shift type :"));
            rightPanel.add(shiftTypesComboBox);
            rightPanel.add(new GenericLabel("Please choose branch :"));
            rightPanel.add(branchComboBox);
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







        showShiftStatusButton.addActionListener(e -> {
            System.out.println("Button show shift status clicked");
            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
            String[] shiftTypes = { "morning", "evening" };
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
            String[] branches = new String[0];
            if (response.isError()) {
                setErrorText(response.getErrorMessage());
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
                        Map<String, Object> shiftStatusData = shiftService.ShowShiftStatusUI(branch, date, shiftType);

                        boolean isLegalShift = (boolean) shiftStatusData.get("isLegalShift");
                        List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");

                        // Create a table to display the position data
                        String[] columnNames = { "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned", "Assign", "Mark" };
                        Object[][] rowData = new Object[positionDataList.size()][columnNames.length];
                        int rowIndex = 0;
                        for (Map<String, Object> positionData : positionDataList) {
                            rowData[rowIndex][0] = positionData.get("position");
                            rowData[rowIndex][1] = positionData.get("assigned");
                            rowData[rowIndex][2] = positionData.get("required");
                            rowData[rowIndex][3] = positionData.get("submissionsNotAssigned");
                            rowData[rowIndex][4] = positionData.get("unassignedSubmissions");
                            rowData[rowIndex][5] = "Assign"; // Assign button text
                            rowData[rowIndex][6] = false; // Mark checkbox initial value
                            rowIndex++;
                        }

                        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
                            @Override
                            public Class<?> getColumnClass(int columnIndex) {
                                if (columnIndex == 5 || columnIndex == 6) {
                                    return Boolean.class;
                                }
                                return super.getColumnClass(columnIndex);
                            }
                        };

                        JTable shiftStatusTable = new JTable(model);

                        // Assign button renderer and editor
                        shiftStatusTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                        shiftStatusTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), shiftStatusTable));


                        // Mark checkbox renderer and editor
                        shiftStatusTable.getColumnModel().getColumn(6).setCellRenderer(new CheckBoxRenderer());
                        shiftStatusTable.getColumnModel().getColumn(6).setCellEditor(new CheckBoxEditor(new JCheckBox()));

                        JScrollPane tableScrollPane = new JScrollPane(shiftStatusTable);
                        tableScrollPane.setPreferredSize(new Dimension(400, 300));

                        JPanel assignPanel = new JPanel();
                        assignPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                        JButton btnAssign = new JButton("Assign");
                        JButton btnAssignAll = new JButton("Assign All");

                        btnAssign.addActionListener(e2 -> {
                            int[] selectedRows = shiftStatusTable.getSelectedRows();
                            for (int rowIdx : selectedRows) {
                                String position = (String) shiftStatusTable.getValueAt(rowIdx, 0);
                                // Perform the assignment logic for the selected position
                                // ...
                                // Update the table model or perform any necessary actions
                                // ...
                            }
                        });

                        btnAssignAll.addActionListener(e3 -> {
                            // Perform assignment logic for all positions
                            // ...
                            // Update the table model or perform any necessary actions
                            // ...
                        });

                        assignPanel.add(btnAssign);
                        assignPanel.add(btnAssignAll);


                        rightPanel.removeAll();
                        rightPanel.setLayout(new BorderLayout());
                        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
                        rightPanel.add(assignPanel, BorderLayout.SOUTH);

                        rightPanel.revalidate();
                        rightPanel.repaint();
                    } catch (Exception ex) {
                        setErrorText("Error occurred while retrieving shift status: " + ex.getMessage());
                    }
                }
            });

            rightPanel.removeAll();
            rightPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            rightPanel.add(new GenericLabel(""), gbc);
            gbc.gridy++;
            rightPanel.add(new GenericLabel("Please enter date:"), gbc);
            gbc.gridy++;
            rightPanel.add(dateField, gbc);
            gbc.gridy++;
            rightPanel.add(new GenericLabel("Please choose shift type:"), gbc);
            gbc.gridy++;
            rightPanel.add(shiftTypesComboBox, gbc);
            gbc.gridy++;
            rightPanel.add(new GenericLabel("Please choose branch:"), gbc);
            gbc.gridy++;
            rightPanel.add(branchComboBox, gbc);
            gbc.gridy++;
            rightPanel.add(new GenericLabel(""), gbc);
            gbc.gridy++;
            rightPanel.add(btnDone, gbc);

            rightPanel.revalidate();
            rightPanel.repaint();
        });











































//
//        showShiftStatusButton.addActionListener(e -> {
//            System.out.println("Button show shift status clicked");
//            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
//            String[] shiftTypes = { "morning", "evening" };
//            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
//            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
//            String[] branches = new String[0];
//            if (response.isError()) {
//                setErrorText(response.getErrorMessage());
//            } else {
//                branches = ((String) response.getReturnValue()).split("\n");
//            }
//            JComboBox<String> branchComboBox = new JComboBox<>(branches);
//            GenericButton btnDone = new GenericButton("Done");
//
//
//            btnDone.addActionListener(e1 -> {
//                setErrorText("");
//                setFeedbackText("");
//                String date = dateField.getDate();
//                String shiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();
//                String branch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();
//
//                if (date.isEmpty() || shiftType.isEmpty() || branch.isEmpty()) {
//                    setErrorText("Please fill all fields");
//                } else {
//                    try {
//                        // Call the showShiftStatusUI function and get the shift status data
//                        Map<String, Object> shiftStatusData = shiftService.ShowShiftStatusUI(branch, date, shiftType);
//
//                        boolean isLegalShift = (boolean) shiftStatusData.get("isLegalShift");
//                        List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");
//
//                        // Create a table to display the position data
//                        String[] columnNames = { "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned" };
//                        Object[][] rowData = new Object[positionDataList.size()][columnNames.length];
//                        int rowIndex = 0;
//                        for (Map<String, Object> positionData : positionDataList) {
//                            rowData[rowIndex][0] = positionData.get("position");
//                            rowData[rowIndex][1] = positionData.get("assigned");
//                            rowData[rowIndex][2] = positionData.get("required");
//                            rowData[rowIndex][3] = positionData.get("submissionsNotAssigned");
//                            rowData[rowIndex][4] = positionData.get("unassignedSubmissions");
//                            rowIndex++;
//                        }
//
//                        JTable shiftStatusTable = new JTable(rowData, columnNames);
//                        JLabel shiftStatusLabel;
//                        if (isLegalShift) {
//                            shiftStatusLabel = new GenericLabel("The shift is approved");
//                        } else {
//                            shiftStatusLabel = new GenericLabel((String) shiftStatusData.get("missingRequirements"));
//                        }
//
//                        // Create a JScrollPane and set the table as its view
//                        JScrollPane tableScrollPane = new JScrollPane(shiftStatusTable);
//                        // Customize the scroll pane
//                        tableScrollPane.setPreferredSize(new Dimension(400, 300));
//
//                        // Remove previous components from rightPanel and add the shift status panel
//                        rightPanel.removeAll();
//                        rightPanel.setLayout(new GridBagLayout());
//                        GridBagConstraints gbc = new GridBagConstraints();
//                        gbc.gridx = 0;
//                        gbc.gridy = 0;
//                        gbc.fill = GridBagConstraints.HORIZONTAL;
//                        gbc.insets = new Insets(5, 5, 5, 5);
//
//                        rightPanel.add(new GenericLabel("Please enter date:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(dateField, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(new GenericLabel("Please choose shift type:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(shiftTypesComboBox, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(new GenericLabel("Please choose branch:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(branchComboBox, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(btnDone, gbc);
//
//                        gbc.gridy++;
//                        gbc.weighty = 1.0;
//                        gbc.gridwidth = GridBagConstraints.REMAINDER;
//                        gbc.fill = GridBagConstraints.BOTH;
//                        rightPanel.add(tableScrollPane, gbc);
//
//                        gbc.gridy++;
//                        rightPanel.add(shiftStatusLabel, gbc);
//
//                        rightPanel.revalidate();
//                        rightPanel.repaint();
//                    } catch (Exception ex) {
//                        // Handle the exception
//                        setErrorText("Error occurred while retrieving shift status: " + ex.getMessage());
//                    }
//                }
//            });
//
//            rightPanel.removeAll();
//            rightPanel.setLayout(new GridBagLayout());
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.insets = new Insets(5, 5, 5, 5);
//
//            rightPanel.add(new GenericLabel(""));
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please enter date:"));
//            gbc.gridy++;
//            rightPanel.add(dateField, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose shift type:"));
//            gbc.gridy++;
//            rightPanel.add(shiftTypesComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose branch:"));
//            gbc.gridy++;
//            rightPanel.add(branchComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel(""));
//            gbc.gridy++;
//            rightPanel.add(btnDone, gbc);
//
//            rightPanel.revalidate();
//            rightPanel.repaint();
//        });



//        showShiftStatusButton.addActionListener(e -> {
//            System.out.println("Button show shift status clicked");
//            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
//            String[] shiftTypes = { "morning", "evening" };
//            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
//            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
//            String[] branches = new String[0];
//            if (response.isError()) {
//                setErrorText(response.getErrorMessage());
//            } else {
//                branches = ((String) response.getReturnValue()).split("\n");
//            }
//            JComboBox<String> branchComboBox = new JComboBox<>(branches);
//            GenericButton btnDone = new GenericButton("Done");
//
//            btnDone.addActionListener(e1 -> {
//                setErrorText("");
//                setFeedbackText("");
//                String date = dateField.getDate();
//                String shiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();
//                String branch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();
//
//                if (date.isEmpty() || shiftType.isEmpty() || branch.isEmpty()) {
//                    setErrorText("Please fill all fields");
//                } else {
//                    try {
//                        // Call the showShiftStatusData function and get the shift status data
//                        Map<String, Object> shiftStatusData = showShiftStatusData(branch, date, shiftType);
//
//                        boolean isLegalShift = (boolean) shiftStatusData.get("isLegalShift");
//                        List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");
//
//                        // Display the shift status data in a table
//                        String[] columnNames = { "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned" };
//                        Object[][] rowData = new Object[positionDataList.size()][columnNames.length];
//                        int rowIndex = 0;
//                        for (Map<String, Object> positionData : positionDataList) {
//                            rowData[rowIndex][0] = positionData.get("position");
//                            rowData[rowIndex][1] = positionData.get("assigned");
//                            rowData[rowIndex][2] = positionData.get("required");
//                            rowData[rowIndex][3] = positionData.get("submissionsNotAssigned");
//                            rowData[rowIndex][4] = positionData.get("unassignedSubmissions");
//                            rowIndex++;
//                        }
//
//                        JTable shiftStatusTable = new JTable(rowData, columnNames);
//                        CustomTableModel tableModel = new CustomTableModel();
//                        shiftStatusTable.setModel(tableModel);
//
//                        JScrollPane tableScrollPane = new JScrollPane(shiftStatusTable);
//                        tableScrollPane.setPreferredSize(new Dimension(400, 300));
//
//                        JLabel shiftStatusLabel;
//                        if (isLegalShift) {
//                            shiftStatusLabel = new GenericLabel("The shift is approved");
//                        } else {
//                            shiftStatusLabel = new GenericLabel("Missing requirements");
//                        }
//
//                        GridBagConstraints gbc = new GridBagConstraints();
//                        gbc.gridy++;
//                        gbc.weighty = 1.0;
//                        gbc.gridwidth = GridBagConstraints.REMAINDER;
//                        gbc.fill = GridBagConstraints.BOTH;
//                        rightPanel.add(tableScrollPane, gbc);
//
//                        gbc.gridy++;
//                        rightPanel.add(shiftStatusLabel, gbc);
//
//                        rightPanel.revalidate();
//                        rightPanel.repaint();
//
//                    } catch (Exception ex) {
//                        // Handle the exception
//                        setErrorText("Error occurred while retrieving shift status: " + ex.getMessage());
//                    }
//                }
//            });
//
//            rightPanel.removeAll();
//            rightPanel.setLayout(new GridBagLayout());
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.insets = new Insets(5, 5, 5, 5);
//
//            rightPanel.add(new GenericLabel("Please enter date:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(dateField, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose shift type:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(shiftTypesComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose branch:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(branchComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(btnDone, gbc);
//
//            rightPanel.revalidate();
//            rightPanel.repaint();
//        });






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
                ArrayList<Integer> nums = new ArrayList();
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

    }
}