package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;

import java.util.LinkedList;
import java.util.List;

public class OrderService {
    private OrderController oc;
    private SupplierController sc;

    public OrderService(OrderController oc) {
        this.oc = oc;
    }
    public String createOrder(List<ItemToOrder> itemsList){
        try {
            if(itemsList.size()==0)
                return "No Item has been ordered";
            oc.createOrder(itemsList);
            return "Order created successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public List<String> getOrders(){
        List<String>  orders = new LinkedList<>();
        try{
             List<OrderBusiness> orderBusinessList =  oc.getOrders();
            for (OrderBusiness order:orderBusinessList) {
               orders.add(order.toString());
            }
        }
        catch (Exception e){
            return orders;
        }
        finally {
            return orders;
        }
    }


}
