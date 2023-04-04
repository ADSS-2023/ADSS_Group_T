package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
/*
    This class represent any kind of category, whether its a subcategory in
    the categories tree, or the first one.
    Each category holds a list of the subcategories/items underneath it.
 */
public class Category implements ProductCategoryManagement{
    protected String index;
    protected String name;
    protected LinkedList<ProductCategoryManagement> categories_list;

    public Category(String name,String index) {
        this.name = name;
        this.index = index;
        categories_list = new LinkedList<>();
    }

    public void add_category(ProductCategoryManagement new_product){
        categories_list.add(new_product);
    }

    /**
     *  This function called from CategoryService when there is a requirement to produce
     *  an inventory report.
     * @return
     */
    @Override
    public String produceInventoryReport(String index) {
        if(index == "") {
            String reportString = "Category : " + name + "\n\n";
            for (ProductCategoryManagement curCategory : categories_list) {
                reportString += "\t" + curCategory.produceInventoryReport("") + "\n";
            }
            return reportString;
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            return categories_list.get(current_index).produceInventoryReport(next_index);
        }

    }

    /**
     * This function gets a Discount as an argument for this current category,
     * and update the sub-category with the new discount.
     * @param discount
     */
    @Override
    public void setDiscount(String index , Discount discount) {
        if(index == ""){
            for (ProductCategoryManagement p :categories_list
            ) {
                p.setDiscount("" , discount);
            }
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            categories_list.get(current_index).setDiscount(next_index , discount);
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

    private String present_names(){
        String names = "";
        int index = 1;
        for(ProductCategoryManagement cur_category : categories_list){
            names += index++ + " : " + cur_category.get_name() + ", ";
        }
        return names.substring(0,names.length()-2);
    }

    @Override
    public String show_data(String index){
        if(index == "")
            return present_names();
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            return categories_list.get(current_index).show_data(next_index);
        }
    }

    @Override
    public void add_product(ProductCategoryManagement add_product) {
        categories_list.add(add_product);
    }

    @Override
    public String get_name() {
        return name;
    }

    public LinkedList<ProductCategoryManagement> getCategories_list() {
        return categories_list;
    }

}