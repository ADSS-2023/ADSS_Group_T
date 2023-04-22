package Util;

public class WeekDaysFunc {
    public static WeekDays toDayOfWeek(String input) {
        input = input.toLowerCase();
        switch (input) {
            case "monday":
                return WeekDays.MONDAY;
            case "tuesday":
                return WeekDays.TUESDAY;
            case "wednesday":
                return WeekDays.WEDNESDAY;
            case "thursday":
                return WeekDays.THURSDAY;
            case "friday":
                return WeekDays.FRIDAY;
            case "saturday":
                return WeekDays.SATURDAY;
            case "sunday":
                return WeekDays.SUNDAY;
            default:
                return null;
        }
    }
}
