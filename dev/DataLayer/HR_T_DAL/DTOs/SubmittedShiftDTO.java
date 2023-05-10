package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class SubmittedShiftDTO extends DTO {
    private int employeeId;
    private String branchAdress;
    private String date;
    private String shiftType;
    public SubmittedShiftDTO(int emloyeeId, String branchAdress, String date, String shiftType) {
        this.branchAdress = branchAdress;
        this.shiftType = shiftType;
        this.employeeId = emloyeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getBranchAdress() {
        return branchAdress;
    }

    public String getDate() {
        return date;
    }

    public String getShiftType() {
        return shiftType;
    }
}
