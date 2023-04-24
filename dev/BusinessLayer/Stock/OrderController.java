package BusinessLayer.Stock;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class OrderController {
    private Inventory inventory;
    private Supplier.OrderService order_service;
    private Map<Integer,ItemToOrder> items_to_place;

    public OrderController(Inventory inventory, Supplier.OrderService orderService) {
        this.inventory = inventory;
        this.order_service = orderService;
        items_to_place = new HashMap<>();
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
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null, -1,-1));
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
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null, -1,-1));
        }
        //call to supplier service with list_to_order
    }

    /**
     * This function happens gets a day (happens day before) and gets all the items that comes tomorrow,
     * and edit them if there is a item that in the shortage list. And makes a call to createSpecialOrder - if
     * the items are not in the list of the items that comes tomorrow.
     * @param curDay
     * @return
     */
    public String makeAutomaticallyOrder(DayOfWeek curDay) {
        List<Item> cur_shortage_list = inventory.getShortageList();
        //List<ItemToOrder> curDay_list = order_service.getRegularOrder(curDay.toString());
        //List<ItemToOrder> curDay_list = order_service.getSpecialOrder(curDay.toString());

        List<ItemToOrder> curDay_list = new LinkedList<>(); // NOT CORRECT
        List<ItemToOrder> curDay_list_to_edit = new LinkedList<>(); // only if needed
        Map<Integer , Integer> item_to_order_map = new HashMap<>();
        boolean found = false;
        for (Item item : cur_shortage_list) {
            int item_id = item.getItem_id();
            for (ItemToOrder item_to_order : curDay_list) {
                String manufacturer_name = item_to_order.getManufacturer();
                String product_name =  item_to_order.getProductName();
                int item_to_order_id = inventory.name_to_id.get(manufacturer_name + " " + product_name);
                if (item_id == item_to_order_id) { // if found an item that comes at curDay - add it to edited items
                    found = true;
                    ItemToOrder item_to_edit = item_to_order.clone(); // make a copy
                    item_to_edit.setQuantity(item.min_amount - item.current_amount() + item_to_order.getQuantity()); //check if the sum is correct
                    //if(!order_service.editRegularItem(item_to_edit , curDay))
                    //      add it to the order map
                    // check if its ok ****UNCOMMIT IT !!!****
                    //curDay_list_to_edit.add(item_to_edit); // add the item to the edited items
                }
            }
            if(!found){ // if there is no item that comes at curDay make add it to the map
                item_to_order_map.put(item_id , (int) (item.current_amount()*1.3)); // need to check how much should double it.
            }
            found = false;
        }
        this.createSpecialOrder(item_to_order_map , true);
        //
        return "";
    }


    public void editRegularOrder(int id, DayOfWeek day, int new_amount) {
        Item cur_item = inventory.get_item_by_id(id);
        order_service.editRegularItem(new ItemToOrder(cur_item.get_name(), cur_item.manufacturer_name , new_amount , null, -1,-1), day.toString());
    }

    /**
     * receive new order that arrived to the store
     * @param newOrder order id,item
     */
    public void receiveOrders(Map<Integer,ItemToOrder> newOrder){
        for (Map.Entry<Integer, ItemToOrder> entry : newOrder.entrySet()) {
            items_to_place.put(entry.getKey(), entry.getValue());
        }
    }
    /**
     * place new arrival in store by index in waiting list
     * @param index
     * @param location
     * @return
     */
    public void placeNewArrival(int index, String location) throws Exception {
        int count = 1;
            for (Map.Entry<Integer, ItemToOrder> entry : items_to_place.entrySet()
            ) {
                if (count == index) {
                    Integer temp_orderId = entry.getKey();
                    ItemToOrder itemToOrder = entry.getValue();
                    Item i = inventory.items.get(inventory.name_to_id.get(itemToOrder.getProductName() + " " + itemToOrder.getManufacturer()));
                    i.recive_order(temp_orderId,
                            itemToOrder.getQuantity() / 2,
                            itemToOrder.getQuantity() - itemToOrder.getQuantity() / 2,
                            itemToOrder.getCost_price(),
                            location,
                            itemToOrder.getExpiryDate());
                    items_to_place.remove(temp_orderId,itemToOrder);
                    break;
                }
                count++;
            }
        }


    /**
     * Presents all the items that hasn't been placed yet
     *
     *  @return
     */
    public String presentItemsToBePlaced() {
        String to_return = "";
        int counter = 1;
        for (Map.Entry<Integer,ItemToOrder> entry:items_to_place.entrySet()
        ) {
            Integer temp_orderId = entry.getKey();
            ItemToOrder itemToOrder = entry.getValue();
            to_return+=String.format("%d. Order id:%s, item:%s, manufacturer:%s\n",counter,temp_orderId,itemToOrder.getProductName(),itemToOrder.getManufacturer());
            counter++;
        }
        return to_return;
    }

    public void nextDay(DayOfWeek tomorrow_day) {
        this.makeAutomaticallyOrder(tomorrow_day);
        this.inventory.nextDay(tomorrow_day);
    }
}
