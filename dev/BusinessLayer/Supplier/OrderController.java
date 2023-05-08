package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import ServiceLayer.Stock.ManageOrderService;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class OrderController {
    private List<OrderBusiness> orders;
    private SupplierController sc;
    private int orderCounter;
    private List<OrderBusiness> ordersNotSupplied;
    private HashMap<DayOfWeek,List<OrderBusiness>> dayToConstantOrders;
    private HashMap<Integer,List<OrderProduct>> shoppingLists; // supplierNumber to list of products
    private ManageOrderService mos;

    public OrderController(SupplierController sc ,ManageOrderService mos) {
        orders = new LinkedList<>();
        this.sc = sc;
        this.mos=mos;
        shoppingLists = new HashMap<>();
        dayToConstantOrders = new HashMap<>();
        for (DayOfWeek day: DayOfWeek.values() ) {
            dayToConstantOrders.put(day,new LinkedList<>());
        }
        orderCounter=0;
        ordersNotSupplied = new LinkedList<>();
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
           int daysToSupplied = -1;
           if(!isRegular){
               daysToSupplied = supplier.findEarliestSupplyDay();
           }

           //create order from products in the send and send to delivery if needed
           OrderBusiness order = new OrderBusiness(orderCounter++, supplier.getName(), Util_Supplier_Stock.getCurrDay(), supplier.getAddress(),
                   "SuperLi", supplier.getSupplierNum(), contactName, contactNum, products, daysToSupplied);
           if (isRegular){//save Regular order
               int deliveryDay = supplier.findEarliestSupplyDay();
               LocalDate today = Util_Supplier_Stock.getCurrDay();
               LocalDate futureDay = today.plusDays(deliveryDay);
               //the next line produces an error
               DayOfWeek orderDay = futureDay.getDayOfWeek();
               dayToConstantOrders.get(orderDay).add(order);
           }
           else{
               ordersNotSupplied.add(order);
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
            OrderProduct orderProduct = new OrderProduct(product.getName(),productNumber,quantity,initialPrice,discount,finalPrice, product.getManufacturer(),product.getExpiryDate());
           //update the suppliers shopping list
            if(!shoppingLists.containsKey(supplierNum))
                shoppingLists.put(supplierNum,new LinkedList());
            shoppingLists.get(supplierNum).add(orderProduct);
        }
    //change
    public void executeTodayOrders(){
        List<ItemToOrder> items = new ArrayList<>();
        List<OrderBusiness> ordersForToday = dayToConstantOrders.get(Util_Supplier_Stock.getCurrDay().getDayOfWeek());
        for(OrderBusiness order:ordersForToday){
            for(OrderProduct product:order.getProducts())
                items.add(new ItemToOrder(product.getProductName(), product.getManufacturer(), product.getQuantity(),
                        product.getExpiryDate(), order.getOrderNum(), product.getFinalPrice()));

            OrderBusiness clonedOrder =  order.clone();
            clonedOrder.setOrderNum(orderCounter++);
            orders.add(clonedOrder);
        }
        List<OrderBusiness> ordersToDelete =  new LinkedList<>();
        for(OrderBusiness order:ordersNotSupplied){
            if(order.getDaysToSupplied() == 0) {
                for (OrderProduct product : order.getProducts())
                    items.add(new ItemToOrder(product.getProductName(), product.getManufacturer(), product.getQuantity(),
                            product.getExpiryDate(), order.getOrderNum(), product.getFinalPrice()/ product.getQuantity()));
                orders.add(order);
                ordersToDelete.add(order);
            }
            else
                order.setDaysToSupplied(order.getDaysToSupplied() - 1);
            }
        for(OrderBusiness order:ordersToDelete)
            ordersNotSupplied.remove(order);
        if(!items.isEmpty())
            mos.receiveOrders(items);
    }

    /**
     *
     * @param day requested day to get its items coming as a special order
     * @return list of the item comes as a special order estimated to be delivered at the day comes as an input
     * @throws Exception
     */
    public List<ItemToOrder> getSpecialOrder(DayOfWeek day) throws Exception {
        List<ItemToOrder> itemsList = new LinkedList<>();

        //find the exact number of the days following the current day
        LocalDate today = Util_Supplier_Stock.getCurrDay();
        int todayValue = today.getDayOfWeek().getValue();
        int daysToAdd = 7;
        int dayValue = day.getValue();
        int daysToNext = (dayValue >= todayValue) ? dayValue - todayValue : 7 - (todayValue - dayValue);
        if (daysToNext < daysToAdd) {
            daysToAdd = daysToNext;
        }
        //adding to the items list all products of the order scheduled for the input day string
        for (OrderBusiness order:ordersNotSupplied) {
            if(order.getDaysToSupplied()==daysToAdd) {
                for (OrderProduct product:order.getProducts()) {
                    itemsList.add(new ItemToOrder(product.getProductName(),product.getManufacturer(),
                            product.getQuantity(),product.getExpiryDate(),order.getOrderNum(),product.getFinalPrice()));
                }
            }
        }
       return itemsList;
    }

    /**
     *
     * @param day requested day to get its items coming as a special order
     * @return list of items comes as a weekly basis estimated to be delivered at the day comes as an input
     * @throws Exception
     */
    public List<ItemToOrder> getRegularOrder(DayOfWeek day) throws Exception {
        List<ItemToOrder> itemsList = new LinkedList<>();
        if(!dayToConstantOrders.containsKey(day) || dayToConstantOrders.get(day).isEmpty())
            return itemsList;
        else{
            for (OrderBusiness order:dayToConstantOrders.get(day)) {
                for (OrderProduct product: order.getProducts()) {
                    itemsList.add(new ItemToOrder(product.getProductName(), product.getManufacturer(), product.getQuantity()
                            , product.getExpiryDate(),order.getOrderNum(),product.getFinalPrice()));
                }
            }
        }
        return itemsList;
    }


    /**
     * once a supplier edits an item, each regular order contains this item needs to be updated
     * @param productName
     * @param manufacturer
     * @param supplierNum
     * @param days
     * @throws Exception
     */
    public void editRegularItem(String productName, String manufacturer, int supplierNum, List<DayOfWeek> days) throws Exception{
        //the supplier made changes to a product, therefore we need to go over all of its regular orders and change accordingly
        for (DayOfWeek day:days) { //over all delivery days of the supplier
            if(dayToConstantOrders.containsKey(day)) {
                for (OrderBusiness order : dayToConstantOrders.get(day)) {
                    if (order.getSupplierNum() == supplierNum) {
                        for (OrderProduct product : order.getProducts()) {
                            if (product.getProductName().equals(productName) && product.getManufacturer().equals(manufacturer)) {
                                SupplierProductBusiness spProduct = sc.getSupplier(supplierNum).getSupplierProduct(productName, manufacturer);
                                if (product.getQuantity() > spProduct.getMaxAmount())
                                    removeRegularItem(productName, manufacturer, supplierNum, days);
                                else
                                    updateRegularItem(product, productName, manufacturer, supplierNum);
                            }
                        }
                    }
                    updateRegularOrder(order);
                }
            }
        }

    }


        //remove the order and make it again from scratch


    /**
     * once a supplier deletes an item, each regular order contains this item needs to be updated accordingly
     * @param productName
     * @param manufacturer
     * @param supplierNum
     * @param days
     * @throws Exception
     */
    public void removeRegularItem(String productName, String manufacturer, int supplierNum, List<DayOfWeek> days) throws Exception {
        List <OrderProduct> toRemove = new LinkedList<>();
        for (DayOfWeek day : days) { //over all delivery days of the supplier
            if (dayToConstantOrders.containsKey(day)) {
                for (OrderBusiness order : dayToConstantOrders.get(day)) {
                    if (order.getSupplierNum() == supplierNum) {
                        for (OrderProduct product : order.getProducts()) {
                            if (product.getProductName().equals(productName) && product.getManufacturer().equals(manufacturer))
                                toRemove.add(product);
                                //remove the order of the item and order it from scratch
                            }
                        for (OrderProduct deletedProduct : toRemove)
                            order.getProducts().remove(deletedProduct);
                        if (toRemove.size()>0) {
                            toRemove = new LinkedList<>();
                            updateRegularOrder(order);
                        }
                    }
                    }
                }
            }
        }

    /**
     * a user from inventory tries to modify an item in a regular order which needs to be updated
     * @param item
     * @param day
     * @throws Exception
     */
    // go over all orders of the day and find out how much to add to one of the suppliers
    //loop to find out what is the number of the requested product
    //go over all the order products and  modify to the max quantity can be supplied
    //update order product and all order
    public void editRegularItem(ItemToOrder item, DayOfWeek day) throws Exception {
        int newQuantity = item.getQuantity();
        if (newQuantity == 0) {
            removeRegularItem(item, day);
            return;
        }
        int oldQuantity = 0;
        boolean found = false;
        if (!dayToConstantOrders.containsKey(day))
            throw new Exception("item has not found");
        //find out quantity to add or reduce
        for (OrderBusiness order : dayToConstantOrders.get(day)) {
            for (OrderProduct product : order.getProducts()) {
                if (product.getProductName().equals(item.getProductName()) &&
                        product.getManufacturer().equals(item.getManufacturer())) {
                    oldQuantity += product.getQuantity();
                    found=true;
                }
            }
        }
        if(!found)
            throw new Exception("item has not found");

        int quantityToChange = newQuantity - oldQuantity;
        if(quantityToChange==0)//if no need to change a thing
            return;

        //add or reduce a specific amount from an item
        for (OrderBusiness order : dayToConstantOrders.get(day)) {
            for (OrderProduct product : order.getProducts()) {
                if (product.getProductName().equals(item.getProductName()) &&
                        product.getManufacturer().equals(item.getManufacturer())) {
                    SupplierProductBusiness spProduct = sc.getSupplier(order.getSupplierNum()).getProduct(product.getProductNumber());
                    if (quantityToChange > 0) { // if there is a need to add - if an existing supplier has enough to add
                        if (spProduct.hasEnoughQuantity(product.getQuantity() + quantityToChange)) {
                            product.setQuantity(product.getQuantity() + quantityToChange);
                            updateRegularItem(product, product.getProductName(), product.getManufacturer(), spProduct.getSupplierNum());
                            updateRegularOrder(order);
                            quantityToChange=0;
                            return;
                        }
                    }
                    else if(quantityToChange<0){//if there is a need to reduce
                        // if the current product has the whole amount to be reduced
                        if(product.getQuantity()>=Math.abs(quantityToChange)) {
                            product.setQuantity(product.getQuantity() - Math.abs(quantityToChange));
                            updateRegularItem(product, product.getProductName(), product.getManufacturer(), spProduct.getSupplierNum());
                            updateRegularOrder(order);
                            quantityToChange=0;
                            return;
                        }
                        else{//else - remove the whole amount of the OrderProduct and continue reducing
                            quantityToChange -= Math.abs(product.getQuantity());
                            order.getProducts().remove(product);
                            updateRegularOrder(order);
                        }
                    }
                }
                if(quantityToChange!=0)
                    throw new Exception("no such item has been found");
            }
        }
    }
    /*public void editRegularItem(ItemToOrder item, DayOfWeek day) throws Exception{
        int newQuantity = item.getQuantity();
        if(newQuantity==0){
            removeRegularItem(item,day);
            return;
        }
        if(!dayToConstantOrders.containsKey(day))
            throw new Exception("item has not found");
        List<SupplierProductBusiness> productsList = new LinkedList<>();
        for (OrderBusiness order: dayToConstantOrders.get(day)) {
            for (OrderProduct product : order.getProducts()) {
                if (product.getProductName().equals(item.getProductName()) &&
                        product.getManufacturer().equals(item.getManufacturer())){

                    SupplierProductBusiness spProduct = sc.getSupplier(order.getSupplierNum()).getProduct(product.getProductNumber());
                    if (!spProduct.hasEnoughQuantity(newQuantity))
                        throw new Exception("supplier has not enough quantity");
                    else {
                        product.setQuantity(newQuantity);
                        updateRegularItem(product, product.getProductName(), product.getManufacturer(), spProduct.getSupplierNum());
                        updateRegularOrder(order);
                        return;
                    }

                }

            }
        }

        throw new Exception("no such item has been found");

    }*/


    /**
     * a user from inventory tries to delete an item in a regular order which needs to be updated
     * @param item
     * @param day
     * @throws Exception
     */
    public void removeRegularItem(ItemToOrder item, DayOfWeek day) throws Exception {
        boolean found=false;
        List<OrderProduct> toRemove = new LinkedList<>();
        if (!dayToConstantOrders.containsKey(day))
            throw new Exception("item has not found");
        for (OrderBusiness order : dayToConstantOrders.get(day)) {
            for (OrderProduct product : order.getProducts()) {
                if (product.getProductName().equals(item.getProductName()) &&
                        product.getManufacturer().equals(item.getManufacturer())){
                    toRemove.add(product);
                }
            }
              for (OrderProduct deletedProduct : toRemove)
                  order.getProducts().remove(deletedProduct);
            if (toRemove.size()>0) {
                toRemove = new LinkedList<>();
                updateRegularOrder(order);
            }
          }

        throw new Exception("no such item has been found");
    }

    /**
     * gets a modified product quantity and updates its cost prices and discounts
     * @param product
     * @param productName
     * @param manufacturer
     * @param supplierNum
     * @throws Exception
     */
    public void updateRegularItem(OrderProduct product, String productName, String manufacturer, int supplierNum) throws Exception {
        SupplierProductBusiness spProduct = sc.getSupplier(supplierNum).getSupplierProduct(productName,manufacturer);
        product.setInitialPrice(spProduct.getPrice()* product.getQuantity());
        product.setDiscount(product.getInitialPrice() - spProduct.getPriceByQuantity(product.getQuantity()));
        product.setFinalPrice(product.getInitialPrice()- product.getDiscount());
    }

    /**
     * this function gets a modified orders and calculates the final supplier discounts and prices
     * @param order
     * @throws Exception
     */
    public void updateRegularOrder(OrderBusiness order) throws Exception {
        int totalProductsNum = 0;
        int totalorderPrice = 0;
        for (OrderProduct product : order.getProducts()){
            totalorderPrice+=product.getFinalPrice();
            totalProductsNum+=product.getQuantity();
        }

        //calculate supplier general discounts and final prices
        SupplierBusiness supplier = sc.getSupplier(order.getSupplierNum());
        float finalTotalPrice = supplier.getPriceAfterTotalDiscount(totalProductsNum,totalorderPrice);

        for (OrderProduct product : order.getProducts()){
            float discountPerProducts = (product.getFinalPrice()/totalorderPrice)*(totalorderPrice-finalTotalPrice);
            product.setDiscount(discountPerProducts+product.getDiscount());
            product.setFinalPrice(product.getFinalPrice()-discountPerProducts);
        }
    }


    //TODO: add a function that executes each day regular orders - by adding them to orders list

        //TODO:: add a function that get all orders of a day of the week

        //TODO:: add a function that can edit an item in a regular order
}

