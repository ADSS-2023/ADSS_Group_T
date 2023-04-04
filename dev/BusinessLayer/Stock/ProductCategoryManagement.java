package BusinessLayer.Stock;

import java.util.List;

/*
    This class bind Item and Category under the same
    interface, inorder to emphasize the similarities between them
 */
public interface ProductCategoryManagement {

    List<String> produceInventoryReport();
    void setDiscount(Discount discount);
}
