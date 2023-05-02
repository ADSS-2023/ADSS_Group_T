package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class BranchDTO extends DTO {

    private String branchAddress;
    private String telNumber;
    private String contactName;
    private int x;
    private int y;

    public BranchDTO(String branchAddress,String telNumber,String contactName, int x, int y){
        super("Branch");
        this.branchAddress = branchAddress;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.x = x;
        this.y = y;
    }
}