package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import ServiceLayer.Supplier.ItemToOrder;
import Util.WeekDays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class OrderController {
    private List<OrderBusiness> orders;
    private SupplierController sc;
    private int orderCounter;
    private HashMap<WeekDays,List<OrderBusiness>> dayToConstantOrders;

    private HashMap<Integer,List<OrderProduct>> shoppingLists; // supplierNumber to list of products


    public OrderController(SupplierController sc) {
        orders = new LinkedList<>();
        this.sc = sc;
        shoppingLists = new HashMap<>();
        dayToConstantOrders = new HashMap<>();
        orderCounter=0;

    }

    /**
     * this function gets a list of items and priorities, find appropriate suppliers and execute orders
     * @param items
     * @param isRegular
     * @param isUrgent
     * @throws Exception
     */
    public void createOrder(List<ItemToOrder> items, boolean isRegular, boolean isUrgent) throws Exception {

        if (isUrgent) {
            for (ItemToOrder item : items) {
                HashMap<SupplierProductBusiness, Integer> productsToOrder = sc.findUrgentSuppliers(item);
                for (Map.Entry<SupplierProductBusiness, Integer> product : productsToOrder.entrySet())
                    addToShoppingList(product.getKey().getProductNum(), product.getKey().getSupplierNum(), product.getValue());
            }
        }
        else {
            SupplierBusiness chosenSupplier = sc.findSingleSupplier(items, isRegular);
            if (chosenSupplier == null) {
                for (ItemToOrder item : items) {
                    HashMap<SupplierProductBusiness, Integer> productsToOrder = sc.findSuppliersProduct(item, isRegular);
                    for (Map.Entry<SupplierProductBusiness, Integer> product : productsToOrder.entrySet())
                        addToShoppingList(product.getKey().getProductNum(), product.getKey().getSupplierNum(), product.getValue());
                }
            } else {
                for (ItemToOrder item : items) {
                    SupplierProductBusiness supplierProduct = chosenSupplier.getSupplierProduct(item.getProductName(), item.getManufacturer());
                    addToShoppingList(supplierProduct.getProductNum(), supplierProduct.getSupplierNum(), item.getQuantity());
                }
            }
        }
            createOrders(isRegular);//execute orders and add order to regular orders map if needed
    }


    /**
     *  goes over every shopping list per supplier consisted of OrderProducts and
     *  performs each order After total discounts, and adds a regular order to the map if needed.
     * @param isRegular
     * @throws Exception
     */
    public void createOrders(boolean isRegular) throws Exception {
       for(Map.Entry<Integer, List<OrderProduct>> shoppingList : shoppingLists.entrySet()){
           List<OrderProduct> products = shoppingList.getValue();
           SupplierBusiness supplier = sc.getSupplier(shoppingList.getKey());
           Map.Entry<String,String> entry = supplier.getContacts().entrySet().iterator().next();
           String contactName = entry.getKey();
           String contactNum = entry.getValue();

           //calculate order quantities&prices
           int totalProductsNum = 0;
           int totalorderPrice = 0;
           for (OrderProduct product : products){
                totalorderPrice+=product.getFinalPrice();
                totalProductsNum+=product.getQuantity();
           }

           //calculate supplier general discounts and final prices
           float finalTotalPrice = supplier.getPriceAfterTotalDiscount(totalProductsNum,totalorderPrice);

           for (OrderProduct product : products){
               float discountPerProducts = (product.getFinalPrice()/totalorderPrice)*(totalorderPrice-finalTotalPrice);
               product.setDiscount(discountPerProducts+product.getDiscount());
               product.setFinalPrice(product.getFinalPrice()-discountPerProducts);
           }

           //create order from products in the send and send to delivery if needed
           OrderBusiness order = new OrderBusiness(orderCounter++,supplier.getName(),LocalDateTime.now(),supplier.getAddress(),
                   "SuperLi",supplier.getSupplierNum(),contactName,contactNum,products);
           orders.add(order);
           if (isRegular){//save Regular order
               int deliveryDay = supplier.findEarliestSupplyDay();
               LocalDate today = LocalDate.now();
               LocalDate futureDay = today.plusDays(deliveryDay);
               WeekDays orderDay = WeekDays.valueOf(futureDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
               if(!dayToConstantOrders.containsKey(orderDay))
                   dayToConstantOrders.put(orderDay,new LinkedList<>());
               dayToConstantOrders.get(orderDay).add(order);
           }
       }
        shoppingLists = new HashMap<>();
    }


    /**
     * support functionality of displaying all orders from different suppliers.
     * @return
     */
    public List<OrderBusiness> getOrders(){
        return orders;
    }

    private void sendDelivery(OrderBusiness order){
        //activate module DELIVERY
    }


    /**
     * this function adds an item to a suppliers shopping list
     * @param productNum
     * @param supplierNum
     * @param quantity
     * @throws Exception
     */
    public void addToShoppingList(int productNum,int supplierNum,int quantity) throws Exception {
            SupplierProductBusiness product = sc.getSupplier(supplierNum).getSupplierProduct(productNum);
            //generate new OrderProduct
            int productNumber = product.getProductNum();
            float initialPrice = product.getPrice()* quantity;
            float discount = initialPrice - product.getPriceByQuantity(quantity);
            float finalPrice = initialPrice-discount;
            OrderProduct orderProduct = new OrderProduct(product.getName(),productNumber,quantity,initialPrice,discount,finalPrice);
           //update the suppliers shopping list
            if(!shoppingLists.containsKey(supplierNum))
                shoppingLists.put(supplierNum,new LinkedList());
            shoppingLists.get(supplierNum).add(orderProduct);
        }


        //TODO: add a function that executes each day regular orders - by adding them to orders list

        //TODO:: add a function that get all orders of a day of the week

        //TODO:: add a function that can edit an item in a regular order
}

