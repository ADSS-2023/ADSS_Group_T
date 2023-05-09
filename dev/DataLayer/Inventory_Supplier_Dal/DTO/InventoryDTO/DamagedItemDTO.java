package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

public class DamagedItemDTO {
    private int item_id;
    private int amount;
    private String description;

    public DamagedItemDTO(int item_id, int amount, String description) {
        this.item_id = item_id;
        this.amount = amount;
        this.description = description;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
