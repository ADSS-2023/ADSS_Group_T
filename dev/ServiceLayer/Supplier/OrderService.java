package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier_Stock.ItemToOrder;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

public class OrderService {
    private OrderController oc;
    private SupplierController sc;

    public OrderService(OrderController oc , SupplierController sc) {
         this.oc = oc;
         this.sc=sc;
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

    public boolean editRegularItem(ItemToOrder item, DayOfWeek day,int newQuantity) {
        try {
            if(day==null)
                return false;
            oc.editRegularItem(item, day,newQuantity);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<ItemToOrder> getRegularOrder(DayOfWeek day) throws Exception {
        try {

            return oc.getRegularOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ItemToOrder> getSpecialOrder(DayOfWeek day) throws Exception {
        try {
            if(day==null)
                return null;
            return oc.getSpecialOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean removeRegularItem(ItemToOrder item, DayOfWeek day) throws Exception {
        try {
            if(day==null)
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
