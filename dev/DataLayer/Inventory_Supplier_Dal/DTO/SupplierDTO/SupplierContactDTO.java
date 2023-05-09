package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class SupplierContactDTO  extends DTO {
    private int supplierNum;
    private String contactName;
    private String contactNumber;

    public SupplierContactDTO(int supplierNum, String contactName, String contactNumber){
        super("supplier_supplier_contact");
        this.supplierNum  = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }
}
