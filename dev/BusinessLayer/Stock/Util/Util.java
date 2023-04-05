package BusinessLayer.Stock.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Unable to parse date: " + dateString);
            return null;
        }
    }
}
