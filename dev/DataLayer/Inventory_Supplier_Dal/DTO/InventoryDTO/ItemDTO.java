package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

public class ItemDTO extends DTO {
    private int itemId;
    private String name;
    private int minAmount;
    private String manufacturerName;
    private double originalPrice;


    public ItemDTO(int itemId, String name, int minAmount, String manufacturerName, double originalPrice) {
        super("inventory_item");
        this.itemId = itemId;
        this.name = name;
        this.minAmount = minAmount;
        this.manufacturerName = manufacturerName;
        this.originalPrice = originalPrice;
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

}
