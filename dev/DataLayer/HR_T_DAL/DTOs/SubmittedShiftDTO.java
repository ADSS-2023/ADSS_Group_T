package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class SubmittedShiftDTO extends DTO {
    private int employeeId;
    private String branch;
    private String date;
    private String shiftType;
    public SubmittedShiftDTO(int emloyeeId, String branchAdress, String date, String shiftType) {
        super("SubmittedShift");
        this.employeeId = emloyeeId;
        this.date = date;
        this.shiftType = shiftType;
        this.branch = branchAdress;

    }

    public SubmittedShiftDTO (){}

    public int getEmployeeId() {
        return employeeId;
    }

    public String getDate() {
        return date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public String getBranch() {
        return branch;
    }
}
