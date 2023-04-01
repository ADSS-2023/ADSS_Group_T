package BusinessLayer.Stock;

import java.time.LocalDate;
import java.util.List;
/*
    This class represents a specific item, each item holds a list of its kind
    classified by validity.
 */
public class Item implements ProductCategoryManagement {
    protected int item_id;
    protected String name;
    protected List<ItemPerOrder> items;
    protected int min_amount;
    protected String manufacturer_name;
    protected double original_price;
    protected List<Discount> discount_list;
    private OnAlertCallBack onAlertCallBack;

    public Item(int item_id, String name, List<ItemPerOrder> items, int min_amount,
                String manufacturer_name, double original_price, List<Discount> discount_list) {
        this.item_id = item_id;
        this.name = name;
        this.items = items;
        this.min_amount = min_amount;
        this.manufacturer_name = manufacturer_name;
        this.original_price = original_price;
        this.discount_list = discount_list;
    }


    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    @Override
    public String produceInventoryReport() {
        return String.format("product:%s manufacturer:%s amount:%d",name,manufacturer_name,current_amount());
    }

    @Override
    public void setDiscount(Discount discount) {
        discount_list.add(discount);
    }
    private String alert(){
        onAlertCallBack.on_alert();
        return  String.format("\u001B[31m%s\u001B[0m","%s has reached its minimal amount",name);
    }

    public int current_amount() {
        return items.stream()
                .mapToInt(ItemPerOrder::amount)
                .reduce(0, Integer::sum);

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
    //we nee to think how we reduce a product? by validity?
    public void reduce(){
        //which product to remove?
        if (current_amount()==min_amount)
            alert();
    }
    public void set_on_alert_callback(OnAlertCallBack c) {
        onAlertCallBack = c;
    }

    /**
     * This function receives new order
     * @param amount_warehouse
     * @param amount_store
     * @param cost_price
     * @param location
     * @param validity
     */
    public void recive_order(int amount_warehouse,int amount_store,double cost_price,String location, LocalDate validity){
        items.add(new ItemPerOrder(amount_warehouse,amount_store,cost_price,location, validity));
    }
}
