package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemPerOrderDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.ItemDalController;

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
    private ItemPerOrderDTO item_per_order_dto;
    private LocalDate arrived_date;

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
    public ItemPerOrder(int orderId, int amount_warehouse, int amount_store, double cost_price, String location, LocalDate validity,LocalDate arrived_date) {
        this.amount_warehouse = amount_warehouse;
        this.amount_store = amount_store;
        this.cost_price = cost_price;
        this.location = location;
        this.validity = validity;
        this.orderId = orderId;
        this.item_per_order_dto = new ItemPerOrderDTO(amount_warehouse , amount_store,cost_price,location,validity.toString()
                            ,orderId,0,arrived_date.toString());
        this.arrived_date = arrived_date;
    }

    ItemPerOrder(ItemPerOrderDTO itemPerOrderDTO){
        this.amount_warehouse = itemPerOrderDTO.getAmountWarehouse();
        this.amount_store = itemPerOrderDTO.getAmountStore();
        this.cost_price = itemPerOrderDTO.getCostPrice();
        this.location = itemPerOrderDTO.getLocation();
        this.validity = Util.stringToDate(itemPerOrderDTO.getValidity());
        this.arrived_date = Util.stringToDate(itemPerOrderDTO.getArrivedDate());
        this.orderId = itemPerOrderDTO.getOrderId();
        this.item_per_order_dto = itemPerOrderDTO;
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

    public ItemPerOrderDTO getDto() {
        return this.item_per_order_dto;
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDate getArrived_date() {
        return arrived_date;
    }
}