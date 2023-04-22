package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.Item;
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
     * This function makes an automatically order by the shortage items that has in our inventory
     */
    public void makeAutomaticalOrder() {
        this.orderController.makeAutomaticallyOrder(LocalDate.now().getDayOfWeek());

        // check what is get tomorrow from getRegulatOrder()
        // add to the order X*1.2 items.
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
}
