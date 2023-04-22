package BusinessLayer.Stock;

import java.time.DayOfWeek;
import java.util.List;

public class OrderController {
    private Inventory inventory;
    private Supplier.OrderService order_service;

    public OrderController(Inventory inventory, Supplier.OrderService orderService){
        this.inventory = inventory;
        this.order_service = orderService;
    }
    public void createRegularOrder(){}

    public void makeAutomaticallyOrder(DayOfWeek curDay){
        List<Item> cur_shortage_list = inventory.getShortageList();
        List<ItemToOrder> curDay_list = order_service.getRegularOrder(curDay.toString());
        for(Item item : cur_shortage_list){
            int item_id = item.getItem_id();
            for(ItemToOrder item_to_order : curDay_list){
                int item_to_order_id = inventory.name_to_id[item_to_order.getManufactorerName() + item_to_order.getName()];
                if(item_id == item_to_order_id){
                    // make an ungent order
                }
            }
        }
    }

}
