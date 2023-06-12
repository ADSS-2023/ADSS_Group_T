package GUI;

import GUI.Generic.GenericButton;
import GUI.Generic.GenericFrameUser;
import GUI.Generic.GenericLabel;
import GUI.Generic.GenericTextField;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.LogisticCenterService;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.ServiceFactory;
import UtilSuper.Time;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class EmployeeMenueFrame extends GenericFrameUser {
    private final ShiftService shiftService ;
    private final EmployeeService employeeService;
    private  final LogisticCenterService logisticCenterService;
    private final BranchService branchService;
    private int id ;

    public EmployeeMenueFrame(ServiceFactory serviceFactory,int id ) {
        super(serviceFactory);
        setTitle("employee : " + id);
        this.employeeService = serviceFactory.getEmployeeService();
        this.shiftService = serviceFactory.getShiftService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.branchService = serviceFactory.getBranchService();
        this.id = id;

        GenericButton assignShiftButton = new GenericButton("submit shift ");
        leftPanel.add((assignShiftButton));

        GenericButton showSubmissionsButton = new GenericButton("show submissions");
        leftPanel.add((showSubmissionsButton));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));

        assignShiftButton.addActionListener(e->{
            System.out.println("Button assign shift clicked");
            rightPanel.removeAll();
            GenericTextField dateField = new GenericTextField();
            String[] shiftTypes = {"morning","evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            String[] branches = {"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9"};
            JComboBox<String> branchComboBox = new JComboBox<>(branches);

            GenericButton submitButton = new GenericButton("submit");

            submitButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                String date = dateField.getText();
                String shiftType = shiftTypesComboBox.getSelectedItem().toString();
                String branch = branchComboBox.getSelectedItem().toString();

                if (date.isEmpty() || shiftType == null || branch == null) {
                    setErrorText("Please fill all fields");
                }
                else {
                    Response response1 = ResponseSerializer.deserializeFromJson( employeeService.submitShiftForEmployee(branch,id, Time.stringToLocalDate(date),shiftType));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("shift been submitted successfully");
                    }
                }
            });

            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
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
            rightPanel.add(submitButton);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        showSubmissionsButton.addActionListener(e->{
            System.out.println("Button show sub clicked");
            rightPanel.removeAll();

//            Response response1 = ResponseSerializer.deserializeFromJson(employeeService.getListOfSubmittion(id));
//            if (response1.isError()) {
//                setErrorText(response1.getErrorMessage());
//            } else {
//                setFeedbackText("shift been submitted successfully");
//            }
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            TextArea textArea = new TextArea(employeeService.getListOfSubmittion(id));
            rightPanel.add(textArea);


        });
    }
}
