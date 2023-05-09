package BusinessLayer.Stock;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;

public class ItemPerOrder {
    private int amount_warehouse;
    private int amount_store;
    private double cost_price;
    private String location;
    private LocalDate validity;
    private int orderId;

    /**
     * This class represnts an item by order (validity)
     * the constructor args are:
     * @param amount_warehouse
     * @param amount_store
     * @param cost_price
     * @param location
     * @param validity
     * @param orderId
     */
    public ItemPerOrder(int orderId,int amount_warehouse, int amount_store, double cost_price, String location, LocalDate validity) {
        this.amount_warehouse = amount_warehouse;
        this.amount_store = amount_store;
        this.cost_price = cost_price;
        this.location = location;
        this.validity = validity;
        this.orderId = orderId;
    }

    /**
     * This function return the amount of this item from a specific order at the warehouse.
     * @return
     */
    public int getAmount_warehouse() {
        return amount_warehouse;
    }
    /**
     * This function return the amount of this item from a specific order at the store.
     * @return
     */
    public int getAmount_store() {
        return amount_store;
    }
    /**
     * This function returns the total amount of this item from this order
     * @ int
     */
    public int amount() {return amount_store+amount_warehouse;}

    /**
     * This function reduces the amount of this current item
     * @param amount
     */
    public void reduce(int amount) {
        if (amount_store<amount){
            amount = amount - amount_store;
            amount_store = 0;
            amount_warehouse = amount_warehouse - amount;
        }
        else {
            amount_store = amount_store-amount;
        }
    }

    public void setAmount_warehouse(int amount_warehouse) {
        this.amount_warehouse = amount_warehouse;
    }

    public void setAmount_store(int amount_store) {
        this.amount_store = amount_store;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    public void move_to_store(int amount){
        amount_store+=amount;
        amount_warehouse-=amount;
    }
}