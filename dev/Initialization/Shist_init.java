//package Initialization;
//
//import BusinessLayer.HR.Shift;
//import ServiceLayer.Transport.BranchService;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Shist_init {
//
//
//    public static void init(ShiftService shiftService){
//    HashMap<String, ArrayList<Shift>> shifts = new HashMap<String, ArrayList<Shift>>();
//    int shiftId = 0;
//
//    // create shifts for 365 days with morning and evening shifts
//        for (int month = 1; month <= 12; month++) {
//        int numDaysInMonth = 0;
//        if (month == 2) { // February
//            numDaysInMonth = 28; // assuming a non-leap year
//        } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
//            numDaysInMonth = 30;
//        } else { // January, March, May, July, August, October, December
//            numDaysInMonth = 31;
//        }
//
//        for (int day = 1; day <= numDaysInMonth; day++) {
//            String date = String.format("%02d.%02d.2023", day, month); // format date as "DD.MM.YYYY"
//            Shift morningShift = new Shift(shiftId, date, true); // morning shift
//            Shift eveningShift = new Shift(shiftId, date, false); // evening shift
//            ArrayList<Shift> shiftList = new ArrayList<Shift>(2);
//            shiftList.add(morningShift);
//            shiftList.add(eveningShift);
//            shifts.put(date, shiftList);
//            shiftId+= 2;
//        }
//    }
//
//}
