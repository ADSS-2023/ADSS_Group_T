package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class DiscountDTO extends DTO {
    private int supplierNum;
    private int amount;
    private int productNum;
    private String isPercentage;
    private String isTotalAmount;
    private String isSupplierDiscount;
    private float discount;

    public int getSupplierNum() {
        return supplierNum;
    }

    public int getAmount() {
        return amount;
    }

    public String isPercentage() {
        return isPercentage;
    }

    public int getProductNum() {
        return productNum;
    }

    public String IsTotalAmount() {
        return isTotalAmount;
    }

    public String IsSupplierDiscount() {
        return isSupplierDiscount;
    }

    public float getDiscount() {
        return discount;
    }

    public DiscountDTO(int supplierNum, int amount, float discount,
                       String isPercentage, String isTotalAmount,
                       String isSupplierDiscount, int productNum) {
        super("supplier_discount");
        this.supplierNum = supplierNum;
        this.amount = amount;
        this.discount=discount;
        this.isPercentage = isPercentage;
        this.isTotalAmount=isTotalAmount;
        this.isSupplierDiscount=isSupplierDiscount;
        this.productNum=productNum;
    }

    public DiscountDTO() {
        super("supplier_discount");
    }
}
