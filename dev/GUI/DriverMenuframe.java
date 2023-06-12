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

public class DriverMenuframe extends GenericFrameUser {

    private final EmployeeService employeeService;
     private int id;

    public DriverMenuframe(ServiceFactory serviceFactory,int id ) {
        super(serviceFactory);
        setTitle("driver: " + id);
        this.employeeService = serviceFactory.getEmployeeService();
        this.id = id;

        GenericButton assignShiftButton = new GenericButton("submit shift ");
        leftPanel.add((assignShiftButton));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));

        assignShiftButton.addActionListener(e->{
            System.out.println("Button assign shift clicked");
            rightPanel.removeAll();
            GenericTextField dateField = new GenericTextField();

            GenericButton submitButton = new GenericButton("submit");

            submitButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                String date = dateField.getText();
                if (date.isEmpty()) {
                    setErrorText("Please fill all fields");
                }
                else {
                    Response response1 = ResponseSerializer.deserializeFromJson( employeeService.submitShiftForDriver(Time.stringToLocalDate(date),id));
                    if (response1.isError()) {
                        setErrorText(response1.getErrorMessage());
                    } else {
                        setFeedbackText("shift been submitted successfully");
                    }
                }
            });

            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel(""));
            rightPanel.add(new GenericLabel("Please enter date:"));
            rightPanel.add(dateField);
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
    }
}
