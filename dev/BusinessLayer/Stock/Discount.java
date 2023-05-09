package BusinessLayer.Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.DiscountDTO;
import DataLayer.Util.DTO;

import java.time.LocalDate;
/*
    This class represents a discount. Each discount has a start and an end dates,
    and the discount in percentage
 */
public class Discount {
    private int index;
    private LocalDate start_date;
    private LocalDate end_date;
    private double percentage;
    private DiscountDTO discount_dto;

    /**
     *
     * @param start_date
     * @param end_date
     * @param percentage
     */
    public Discount(int index ,LocalDate start_date, LocalDate end_date, double percentage, String index_product) {
        this.index = index;
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
        this.discount_dto = new DiscountDTO(index,start_date.toString(),end_date.toString(),percentage,index_product);
    }

    /**
     * This is a boolean function that let know if this specific discount is relevant or not.
     * @return
     */
    public boolean isDue() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(start_date) || today.isAfter(start_date))
                && (today.isEqual(end_date) || today.isBefore(end_date));
    }

    /**
     * This function returns the number of percentage of discount.
     * @return
     */
    public double getPercentageAmount() {
        return percentage;
    }

    public DTO getDto() {
        return discount_dto;
    }
}
