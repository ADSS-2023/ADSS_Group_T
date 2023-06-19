package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class BranchToEmployeeDTO extends DTO {
    private String branchAddress;
    private int employeeId;

    public BranchToEmployeeDTO(String branchAddress, int employeeId){
        super("BranchToEmployee");
        this.branchAddress = branchAddress;
        this.employeeId = employeeId;
    }

    public BranchToEmployeeDTO(){
        super("BranchToEmployee");

    }


}
