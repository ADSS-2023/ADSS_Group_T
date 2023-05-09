package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class SupplierDTO extends DTO {
    private int supplierNum;
    private String supplierName;
    private String address;
    private int bankAccountNum;
    private boolean selfDelivery;

    private int daysToDeliver;

    public SupplierDTO(int supplierNum, String supplierName, String address, int bankAccountNum, boolean selfDelivery, int daysToDeliver){
        super("supplier_supplier");
        this.supplierNum = supplierNum;
        this.supplierName = supplierName;
        this.address = address;
        this.bankAccountNum = bankAccountNum;
        this.selfDelivery = selfDelivery;
        this.daysToDeliver = daysToDeliver;
    }
}
