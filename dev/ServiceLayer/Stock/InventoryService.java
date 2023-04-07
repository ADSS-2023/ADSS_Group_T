package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.util.LinkedList;

public class InventoryService {
    private Inventory inventory;

    public InventoryService(){
        inventory = new Inventory();
    }
    public String show_data(){
        try {
            return inventory.show_data("");
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }


    public Inventory get_inventory(){
        return inventory;
    }

    public String produce_inventory_report(LinkedList<String> categories_list){
        try {
            return inventory.produce_inventory_report(categories_list);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public void set_discount(String product, double percentageAmount, String end_date_string, String start_date_string) {
        try {
            inventory.set_discount(product , percentageAmount , end_date_string , start_date_string);
        }
        catch (Exception e){
            e.getMessage();
        }

    }
    public String produce_shortage_report(){
        return inventory.produce_shortage_list();
    }
    public void setUp() {
      inventory.setUp();
    }

}

