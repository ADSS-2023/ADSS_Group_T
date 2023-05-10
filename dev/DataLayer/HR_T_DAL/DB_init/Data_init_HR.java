package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.CounterDTO;
import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;
import DataLayer.Util.DAO;
import UtilSuper.Location;

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
    }
}