package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

public class DiscountCounterDTO extends DTO {
    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    private String name;
    private int count;
    private String date;
    public DiscountCounterDTO(int count) {
        super("inventory_constants");
        this.count = count;
        name = "discountCounter";
        date = "empty";
    }
    public  DiscountCounterDTO(){
        super("inventory_constants");
    }
    public int getCount() {
        return count;
    }
}
