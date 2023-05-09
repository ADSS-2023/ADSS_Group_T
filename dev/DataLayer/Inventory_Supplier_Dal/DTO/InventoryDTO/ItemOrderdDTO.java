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
    public ItemOrderdDTO(String tableName,int id,int quantity) {
        super(tableName);
        this.id=id;
        this.quantity=quantity;
    }
}
