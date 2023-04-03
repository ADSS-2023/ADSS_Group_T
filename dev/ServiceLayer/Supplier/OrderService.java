package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;

import java.util.List;

public class OrderService {
    private OrderController oc;
    private SupplierController sc;


    public OrderService(OrderController oc) {
        this.oc = oc;
    }
    public void createOrder(List<ItemToOrder> itemsList) throws Exception {


    }
}
