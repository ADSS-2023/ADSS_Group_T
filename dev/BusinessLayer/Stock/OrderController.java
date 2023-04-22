package BusinessLayer.Stock;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class OrderController {
    private Inventory inventory;
    private Supplier.OrderService order_service;

    public OrderController(Inventory inventory, Supplier.OrderService orderService) {
        this.inventory = inventory;
        this.order_service = orderService;
    }

    public void createRegularOrder(Map<Integer, Integer> items_quantity) {
        LinkedList<ItemToOrder> list_to_order = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : items_quantity.entrySet()) {
            Integer item_id = entry.getKey();
            Integer quantity = entry.getValue();
            list_to_order.addLast(new ItemToOrder(inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null));
        }
        //call to supplier service with list_to_order
    }

    public String makeAutomaticallyOrder(DayOfWeek curDay) {
        List<Item> cur_shortage_list = inventory.getShortageList();
        List<ItemToOrder> curDay_list = order_service.getRegularOrder(curDay.toString());
        Map<Integer , Integer> item_to_order_map = new HashMap<>();
        for (Item item : cur_shortage_list) {
            int item_id = item.getItem_id();
            for (ItemToOrder item_to_order : curDay_list) {
                String manufacturer_name = item_to_order.getManufacturer();
                String product_name =  item_to_order.getProductName();
                int item_to_order_id = inventory.name_to_id.get(manufacturer_name + " " + product_name);
                if (item_id == item_to_order_id) {
                    item_to_order_map.put(item_id , 5);
                    //make a flag
                }
            }
            //if not flag (not get it tomorrow)
            //make to item special order.
        }
        this.createSpecialOrder(item_to_order_map , true);
        return "";
    }


}


//50 < 60
// 5
//manager = 10