package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

public class SupplierDiscountDTO extends DiscountDTO{
    private boolean isTotalAmount;

    public boolean isTotalAmount() {
        return isTotalAmount;
    }

    public SupplierDiscountDTO(int supplierNum, int amount, float discount, boolean isPercentage, boolean isTotalAmount) {
        super("supplier_supplier_discount", supplierNum, amount, discount, isPercentage);
        this.isTotalAmount = isTotalAmount;
    }
}
