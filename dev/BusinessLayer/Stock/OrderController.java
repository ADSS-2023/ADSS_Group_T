package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemOrderdDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemToOrderDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import ServiceLayer.Supplier.OrderService;

import java.sql.SQLException;
import java.time.DayOfWeek;

import java.util.*;


public class OrderController {
    private Inventory inventory;
    private OrderService order_service;
    private List<ItemToOrder> items_to_place;
    /**
     * A map that holds <id -- amount> for items that have been ordered in a former special order,
     * buy yet to be received.
     */
    private Map<Integer,Integer> special_orders_track;
    private InventoryDalController inventoryDalController;

    public OrderController(Inventory inventory, OrderService orderService,InventoryDalController inventoryDalController) {
        this.inventory = inventory;
        this.order_service = orderService;
        items_to_place = new LinkedList<>();
        special_orders_track = new HashMap<>();
        this.inventoryDalController = inventoryDalController;
    }

    public void setInventoryDalController(InventoryDalController inv){
        inventoryDalController = inv;
    }

    /**
     * This method send an order to suppliers which will be supplied each week in a permanent day.
     * @param items_quantity a map that maps item id to the desired amount
     */
    public void createRegularOrder(Map<Integer, Integer> items_quantity) throws Exception {
        LinkedList<ItemOrderdDTO> insert_items = new LinkedList<>();
        LinkedList<ItemToOrder> list_to_order = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : items_quantity.entrySet()) {
            Integer item_id = entry.getKey();
            Integer quantity = entry.getValue();
            ItemToOrder new_item = new ItemToOrder(inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null, -1,-1);
            list_to_order.addLast(new_item);
            ItemOrderdDTO item_dto = new ItemOrderdDTO(item_id,quantity);
            insert_items.add(item_dto);
        }

        if(!order_service.createRegularOrder(list_to_order)){
            throw new Exception("\u001B[31mOrder cannot be supplied\u001B[0m");
        }
        else{
            // only if success!
            for (ItemOrderdDTO item_dto :  insert_items) {
                inventoryDalController.insert(item_dto);
            }
        }
    }

    /**
     * This method send an order to suppliers which will be supplied one time as a special order.
     * @param items_quantity a map that maps item id to the desired amount.
     * @param isUrgent boolean flag to indicate whether the order priority is arrival.
     */
    public void createSpecialOrder(Map<Integer, Integer> items_quantity,boolean isUrgent) throws Exception {
        LinkedList<ItemOrderdDTO> insert_items = new LinkedList<>();
        LinkedList<ItemToOrder> list_to_order = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : items_quantity.entrySet()) {
            Integer item_id = entry.getKey();
            Integer quantity = entry.getValue();
            list_to_order.addLast(new ItemToOrder(inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).manufacturer_name, quantity, null, -1,-1));
            if(special_orders_track.containsKey(item_id)){
                quantity += special_orders_track.get(item_id);
            }
            ItemOrderdDTO item_dto = new ItemOrderdDTO(item_id,quantity);
            insert_items.add(item_dto);
            special_orders_track.put(item_id,quantity);
        }
        if (!order_service.createSpecialOrder(list_to_order,isUrgent)) {
            throw new Exception("\u001B[31mOrder cannot be supplied\u001B[0m");
        }
        else{
            // only if success!
            for (ItemOrderdDTO item_dto :  insert_items) {
                inventoryDalController.insert(item_dto);
            }
        }

    }

    /**
     * This function happens gets a day (happens day before) and gets all the items that comes tomorrow,
     * and edit them if there is a item that in the shortage list. And makes a call to createSpecialOrder - if
     * the items are not in the list of the items that comes tomorrow.
     * @param curDay
     * @return
     */
    public String makeAutomaticallyOrder(DayOfWeek curDay) throws Exception {
        List<Item> cur_shortage_list = inventory.getShortageList();
        List<ItemToOrder> curDay_list1 = order_service.getRegularOrder(curDay);
        List<ItemToOrder> curDay_list2 = order_service.getSpecialOrder(curDay);
        List<ItemToOrder> curDay_list = new LinkedList<>();
        curDay_list.addAll(curDay_list1);
        curDay_list.addAll(curDay_list2);
        Map<Integer , Integer> item_to_order_map = new HashMap<>();
        boolean found = false;
        for (Item item : cur_shortage_list) {
            //calculate how many need to order
            int amount_to_order = (item.min_amount - item.current_amount())- amountOfReceivedItem(curDay_list , item.manufacturer_name , item.name);
            if(special_orders_track.containsKey(item.item_id))
                amount_to_order -=special_orders_track.get(item.item_id);
            if(amount_to_order > 0) {
                for (ItemToOrder item_to_order : curDay_list1) {
                    if(!found) {
                        int item_to_order_id = inventory.name_to_id.get(item_to_order.getProductName() + " " + item_to_order.getManufacturer());
                        if (item.getItem_id() == item_to_order_id) { // if found an item that comes at curDay
                            item_to_order.setQuantity(amount_to_order + item_to_order.getQuantity()); //set the new quantity that required
                            found = order_service.editRegularItem(item_to_order, curDay);
                        }
                    }
                }
            }
            if(!found){ // if there is no item that comes at curDay make add it to the map
                item_to_order_map.put(item.getItem_id() , (int)(amount_to_order*1.3)); // need to check how much should double it.
            }
            found = false;
        }
        if(!item_to_order_map.isEmpty())
            this.createSpecialOrder(item_to_order_map , true);
        return "";
    }

    private int amountOfReceivedItem(List<ItemToOrder> items_list , String manufacturer_name , String product_name){
        int amount = 0;
        for(ItemToOrder item_to_order : items_list){
            if(item_to_order.getManufacturer().equals(manufacturer_name) && item_to_order.getProductName().equals(product_name)){
                amount += item_to_order.getQuantity();
            }
        }
        return amount;
    }

    public void editRegularOrder(int id, DayOfWeek day, int new_amount) throws Exception {
        Item cur_item = inventory.get_item_by_id(id);
        order_service.editRegularItem(new ItemToOrder(cur_item.get_name(), cur_item.manufacturer_name , new_amount , null, -1,-1), day);
    }

    /**
     * receive new order that arrived to the store
     * @param newOrder order id,item
     */
    public void receiveOrders(List<ItemToOrder> newOrder) throws SQLException {
        for (ItemToOrder ito:newOrder) {
            //int num = this.inventory.name_to_id.get(ito.getProductName() + ito.getProductName());
            inventoryDalController.insert(new ItemToOrderDTO(
                    "inventory_waiting_list",ito.getProductName(),ito.getManufacturer(),
                    ito.getQuantity(),ito.getExpiryDate().toString(),ito.getCostPrice(),ito.getOrderId()));
        }
        items_to_place.addAll(newOrder);
        handle_special_order_track(newOrder);
    }

    /**
     * Gets the new arrivals, and check the special order track to see if update is needed
     * @param newOrder
     */
    private void handle_special_order_track(List<ItemToOrder> newOrder) throws SQLException {
        for (ItemToOrder itemToOrder : newOrder){
            int item_id = inventory.itemToOrder_to_item(itemToOrder).item_id;
            if(special_orders_track.containsKey(item_id)){
                int amount = special_orders_track.get(item_id);
                if(itemToOrder.getQuantity()>= amount) {
                    inventoryDalController.delete(new ItemOrderdDTO(item_id, itemToOrder.getQuantity()));
                    special_orders_track.remove(item_id);
                }
                else {
                    amount -= itemToOrder.getQuantity();
                    inventoryDalController.update(new ItemOrderdDTO(item_id, itemToOrder.getQuantity()),
                            new ItemOrderdDTO(item_id, amount));
                    special_orders_track.put(item_id,amount);
                }
            }
        }
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
        inventoryDalController.delete(new ItemToOrderDTO(
                "inventory_waiting_list",tempItem.getProductName(),tempItem.getManufacturer(),
                tempItem.getQuantity(),tempItem.getExpiryDate().toString(),tempItem.getCostPrice(),tempItem.getOrderId()));
        inventory.itemToOrder_to_item(tempItem).recive_order(
                tempItem.getOrderId(),
                (int)Math.floor(tempItem.getQuantity()/2),
                (int)Math.ceil(tempItem.getQuantity()/2),
                tempItem.getCostPrice(),
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
            toReturn+=String.format("%d. Order id:%s, item:%s, manufacturer:%s, amount:%d\n",i,item.getOrderId(),item.getProductName(),item.getManufacturer(),item.getQuantity());
        }
        return toReturn;
    }

    public void nextDay() throws Exception {
        this.makeAutomaticallyOrder(Util_Supplier_Stock.getCurrDay().plusDays(1).getDayOfWeek());
        this.inventory.nextDay(Util_Supplier_Stock.getCurrDay().plusDays(1).getDayOfWeek());
    }

    public String presentItemsByDay(DayOfWeek cur_day) throws Exception {
        String toReturn = "";
        List<ItemToOrder> items_to_show = order_service.getRegularOrder(cur_day);
        Map<String , Integer> map_of_amount = new HashMap(); // list that sums all the items from a specific one
        if (items_to_show.isEmpty())
            return "No items to present\n";
        for(ItemToOrder item : items_to_show) {
            String item_key = item.getProductName() +" "+ item.getManufacturer();
            if(map_of_amount.containsKey(item_key))
                map_of_amount.put(item_key , map_of_amount.get(item_key) + item.getQuantity());
            else
                map_of_amount.put(item_key, item.getQuantity());
        }
        for (Map.Entry<String, Integer> entry : map_of_amount.entrySet()) {
            toReturn+=String.format("Item id: %s, item name and manufacturer name: %s, amount: %s\n"
                    ,inventory.name_to_id.get(entry.getKey()),entry.getKey(),entry.getValue());
        }
        return toReturn;
    }

    /**
     * Set up function to test place items functionality
     */
    public void set_up_waiting_items() throws SQLException {
        ItemToOrder milk_3 = new ItemToOrder("3% milk","IDO LTD",40, Util.stringToDate("2023-05-10"),12,1.2);
        ItemToOrder beef_sausage = new ItemToOrder("Beef Sausage","Zogloveck",16,Util.stringToDate("2023-10-01"),1005,10.05);
        receiveOrders(Arrays.asList(milk_3,beef_sausage));
    }

    public String show_all_orders() throws Exception {
        String to_return = "";
        for(int i = 0; i < 7 ;i++){
            to_return +=
                    String.format("---------%s---------\nRegular orders:\n\t%s\nSpecial orders:\n\t%s",
            Util_Supplier_Stock.getCurrDay().plusDays(i).getDayOfWeek().toString()
            ,presentItemsByDay(Util_Supplier_Stock.getCurrDay().plusDays(i).getDayOfWeek())
            ,show_special_orders(Util_Supplier_Stock.getCurrDay().plusDays(i).getDayOfWeek()));
        }
        return to_return;
    }

    private String show_special_orders(DayOfWeek cur_day){
        String toReturn = "";
        List<ItemToOrder> special_orders = order_service.getSpecialOrder(cur_day);
        Map<String , Integer> map_of_amount = new HashMap(); // list that sums all the items from a specific one
        if (special_orders.isEmpty())
            return "No items to present\n";
        for(ItemToOrder item : special_orders) {
            String item_key = item.getProductName() +" "+ item.getManufacturer();
            if(map_of_amount.containsKey(item_key))
                map_of_amount.put(item_key , map_of_amount.get(item_key) + item.getQuantity());
            else
                map_of_amount.put(item_key, item.getQuantity());
        }
        for (Map.Entry<String, Integer> entry : map_of_amount.entrySet()) {
            toReturn+=String.format("Item id: %s, item name and manufacturer name: %s, amount: %s\n"
                    ,inventory.name_to_id.get(entry.getKey()),entry.getKey(),entry.getValue());
        }
        return toReturn;
    }

    public List<ItemToOrder> getItems_to_place(){
        return items_to_place;
    }

    public void loadWaitingItems() throws Exception{
        List<ItemToOrderDTO> item_to_order_DTOS = inventoryDalController.findAll("inventory_waiting_list", ItemToOrderDTO.class);
        for (ItemToOrderDTO i:item_to_order_DTOS) {
            this.items_to_place.add(new ItemToOrder(i));
        }
    }

    public void loadOrderedItems() throws Exception{
        List<ItemOrderdDTO> item_to_order_DTOS = inventoryDalController.findAll("inventory_item_ordered", ItemOrderdDTO.class);
        for (ItemOrderdDTO i:item_to_order_DTOS) {
            this.special_orders_track.put(i.getId() , i.getQuantity());
        }
    }

    /**
     * Returns all the item that can be supplied
     * @return
     */
    public String show_new_items() {
        String toReturn= "";
        for (ItemToOrder itemToOrder : order_service.getAllProducts()){
            toReturn += String.format("name: %s , manufacture: %s, amount: %d\n"
                    ,itemToOrder.getProductName(),
                    itemToOrder.getManufacturer(),
                    itemToOrder.getQuantity());
        }
        if(toReturn == "")
            toReturn =  "No item to supply";
        return toReturn;
    }
}
