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
import com.google.gson.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeMenueFrame extends GenericFrameUser {
    private final ShiftService shiftService ;
    private final EmployeeService employeeService;
    private  final LogisticCenterService logisticCenterService;
    private final BranchService branchService;
    private int id ;

    private ArrayList<GenericButton> buttonList;

    public EmployeeMenueFrame(ServiceFactory serviceFactory,int id ) {
        super(serviceFactory);
        setTitle("employee : " + id);
        this.employeeService = serviceFactory.getEmployeeService();
        this.shiftService = serviceFactory.getShiftService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.branchService = serviceFactory.getBranchService();
        this.id = id;
        this.buttonList = new ArrayList<>();

        GenericButton assignShiftButton = new GenericButton("submit shift ");
        leftPanel.add((assignShiftButton));
        buttonList.add(assignShiftButton);

        GenericButton showSubmissionsButton = new GenericButton("show submissions");
        leftPanel.add((showSubmissionsButton));
        buttonList.add(showSubmissionsButton);
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));
        leftPanel.add(new GenericLabel(""));

        assignShiftButton.addActionListener(e->{
            anyButtonPressed(assignShiftButton);
            System.out.println("Button assign shift clicked");
            rightPanel.removeAll();
            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
            String[] shiftTypes = {"morning","evening"};
            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
            String[] branches = {"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9"};
            JComboBox<String> branchComboBox = new JComboBox<>(branches);

            GenericButton submitButton = new GenericButton("submit");

            submitButton.addActionListener(e1 ->{
                setErrorText("");
                setFeedbackText("");
                String date = dateField.getDate();
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
            anyButtonPressed(showSubmissionsButton);
            String json = employeeService.getListOfSubmittion(id,"GUI");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement element = gson.fromJson(json, JsonElement.class);
            JsonArray submissionsArray = element.getAsJsonObject().getAsJsonArray("returnValue");

            System.out.println("Button show sub clicked");
            rightPanel.removeAll();

            // Create the table model
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Shift Type");
            tableModel.addColumn("Assigned Position");
            tableModel.addColumn("Date");
            tableModel.addColumn("Is Approved");

            // Fill the table model with data from the submissions array
            for (JsonElement submissionElement : submissionsArray) {
                JsonObject submissionObject = submissionElement.getAsJsonObject();
                String shiftType = submissionObject.get("Shift Type").getAsString();
                String assignedPosition = submissionObject.get("Assigned Position").getAsString();
                String date = submissionObject.get("Date").getAsString();
                String isApproved = submissionObject.get("Is Approved").getAsString();
                tableModel.addRow(new Object[]{shiftType, assignedPosition, date, isApproved});
            }

            // Create the JTable and set its model
            JTable table = new JTable(tableModel);
            table.setFont(new Font("Monospaced", Font.PLAIN, 12));

            // Add the table to a JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            rightPanel.add(scrollPane);
            rightPanel.revalidate();
            rightPanel.repaint();
        });







    }


    public static String formatJsonString(String json) {
        StringBuilder formattedJson = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (char charJson : json.toCharArray()) {
            if (charJson == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (charJson == '{' || charJson == '[') {
                    formattedJson.append(charJson).append("\n").append(getIndent(indentLevel + 1));
                    indentLevel++;
                } else if (charJson == '}' || charJson == ']') {
                    indentLevel--;
                    formattedJson.append("\n").append(getIndent(indentLevel)).append(charJson);
                } else if (charJson == ',') {
                    formattedJson.append(charJson).append("\n").append(getIndent(indentLevel));
                } else {
                    formattedJson.append(charJson);
                }
            } else {
                formattedJson.append(charJson);
            }
        }

        return formattedJson.toString();
    }

    public static String getIndent(int indentLevel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("  "); // Two spaces for each level of indentation
        }
        return indent.toString();
    }


    private void anyButtonPressed (GenericButton g){
        for (GenericButton x : buttonList) {
            if (!x.equals(g))
                x.setBackground(new Color(255, 255, 255));
        }
    }

}
