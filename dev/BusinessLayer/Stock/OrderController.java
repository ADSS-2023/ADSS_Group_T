package BusinessLayer.Stock;

import java.time.DayOfWeek;
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

    /**
     * This method send an order to suppliers which will be supplied each week in a permanent day.
     * @param items_quantity a map that maps item id to the desired amount
     */
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

    /**
     * This method send an order to suppliers which will be supplied one time as a special order.
     * @param items_quantity a map that maps item id to the desired amount.
     * @param isUrgent boolean flag to indicate whether the order priority is arrival.
     */
    public void createSpecialOrder(Map<Integer, Integer> items_quantity,boolean isUrgent){
        LinkedList<ItemToOrder> list_to_order = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : items_quantity.entrySet()) {
            Integer item_id = entry.getKey();
            Integer quantity = entry.getValue();
            list_to_order.addLast(new ItemToOrder(inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null));
        }
        //call to supplier service with list_to_order
    }

    public void makeAutomaticallyOrder(DayOfWeek curDay) {
        List<Item> cur_shortage_list = inventory.getShortageList();
        List<ItemToOrder> curDay_list = order_service.getRegularOrder(curDay.toString());
        for (Item item : cur_shortage_list) {
            int item_id = item.getItem_id();
            for (ItemToOrder item_to_order : curDay_list) {
                int item_to_order_id = inventory.name_to_id[item_to_order.getManufactorerName() + item_to_order.getName()];
                if (item_id == item_to_order_id) {
                    // make an ungent order
                }
            }
        }
    }
}


