package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class BranchDTO extends DTO {

    private String branchAddress;
    private String telNumber;
    private String contactName;
    private int x;
    private int y;
    private int shippingArea;

    public BranchDTO(String branchAddress,String telNumber,String contactName, int x, int y, int shippingArea){
        super("Branch");
        this.branchAddress = branchAddress;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.x = x;
        this.y = y;
        this.shippingArea = shippingArea;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getShippingArea() {
        return shippingArea;
    }
}
