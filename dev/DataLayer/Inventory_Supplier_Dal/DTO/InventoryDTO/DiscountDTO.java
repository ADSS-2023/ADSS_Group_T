package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class DiscountDTO extends DTO {
    private int id;
    private String start_date;
    private String end_date;
    private double percentage;
    private String index_product;

    public DiscountDTO(int id ,String start_date, String end_date, double percentage ,  String index_product) {
        super("inventory_discount");
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
        this.index_product = index_product;
    }
    public DiscountDTO(){
        super("inventory_discount");
    }
    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public int getId() {
        return id;
    }


    public double getPercentage() {
        return percentage;
    }

    public String getIndex_product() {
        return index_product;
    }
}
