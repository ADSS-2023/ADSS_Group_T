package GUI;

import GUI.Generic.GenericButton;
import GUI.Generic.GenericFrameUser;
import GUI.Generic.GenericLabel;
import GUI.Generic.GenericTextField;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import UtilSuper.ServiceFactory;

import javax.swing.*;

public class HRManagerFrame  extends GenericFrameUser {

    private final ShiftService shiftService ;
    private final EmployeeService employeeService;

    public HRManagerFrame(ServiceFactory serviceFactory) {
        super(serviceFactory);
        setTitle("HR Manager");
        this.employeeService = serviceFactory.getEmployeeService();
        this.shiftService = serviceFactory.getShiftService();

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
                    employeeService.addNewEmployee(id,name,bank,description,salary, joining ,password);
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
            rightPanel.add(new GenericLabel("Please enter the employee ID:"));
            rightPanel.add(idField);
            rightPanel.add(new GenericLabel("Please choose new Qualification"));
            rightPanel.add(qualificationsComboBox);
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
                    employeeService.addNewDriver(id,name,bank,description,salary,joining,password,licenseType,coolingLevel);
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

        });
    }
}