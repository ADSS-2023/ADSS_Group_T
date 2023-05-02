package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class SupplierDTO extends DTO {

    private String supplierAddress;
    private String telNumber;
    private String contactName;
    private int x;
    private int y;

    public SupplierDTO(String supplierAddress, String telNumber, String contactName, int x, int y){
        super("Supplier");
        this.supplierAddress = supplierAddress;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.x = x;
        this.y = y;
    }
}
