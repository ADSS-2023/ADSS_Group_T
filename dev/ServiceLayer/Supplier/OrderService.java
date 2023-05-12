package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;

import java.rmi.server.ExportException;
import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private OrderController oc;
    private SupplierController sc;

    public OrderService(OrderController oc , SupplierController sc) {
         this.oc = oc;
         this.sc=sc;
        }
        public void loadOrders()  {
        try {
            sc.loadSuppliers();
            oc.loadOrders();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        }
    public boolean nextDay(){
        try {
            oc.executeTodayOrders();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean createRegularOrder(List<ItemToOrder> items) {
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

    public boolean createSpecialOrder(List<ItemToOrder> items, boolean isUrgent)  {
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

    public boolean editRegularItem(ItemToOrder item, DayOfWeek day) {
        try {
            if(day==null)
                return false;
            oc.editRegularItem(item, day);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<ItemToOrder> getRegularOrder(DayOfWeek day)  {
        try {
            return oc.getRegularOrder(day);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ItemToOrder> getSpecialOrder(DayOfWeek day)  {
        try {
            if(day==null)
                return null;
            return oc.getSpecialOrder(day);
        }
        catch (Exception e){
            return null;
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

    public String deleteAllOrders(){
        try {
            oc.deleteOrders();
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Deleted Successfully";
    }

    public List<ItemToOrder> getAllProducts() {
        List<ItemToOrder> items = new LinkedList<>();
        ConcurrentHashMap<Integer, SupplierProductBusiness> products = new ConcurrentHashMap<>();
        try {
            ConcurrentHashMap<Integer, SupplierBusiness> suppliers=sc.getSuppliers();
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                products = entry.getValue().getProducts();
                for (Map.Entry<Integer, SupplierProductBusiness> entry2 : products.entrySet())
                    items.add(new ItemToOrder(entry2.getValue().getName(),
                            entry2.getValue().getManufacturer(),entry2.getValue().getMaxAmount(),
                            null,-1,entry2.getValue().getPrice()));
            }
        }
        catch (Exception e){
            return null;
        }
        finally {
            return items;
        }
    }


}
