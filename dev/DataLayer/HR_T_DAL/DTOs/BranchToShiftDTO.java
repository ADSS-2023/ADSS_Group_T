package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class BranchToShiftDTO extends DTO {
    private String branchAddress;
    private String shiftDate;
    private String shiftType;

    public BranchToShiftDTO(String branchAddress, String shiftDate, String shiftType){
        super("BranchToShift");
        this.branchAddress = branchAddress;
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
    }

    public BranchToShiftDTO(){

    }


}
