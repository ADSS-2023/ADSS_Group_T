package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ShiftToEmployeeDTO extends DTO {

    private String shiftDate;
    private String shiftType;
    private int employeeId;
    private String position;
    private String isAssigned;

    private String branch;

    public ShiftToEmployeeDTO( String shiftDate, String shiftType, int employeeId, String position, String isAssigned, String branch) {
        super("ShiftToEmployee");
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.employeeId = employeeId;
        this.position = position;
        this.isAssigned = isAssigned;
        this.branch = branch;
    }

    public ShiftToEmployeeDTO() {

    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(String isAssigned) {
        this.isAssigned = isAssigned;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
