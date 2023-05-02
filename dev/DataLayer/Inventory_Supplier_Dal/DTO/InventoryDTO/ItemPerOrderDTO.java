package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import java.time.LocalDate;

public class ItemPerOrderDTO {
    private int amountWarehouse;
    private int amountStore;
    private double costPrice;
    private String location;
    private String validity;
    private int orderId;
    private int itemId;

    public ItemPerOrderDTO(int amountWarehouse, int amountStore, double costPrice, String location, String validity, int orderId , int itemId) {
        this.amountWarehouse = amountWarehouse;
        this.amountStore = amountStore;
        this.costPrice = costPrice;
        this.location = location;
        this.validity = validity;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmountWarehouse() {
        return amountWarehouse;
    }


    public int getAmountStore() {
        return amountStore;
    }


    public double getCostPrice() {
        return costPrice;
    }


    public String getLocation() {
        return location;
    }


    public String getValidity() {
        return validity;
    }

    public int getOrderId() {
        return orderId;
    }

}
