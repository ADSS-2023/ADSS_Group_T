package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.OrderDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.OrderProductDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierProductDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.OrderDalController;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import ServiceLayer.Stock.ManageOrderService;


import java.sql.Connection;
import java.sql.SQLException;
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

    private Connection connection;

    private OrderDalController orderDalController;

    public OrderController(SupplierController sc , ManageOrderService mos, Connection connection, OrderDalController orderDalController) {
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
        this.connection = connection;
        this.orderDalController = orderDalController;
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
               product.setOrderProductDTO(new OrderProductDTO(orderCounter, product.getManufacturer(), product.getExpiryDate().toString(), product.getProductNumber(), product.getQuantity(), product.getInitialPrice(), product.getDiscount(), product.getFinalPrice(), product.getProductName()));
           }
           int daysToSupplied = -1;
           if(!isRegular){
               daysToSupplied = supplier.findEarliestSupplyDay();
           }

           //create order from products in the send and send to delivery if needed
           if (isRegular){//save Regular order
               int deliveryDay = supplier.findEarliestSupplyDay();
               LocalDate today = Util_Supplier_Stock.getCurrDay();
               LocalDate futureDay = today.plusDays(deliveryDay);
               //the next line produces an error
               DayOfWeek orderDay = futureDay.getDayOfWeek();
               // Calculate the difference between the orderDay and the last day of the week (Sunday)
               int diff = DayOfWeek.SUNDAY.getValue() - orderDay.getValue();
               // Get the last day of the week
               DayOfWeek lastDay = orderDay.plus(diff);
               OrderBusiness order = new OrderBusiness(orderCounter++, supplier.getName(), Util_Supplier_Stock.getCurrDay(), supplier.getAddress(),
                       "SuperLi", supplier.getSupplierNum(), contactName, contactNum, products, daysToSupplied, false, -1,lastDay.getValue());
               dayToConstantOrders.get(orderDay).add(order);
           }
           else{
               OrderBusiness order = new OrderBusiness(orderCounter++, supplier.getName(), Util_Supplier_Stock.getCurrDay(), supplier.getAddress(),
                       "SuperLi", supplier.getSupplierNum(), contactName, contactNum, products, daysToSupplied, false, daysToSupplied, -1);
               ordersNotSupplied.add(order);
           }
       }
        shoppingLists = new HashMap<>();
    }

    public void loadOrders() throws Exception {
        List<OrderDTO> ordersList = orderDalController.findAll("supplier_orders",OrderDTO.class);
        List<OrderProduct> orderProducts=null;
        int maxId = 0;
        for (OrderDTO orderDTO : ordersList ) {
            if(orderDTO.getOrderNum()>maxId)
                maxId=orderDTO.getOrderNum();
            orderProducts = loadOrderProducts(orderDTO.getOrderNum());
            OrderBusiness order = new OrderBusiness(orderDTO, orderProducts,sc.getSupplier(orderDTO.getSupplierNum()).getName());
            if(orderDTO.isOrderSupplied()) // if the order already supplied
                orders.add(order);
            else if(orderDTO.getDaysToDeliver()!=-1) //if the order is special order
                ordersNotSupplied.add(order);
                else{ // if the orderis a regular order
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(orderDTO.getConstantDay().toUpperCase());
                    dayToConstantOrders.get(dayOfWeek).add(order);
                }
            }
        orderCounter= ++maxId;
        }
    }
    public List<OrderProduct> loadOrderProducts(int orderId) throws SQLException {
        List<OrderProduct> orderProducts = new LinkedList<>();
        List<OrderProductDTO> orderProductDTOS= orderDalController.findAllOfCondition(
                "supplier_order_products","orderId", orderId, OrderProductDTO.class);
        for (OrderProductDTO orderProductDTO : orderProductDTOS ) {
            orderProducts.add(new OrderProduct(orderProductDTO));
        }
        return orderProducts;
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
            OrderProduct orderProduct = new OrderProduct(product.getName(),productNumber,quantity,initialPrice,discount,finalPrice, product.getManufacturer(),product.getExpiryDate(), new OrderProductDTO(orderCounter, product.getManufacturer(), product.getExpiryDate().toString(), productNumber, quantity, initialPrice, discount, finalPrice, product.getName()));
           //update the suppliers shopping list
            if(!shoppingLists.containsKey(supplierNum))
                shoppingLists.put(supplierNum,new LinkedList());
            shoppingLists.get(supplierNum).add(orderProduct);
        }
    //change
    public void executeTodayOrders() throws SQLException {
        List<ItemToOrder> items = new ArrayList<>();
        List<OrderBusiness> ordersForToday = dayToConstantOrders.get(Util_Supplier_Stock.getCurrDay().getDayOfWeek());
        for(OrderBusiness order:ordersForToday){
            OrderDTO oldOrderDTO = order.getOrderDTO();
            OrderDTO newOrderDTO = new OrderDTO(oldOrderDTO.getOrderNum(), oldOrderDTO.getSupplierNum(), oldOrderDTO.getContactName(), oldOrderDTO.getContactNumber(), oldOrderDTO.getOrderDate(), oldOrderDTO.getSupplierAddress(), oldOrderDTO.getDestinationAddress(), true, oldOrderDTO.getDaysToDeliver(), oldOrderDTO.getConstantDay());
            orderDalController.update(oldOrderDTO, newOrderDTO);
            order.setOrderDTO(newOrderDTO);
            for(OrderProduct product:order.getProducts()) {
                items.add(new ItemToOrder(product.getProductName(), product.getManufacturer(), product.getQuantity(),
                        product.getExpiryDate(), order.getOrderNum(), product.getFinalPrice()));
            }
            if(!order.getProducts().isEmpty()) {
                OrderBusiness clonedOrder = order.clone(orderCounter++);
                orders.add(clonedOrder);
            }
        }
        List<OrderBusiness> ordersToDelete =  new LinkedList<>();
        for(OrderBusiness order:ordersNotSupplied){
            if(order.getDaysToSupplied() == 0) {
                for (OrderProduct product : order.getProducts()) {
                    items.add(new ItemToOrder(product.getProductName(), product.getManufacturer(), product.getQuantity(),
                            product.getExpiryDate(), order.getOrderNum(), product.getFinalPrice() / product.getQuantity()));
                }
                if(!order.getProducts().isEmpty()) {
                    orders.add(order);
                    OrderDTO oldOrderDTO = order.getOrderDTO();
                    OrderDTO newOrderDTO = new OrderDTO(oldOrderDTO.getOrderNum(), oldOrderDTO.getSupplierNum(), oldOrderDTO.getContactName(), oldOrderDTO.getContactNumber(), oldOrderDTO.getOrderDate(), oldOrderDTO.getSupplierAddress(), oldOrderDTO.getDestinationAddress(), true, -1, -1);
                    orderDalController.update(oldOrderDTO, newOrderDTO);
                    order.setOrderDTO(newOrderDTO);
                }
                else {
                    OrderDTO oldOrderDTO = order.getOrderDTO();
                    orderDalController.delete(oldOrderDTO);
                }
                ordersToDelete.add(order);
            }
            else {
                order.setDaysToSupplied(order.getDaysToSupplied() - 1);
                OrderDTO oldOrderDTO = order.getOrderDTO();
                OrderDTO newOrderDTO = new OrderDTO(oldOrderDTO.getOrderNum(), oldOrderDTO.getSupplierNum(), oldOrderDTO.getContactName(), oldOrderDTO.getContactNumber(), oldOrderDTO.getOrderDate(), oldOrderDTO.getSupplierAddress(), oldOrderDTO.getDestinationAddress(), true, oldOrderDTO.getDaysToDeliver()-1, -1);
                orderDalController.update(oldOrderDTO, newOrderDTO);
                order.setOrderDTO(newOrderDTO);
            }
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
                                    //supplier does not have enough of the product's quantity
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
                        for (OrderProduct deletedProduct : toRemove) {
                            order.getProducts().remove(deletedProduct);
                            orderDalController.delete(deletedProduct.getOrderProductDTO());
                        }
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
                            OrderProductDTO oldOrderProductDTO = product.getOrderProductDTO();
                            OrderProductDTO newOrderProductDTO = new OrderProductDTO(oldOrderProductDTO.getOrderID(), oldOrderProductDTO.getManufacturer(), oldOrderProductDTO.getExpiryDate(), oldOrderProductDTO.getProductNumber(), product.getQuantity() + quantityToChange, oldOrderProductDTO.getInitialPrice(), oldOrderProductDTO.getDiscount(), oldOrderProductDTO.getFinalPrice(), oldOrderProductDTO.getProductName());
                            orderDalController.update(oldOrderProductDTO, newOrderProductDTO);
                            product.setOrderProductDTO(newOrderProductDTO);
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
                            OrderProductDTO oldOrderProductDTO = product.getOrderProductDTO();
                            OrderProductDTO newOrderProductDTO = new OrderProductDTO(oldOrderProductDTO.getOrderID(), oldOrderProductDTO.getManufacturer(), oldOrderProductDTO.getExpiryDate(), oldOrderProductDTO.getProductNumber(), product.getQuantity() - Math.abs(quantityToChange), oldOrderProductDTO.getInitialPrice(), oldOrderProductDTO.getDiscount(), oldOrderProductDTO.getFinalPrice(), oldOrderProductDTO.getProductName());
                            orderDalController.update(oldOrderProductDTO, newOrderProductDTO);
                            product.setOrderProductDTO(newOrderProductDTO);
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
              for (OrderProduct deletedProduct : toRemove) {
                  orderDalController.delete(deletedProduct.getOrderProductDTO());
                  order.getProducts().remove(deletedProduct);
              }
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
        OrderProductDTO oldOrderProductDTO = product.getOrderProductDTO();
        OrderProductDTO newOrderProductDTO = new OrderProductDTO(oldOrderProductDTO.getOrderID(), oldOrderProductDTO.getManufacturer(), oldOrderProductDTO.getExpiryDate(), oldOrderProductDTO.getProductNumber(), product.getQuantity(), product.getInitialPrice(), product.getDiscount(), product.getFinalPrice(), product.getProductName());
        orderDalController.update(oldOrderProductDTO, newOrderProductDTO);
        product.setOrderProductDTO(newOrderProductDTO);
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
            OrderProductDTO oldOrderProductDTO = product.getOrderProductDTO();
            OrderProductDTO newOrderProductDTO = new OrderProductDTO(oldOrderProductDTO.getOrderID(), oldOrderProductDTO.getManufacturer(), oldOrderProductDTO.getExpiryDate(), oldOrderProductDTO.getProductNumber(), product.getQuantity(), product.getInitialPrice(), product.getDiscount(), product.getFinalPrice(), product.getProductName());
            orderDalController.update(oldOrderProductDTO, newOrderProductDTO);
            product.setOrderProductDTO(newOrderProductDTO);
        }
    }


    //TODO: add a function that executes each day regular orders - by adding them to orders list

        //TODO:: add a function that get all orders of a day of the week

        //TODO:: add a function that can edit an item in a regular order
}

