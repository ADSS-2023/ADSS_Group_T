package BusinessLayer.Stock;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class OrderController {
    private Inventory inventory;
    private Supplier.OrderService order_service;
    private List<ItemToOrder> items_to_place;

    public OrderController(Inventory inventory, Supplier.OrderService orderService) {
        this.inventory = inventory;
        this.order_service = orderService;
        items_to_place = new LinkedList<>();
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
                    //order_service.editRegularItem(item_to_edit , curDay); // check if its ok ****UNCOMMIT IT !!!****
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
    public void receiveOrders(List<ItemToOrder> newOrder){
        newOrder.stream().map((x)->items_to_place.add(x));
    }
    /**
     * place new arrival in store by index in waiting list
     * @param index
     * @param location
     * @return
     */
    public void placeNewArrival(int index, String location) throws Exception {
        if(index > items_to_place.size())
            throw new Exception("Illegal index");
        ItemToOrder tempItem = items_to_place.get(index-1);
        inventory.itemToOrder_to_item(tempItem).recive_order(
                tempItem.getOrderId(),
                (int)Math.floor(tempItem.getQuantity()/2),
                (int)Math.ceil(tempItem.getQuantity()/2),
                tempItem.getCost_price(),
                location,
                tempItem.getExpiryDate());
        items_to_place.remove(index-1);
    }


    /**
     * Presents all the items that hasn't been placed yet
     *
     *  @return
     */
    public String presentItemsToBePlaced() throws Exception {
        String toReturn = "";
        if (items_to_place.isEmpty())
            throw new Exception("No items to be placed");
        for(int i = 1; i<= items_to_place.size();i++){
            ItemToOrder item = items_to_place.get(i-1);
            toReturn+=String.format("%d. Order id:%s, item:%s, manufacturer:%s\n",4,item.getOrderId(),item.getProductName(),item.getManufacturer());
        }
        return toReturn;
    }
}
