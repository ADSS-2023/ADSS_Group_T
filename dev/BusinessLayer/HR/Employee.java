package BusinessLayer.HR;

import java.util.*;

public class Employee {
    private int id;
    private String employeeName;
    private String bankAccount;
    private List<Position> qualifiedPositions;
    private Map<String, List<Constraint>> submittedShifts;
    private Map<String, List<Constraint>> assignedShifts;
    private Map<String, List<boolean>> shiftsRestriction;
    private String description;
    private int salary;
    private String joiningDay;
    private boolean isManager;
    private String password;

    public Employee(String employeeName, String bankAccount, List<Position> qualifiedPositions, String joiningDay, int employeeId, String password) {
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.qualifiedPositions = qualifiedPositions;
        this.joiningDay = joiningDay;
        this.id = employeeId;
        this.password = password;
        this.submittedShifts = new HashMap<>();
        this.assignedShifts = new HashMap<>();
        this.shiftsRestriction = new HashMap<>();
    }

    public void addSubmittedShift(String date, boolean shiftType, boolean isTemp) {
        if (shiftsRestriction.containsKey(date) && shiftsRestriction.get(date).contains(shiftType)) {
            throw new IllegalArgumentException("Cannot submit to that shift");
        } else {
            Constraint cons = new Constraint(date, shiftType, isTemp);
            List<Constraint> vecConstraint = submittedShifts.get(date);
            if (vecConstraint == null) {
                vecConstraint = new ArrayList<>();
                vecConstraint.add(cons);
                submittedShifts.put(date, vecConstraint);
            } else {
                submittedShifts.get(date).add(cons);
            }
        }
    }

    public String getListOfSubmittedConstraints() {
        String concat = "";
        String type = "";
        for (List<Constraint> vec : assignedShifts.values()) {
            for (Constraint cons : vec) {
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
            Vector<Boolean> shiftTypes = new Vector<>();
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

    public Vector<String> getQualifiedPositions() {
        Vector<String> positionNames = new Vector<String>();
        for (Position position : qualifiedPositions) {
            positionNames.add(position.getPositionName());
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
