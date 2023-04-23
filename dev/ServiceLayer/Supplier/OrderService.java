package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import Util.WeekDays;
import Util.WeekDaysFunc;
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

    public boolean editRegularItem(ItemToOrder item, String day) throws Exception {
        try {
            WeekDays weekDay = WeekDaysFunc.toDayOfWeek(day);
            if(weekDay==null)
                return false;
            oc.editRegularItem(item, day);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<ItemToOrder> getRegularOrder(String day) throws Exception {
        try {
            WeekDays weekDay = WeekDaysFunc.toDayOfWeek(day);
            if(weekDay==null)
                return null;
            return oc.getRegularOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ItemToOrder> getSpecialOrder(String day) throws Exception {
        try {
            WeekDays weekDay = WeekDaysFunc.toDayOfWeek(day);
            if(weekDay==null)
                return null;
            return oc.getSpecialOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean removeRegularItem(ItemToOrder item, String day) throws Exception {
        try {
            WeekDays weekDay = WeekDaysFunc.toDayOfWeek(day);
            if(weekDay==null)
                return false;
            oc.removeRegularItem(item, day);
            return true;
        }
        catch (Exception e){
            return false;
        }
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
