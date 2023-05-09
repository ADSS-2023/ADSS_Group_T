package BusinessLayer.Supplier.Discounts;

import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.DiscountDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

public class NumberDiscount extends Discount{
    //in this class discount per product presented in shekels
    public NumberDiscount(int amount, float discount, boolean isPercentage, SupplierDalController supplierDalController, DiscountDTO dto) {
        super(amount, discount,isPercentage, supplierDalController, dto);
        this.discountDTO = dto;
    }

    @Override
    public String toString() {
        return "Amount to be discounted: " + amount +
                ", Discount: " + discount +
                ", is a Percent discount: " + isPercentage +
                '}';
    }

    @Override
    public float getPriceAfterDiscount(float oldPrice) {
        return oldPrice -discount;
    }
}
