package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderController;

import java.util.List;

public class OrderService {
    private OrderController oc;

    public OrderService(OrderController oc) {
        this.oc = oc;
    }
    public void createOrder(List<ItemToOrder> itemsList) throws Exception {
        for (ItemToOrder item  : itemsList) {
            oc.addToShoppingLists(item);
        }
        oc.createOrders();
    }
}
