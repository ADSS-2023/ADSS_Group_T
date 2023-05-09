package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.CategoryDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DTO;

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
    protected List<Discount> discount_list;
    protected CategoryDTO categoryDTO;
    protected InventoryDalController inv_dal_controller;


    public Category(String name,String index,InventoryDalController inv_dal_controller) {
        this.name = name;
        this.index = index; // need to change it to be 0.1.1...
        categories_list = new LinkedList<>();
        discount_list = new LinkedList<>();
        categoryDTO = new CategoryDTO(index , name , "");
        this.inv_dal_controller = inv_dal_controller;
    }

    /**
     *  This function called from CategoryService when there is a requirement to produce
     *  an inventory report.
     * @return
     */
    @Override
    public String produceInventoryReport(String index) {
        if(index == "") {
            String outCome = "Category : " + name + "\n\n";
            String reportString = "";
            for (ProductCategoryManagement curCategory : categories_list) {
                reportString += "\t" + curCategory.produceInventoryReport("") + "\n";
            }
            if (reportString.isBlank())
                reportString =  "\t\tNo products in this category";
            return outCome+reportString;
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            return categories_list.get(current_index).produceInventoryReport(next_index);
        }
    }

    /**
     *
     */
    public CategoryDTO getDto(){
        return categoryDTO;
    }

    /**
     * This function gets a Discount as an argument for this current category,
     * and update the sub-category with the new discount.
     * @param discount
     * @param index
     */
    @Override
    public void setDiscount(String index , Discount discount) throws Exception {
        discount_list.add(discount);
        if(index == ""){
            for (ProductCategoryManagement p :categories_list
            ) {
                p.setDiscount("" , discount);
            }
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            if (categories_list.size()<= current_index)
                throw new Exception("Illegal index");
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

    /**
     * This function present the names of this specific category
     * @return
     */
    private String present_names(){
        if(categories_list.size() == 0){
            System.out.println("No products in this category");
            return "";
        }
        String names = name+":\n";
        int index = 1;
        for(ProductCategoryManagement cur_category : categories_list){
            names += index++ + " : " + cur_category.get_name() + ", ";
        }
        return names.substring(0,names.length()-2);
    }

    /**
     * This function show the details on "index" sub-category
     * @param index
     * @return
     * @throws Exception
     */
    @Override
    public String show_data(String index) throws Exception {
        if(index == "")
            return present_names();
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            if (categories_list.size()<=current_index)
                throw new Exception("Illegal index has been chosen");
            return categories_list.get(current_index).show_data(next_index);
        }
    }

    /**
     * This function adds an item \ category to the list of the products.
     * @param add_product
     */
    @Override
    public void add_product(ProductCategoryManagement add_product) {
        categories_list.add(add_product);
    }

    /**
     * Adding product by its name and index- for adding new categories
     * @param index
     * @param name
     */
    @Override
    public void add_product(String index, String name) throws Exception {
        if (index == "") {
            Category new_category = new Category(name," ",inv_dal_controller);
            categories_list.add(new_category);
            new_category.categoryDTO.setFatherCategoryIndex(this.categoryDTO.getIndex());
            new_category.categoryDTO.setIndex(categoryDTO.getIndex() +"."+ categories_list.size());
            inv_dal_controller.insert(new_category.getDto());
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            categories_list.get(current_index).add_product(next_index,name);
        }

    }

//    public Category getCategoryByIndex(String index) throws Exception {
//        if (index == "") {
//            return this;
////            DTO curDto = new_category.getDto();
////            inv_dal_controller.insert(curDto);
//        }
//        else {
//            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
//            String next_index = Util.extractNextIndex(index);
//            categories_list.get(current_index).add_product(next_index,name);
//        }
//    }
    /**
     * This function returns the name of the current category
     * @return
     */
    @Override
    public String get_name() {
        return name;
    }

    /**
     * This function returns the list of the products in this category
     * @return
     */
    public LinkedList<ProductCategoryManagement> getCategories_list() {
        return categories_list;
    }

    /**
     * This function insert an item to the sub-category name of "index" and updates
     * the item with the relevant details.
     * @param index
     * @param i
     * @throws Exception
     */
    public void add_item(String index, Item i) throws Exception {
        for (Discount d:discount_list
             ) {
            i.setDiscount("",d);
        }
        if (index == ""){
            categories_list.add(i);
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            if (categories_list.size()<=current_index)
                throw new Exception("Illegal index");
            categories_list.get(current_index).add_item(next_index, i);
        }
    }
}