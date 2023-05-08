package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

import java.time.LocalDate;

public class DiscountDTO {
    private int id;
    private String start_date;
    private String end_date;
    private double percentage;
    private boolean is_category;

    public DiscountDTO(String start_date, String end_date, double percentage , int id, boolean is_category) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
        this.id = id;
        this.is_category = is_category;
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

    public boolean get_is_category() {
        return is_category;
    }

    public double getPercentage() {
        return percentage;
    }

}
