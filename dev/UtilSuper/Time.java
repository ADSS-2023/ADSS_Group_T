package UtilSuper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Time {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static Time currDate = new Time(); // Singleton instance

    private Time() {} // private constructor to prevent instantiation

    public static LocalDate stringToDate(String string){
        return LocalDate.parse(string, formatter);
    }

    public static void setCurrDate(LocalDate date) {
        currDate = new Time(); // create a new instance to prevent mutation of singleton instance
        currDate.date = date;
    }

    public static LocalDate getCurrDate() {
        return currDate.date;
    }

    private LocalDate date;
}
