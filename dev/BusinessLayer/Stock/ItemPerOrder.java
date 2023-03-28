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

    public ItemPerOrder(int amount_warehouse, int amount_store, double cost_price, String location, LocalDate validity) {
        this.amount_warehouse = amount_warehouse;
        this.amount_store = amount_store;
        this.cost_price = cost_price;
        this.location = location;
        this.validity = validity;
    }

    public int getAmount_warehouse() {
        return amount_warehouse;
    }

    public void setAmount_warehouse(int amount_warehouse) {
        this.amount_warehouse = amount_warehouse;
    }

    public int getAmount_store() {
        return amount_store;
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
}
