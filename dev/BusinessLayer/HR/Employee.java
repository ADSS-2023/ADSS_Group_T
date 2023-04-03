package BusinessLayer.HR;


import UtilSuper.PositionType;

import java.util.*;

public class Employee {
    private int id;
    private String employeeName;
    private String bankAccount;
    private List<PositionType> qualifiedPositions;
    private Map<String, List<Constraint>> submittedShifts;
    private Map<String, List<Constraint>> assignedShifts;
    private Map<String, List<Boolean>> shiftsRestriction;
    private String description;
    private int salary;
    private String joiningDay;
    private boolean isManager;
    private String password;

    public Employee(String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String joiningDay, int employeeId, String password) {
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.qualifiedPositions = qualifiedPositions;
        this.joiningDay = joiningDay;
        this.id = employeeId;
        this.password = password;
        this.submittedShifts = new HashMap<>();
        this.assignedShifts = new HashMap<>();
        this.shiftsRestriction = new HashMap<String, List<Boolean>>();
    }
    public Employee(String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String joiningDay, int employeeId, String password, boolean isManager) {
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.qualifiedPositions = qualifiedPositions;
        this.joiningDay = joiningDay;
        this.id = employeeId;
        this.password = password;
        this.submittedShifts = new HashMap<>();
        this.assignedShifts = new HashMap<>();
        this.shiftsRestriction = new HashMap<String, List<Boolean>>();
        this.isManager = isManager;
    }

    public void addSubmittedShift(String date, boolean shiftType, boolean isTemp) {
        if (shiftsRestriction.containsKey(date) && shiftsRestriction.get(date).contains(shiftType)) {
            throw new IllegalArgumentException("Cannot submit to that shift");
        } else {
            Constraint cons = new Constraint(date, shiftType, isTemp);
            List<Constraint> constraints = submittedShifts.get(date);
            if (constraints == null) {
                constraints = new ArrayList<>();
                constraints.add(cons);
                submittedShifts.put(date, constraints);
            } else {
                submittedShifts.get(date).add(cons);
            }
        }
    }

    public String getListOfSubmittedConstraints() {
        String concat = "";
        String type = "";
        for (List<Constraint> constraints : assignedShifts.values()) {
            for (Constraint cons : constraints) {
                if (cons.getShiftType()) {
                    type = "morning";
                } else {
                    type = "evening";
                }
                concat += "Date: " + cons.getDate() + " Type: " + type + "\n";
            }
        }
        return concat;
    }


    public Map<String, List<Constraint>> getSubmittedShifts() {
        return submittedShifts;
    }

    public Map<String, List<Constraint>> getAssignedShifts() {
        return assignedShifts;
    }

    public Map<String, List<Boolean>> getShiftsRestriction() {
        return shiftsRestriction;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public void  setDescription(String description){
        this.description = description;
    }

    public void addSAssignShift (int constraintId, Constraint constraint){
        if (submittedShifts.containsKey(constraintId)){
            submittedShifts.remove(constraintId);
            assignedShifts.get(constraintId).add(constraint);
        }
    }

    public void addRestriction(String date, boolean isMorning) {
        if (shiftsRestriction.containsKey(date)) {
            if (!shiftsRestriction.get(date).contains(isMorning)) {
                shiftsRestriction.get(date).add(isMorning);
            }

        } else {
            List<Boolean> shiftTypes = new ArrayList<>();
            shiftTypes.add(isMorning);
            shiftsRestriction.put(date, shiftTypes);
        }
    }





    public boolean isManager() {
        return isManager;
    }

    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public List<String> getQualifiedPositions() {
        List<String> positionNames = new ArrayList<>();
        for (PositionType position : qualifiedPositions) {
            positionNames.add(position.name());
        }
        return positionNames;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public String getJoiningDay() {
        return joiningDay;

    }


}
