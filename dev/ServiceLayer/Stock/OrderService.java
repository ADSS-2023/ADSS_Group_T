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
    public String createRegularOrder(Map<Integer,Integer> items_quantity){
        try {
            orderController.createRegularOrder(items_quantity);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Order recived succesfully";
    }
}
