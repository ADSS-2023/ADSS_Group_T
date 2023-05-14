package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ConstraintDTO extends DTO {

    private int employeeId;
    private String branchAddress;
    private String constraintDate;
    private String shiftType;
    private String isTemporary;
    private String positionType;

    public ConstraintDTO(int employeeId, String branchAddress, String constraintDate, String shiftType , String positionType) {
        super("Constraint");
        this.employeeId = employeeId;
        this.branchAddress = branchAddress;
        this.constraintDate = constraintDate;
        this.shiftType = shiftType;
        this.positionType = positionType;
    }

    public ConstraintDTO() {

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public String getConstraintDate() {
        return constraintDate;
    }

    public String getShiftType() {
        return shiftType;
    }

    public String getIsTemporary() {
        return isTemporary;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }


}
