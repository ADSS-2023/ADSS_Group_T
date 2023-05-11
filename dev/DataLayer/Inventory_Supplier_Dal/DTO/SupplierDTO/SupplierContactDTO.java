package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class SupplierContactDTO  extends DTO {
    private int supplierNum;
    private String contactName;
    private String contactNumber;

    public int getSupplierNum() {
        return supplierNum;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public SupplierContactDTO(int supplierNum, String contactName, String contactNumber){
        super("supplier_supplier_contact");
        this.supplierNum  = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public SupplierContactDTO(){
        super("supplier_supplier_contact");
    }
}
