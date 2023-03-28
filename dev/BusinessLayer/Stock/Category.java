package BusinessLayer.Stock;

import java.awt.*;
import java.util.LinkedList;

public abstract class Category implements ProductCategoryManagement{
    protected String name;
    protected LinkedList<ProductCategoryManagement> categories_list;
    @Override
    public String produceInventoryReport() {
        return null;
    }

    @Override
    public void setDiscount(Discount discount) {
        for (ProductCategoryManagement p :categories_list
             ) {
            p.setDiscount(discount);
        }
    }

}
