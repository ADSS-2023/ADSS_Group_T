package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

public class DamagedItemDTO {
    private ItemDTO item;
    private int amount;
    private String description;

    public DamagedItemDTO(ItemDTO item, int amount, String description) {
        this.item = item;
        this.amount = amount;
        this.description = description;
    }

    public ItemDTO getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
