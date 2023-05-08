package BusinessLayer.Supplier_Stock;
import java.util.Calendar;
import java.util.Date;
public class Util_Supplier_Stock {
    public Util_Supplier_Stock(){}
    public void next_day(){
                // Create a calendar object and set it to January 1st, 2023
                Calendar calendar = Calendar.getInstance();
                calendar.set(2023, Calendar.JANUARY, 1);

                // Get the date object from the calendar
                Date date = calendar.getTime();

                // Set the system date to the specified date
                System.setProperty("user.timezone", "UTC");
                System.setProperty("user.dateoverride", String.valueOf(date.getTime()));

                // Test by printing the current date
                System.out.println(new Date());
            }
}



