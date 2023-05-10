package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

public class ItemOrderdDTO extends DTO {
    private int id;
    private int quantity;

    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    public ItemOrderdDTO(int id,int quantity) {
        super("inventory_item_ordered");
        this.id=id;
        this.quantity=quantity;
    }
    public ItemOrderdDTO(){super("inventory_item_ordered");}

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
