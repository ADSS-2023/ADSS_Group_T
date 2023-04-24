package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.Item;
import BusinessLayer.Stock.ItemToOrder;
import BusinessLayer.Stock.OrderController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class OrderService {
    private OrderController orderController;

    public OrderService(Inventory inventory , Supplier.OrderService orderService) {
        this.orderController = new OrderController(inventory , orderService);
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
    public String createRegularOrder(Map<Integer,Integer> items_quantity){
        try {
            orderController.createRegularOrder(items_quantity);
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

    public void nextDay() {
        this.orderController.nextDay(LocalDate.now().getDayOfWeek().plus(1));
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
}
