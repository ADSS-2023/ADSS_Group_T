package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;
import UtilSuper.Location;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.LocalDate;

public class Data_init_HR {

    public static void initBasicData(DAO dao) throws SQLException {
        UserDTO userDTO = new UserDTO("User", "1", "1", "HRManager", 1, "123456", "2020-04-03", "good worker", 10000);
        dao.deleteTableDataWithDTO(userDTO);
        dao.insert(userDTO);
    }
    public static void initOldData(DAO dao) throws SQLException {

        UserDTO userDTO = new UserDTO("User", "11", "11", "employee", 11, "123456", "2021-05-14", "good worker", 10000);
        dao.deleteTableDataWithDTO(userDTO);
        dao.insert(userDTO);

        UserDTO employee1 = new UserDTO("User", "12", "12", "employee", 12, "123456", "2021-05-14", "good worker", 10000);
        dao.deleteTableDataWithDTO(employee1);
        dao.insert(employee1);

        UserDTO employee2 = new UserDTO("User", "13", "13", "employee", 13, "123456", "2021-05-14", "good worker", 10000);
        dao.deleteTableDataWithDTO(employee2);
        dao.insert(employee1);

        UserDTO employee3 = new UserDTO("User", "14", "14", "employee", 14, "123456", "2021-05-14", "good worker", 10000);
        dao.deleteTableDataWithDTO(employee3);
        dao.insert(employee1);

        UserDTO employee4 = new UserDTO("User", "15", "15", "employee", 15, "123456", "2021-05-14", "good worker", 10000);
        dao.deleteTableDataWithDTO(employee4);
        dao.insert(employee1);


//
//        DriverDTO driver1 = new DriverDTO(10,"C", "non");
//        dao.insert(driver1);
//
//        DriverDTO driver2 = new DriverDTO(20,"C", "non");
//        dao.insert(driver2);
//
//
//
//
//        DateToDriverDTO d2d1 = new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),10);
//        DateToDriverDTO d2d2 = new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(1)),20);
//        DateToDriverDTO d2d21 = new DateToDriverDTO(Time.localDateToString(LocalDate.now().plusDays(2)),20);
//        dao.insert(d2d1);
//        dao.insert(d2d2);
//        dao.insert(d2d21);


    }
}