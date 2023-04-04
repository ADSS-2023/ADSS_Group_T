package BusinessLayer.Stock;

import java.awt.*;
import java.util.LinkedList;
/*
    This class represent any kind of category, whether its a subcategory in
    the categories tree, or the first one.
    Each category holds a list of the subcategories/items underneath it.
 */
public class Category implements ProductCategoryManagement{
    protected String name;
    protected LinkedList<ProductCategoryManagement> categories_list;

    public Category(String name) {
        this.name = name;
        categories_list = new LinkedList<>();
    }

    /**
     *  This function called from CategoryService when there is a requirement to produce
     *  an inventory report.
     * @return
     */
    @Override
    public String produceInventoryReport() {
        String reportString = "Category : " + name + "\n\n";
        for(ProductCategoryManagement curCategory : categories_list){
            reportString += "\t" + curCategory.produceInventoryReport() + "\n";
        }
        return reportString;
    }

    /**
     * This function gets a Discount as an argument for this current category,
     * and update the sub-category with the new discount.
     * @param discount
     */
    @Override
    public void setDiscount(Discount discount) {
        for (ProductCategoryManagement p :categories_list
             ) {
            p.setDiscount(discount);
        }
    }

    /**
     * This function gets an index as an argument and return the Category \ Item from
     * the specific category.
     *
     * @param index
     * @return
     */
    public ProductCategoryManagement get_productCategoryManagement(int index){
        return categories_list.get(index);
    }


    public String toString(){
        return "";
    }
}
