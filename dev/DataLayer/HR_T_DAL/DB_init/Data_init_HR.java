package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.*;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.HR_T_DAL.DalService.DalShiftService;
import DataLayer.Util.DAO;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import UtilSuper.Location;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Data_init_HR {



    public static void initBasicData(DAO dao,ShiftService shiftService,EmployeeService employeeService) throws Exception {

        employeeService.addNewSuper(1111, "SuperManager", "1237894561", "Route SuperManager expert", 22000, "2023-05-14", "1111");
        //UserDTO userDTO = new UserDTO("User", "1", "1", "HRManager", 1, "123456", "2023-04-03", "good worker", 10000);
        //dao.deleteTableDataWithDTO(userDTO);
        //dao.insert(userDTO);

        //init shifts for ayear in all branches
        LocalDate startDate = LocalDate.now(); // Start date is one year from now
        LocalDate endDate = startDate.plusWeeks(2); // End date is one month from the start date

        //this was the old before map:
       // List<String> branches = Arrays.asList("b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9");
        //get 0-8 branches from site addresses and insert them to List
        //from 0 to 8 because we have 9 branches
        //use the static method getBranchAddress from SiteAddresses class
        //use for loop to insert the branches to the list
        List<String> branches = new java.util.ArrayList<>();
        for (int i = 0; i < 9; i++) {
            branches.add(SiteAddresses.getBranchAddress(i));
        }


        // Loop through each date and insert shifts for the morning and evening
        while (startDate.isBefore(endDate)) {
            for (String branch : branches) {
                try{
                    ShiftDTO shift1 = new ShiftDTO(startDate.toString(), "m", -1, branch);
                    ShiftDTO shift2 = new ShiftDTO(startDate.toString(), "e", -1, branch);
                    dao.insert(shift1);
                    dao.insert(shift2);
                }
                catch (Exception exp){
                    throw new Exception("failed to insert shifts to DB");
                }

            }

            // Increment the date by 1 day
            startDate = startDate.plusDays(1);
        }
        //init requirements for month
        LinkedHashMap<String, Integer> positions = new LinkedHashMap<>();
        positions.put(PositionType.orderly.name(), 1);
        positions.put(PositionType.security.name(), 1);
        positions.put(PositionType.general_worker.name(), 2);
        positions.put(PositionType.cleaning.name(), 1);
        positions.put(PositionType.cashier.name(), 3);
        positions.put(PositionType.storekeeper.name(), 2);
       // positions.put(PositionType.shiftManager.name(), 1);

        //init shifts for ayear in all branches
        startDate = LocalDate.now(); // Start date is one year from now
        endDate = startDate.plusWeeks(1); // End date is one month
// Loop through each date and insert shifts for the morning and evening
        while (startDate.isBefore(endDate)) {
            for (String branch : branches) {
                shiftService.addShiftRequirements(branch, positions, startDate.toString(), "m" );
                shiftService.addShiftRequirements(branch, positions, startDate.toString(), "e" );
            }

            // Increment the date by 1 day
            startDate = startDate.plusDays(1);
        }

    }
    public static void initOldData(DAO dao, EmployeeService employeeService , ShiftService shiftService, EmployeeController employeeController, ShiftController shiftController, DalShiftService dalShiftService) throws Exception {
        initBasicData(dao,shiftService,employeeService);

        //adding employees
//        UserDTO user1 = new UserDTO("User", "JohnDoe", "1", "employee", 11, "123456789", "2023-05-14", "Good worker", 15000);
//        dao.insert(user1);
//        UserDTO user2 = new UserDTO("User", "JaneDoe", "2", "employee", 12, "987654321", "2023-05-14", "Professional driver", 20000);
//        dao.insert(user2);
//        UserDTO user3 = new UserDTO("User", "BobSmith", "3", "employee", 13, "456789123", "2023-05-14", "Experienced HR professional", 25000);
//        dao.insert(user3);
//        UserDTO user4 = new UserDTO("User", "AliceJohnson", "14", "employee", 14, "789123456", "2023-05-14", "Operations expert", 30000);
//        dao.insert(user4);
//        UserDTO user5 = new UserDTO("User", "DavidLee", "15", "employee", 15, "321654987", "2023-05-14", "Team player", 16000);
//        dao.insert(user5);
//        UserDTO user6 = new UserDTO("User", "MaryBrown", "16", "employee", 16, "654987321", "2023-05-14", "Safe and reliable", 21000);
//        dao.insert(user6);
//        UserDTO user7 = new UserDTO("User", "JoeWilson", "17", "employee", 17, "789654123", "2023-05-14", "People person", 26000);
//        dao.insert(user7);
//        UserDTO user8 = new UserDTO("User", "SaraJones", "18", "employee", 18, "456321789", "2023-05-14", "Logistics pro", 31000);
//        dao.insert(user8);
//        UserDTO user9 = new UserDTO("User", "MarkTaylor", "19", "employee", 19, "789456123", "2023-05-14", "Hard worker", 17000);
//        dao.insert(user9);
//        UserDTO user10 = new UserDTO("User", "EmilyDavis", "20", "employee", 20, "123789456", "2023-05-14", "Route optimization expert", 22000);
//        dao.insert(user10);

        employeeService.addNewEmployee(11, "JohnDoe", "123456789", "Good worker", 15000, "2023-05-14", "11");
        employeeService.addNewEmployee(12, "JaneDoe", "987654321", "Professional driver", 20000, "2023-05-14", "12");
        employeeService.addNewEmployee(13, "BobSmith", "456789123", "Experienced HR professional", 25000,"2023-05-14", "13");
        employeeService.addNewEmployee(14, "AliceJohnson", "789123456", "Operations expert", 30000,"2023-05-14", "14");
        employeeService.addNewEmployee(15, "DavidLee", "321654987", "Team player", 16000, "2023-05-14", "15");
        employeeService.addNewEmployee(16, "MaryBrown", "654987321", "Safe and reliable", 21000,"2023-05-14", "16");
        employeeService.addNewEmployee(17, "JoeWilson", "789654123", "People person", 26000, "2023-05-14", "17");
        employeeService.addNewEmployee(18, "SaraJones", "456321789", "Logistics pro", 31000, "2023-05-14", "18");
        employeeService.addNewEmployee(19, "MarkTaylor", "789456123", "Hard worker", 17000, "2023-05-14", "19");
        employeeService.addNewEmployee(20, "EmilyDavis6", "1237894562", "Route optimization expert", 22000, "2023-05-14", "20");
        employeeService.addNewEmployee(21, "EmilyDavis1", "1237894563", "Route optimization expert", 22000, "2023-05-14", "21");
        employeeService.addNewEmployee(22, "EmilyDavis2", "1237894561", "Route optimization expert", 22000, "2023-05-14", "22");
        employeeService.addNewEmployee(23, "EmilyDavis3", "1237894562", "Route optimization expert", 22000, "2023-05-14", "23");
        employeeService.addNewEmployee(24, "EmilyDavis4", "1237894563", "Route optimization expert", 22000, "2023-05-14", "24");
        employeeService.addNewEmployee(25, "EmilyDavis5", "1237894561", "Route optimization expert", 22000, "2023-05-14", "25");


        // Store keepers
        for (int id = 80; id <= 98; id++) {
            String employeeName = "Store Keeper " + id ; // Generate a unique name for each store keeper
            String employeePhoneNumber = "123789456" + id ; // Generate a unique phone number for each store keeper
            String employeePosition = "storekeeper" + id;
            int employeeSalary = 22000 + id;
            String employeeStartDate = "2023-05-14";
            String employeePassword = Integer.toString(id);
            employeeService.addNewEmployee(id, employeeName, employeePhoneNumber, employeePosition, employeeSalary, employeeStartDate, employeePassword);
            employeeService.addQualification(id , "storekeeper");
            System.out.println("addEmployee" + id);
        }





        //cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager;
        employeeService.addQualification(11 , "shiftManager");
        employeeService.addQualification(11 , "cashier");
        employeeService.addQualification(12 , "general_worker");
        employeeService.addQualification(12 , "orderly");
        employeeService.addQualification(13 , "cleaning");
        employeeService.addQualification(13 , "security");
        employeeService.addQualification(13 , "storekeeper");
        employeeService.addQualification(14 , "storekeeper");
        employeeService.addQualification(15 , "cashier");
        employeeService.addQualification(16 , "cashier");
        employeeService.addQualification(16 , "cleaning");
        employeeService.addQualification(17 , "security");
        employeeService.addQualification(17 , "storekeeper");
        employeeService.addQualification(17 , "storekeeper");
        employeeService.addQualification(18 , "cashier");
        employeeService.addQualification(19 , "cashier");
        employeeService.addQualification(19 , "storekeeper");
        employeeService.addQualification(20 , "orderly");
        employeeService.addQualification(21 , "general_worker");
        employeeService.addQualification(22 , "orderly");
        employeeService.addQualification(22 , "storekeeper");
        employeeService.addQualification(23 , "general_worker");
        employeeService.addQualification(24 , "security");
        employeeService.addQualification(25 , "storekeeper");
        //employeeService.addQualification(20 , "general_worker");



        List<String> branches = new java.util.ArrayList<>();
        for (int i = 0; i < 9; i++) {
            branches.add(SiteAddresses.getBranchAddress(i));
        }


        //new:
        employeeService.submitShiftForEmployee(branches.get(0), 13, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 11, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 12, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 14, LocalDate.now().plusDays(1), "e");
        employeeService.submitShiftForEmployee(branches.get(1), 15, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 13, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 14, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 15, LocalDate.now().plusDays(1), "e");
        employeeService.submitShiftForEmployee(branches.get(0), 16, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 17, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 18, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 19, LocalDate.now().plusDays(1), "e");
        employeeService.submitShiftForEmployee(branches.get(0), 20, LocalDate.now().plusDays(2), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 20, LocalDate.now().plusDays(3), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 20, LocalDate.now().plusDays(4), "e");
        employeeService.submitShiftForEmployee(branches.get(0), 20, LocalDate.now().plusDays(5), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 20, LocalDate.now().plusDays(6), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 21, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 22, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 23, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 24, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(0), 25, LocalDate.now().plusDays(1), "m");
        employeeService.submitShiftForEmployee(branches.get(1), 16, LocalDate.now().plusDays(3), "e");
        employeeService.submitShiftForEmployee(branches.get(2), 17, LocalDate.now().plusDays(4), "m");
        employeeService.submitShiftForEmployee(branches.get(2), 18, LocalDate.now().plusDays(4), "e");
        employeeService.submitShiftForEmployee(branches.get(3), 19, LocalDate.now().plusDays(5), "m");
        employeeService.submitShiftForEmployee(branches.get(3), 20, LocalDate.now().plusDays(5), "e");

        for (int days = 1; days <= 6; days++) {
                for (int branch = 0; branch < 9; branch++) {
                    String branchName = branches.get(branch);
                    LocalDate shiftDate = LocalDate.now().plusDays(days);

                    // Submit morning shift for store keeper
                    String morningShiftType = "m";
                    employeeService.submitShiftForEmployee(branchName, 80+branch , shiftDate, morningShiftType);
                    shiftService.assignEmployeeForShift(branchName, 80 + branch, shiftDate.toString(), morningShiftType, PositionType.storekeeper.name());
                    System.out.println("submitEmployee" + (80 + branch));

                    // Submit evening shift for store keeper
                    String eveningShiftType = "e";
                    employeeService.submitShiftForEmployee(branchName, 80+branch +1, shiftDate, eveningShiftType);
                    shiftService.assignEmployeeForShift(branchName, 80 + branch +1, shiftDate.toString(), eveningShiftType, PositionType.storekeeper.name());
                    System.out.println("assignEmployee" + (80 + branch +1));
                }
        }





        //adding drivers

        employeeService.addNewDriver(31, "Driver 1", "123456789", "Driver with license type C and no cooling", 20000, "2023-05-14", "31", "C1", 3);
        employeeService.addNewDriver(32, "Driver 2", "987654321", "Driver with license type C1 and fridge cooling", 21000, "2023-05-14", "32", "C1", 2);
        employeeService.addNewDriver(33, "Driver 3", "456789123", "Driver with license type E and freezer cooling", 22000, "2023-05-14", "33", "E", 3);
        employeeService.addNewDriver(34, "Driver 4", "789123456", "Driver with license type C and fridge cooling", 23000, "2023-05-14", "34", "C", 2);
        employeeService.addNewDriver(35, "Driver 5", "321654987", "Driver with license type C1 and no cooling", 24000, "2023-05-14", "35", "C1", 1);
        employeeService.addNewDriver(36, "Driver 6", "654987321", "Driver with license type E and no cooling", 25000, "2023-05-14", "36", "E", 1);
        employeeService.addNewDriver(37, "Driver 7", "789654123", "Driver with license type C and freezer cooling", 26000, "2023-05-14", "37", "C", 3);
        employeeService.addNewDriver(38, "Driver 8", "456321789", "Driver with license type C1 and fridge cooling", 27000, "2023-05-14", "38", "C1", 2);
        employeeService.addNewDriver(39, "Driver 9", "789456123", "Driver with license type E and fridge cooling", 28000, "2023-05-14", "39", "E", 3);
        employeeService.addNewDriver(40, "Driver 10", "123789456", "Driver with license type C and no cooling", 29000, "2023-05-14", "40", "C", 1);



        //submit shifts for drivers
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 31);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(2), 32);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 33);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 34);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 35);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 36);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 37);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 38);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(1), 39);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(3), 39);
        employeeService.submitShiftForDriver(LocalDate.now().plusDays(3), 31);

        //store keepers
        for (int id = 1; id <= 6; id++) {
            employeeService.submitShiftForDriver(LocalDate.now().plusDays(id), 40);
            System.out.println("submitDriver" + id);
            employeeService.assignDriverForShift(LocalDate.now().plusDays(id), 40);
            System.out.println("assignDriver" + id);
        }



        //employeeService.assignDriverForShift(LocalDate.now().plusDays(1), 29, "C1", "fridge" );
//        shiftService.addDriverReq(LocalDate.now().plusDays(2).toString(),"C1", "1");
//        shiftService.addDriverReq(LocalDate.now().plusDays(2).toString(),"C", "2");
//        shiftService.addDriverReq(LocalDate.now().plusDays(2).toString(),"E", "0");




//        DriverDTO driver1 = new DriverDTO(21, "C", "non");
//        dao.insert(driver1);
//        DriverDTO driver2 = new DriverDTO(22, "C1", "fridge");
//        dao.insert(driver2);
//        DriverDTO driver3 = new DriverDTO(23, "E", "freezer");
//        dao.insert(driver3);
//        DriverDTO driver4 = new DriverDTO(24, "C", "fridge");
//        dao.insert(driver4);
//        DriverDTO driver5 = new DriverDTO(25, "C1", "non");
//        dao.insert(driver5);
//        DriverDTO driver6 = new DriverDTO(26, "E", "non");
//        dao.insert(driver6);
//        DriverDTO driver7 = new DriverDTO(27, "C", "freezer");
//        dao.insert(driver7);
//        DriverDTO driver8 = new DriverDTO(28, "C1", "fridge");
//        dao.insert(driver8);
//        DriverDTO driver9 = new DriverDTO(29, "E", "fridge");
//        dao.insert(driver9);
//        DriverDTO driver10 = new DriverDTO(30, "C", "non");
//        dao.insert(driver10);
//
//        //adding the users equivalent to the drivers
//
//        UserDTO driver1a = new UserDTO("User", "AdamDriver", "21", "driver", 21, "987654321", "2023-05-14", "Expert navigator", 22000);
//        dao.insert(driver1a);
//        UserDTO driver2a = new UserDTO("User", "BrianDriver", "22", "driver", 22, "456789123", "2023-05-14", "Courteous driver", 21000);
//        dao.insert(driver2a);
//        UserDTO driver3a = new UserDTO("User", "CharlieDriver", "23", "driver", 23, "789123456", "2023-05-14", "Safety first", 20000);
//        dao.insert(driver3a);
//        UserDTO driver4a = new UserDTO("User", "DianaDriver", "24", "driver", 24, "321654987", "2023-05-14", "Good listener", 19000);
//        dao.insert(driver4a);
//        UserDTO driver5a = new UserDTO("User", "EricDriver", "25", "driver", 25, "654987321", "2023-05-14", "Fuel efficient", 18000);
//        dao.insert(driver5a);
//        UserDTO driver6a = new UserDTO("User", "FrankDriver", "26", "driver", 26, "789654123", "2023-05-14", "Friendly demeanor", 17000);
//        dao.insert(driver6a);
//        UserDTO driver7a = new UserDTO("User", "GraceDriver", "27", "driver", 27, "456321789", "2023-05-14", "Mechanical aptitude", 16000);
//        dao.insert(driver7a);
//        UserDTO driver8a = new UserDTO("User", "HenryDriver", "28", "driver", 28, "789456123", "2023-05-14", "Attention to detail", 15000);
//        dao.insert(driver8a);
//        UserDTO driver9a = new UserDTO("User", "IsabelleDriver", "29", "driver", 29, "123789456", "2023-05-14", "Good sense of direction", 14000);
//        dao.insert(driver9a);
//        UserDTO driver10a = new UserDTO("User", "JasonDriver", "30", "driver", 30, "987456321", "2023-05-14", "Strong work ethic", 13000);
//        dao.insert(driver10a);

//
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),21));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),22));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),22));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),23));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),24));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),24));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),25));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),26));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),26));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),27));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),28));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),28));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),29));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),30));
//        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),30));
//
//        dao.insert(new SiteDTO("super", "000000001", "Contact super", 42, 42, Location.getShippingArea(42,42),"branch"));
//
//        String date = Time.localDateToString(LocalDate.now().plusDays(1));
//        dao.insert(new ShiftDTO(date, "m", 11, "super"));
//        dao.insert(new ShiftDTO(date, "e", 11, "super"));
//        date = Time.localDateToString(LocalDate.now());
//        dao.insert(new ShiftDTO(date, "m", 11, "super"));
//        dao.insert(new ShiftDTO(date, "e", 11, "super"));
//        date = Time.localDateToString(LocalDate.now().plusDays(2));
//        dao.insert(new ShiftDTO(date, "m", 11, "super"));
//        dao.insert(new ShiftDTO(date, "e", 11, "super"));
//
//        //cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager;
//        LinkedHashMap<String,Integer> howmany = new LinkedHashMap<>();
//        howmany.put("cashier",1);
//        howmany.put("storekeeper",1);
//        howmany.put("security",1);
//        howmany.put("cleaning",1);
//        howmany.put("general_worker",1);
//        howmany.put("orderly",1);
//        howmany.put("shiftManager",1);
//        shiftService.addShiftRequirements("super",howmany,Time.localDateToString(LocalDate.now().plusDays(1)), "e" );
//        shiftService.addShiftRequirements("super",howmany,Time.localDateToString(LocalDate.now().plusDays(1)), "m");
//        shiftService.addShiftRequirements("super",howmany,Time.localDateToString(LocalDate.now().plusDays(1)), "e");
//        shiftService.addShiftRequirements("super",howmany,Time.localDateToString(LocalDate.now().plusDays(1)), "m");
//        shiftService.addShiftRequirements("super",howmany,Time.localDateToString(LocalDate.now().plusDays(1)), "e");

//
//        SubmittedShiftDTO s = new SubmittedShiftDTO(11, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "true");
//        dao.insert(s);
//        dao.insert(new SubmittedShiftDTO(12, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "e"));
//        dao.insert(new SubmittedShiftDTO(13, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "m"));
//        dao.insert(new SubmittedShiftDTO(14, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "e"));
//        dao.insert(new SubmittedShiftDTO(15, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "m"));
//        dao.insert(new SubmittedShiftDTO(16, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "e"));
//        dao.insert(new SubmittedShiftDTO(17, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "m"));
//        dao.insert(new SubmittedShiftDTO(18, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "e"));
//        dao.insert(new SubmittedShiftDTO(19, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "m"));
//        dao.insert(new SubmittedShiftDTO(20, "super", Time.localDateToString(LocalDate.now().plusDays(1)), "e"));

    }
}