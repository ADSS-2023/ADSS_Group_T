package BusinessLayer.Stock;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
    This class represent any kind of category, whether its a subcategory in
    the categories tree, or the first one.
    Each category holds a list of the subcategories/items underneath it.
 */
public class Category implements ProductCategoryManagement{
    protected String name;
    protected LinkedList<ProductCategoryManagement> categories_list;
    @Override
    public List<String> produceInventoryReport() {
        return null;
    }

    @Override
    public void setDiscount(Discount discount) {
        for (ProductCategoryManagement p :categories_list
             ) {
            p.setDiscount(discount);
        }
    }
    public ProductCategoryManagement get_productCategoryManagement(){
        return null;
    }

}
