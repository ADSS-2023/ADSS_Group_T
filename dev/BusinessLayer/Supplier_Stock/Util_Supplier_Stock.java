package BusinessLayer.Supplier_Stock;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
public class Util_Supplier_Stock {
    private static LocalDate currDay;
    public Util_Supplier_Stock(){
        currDay = LocalDate.of(2023, 9, 17);
    }
    public static LocalDate getCurrDay(){
        return currDay;
    }
    public void nextDay(){
        currDay = currDay.plusDays(1);
    }
}



