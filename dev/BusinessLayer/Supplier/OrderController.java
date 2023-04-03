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

    public void createOrder(List<ItemToOrder> items) throws Exception {
        SupplierBusiness chosenSupplier = sc.findSingleSupplier(items);


        if(chosenSupplier==null) {
            for (ItemToOrder item : items) {
                HashMap<SupplierProductBusiness,Integer> productsToOrder  =  sc.findSuppliersProduct(item);
                for(Map.Entry<SupplierProductBusiness, Integer> product : productsToOrder.entrySet()) {
                    addToShoppingLists(item.getProductName(),item.getManufacturer()),product.getValue();

                }
            }
            createOrders();
        }
        else{
            chosenSupplier.
        }
    }



    //createOrders goes over every shopping list per supplier consisted of OrderProducts and
    // performs each order After total discounts
    public void createOrders() throws Exception {
       for(Map.Entry<Integer, List<OrderProduct>> shoppingList : shoppingLists.entrySet()){
           List<OrderProduct> products = shoppingList.getValue();
           SupplierBusiness supplier = sc.getSupplier(shoppingList.getKey());
           Map.Entry<String,Integer> entry = supplier.getContacts().entrySet().iterator().next();
           String contactName = entry.getKey();
           int contactNum = entry.getValue();

           //calculate order quantities&prices
           int totalProductsNum = 0;
           int totalorderPrice = 0;
           for (OrderProduct product : products){
                totalorderPrice+=product.getFinalPrice();
                totalProductsNum+=product.getQuantity();
           }

           //calculate supplier general discounts and final prices
           int finalPrice = supplier.getPriceAfterTotalDiscount(totalProductsNum,totalorderPrice);
           int discountPerProduct = (totalorderPrice-finalPrice)/products.size();
           for (OrderProduct product : products){
               product.setDiscount(discountPerProduct+product.getDiscount());
               product.setFinalPrice(product.getFinalPrice()-discountPerProduct);
           }

           //create order from products in the send and send to delivery if needed
           OrderBusiness order = new OrderBusiness(orderCounter++,supplier.getName(),LocalDateTime.now(),supplier.getAddress(),
                   "SuperLi",supplier.getSupplierNum(),contactName,contactNum,products);
                   orders.add(order);
//              in case of delivery needed - connect with Delivery module
//           if(!supplier.isDelivering()){
//               sendDelivery(order);
//           }

       }
    }
    //support functionality of displaying all orders from different suppliers.
    public List<OrderBusiness> getOrders(){
        return orders;
    }
    private void sendDelivery(OrderBusiness order){
        //activate module DELIVERY
    }

    //this function gets an item, calculates which suppliers will supply the specific quantities
    // and adds an orderProduct to a specific shopping list of a certain supplier
    public void addToShoppingLists(String productName,String Manufacturer,int quantity,int supplierNum) throws Exception {

            sc.getSupplier(supplierNum).get
            //generate new OrderProduct
            int productNumber = product.getKey().getProductNum();
            int initialPrice = product.getKey().getPrice()* item.getQuantity();
            int discount = initialPrice - product.getKey().getPriceByQuantity(item.getQuantity());
            int finalPrice = initialPrice-discount;
            OrderProduct orderProduct = new OrderProduct(item.getProductName(),productNumber,item.getQuantity(),initialPrice,discount,finalPrice);
           //update the suppliers shopping list
            int supplierNum = product.getKey().getSupplierNum();
            if(!shoppingLists.containsKey(supplierNum))
                shoppingLists.put(supplierNum,new LinkedList());
            shoppingLists.get(supplierNum).add(orderProduct);
        }

}

