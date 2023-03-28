package BusinessLayer.Stock;

/*
    This class bind Item and Category under the same
    interface, inorder to emphasize the similarities between them
 */
public interface ProductCategoryManagement {

    String produceInventoryReport();
    void setDiscount(Discount discount);
}
