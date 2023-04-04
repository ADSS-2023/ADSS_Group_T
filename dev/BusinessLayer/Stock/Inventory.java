package BusinessLayer.Stock;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
/*
    This class represents the inventory of the store. It includes all items
    ordered in two data structures, categories trees and a hash table.
    This class also keep on count the shortage items.
 */
public class Inventory {
    protected List<Category> categories;
    protected Dictionary<Integer,Item> items;
    protected List<Item> shortage_list;
    public Inventory(){
        categories = new LinkedList<>();
        items = new Hashtable<>();
        shortage_list = new LinkedList<>();
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
//    public Category get_category(String index){
//        int number_index = (Integer) index.split(".")[0];
//    }
}
