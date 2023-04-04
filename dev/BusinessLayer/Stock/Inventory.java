package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;

import java.time.LocalDate;
import java.util.*;

/*
    This class represents the inventory of the store. It includes all items
    ordered in two data structures, categories trees and a hash table.
    This class also keep on count the shortage items.
 */
public class Inventory {
    protected List<Category> categories;
    protected Dictionary<Integer,Item> items;
    protected List<Item> shortage_list;
    protected Damaged damaged;

    public Inventory(){
        categories = new LinkedList<>();
        items = new Hashtable<>();
        shortage_list = new LinkedList<>();
        damaged = new Damaged();
    }

    /**
     * this function gets an item and set his alert callback
     * to be added to shortage list, when needed.
     * @param item
     */
    public void set_item_call_back(Item item){
        item.set_on_alert_callback(()->shortage_list.add(item));
    }

    public String produce_shortage_list(){
        String report="";
        for (Item item:shortage_list
             ) {
            report += String.format("%s\nminimal amount: %d"+"\n"+"current amount: %d\namount to order: %d",
                    item.name,item.min_amount,item.current_amount(), item.min_amount-item.current_amount());
                    report+="\n-------------------------------------------\n";
        }
        if (report.isEmpty()) return "no shortage";
        return report;
    }

    public void addDamagedItem(int item_id,int order_id,int amount,String description){
        damaged.addDamagedItem(items.get(item_id),order_id,amount,description);
    }

    private String present_names(){
        String names = "";
        int index = 1;
        for(Category cur_category : categories){
            names += index++ + " : " + cur_category.get_name() + ", ";
        }
        return names.substring(0,names.length()-2);
    }

    public String show_data(String index) {
        if(index == "")
            return present_names();
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            return categories.get(current_index).show_data(next_index);
        }

    }

    public void set_discount(String index , double percentageAmount , String end_date_string , String start_date_string) {
        int current_index = Integer.parseInt(Util.extractFirstNumber(index));
        String next_index = Util.extractNextIndex(index);
        LocalDate end_date = Util.stringToDate(end_date_string);
        LocalDate start_date = Util.stringToDate(end_date_string);
        categories.get(current_index).setDiscount(next_index , new Discount(start_date , end_date , percentageAmount));
    }

    public String produce_inventory_report(LinkedList<String> categories_indexes){
        String report = "";
        for (String index:categories_indexes
             ) {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            report +="----"+ categories.get(current_index).produceInventoryReport(next_index);
        }
        return report;
    }

    public void setUp() {
        categories.add(new Category("Milk-product", "0"));
        categories.get(0).add_product(new Category("Cheese" , "0"));
        categories.get(0).add_product(new Category("bottle milk" , "1"));
        categories.get(0).getCategories_list().get(1).add_product(new Item(0 , "3%" , 5 , "IDO LTD",  3.5));

        categories.add(new Category("Meat-product", "0"));
        categories.get(1).add_category(new Category("chicken" , "0"));
        categories.get(1).add_category(new Category("beef" , "1"));

    }
}
