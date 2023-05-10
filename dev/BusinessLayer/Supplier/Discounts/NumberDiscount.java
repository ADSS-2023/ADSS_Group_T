package BusinessLayer.Supplier.Discounts;

import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.DiscountDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;

public class NumberDiscount extends Discount{
    //in this class discount per product presented in shekels
    public NumberDiscount(int amount, float discount, boolean isPercentage, SupplierDalController supplierDalController, DiscountDTO dto) throws SQLException {
        super(amount, discount,isPercentage, supplierDalController, dto);
        this.discountDTO = dto;
    }

    public NumberDiscount(DiscountDTO discountDTO, SupplierDalController supplierDalController) throws SQLException {
        this.amount = discountDTO.getAmount();
        this.discount = discountDTO.getDiscount();
        this.isPercentage = discountDTO.isPercentage();
        this.supplierDalController = supplierDalController;
        this.discountDTO = discountDTO;
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
