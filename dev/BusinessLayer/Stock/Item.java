package BusinessLayer.Stock;

import java.util.List;

public class Item {
    protected int item_id;
    protected String name;
    protected List<ItemPerOrder> items;
    protected int min_amount;
    protected String manufacturer_name;
    protected double original_price;
    protected List<Discount> discount_list;

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

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemPerOrder> getItems() {
        return items;
    }

    public void setItems(List<ItemPerOrder> items) {
        this.items = items;
    }

    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public List<Discount> getDiscount_list() {
        return discount_list;
    }

    public void setDiscount_list(List<Discount> discount_list) {
        this.discount_list = discount_list;
    }
}
