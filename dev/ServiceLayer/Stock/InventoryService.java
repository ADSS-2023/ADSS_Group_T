package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.util.LinkedList;

public class InventoryService {
    private Inventory inventory;
    public String produce_shortage_report(){
        return null;
    }
    public String show_data(){
        return inventory.show_data("");
    }

    public InventoryService(){
        inventory = new Inventory();
    }
    public Inventory get_inventory(){
        return inventory;
    }

    public String produce_inventory_report(LinkedList<String> categories_list){
        try {
            return inventory.produce_inventory_report(categories_list);
        }
        catch (Exception e) {
            return e.toString();
        }
    }

    public void set_discount(String product, double percentageAmount, String end_date_string, String start_date_string) {
        inventory.set_discount(product , percentageAmount , end_date_string , start_date_string);
    }

    public void setUp() {
      inventory.setUp();
    }
}

