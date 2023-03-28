package ServiceLayer.Supplier;

public class OrderService {
    private OrderController oc;

    public OrderService(OrderController oc) {
        this.oc = oc;
    }
    public void createOrder(List<ItemToOrder> itemsList){
        oc.createOrder(itemsList);
    }
}
