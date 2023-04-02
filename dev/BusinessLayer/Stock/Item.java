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

    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    @Override
    public List<String> produceInventoryReport() {
         return Arrays.asList(String.format("product:%s manufacturer:%s amount:%d",name,manufacturer_name,current_amount()));
    }

    @Override
    public void setDiscount(Discount discount) {
        discount_list.add(discount);
    }
    private String alert(){
        onAlertCallBack.on_alert();
        return  String.format("\u001B[31m%s\u001B[0m","%s has reached its minimal amount",name);
    }

    /**
     * This function returns the current amount both in warehouse and in store.
     * @return
     */
    public int current_amount() {
        int amount = 0;
        for (Map.Entry<Integer, ItemPerOrder> entry : items.entrySet()) {
            ItemPerOrder itemPerOrder = entry.getValue();
            amount += itemPerOrder.amount();
        }
        return amount;
    }
    /**
        This function returns the calculated price of an item,
        after consider all discounts and their periods.
        The maximum discount that is valid for now will be chosen
     */
    public double get_price(){
        double price = original_price;
        if(!discount_list.isEmpty())
            price =  (original_price*(100-(discount_list.stream().mapToDouble(Discount::getAmount).max().orElse(1))))/100;
        return price;
    }
    public void reduce(int orderId,int amount){
        if(items.get(orderId).amount()<amount)
            throw new IllegalArgumentException("not enough items in inventory");
        items.get(orderId).reduce(amount);
        if (current_amount()==min_amount)
            alert();
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
}
