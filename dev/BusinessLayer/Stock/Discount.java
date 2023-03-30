package BusinessLayer.Stock;

import java.time.LocalDate;
/*
    This class represents a discount. Each discount has a start and an end dates,
    and the discount in percentage
 */
public class Discount {
    private LocalDate start_date;
    private LocalDate end_date;
    private int amount;

    public Discount(LocalDate start_date, LocalDate end_date, int amount) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount = amount;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
