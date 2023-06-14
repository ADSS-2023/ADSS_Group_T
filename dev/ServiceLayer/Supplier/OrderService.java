package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderBusiness;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import ServiceLayer.Supplier_Stock.Response;

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

        public Response loadOrders()  {
        try {
            sc.loadSuppliers();
            oc.loadOrders();
            return Response.okResponse("Load data succeeded.");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        }
    public Response nextDay(){
        try {
            oc.executeTodayOrders();
            return Response.okResponse(true);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
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


    public Response getOrders(){
        List<String>  orders = new LinkedList<>();
        try{//TODO:change implementation to display both types of order.
             List<OrderBusiness> orderBusinessList =  oc.getOrders();
            for (OrderBusiness order:orderBusinessList) {
               orders.add(order.toString());
            }
            return Response.okResponse(orders);
        }
        catch (Exception e){
           return Response.errorResponse(e.getMessage());
            //return orders; TODO: why we didnt return e.getMessage()?
        }
    }

    public Response deleteAllOrders(){
        try {
            oc.deleteOrders();
            return Response.errorResponse("Deleted Successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
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
            return  items;
        }
        catch (Exception e){
            return  null;
        }
    }


}
