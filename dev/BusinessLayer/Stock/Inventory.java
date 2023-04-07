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

    /**
     * returns a report of all the item in stock that have reached their minimum amount.
     * @return
     */
    public String produce_shortage_list(){
        String report="";
        for (Item item:shortage_list
             ) {
            report += String.format("%s, %s\n minimal amount: %d"+"\n"+"current amount: %d\namount to order: %d",
                    item.name,item.manufacturer_name,item.min_amount,item.current_amount(), item.min_amount-item.current_amount());
                    report+="\n-------------------------------------------\n";
        }
        if (report.isEmpty()) return "no shortage";
        return report;
    }

    /**
     * This function adds a damaged item to the list of the damaged items.
     * @param item_id
     * @param order_id
     * @param amount
     * @param description
     * @return
     * @throws Exception
     */
    public String addDamagedItem(int item_id,int order_id,int amount,String description) throws Exception {
        return damaged.addDamagedItem(items.get(item_id),order_id,amount,description);
    }

    /**
     * This function present the names of this specific category
     * @return
     */
    private String present_names(){
        String names = "";
        int index = 1;
        for(Category cur_category : categories){
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

    public String show_data(String index) throws Exception {
        if(index == "")
            return present_names();
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            if (categories.size()<= current_index)
                throw new Exception("Illegal index has been chosen");
            return categories.get(current_index).show_data(next_index);
        }

    }

    /**
     * Set discount to a specific category or specific item
     * @param index the index of the category/item in the categories tree
     * @param percentageAmount
     * @param end_date_string
     * @param start_date_string
     */
    public void set_discount(String index , double percentageAmount , String end_date_string , String start_date_string) throws Exception {
        int current_index = Integer.parseInt(Util.extractFirstNumber(index));
        String next_index = Util.extractNextIndex(index);
        LocalDate end_date = Util.stringToDate(end_date_string);
        LocalDate start_date = Util.stringToDate(end_date_string);
        if (categories.size()<= current_index)
            throw new Exception("Illegal index");
        categories.get(current_index).setDiscount(next_index , new Discount(start_date , end_date , percentageAmount));
    }

    /**
     * This function receives a list of indexes that represent categories,
     * and produce an inventory report for all of those categories.
     * @param categories_indexes
     * @return
     */
    public String produce_inventory_report(LinkedList<String> categories_indexes) throws Exception {

        if(Util.no_categories(categories_indexes))
            throw new Exception("No categories have been chosen");
        String report = "inventory report:\n" ;
        for (String index:categories_indexes
             ) {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            report +="\n"+ categories.get(current_index).produceInventoryReport(next_index);
        }
        return report;
    }

    /**
     * This function is a setup function with specific items and categories
     * in order to test the system.
     */
    public void setUp() {
        Item milk_3 = new Item(0 , "3% milk" , 5 , "IDO LTD",  3.5);
        set_item_call_back(milk_3);
        Item milk_1_5=new Item(1 , "1.5% milk" , 2 , "IDO LTD",  3.5);
        set_item_call_back(milk_1_5);
        Item yellow_cheese = new Item(2,"yellow cheese",5,"Emeck",10);
        set_item_call_back(yellow_cheese);
        Item beef_sausage = new Item(3,"Beef Sausage",3,"Zogloveck",25);
        set_item_call_back(beef_sausage);
        categories.add(new Category("Milk-product", "0"));
        categories.get(0).add_product(new Category("Cheese" , "0"));
        categories.get(0).add_product(new Category("bottle milk" , "1"));
        categories.get(0).getCategories_list().get(0).add_product(yellow_cheese);
        categories.get(0).getCategories_list().get(1).add_product(milk_3);
        categories.get(0).getCategories_list().get(1).add_product(milk_1_5);
        items.put(2,yellow_cheese);
        items.put(0,milk_3);
        items.put(1,milk_1_5);
        yellow_cheese.recive_order(20,2,3,5.3,"ile 2 shelf 3",Util.stringToDate("2023-04-25"));
        milk_3.recive_order(155,20,20,2.15,"ile 5 shelf 10",Util.stringToDate("2023-05-20"));
        milk_1_5.recive_order(120,10,10,2.55,"ile 5 shelf 11",Util.stringToDate("2023-05-23"));
        beef_sausage.recive_order(345,5,15,12.25,"ile 6 shelf 2",Util.stringToDate("2023-10-20"));
        categories.add(new Category("Meat-product", "1"));
        categories.get(1).add_product(new Category("chicken" , "0"));
        categories.get(1).add_product(new Category("beef" , "1"));
        categories.get(1).getCategories_list().get(1).add_product(beef_sausage);
        items.put(3,beef_sausage);
    }

    /**
     * This function sets the minimal amount of items in the specific item.
     * @param item_id
     * @param amount
     * @return
     */
    public String set_minimal_amount(int item_id, int amount) {
        return items.get(item_id).setMin_amount(amount);
    }

    /**
     * This function produces the report of the damaged items.
     * @return
     * @throws Exception
     */
    public String produce_damaged_report() throws Exception{
        return damaged.produce_damaged_report();
    }

    /**
     * Add new item to the system
     * @param categories_index
     * @param item_id
     * @param name
     * @param min_amount
     * @param manufacturer_name
     * @param original_price
     */
    public void add_item(String categories_index,int item_id, String name, int min_amount, String manufacturer_name, double original_price) throws Exception {
        Item i = new Item(item_id,name,min_amount,manufacturer_name,original_price);
        set_item_call_back(i);
        items.put(item_id,i);
        int current_index = Integer.parseInt(Util.extractFirstNumber(categories_index));
        String next_index = Util.extractNextIndex(categories_index);
        categories.get(current_index).add_item(next_index,i);

    }

    /**
     * Receive a new order for a specific item
     * @param order_id each order must have unique id.
     * @param item_id
     * @param amount
     * @param location
     * @param validity
     * @param cost_price the price that the store paid for this item, after discounts.
     */
    public void receive_order(int order_id, int item_id, int amount,String location,LocalDate validity,double cost_price) {
        int amount_warehouse = amount/2;
        int amount_store = amount - amount_warehouse;
        items.get(item_id).recive_order(order_id,amount_warehouse,amount_store,cost_price,location,validity);
    }

    /**
     * This functions returns a specific Item by an id as a parameter.
     * @param id
     * @return
     */
    public Item get_item_by_id(int id){
        return items.get(id);
    }
}
