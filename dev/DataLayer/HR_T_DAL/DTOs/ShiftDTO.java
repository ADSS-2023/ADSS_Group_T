package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.HR.Shift;
import DataLayer.Util.DTO;

public class ShiftDTO extends DTO {

    private String branch;
    private String shiftDate;
    private String shiftType;
    private int managerId;

    public ShiftDTO(String shiftDate, String shiftType, int managerId, String branch) {
        super("Shift");
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.managerId = managerId;
        this.branch = branch;
    }
    public ShiftDTO(Shift shift) {
        super("Shift");
        this.shiftDate = shift.getDate().toString();
        this.shiftType = "m/n";
        this.managerId = shift.getShiftManagerId();
        this.branch = shift.getBranch();
    }

    public ShiftDTO() {
    }

    public ShiftDTO(String tableName, String branch) {
        super(tableName);
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getShiftType() {
        return shiftType;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
