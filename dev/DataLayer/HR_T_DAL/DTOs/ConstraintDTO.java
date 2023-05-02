package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ConstraintDTO extends DTO {

    private int employeeId;
    private String branchAddress;
    private String constraintDate;
    private String shiftType;
    private String isTemporary;
    private String positionType;

    public ConstraintDTO(int employeeId, String branchAddress, String constraintDate, String shiftType, String isTemporary, String positionType) {
        super("Constraint");
        this.employeeId = employeeId;
        this.branchAddress = branchAddress;
        this.constraintDate = constraintDate;
        this.shiftType = shiftType;
        this.isTemporary = isTemporary;
        this.positionType = positionType;
    }
}
