package BusinessLayer.Supplier;

import java.util.HashMap;

public class OrderController {
    private HashMap<int,OrderBusiness> orders;
    private SupplierController sc;

    public OrderController(HashMap<int, OrderBusiness> orders, SupplierController sc) {
        this.orders = orders;
        this.sc = sc;
    }
    public void createOrder(List<ItemToSend>){

    }
    public List<OrderBusiness> getOrders(){

    }
    private void sendDelivery(){

    }

}
