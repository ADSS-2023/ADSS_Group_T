package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ShiftRequirementsDTO extends DTO {

    private String shiftDate;
    private String shiftType;
    private String positionName;
    private int amount;

    public ShiftRequirementsDTO(String shiftDate, String shiftType, String positionName, int amount) {
        super("ShiftRequirements");
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.positionName = positionName;
        this.amount = amount;
    }



    public ShiftRequirementsDTO() {
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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
