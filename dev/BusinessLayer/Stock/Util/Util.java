package BusinessLayer.Stock.Util;

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
}
