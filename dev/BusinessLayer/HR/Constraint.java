package BusinessLayer.HR;

import BusinessLayer.HR.User.PositionType;
import DataLayer.HR_T_DAL.DTOs.ConstraintDTO;

import java.time.LocalDate;

public class Constraint {
    private String branch;

    private int employeeId;
    private LocalDate date;
    private boolean shiftType; //true-morning, false- evening
    private PositionType assignedPosition; // also indicates if the employee assigned, null mean that not assigned yet.


    public Constraint(String branch, int employeeId, LocalDate date, boolean shiftType) {
        this.branch = branch;
        this.employeeId = employeeId;
        this.date = date;
        this.shiftType = shiftType;
        this.assignedPosition = null;
    }
//TODO -adding the time class
    public Constraint(ConstraintDTO constraintDTO) {
        this.branch = constraintDTO.getBranchAddress();
        this.employeeId = constraintDTO.getEmployeeId();
        this.date = date;
        this.shiftType = stringTObooleanST(constraintDTO.getShiftType());
        this.assignedPosition = PositionType.valueOf(constraintDTO.getPositionType());
    }

    public boolean stringTObooleanST(String s){
        if (s.equals("morning"))return true;
        return false;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean isShiftType() {
        return shiftType;
    }

    public String getBranch() {
        return branch;
    }

    public PositionType getAssignedPosition() {
        return assignedPosition;
    }

    public void setAssignedPosition(PositionType assignedPosition) {
        this.assignedPosition = assignedPosition;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean getShiftType() {
        return shiftType;
    }
}
