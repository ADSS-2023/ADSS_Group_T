package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class ItemToOrderDTO extends DTO {
    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    private String productName;
    private String manufacturer;
    private int quantity;
    private String expiryDate;
    private double costPrice;
    private int orderId;
    public ItemToOrderDTO(String tableName,String productName,String manufacturer,int quantity,String expiryDate,double costPrice,int orderId) {
        super(tableName);
        this.productName =productName;
        this.manufacturer =manufacturer;
        this.quantity = quantity;
        this.expiryDate=expiryDate;
        this.costPrice =costPrice;
        this.orderId = orderId;
    }
}
