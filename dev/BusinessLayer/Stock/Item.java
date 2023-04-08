package BusinessLayer.Stock;

import java.time.LocalDate;
import java.util.*;

/*
    This class represents a specific item, each item holds a list of its kind
    classified by validity.
 */


public class Item implements ProductCategoryManagement {
    protected int item_id;
    protected String name;
    protected Hashtable<Integer,ItemPerOrder> items;
    protected int min_amount;
    protected String manufacturer_name;
    protected double original_price;
    protected List<Discount> discount_list;
    private OnAlertCallBack onAlertCallBack;

    /**
     * Item constructor
     * @param item_id
     * @param name
     * @param min_amount
     * @param manufacturer_name
     * @param original_price
     */
    public Item(int item_id, String name, int min_amount,
                String manufacturer_name, double original_price) {
        this.item_id = item_id;
        this.name = name;
        this.min_amount = min_amount;
        this.manufacturer_name = manufacturer_name;
        this.original_price = original_price;

        items = new Hashtable<>();
        discount_list = new LinkedList<>();

    }

    /**
     * This function return the id of the current item.
     * @return
     */
    public int getItem_id() {
        return item_id;
    }

    /**
     * This function return the name of the current item.
     * @return
     */
    public String get_name() {
        return name;
    }

    /**
     * Overrides a function only for abstraction , doesn't have an implement.
     * @param index
     * @param item
     */
    @Override
    public void add_item(String index, Item item) {
        //do nothing
    }
    /**
     * This function return the minimum amount of the current item.
     * @return
     */
    public int getMin_amount() {
        return min_amount;
    }

    /**
     * This function sets minimum amount of the current item , and returns a string with the details
     * about what is the new amount and an alert if the amount is higher than the current amount.
     * @param min_amount
     * @return
     */
    public String setMin_amount(int min_amount) {
        String returnString = "";
        if (current_amount() < min_amount)
            returnString = alert() + "\n";
        this.min_amount = min_amount;
        returnString += name+" new minimal amount:"+min_amount;
        return returnString;
    }

    /**
     * This function returns a report of the inventory about the specific product.
     * @param index
     * @return
     */
    @Override
    public String produceInventoryReport(String index) {
         return String.format("\tid:%d, product:%s, manufacturer:%s, amount in store:%d, amount in warehouse:%d",item_id,name,manufacturer_name,amount_store(),amount_warehouse());
    }


    /**
     * This function add a discount to the list of the discounts in this item.
     * @param index - has no use , only for abstraction.
     * @param discount
     */
    @Override
    public void setDiscount(String index , Discount discount) {
        discount_list.add(discount);
    }

    /**
     * This function returns the details about this specific item.
     * @param index
     * @return
     * @throws Exception
     */
    @Override
    public String show_data(String index) throws Exception {
        return String.format("--product:%s , manufacturer:%s , price:%.2f --",name,manufacturer_name, get_price());
    }

    /**
     * This is a function that only for abstraction , has no implement.
     * @param add_product
     */
    @Override
    public void add_product(ProductCategoryManagement add_product) {
        //do nothing.
    }

    /**
     * This function awakes the callback when the amount of this current item is lower than
     * the minimum amount due to a change that happened.
     * @return
     */
    private String alert(){
        onAlertCallBack.on_alert();
        return  String.format("\u001B[31m%s has reached its minimal amount\u001B[0m", name);
    }

    /**
     * This function returns the current amount both in warehouse and in store.
     * @return
     */
    public int current_amount() {
        return amount_store()+amount_warehouse();
    }
    /**
        This function returns the calculated price of an item,
        after consider all discounts and their periods.
        The maximum discount that is valid for now will be chosen
     */
    public double get_price(){
        double price = original_price;
        if(!discount_list.isEmpty())
            price =  (original_price*(100-(discount_list.stream().filter(Discount::isDue).mapToDouble(Discount::getPercentageAmount).max().orElse(1))))/100;
        return price;
    }

    /**
     * This function subtract the amount of this item in stock.
     * If the amount in stock is smaller the amount needed, the function throws exception.
     * The function returns a string, if it is not null,
     * its an alert message that need to be presented.
     * @param orderId
     * @param amount
     */
    public String reduce(int orderId,int amount) throws Exception {
        if (!items.containsKey(orderId))
            throw new Exception(String.format("No such order with order id:%d",orderId));
        if(items.get(orderId).amount()<amount)
            throw new IllegalArgumentException("not enough items in inventory");
        items.get(orderId).reduce(amount);
        if (current_amount()<=min_amount)
            return alert();
        return "The operation was carried out successfully";
    }

    public void set_on_alert_callback(OnAlertCallBack c) {
        onAlertCallBack = c;
    }

    /**
     * This function receives new order
     * @param orderId
     * @param amount_warehouse
     * @param amount_store
     * @param cost_price
     * @param location
     * @param validity
     */
    public void recive_order(int orderId,int amount_warehouse,int amount_store,double cost_price,String location, LocalDate validity){
        items.put(orderId,new ItemPerOrder(orderId,amount_warehouse,amount_store,cost_price,location, validity));
    }

    /**
     * This function return the amount of this specific item at the warehouse.
     * @return
     */
    public int amount_warehouse(){
        int amount = 0;
        for (Map.Entry<Integer, ItemPerOrder> entry : items.entrySet()) {
            ItemPerOrder itemPerOrder = entry.getValue();
            amount += itemPerOrder.getAmount_warehouse();
        }
        return amount;
    }
    /**
     * This function return the amount of this specific item at the store.
     * @return
     */
    public int amount_store(){
        int amount = 0;
        for (Map.Entry<Integer, ItemPerOrder> entry : items.entrySet()) {
            ItemPerOrder itemPerOrder = entry.getValue();
            amount += itemPerOrder.getAmount_store();
        }
        return amount;
    }
}
