package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.util.LinkedList;

public class InventoryService {
    private Inventory inventory;
    public String produce_shortage_report(){
        return null;
    }
    public String show_data(){
        return null;
    }
    public String produce_inventory_report(LinkedList<String> categories_list){
        return inventory.produce_inventory_report(categories_list);
    }

}

