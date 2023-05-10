package BusinessLayer.Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.CategoryDTO;
import DataLayer.Util.DTO;

/*
    This class bind Item and Category under the same
    interface, inorder to emphasize the similarities between them
 */
public interface ProductCategoryManagement {

    String produceInventoryReport(String index);

    void setDiscount(String index , Discount discount) throws Exception;

    String show_data(String index) throws Exception;

    void add_product(ProductCategoryManagement add_product);
    void add_product(String index,String name) throws Exception;
    String get_name();
    DTO getDto();
    void add_item(String index,Item item) throws Exception;

    void add_product(CategoryDTO categoryDTO, String next_index) throws Exception;
}
