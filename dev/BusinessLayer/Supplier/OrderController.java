package BusinessLayer.Supplier;

import ServiceLayer.Supplier.ItemToOrder;

import java.util.HashMap;
import java.util.List;

public class OrderController {
    private HashMap<int,OrderBusiness> orders;
    private SupplierController sc;

    public OrderController(HashMap<int, OrderBusiness> orders, SupplierController sc) {
        this.orders = orders;
        this.sc = sc;
    }
    public void createOrder(List<ItemToOrder> itemsList){

    }
    public List<OrderBusiness> getOrders(){
        return null;
    }
    private void sendDelivery(){

    }

}
