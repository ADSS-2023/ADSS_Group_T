//package Initialization;
//
//import BusinessLayer.HR.Branch;
//import BusinessLayer.HR.EmployeeController;
//import BusinessLayer.HR.Shift;
//import ServiceLayer.HR.EmployeeService;
//import ServiceLayer.HR.Presentaition;
//import ServiceLayer.HR.ShiftService;
//import UtilSuper.PositionType;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Scanner;
//
//
//public class HR_Initialization {
//
//
//    public static void init_data(ShiftService shiftService, EmployeeService employeeService){
////        Branch branch = new Branch();
////
////        EmployeeController employeeController = branch.getEmployeesController(1);
////        employeeController.initEmployeeConroller(shiftController);
////        EmployeeService Emp = new EmployeeService(employeeController);
////        ShiftService shiftService = sh
//
//        HashMap<String, ArrayList<Shift>> shifts = new HashMap<String, ArrayList<Shift>>();
//        int shiftId = 0;
//
//        // create shifts for 365 days with morning and evening shifts
//        for (int month = 1; month <= 12; month++) {
//            int numDaysInMonth = 0;
//            if (month == 2) { // February
//                numDaysInMonth = 28; // assuming a non-leap year
//            } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
//                numDaysInMonth = 30;
//            } else { // January, March, May, July, August, October, December
//                numDaysInMonth = 31;
//            }
//
//            for (int day = 1; day <= numDaysInMonth; day++) {
//                String date = String.format("%02d.%02d.2023", day, month); // format date as "DD.MM.YYYY"
//                Shift morningShift = new Shift(shiftId, date, true); // morning shift
//                Shift eveningShift = new Shift(shiftId, date, false); // evening shift
//                ArrayList<Shift> shiftList = new ArrayList<Shift>(2);
//                shiftList.add(morningShift);
//                shiftList.add(eveningShift);
//                shifts.put(date, shiftList);
//                shiftId+= 2;
//            }
//        }
//
////TODO
////        Scanner input = new Scanner(System.in);
////        System.out.println("1. Do you want to load a random data? - enter 1 for yes, enter 2 for no");
////        String ans_data = input.next();
////        if (ans_data.equals("1")) {
////
////            initWorkers(employeeController);
////            initShiftsRequeirments(shiftService);
////            initSubmisiion(shiftService);
////            Presentaition presentaition = new Presentaition(Emp,shiftService);
////            presentaition.begin();
////        }
////        else if (ans_data.equals("2")){
////            shiftController.init(shifts, employeeController.getEmployeesMapper());
////            Presentaition presentaition = new Presentaition(Emp,shiftService);
////            presentaition.begin();
////        }
//
//    }
//
//    public static void initWorkers(EmployeeController employeeController) {
//        List<PositionType> pt1 = new ArrayList<PositionType>();
//        pt1.add(PositionType.general_worker);
//        pt1.add(PositionType.cashier);
//        employeeController.addNewEmployee("Worker1","123456",pt1,"11.03.2023",1 , "1",true);
//
//        List<PositionType> pt2 = new ArrayList<PositionType>();
//        pt2.add(PositionType.cashier);
//        employeeController.addNewEmployee("Worker2","123456",pt2,"11.01.2021",2 , "2",false);
//
//        List<PositionType> pt3 = new ArrayList<PositionType>();
//        pt3.add(PositionType.cashier);
//        employeeController.addNewEmployee("Worker3","123456",pt3,"12.02.2022",3 , "3",false);
//
//        List<PositionType> pt4 = new ArrayList<PositionType>();
//        pt4.add(PositionType.cleaning);
//        employeeController.addNewEmployee("Worker4","123456",pt4,"12.02.2022",4 , "4",false);
//
//        List<PositionType> pt5 = new ArrayList<PositionType>();
//        pt5.add(PositionType.general_worker);
//        employeeController.addNewEmployee("Worker5","123456",pt5,"12.02.2022",5 , "5",false);
//
//        List<PositionType> pt6 = new ArrayList<PositionType>();
//        pt6.add(PositionType.orderly);
//        pt6.add(PositionType.general_worker);
//        employeeController.addNewEmployee("Worker6","123456",pt6,"12.02.2022",6 , "6",false);
//
//        List<PositionType> pt7 = new ArrayList<PositionType>();
//        pt7.add(PositionType.security);
//        pt7.add(PositionType.general_worker);
//        employeeController.addNewEmployee("Worker7","123456",pt7,"12.02.2022",7 , "7",false);
//
//        List<PositionType> pt8 = new ArrayList<PositionType>();
//        pt1.add(PositionType.storekeeper);
//        employeeController.addNewEmployee("Worker8","123456",pt8,"12.02.2022",8 , "8",false);
//    }
//
//    public static void initShiftsRequeirments(ShiftService shiftService) {
//        HashMap<String,Integer> requeirments = new HashMap<>();
//        requeirments.put("cashier", 3);
//        requeirments.put("cleaning", 1);
//        requeirments.put("general_worker", 1);
//        requeirments.put("orderly", 1);
//        requeirments.put("security", 1);
//        requeirments.put("storekeeper", 1);
//        shiftService.addShiftRequirements(requeirments, "14.03.2023", "0");
//    }
//
//    public static void initSubmisiion(ShiftService shiftService) {
//        shiftService.addNewSubmittedPositionByEmployee(1, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(1, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(2, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(3, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(4, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(5, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(6, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(7, "14.03.2023", "m", "y");
//        shiftService.addNewSubmittedPositionByEmployee(8, "14.03.2023", "m", "y");
//    }
//
//}