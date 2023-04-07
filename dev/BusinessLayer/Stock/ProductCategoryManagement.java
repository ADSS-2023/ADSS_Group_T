package BusinessLayer.Stock;

import java.util.List;

/*
    This class bind Item and Category under the same
    interface, inorder to emphasize the similarities between them
 */
public interface ProductCategoryManagement {

    String produceInventoryReport(String index);

    void setDiscount(String index , Discount discount) throws Exception;

    String show_data(String index) throws Exception;

    void add_product(ProductCategoryManagement add_product);

    String get_name();
    
    void add_item(String index,Item item) throws Exception;
}
