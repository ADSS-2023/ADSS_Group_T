package BusinessLayer.Stock;

import java.time.LocalDate;
/*
    This class represents a discount. Each discount has a start and an end dates,
    and the discount in percentage
 */
public class Discount {
    private LocalDate start_date;
    private LocalDate end_date;
    private double percentage;

    public Discount(LocalDate start_date, LocalDate end_date, double percentage) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
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
}
