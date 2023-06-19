package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.HR.User.PositionType;
import DataLayer.Util.DTO;

public class ConstraintByEmployeeDTO extends DTO {

    private int employeeId;
    private String branchAddress;
    private String constraintDate;
    private String shiftType;

    private String positionType;

    public ConstraintByEmployeeDTO(int employeeId, String branchAddress, String constraintDate, String shiftType , String positionType) {
        super("ConstraintByEmployee");
        this.employeeId = employeeId;
        this.branchAddress = branchAddress;
        this.constraintDate = constraintDate;
        this.shiftType = shiftType;
        this.positionType = positionType;

    }



    public ConstraintByEmployeeDTO() {
        super("ConstraintByEmployee");

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



    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }


}
