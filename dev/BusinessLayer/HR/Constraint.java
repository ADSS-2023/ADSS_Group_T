package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.time.LocalDate;

public class Constraint {
    private int branch;

    private int employeeId;
    private LocalDate date;
    private boolean shiftType; //true-morning, false- evening
    private PositionType assignedPosition; // also indicates if the employee assigned, null mean that not assigned yet.


    public Constraint(int branch, int employeeId, LocalDate date, boolean shiftType) {
        this.branch = branch;
        this.employeeId = employeeId;
        this.date = date;
        this.shiftType = shiftType;
        this.assignedPosition = null;
    }


    public int getBranch() {
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
