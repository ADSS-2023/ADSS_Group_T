package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class ItemPerOrderDTO extends DTO {
    private int itemId;
    private int orderId;
    private String validity;
    private String location;
    private double costPrice;
    private int amountStore;
    private int amountWarehouse;


    public ItemPerOrderDTO(int amountWarehouse, int amountStore, double costPrice, String location, String validity, int orderId , int itemId) {
        super("inventory_item_per_order");
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
