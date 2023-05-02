package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.OrderController;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import ServiceLayer.Supplier.OrderService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ManageOrderService {
    private OrderController orderController;


    public ManageOrderService() {
    }

    /**
     * This function gets an input from the user that includes id of item , day of week , new amount that
     * required for the specific item.
     * @param id
     * @param day
     * @param new_amount
     * @return
     */
    public String editRegularOrder(int id ,DayOfWeek day , int new_amount){
        try {
            orderController.editRegularOrder(id , day , new_amount);
            return "Order edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
    /**
     * Receives a map that holds item id and amount to be ordered
     * @param items_quantity
     * @return
     */
    public String createRegularOrder(Map<Integer,Integer> items_quantity,boolean isUrgent){
        try {
            orderController.createRegularOrder(items_quantity,isUrgent);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Order received successfully";
    }

    /**
     * This method send an order to suppliers which will be supplied one time as a special order.
     * @param items_quantity a map that maps item id to the desired amount.
     * @param isUrgent boolean flag to indicate whether the order priority is arrival.
     * @return string that indicates whether the action succeeded
     */
    public String createSpecialOrder(Map<Integer,Integer> items_quantity,boolean isUrgent){
        try{
            orderController.createSpecialOrder(items_quantity,isUrgent);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Order received successfully";
    }

    public String nextDay() {
        try {
            this.orderController.nextDay(LocalDate.now().getDayOfWeek().plus(1));
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Next Day functions succeed";
    }

    /**
     * Receive new order that arrived to the store
     * @param newOrder all the new arrivals
     */
    public String receiveOrders(List<ItemToOrder> newOrder){
        try{
            orderController.receiveOrders(newOrder);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Order received successfully";
    }

    /**
     * Presents all the items that hasn't been placed yet
     * @return
     */
    public String presentItemsToBePlaced(){
        try {
            return orderController.presentItemsToBePlaced();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * place new arrival in store by index in waiting list
     * @param index
     * @param location
     * @return
     */
    public String placeNewArrival(int index,String location){
        try{
            orderController.placeNewArrival(index,location);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Item has been placed successfully";
    }

    public String presentItemsById(DayOfWeek cur_day) {
        try{
            return orderController.presentItemsByDay(cur_day);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public void set_up(){
        orderController.set_up_waiting_items();
    }

    public void setOrderController(Inventory inv,OrderService orderService) {
        this.orderController = new OrderController(inv,orderService);
    }
}
