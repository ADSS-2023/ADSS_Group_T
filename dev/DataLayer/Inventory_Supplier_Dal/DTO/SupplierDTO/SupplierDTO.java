package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class SupplierDTO extends DTO {
    private int supplierNum;
    private String supplierName;
    private String address;
    private int bankAccountNum;
    private boolean selfDelivery;

    public int getSupplierNum() {
        return supplierNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAddress() {
        return address;
    }

    public int getBankAccountNum() {
        return bankAccountNum;
    }

    public boolean isSelfDelivery() {
        return selfDelivery;
    }

    public int getDaysToDeliver() {
        return daysToDeliver;
    }

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
