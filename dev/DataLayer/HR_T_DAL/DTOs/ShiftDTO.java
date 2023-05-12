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
        this.shiftType = "m/n";//////////////////////////////////////////////////////////////////////////////
        this.managerId = shift.getShiftManagerId();
    }
}
