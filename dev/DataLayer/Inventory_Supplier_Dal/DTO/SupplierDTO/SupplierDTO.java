package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class SupplierDTO extends DTO {
    private int supplierNum;
    private String supplierName;
    private String address;
    private int bankAccountNum;
    private String selfDelivery;
    private String paymentTerms;

    public String getPaymentTerms() {
        return paymentTerms;
    }

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

    public String isSelfDelivery() {
        return selfDelivery;
    }

    public int getDaysToDeliver() {
        return daysToDeliver;
    }

    private int daysToDeliver;

    public SupplierDTO(int supplierNum, String supplierName, String address, int bankAccountNum, String selfDelivery, int daysToDeliver, String paymentTerms){
        super("supplier_supplier");
        this.supplierNum = supplierNum;
        this.supplierName = supplierName;
        this.address = address;
        this.bankAccountNum = bankAccountNum;
        this.selfDelivery = selfDelivery;
        this.daysToDeliver = daysToDeliver;
        this.paymentTerms = paymentTerms;
    }
    public SupplierDTO(){
        super("supplier_supplier");
    }
}
