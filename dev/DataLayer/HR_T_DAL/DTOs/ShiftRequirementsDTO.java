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
}
