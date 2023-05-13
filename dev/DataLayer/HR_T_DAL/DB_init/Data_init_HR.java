package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Shift;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.Util.DAO;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import UtilSuper.Location;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Data_init_HR {

    public static void initBasicData(DAO dao) throws SQLException {
        UserDTO userDTO = new UserDTO("User", "1", "1", "HRManager", 1, "123456", "2023-04-03", "good worker", 10000);
        dao.deleteTableDataWithDTO(userDTO);
        dao.insert(userDTO);
    }
    public static void initOldData(DAO dao, EmployeeService employeeService , ShiftService shiftService) throws SQLException {

        dao.deleteTableDataWithTableName("User");
        dao.deleteTableDataWithTableName("Driver");
        dao.deleteTableDataWithTableName("DateToDriver");
        //adding employees

        UserDTO user1 = new UserDTO("User", "JohnDoe", "1", "employee", 11, "123456789", "2023-05-14", "Good worker", 15000);
        dao.insert(user1);
        UserDTO user2 = new UserDTO("User", "JaneDoe", "2", "employee", 12, "987654321", "2023-05-14", "Professional driver", 20000);
        dao.insert(user2);
        UserDTO user3 = new UserDTO("User", "BobSmith", "3", "employee", 13, "456789123", "2023-05-14", "Experienced HR professional", 25000);
        dao.insert(user3);
        UserDTO user4 = new UserDTO("User", "AliceJohnson", "14", "employee", 14, "789123456", "2023-05-14", "Operations expert", 30000);
        dao.insert(user4);
        UserDTO user5 = new UserDTO("User", "DavidLee", "15", "employee", 15, "321654987", "2023-05-14", "Team player", 16000);
        dao.insert(user5);
        UserDTO user6 = new UserDTO("User", "MaryBrown", "16", "employee", 16, "654987321", "2023-05-14", "Safe and reliable", 21000);
        dao.insert(user6);
        UserDTO user7 = new UserDTO("User", "JoeWilson", "17", "employee", 17, "789654123", "2023-05-14", "People person", 26000);
        dao.insert(user7);
        UserDTO user8 = new UserDTO("User", "SaraJones", "18", "employee", 18, "456321789", "2023-05-14", "Logistics pro", 31000);
        dao.insert(user8);
        UserDTO user9 = new UserDTO("User", "MarkTaylor", "19", "employee", 19, "789456123", "2023-05-14", "Hard worker", 17000);
        dao.insert(user9);
        UserDTO user10 = new UserDTO("User", "EmilyDavis", "20", "employee", 20, "123789456", "2023-05-14", "Route optimization expert", 22000);
        dao.insert(user10);

        //cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager;
        employeeService.addQualification(11 , "shiftManager");
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
        employeeService.addQualification(20 , "cashier");

        //adding drivers

        DriverDTO driver1 = new DriverDTO(21, "C", "non");
        dao.insert(driver1);
        DriverDTO driver2 = new DriverDTO(22, "C1", "fridge");
        dao.insert(driver2);
        DriverDTO driver3 = new DriverDTO(23, "E", "freezer");
        dao.insert(driver3);
        DriverDTO driver4 = new DriverDTO(24, "C", "fridge");
        dao.insert(driver4);
        DriverDTO driver5 = new DriverDTO(25, "C1", "non");
        dao.insert(driver5);
        DriverDTO driver6 = new DriverDTO(26, "E", "non");
        dao.insert(driver6);
        DriverDTO driver7 = new DriverDTO(27, "C", "freezer");
        dao.insert(driver7);
        DriverDTO driver8 = new DriverDTO(28, "C1", "fridge");
        dao.insert(driver8);
        DriverDTO driver9 = new DriverDTO(29, "E", "fridge");
        dao.insert(driver9);
        DriverDTO driver10 = new DriverDTO(30, "C", "non");
        dao.insert(driver10);

        //adding the users equivalent to the drivers

        UserDTO driver1a = new UserDTO("User", "AdamDriver", "21", "driver", 21, "987654321", "2023-05-14", "Expert navigator", 22000);
        dao.insert(driver1a);
        UserDTO driver2a = new UserDTO("User", "BrianDriver", "22", "driver", 22, "456789123", "2023-05-14", "Courteous driver", 21000);
        dao.insert(driver2a);
        UserDTO driver3a = new UserDTO("User", "CharlieDriver", "23", "driver", 23, "789123456", "2023-05-14", "Safety first", 20000);
        dao.insert(driver3a);
        UserDTO driver4a = new UserDTO("User", "DianaDriver", "24", "driver", 24, "321654987", "2023-05-14", "Good listener", 19000);
        dao.insert(driver4a);
        UserDTO driver5a = new UserDTO("User", "EricDriver", "25", "driver", 25, "654987321", "2023-05-14", "Fuel efficient", 18000);
        dao.insert(driver5a);
        UserDTO driver6a = new UserDTO("User", "FrankDriver", "26", "driver", 26, "789654123", "2023-05-14", "Friendly demeanor", 17000);
        dao.insert(driver6a);
        UserDTO driver7a = new UserDTO("User", "GraceDriver", "27", "driver", 27, "456321789", "2023-05-14", "Mechanical aptitude", 16000);
        dao.insert(driver7a);
        UserDTO driver8a = new UserDTO("User", "HenryDriver", "28", "driver", 28, "789456123", "2023-05-14", "Attention to detail", 15000);
        dao.insert(driver8a);
        UserDTO driver9a = new UserDTO("User", "IsabelleDriver", "29", "driver", 29, "123789456", "2023-05-14", "Good sense of direction", 14000);
        dao.insert(driver9a);
        UserDTO driver10a = new UserDTO("User", "JasonDriver", "30", "driver", 30, "987456321", "2023-05-14", "Strong work ethic", 13000);
        dao.insert(driver10a);


        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),21));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),22));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),22));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),23));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),24));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),24));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),25));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),26));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),26));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),27));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),28));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),28));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),29));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),30));
        dao.insert(new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),30));

        dao.insert(new SiteDTO("super", "000000001", "Contact super", 42, 42, Location.getShippingArea(42,42),"branch"));

        String date = Time.localDateToString(LocalDate.now().plusDays(1));
        dao.insert(new ShiftDTO(date, "m", 11, "super"));
        dao.insert(new ShiftDTO(date, "e", 11, "super"));
        date = Time.localDateToString(LocalDate.now());
        dao.insert(new ShiftDTO(date, "m", 11, "super"));
        dao.insert(new ShiftDTO(date, "e", 11, "super"));
        date = Time.localDateToString(LocalDate.now().plusDays(2));
        dao.insert(new ShiftDTO(date, "m", 11, "super"));
        dao.insert(new ShiftDTO(date, "e", 11, "super"));

        //cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager;
        LinkedHashMap<String,Integer> howmany = new LinkedHashMap<>();
        howmany.put("cashier",1);
        howmany.put("storekeeper",1);
        howmany.put("security",1);
        howmany.put("cleaning",1);
        howmany.put("general_worker",1);
        howmany.put("orderly",1);
        howmany.put("shiftManager",1);
        shiftService.addShiftRequirements("super",howmany,"2023-05-14", "e");
        shiftService.addShiftRequirements("super",howmany,"2023-05-14", "m");
        shiftService.addShiftRequirements("super",howmany,"2023-05-15", "e");
        shiftService.addShiftRequirements("super",howmany,"2023-05-15", "m");
        shiftService.addShiftRequirements("super",howmany,"2023-05-13", "e");

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