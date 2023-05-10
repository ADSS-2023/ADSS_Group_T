package DataLayer.Inventory_Supplier_Dal.DTO;

import DataLayer.Util.DTO;

public class CurrDateDTO extends DTO {
    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param date
     */
    public CurrDateDTO(String date) {
        super("inventory_constants");
        this.date = date;
        name = "currDate";
        count = -1;
    }
    public CurrDateDTO(){
        super("inventory_constants");
    }
    private String name;
    private int count;
    private String date;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
