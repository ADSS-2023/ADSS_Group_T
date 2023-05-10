package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

public class ProductDiscountDTO extends DiscountDTO{
    private int productNum;

    public int getProductNum() {
        return productNum;
    }

    public ProductDiscountDTO(int supplierNum, int amount, float discount, boolean isPercentage, int productNum) {
        super("supplier_product_discount", supplierNum, amount, discount, isPercentage);
        this.productNum = productNum;
    }
    public ProductDiscountDTO() {
        super("supplier_product_discount");
    }
}
