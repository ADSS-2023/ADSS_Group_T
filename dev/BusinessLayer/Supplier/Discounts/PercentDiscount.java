package BusinessLayer.Supplier.Discounts;

import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.DiscountDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;

public class PercentDiscount extends Discount {
    //in this class discount per product presented in percentage
    public PercentDiscount(int amount, float discount,boolean isPercentage, SupplierDalController supplierDalController, DiscountDTO dto) throws SQLException {
        super(amount, discount,isPercentage, supplierDalController, dto);
    }

    @Override
    public float getPriceAfterDiscount(float oldPrice) {
       return (1-(discount/100))*oldPrice;
    }

    @Override
    public String toString() {
        return "Amount to be discounted: " + amount +
                ", Discount: " + discount +
                ", is a Percent discount: " + isPercentage +
                '}';
    }
}
