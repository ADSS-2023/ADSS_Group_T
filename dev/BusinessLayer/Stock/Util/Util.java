package BusinessLayer.Stock.Util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;

public class Util {
    public static String extractFirstNumber(String inputString) {
        // Find the index of the first non-digit character
        int index = 1;
        while (index < inputString.length() && Character.isDigit(inputString.charAt(index))) {
            index++;
        }
        // Extract the first number using the substring method
        return inputString.substring(1, index);
    }
    public static String extractNextIndex(String inputString){
        // Find the index of the first non-digit character
        int index = 1;
        while (index < inputString.length() && Character.isDigit(inputString.charAt(index))) {
            index++;
        }
        // Extract the remaining string using the substring method
        return inputString.substring(index);
    }

    /**
     * convert string to date in format "yyyy-MM-DD".
     * @param dateString
     * @return
     */
    public static LocalDate stringToDate(String dateString) {
        if(dateString == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Unable to parse date: " + dateString);
            return null;
        }
    }

    public static boolean no_categories(LinkedList<String> categories_indexes) {
        return categories_indexes.stream().anyMatch(String::isEmpty);
    }

    public static String DateToString(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String month_string = month+"";
        String day_string = day+"";
        if (month<10)
            month_string="0"+month_string;
        if (day<10)
            day_string ="0"+day_string;
        return year+"-"+month_string+"-"+day_string;
    }

    public static DayOfWeek stringToDayOfWeek(String day) {
        switch (day) {
            case "SUNDAY":
                return DayOfWeek.SUNDAY;
            case "MONDAY":
                return DayOfWeek.MONDAY;
            case "TUESDAY":
                return DayOfWeek.TUESDAY;
            case "WEDNESDAY":
                return DayOfWeek.WEDNESDAY;
            case "THURSDAY":
                return DayOfWeek.THURSDAY;
            case "FRIDAY":
                return DayOfWeek.FRIDAY;
            case "SATURDAY":
                return DayOfWeek.SATURDAY;
            default:
                return null; // or handle invalid day input
        }
    }




}
