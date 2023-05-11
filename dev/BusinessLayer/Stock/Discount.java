package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
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

    public Discount(DiscountDTO dto){
        this.start_date = Util.stringToDate(dto.getStartDate());
        this.end_date = Util.stringToDate(dto.getEndDate());
        this.percentage = dto.getPercentage();
    }
    /**
     * This is a boolean function that let know if this specific discount is relevant or not.
     * @return
     */
    public boolean isDue() {
        LocalDate today = Util_Supplier_Stock.getCurrDay();
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

    public int getIndex() {
        return index;
    }
}
