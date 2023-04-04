package BusinessLayer.Stock;

import java.time.LocalDate;
/*
    This class represents a discount. Each discount has a start and an end dates,
    and the discount in percentage
 */
public class Discount {
    private LocalDate start_date;
    private LocalDate end_date;
    private int percentage;

    public Discount(LocalDate start_date, LocalDate end_date, int Percentage) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
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

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getPercentageAmount() {
        return percentage;
    }

    public void setPercentageAmount(int Percentage) {
        this.percentage = percentage;
    }
}
