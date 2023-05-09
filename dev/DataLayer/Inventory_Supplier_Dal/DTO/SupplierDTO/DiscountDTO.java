package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public abstract class DiscountDTO extends DTO {
    private int supplierNum;
    private int amount;
    private float discount;
    private boolean isPercentage;

    public DiscountDTO(String tableName, int supplierNum, int amount, float discount, boolean isPercentage) {
        super(tableName);
        this.supplierNum = supplierNum;
        this.amount = amount;
        this.discount = discount;
        this.isPercentage = isPercentage;
    }
}
