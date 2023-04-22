package BusinessLayer.Stock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderController {
    private Inventory inventory;
    public OrderController(Inventory inventory){
        this.inventory = inventory;
    }
    public void createRegularOrder(Map<Integer,Integer> items_quantity){
        LinkedList<ItemToOrder> list_to_order = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : items_quantity.entrySet()) {
            Integer item_id = entry.getKey();
            Integer quantity = entry.getValue();
            list_to_order.addLast(new ItemToOrder(inventory.get_item_by_id(item_id).get_name(),
                                                  inventory.get_item_by_id(item_id).manufacturer_name, quantity,null));
        }
        //call to supplier service with list_to_order
    }

}
