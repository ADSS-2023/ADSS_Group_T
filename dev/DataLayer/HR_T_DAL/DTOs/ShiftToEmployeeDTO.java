package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ShiftToEmployeeDTO extends DTO {

    private String shiftDate;
    private String shiftType;
    private int employeeId;
    private String approved;

    public ShiftToEmployeeDTO(String shiftDate, String shiftType, int employeeId, String approved) {
        super("ShiftToEmployee");
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.employeeId = employeeId;
        this.approved = approved;
    }
}
