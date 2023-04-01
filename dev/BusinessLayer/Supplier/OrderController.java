package BusinessLayer.Supplier;

import ServiceLayer.Supplier.ItemToOrder;

import java.time.LocalDateTime;
import java.util.*;

public class OrderController {
    private List<OrderBusiness> orders;
    private SupplierController sc;
    private int orderCounter;
    private HashMap<Integer,List<OrderProduct>> shoppingLists; // supplierNumber to list of products


    public OrderController(SupplierController sc) {
        orders = new LinkedList<>();
        this.sc = sc;
        shoppingLists = new HashMap<>();
        orderCounter=0;
    }
    public void createOrders(){
       for( Map.Entry<Integer, List<OrderProduct>> shoppingList : shoppingLists.entrySet()){
           List<OrderProduct> products = shoppingList.getValue();
           SupplierBusiness supplier = sc.getSupplier(shoppingList.getKey());
           Map.Entry<String,Integer> entry = supplier.getContacts().entrySet().iterator().next();
           String contactName = entry.getKey();
           int contactNum = entry.getValue();
           OrderBusiness order = new OrderBusiness(orderCounter++,supplier.getName(),LocalDateTime.now(),supplier.getAddress(),
                   "SuperLi",supplier.getSupplierNum(),contactName,contactNum,products);
           orders.add(order);
           if(!supplier.getSelfDelivery()){
               sendDelivery(order);
           }

       }
    }
    public List<OrderBusiness> getOrders(){
        return orders;
    }
    private void sendDelivery(OrderBusiness order){
        //activate module DELIVERY
    }

    public void addToShoppingLists(String productName, String manufacturer, int quantity) {
        HashMap<SupplierProductBusiness,Integer> productsToOrder  =  sc.findSuppliersProduct(productName,manufacturer,quantity);
        for(Map.Entry<SupplierProductBusiness, Integer> product : productsToOrder.entrySet()) {
            int productNumber = product.getKey().getProductNum();
            int initialPrice = product.getKey().getPrice();
            int discount = product.getKey().getDiscount(quantity);
            int finalPrice = initialPrice*quantity*(1-(discount/100));
            OrderProduct orderProduct = new OrderProduct(productName,productNumber,quantity,initialPrice,discount,finalPrice);
            int supplierNum = product.getKey().getSupplierNum();
            if(!shoppingLists.containsKey(supplierNum))
                shoppingLists.put(supplierNum,new LinkedList());
            shoppingLists.get(supplierNum).add(orderProduct);
        }
    }
}

