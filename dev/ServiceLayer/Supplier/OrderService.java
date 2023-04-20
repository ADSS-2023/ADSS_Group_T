package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OrderService {
    private OrderController oc;
    private SupplierController sc;

    public OrderService(OrderController oc) {
        this.oc = oc;
    }

    public boolean createRegularOrder(List<ItemToOrder> items) throws Exception {
        try {
            if (items.size() == 0)
                return false;
            oc.createOrder(items, true, false);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean createSpecialOrder(List<ItemToOrder> items, boolean isUrgent) throws Exception {
        try {
            if (items.size() == 0)
                return false;
            oc.createOrder(items, false, isUrgent);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean editRegularItem(ItemToOrder item, String supplyday) throws Exception {
        try {
            List<String> dayNames = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            if(!dayNames.contains(supplyday))
                return false;
            oc.editRegularItem(item, supplyday);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<ItemToOrder> getRegularOrder(String day) throws Exception {
        try {
            List<String> dayNames = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            if (!dayNames.contains(day))
                return null;
            return oc.getRegularOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean removeRegularItem(ItemToOrder item, String day){
        List<String> dayNames = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        if(!dayNames.contains(day))
            return false;
        return oc.removeRegularItem(item, day);
    }

    public List<String> getOrders(){
        List<String>  orders = new LinkedList<>();
        try{//TODO:change implementation to display both types of order.
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
