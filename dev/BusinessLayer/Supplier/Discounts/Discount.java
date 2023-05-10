package BusinessLayer.Supplier.Discounts;

import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.DiscountDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;

public abstract class Discount {
    protected int amount;
    protected float discount;
    protected  boolean isPercentage;
    protected DiscountDTO discountDTO;
    protected SupplierDalController supplierDalController;

    public abstract float getPriceAfterDiscount(float oldPrice) ;

    public Discount(int amount,float discount,boolean isPercentage, SupplierDalController supplierDalController, DiscountDTO dto) throws SQLException {
        this.amount=amount;
        this.discount =discount;
        this.isPercentage = isPercentage;
        this.supplierDalController = supplierDalController;
        this.discountDTO = dto;
        supplierDalController.insert(dto);
    }

    public Discount(){
    }

    public void editDiscount(int amount, float discount){
        this.discount=discount;
        this.amount=amount;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public int getAmount() {
        return amount;
    }

    public float getDiscount() {
        return discount;
    }

    public boolean isPercentage() {
        return isPercentage;
    }
}
