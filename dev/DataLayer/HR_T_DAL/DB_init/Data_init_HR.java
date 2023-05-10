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
        UserDTO userDTO = new UserDTO("User", "1", "1", "HRManager", 1, "123456", "01/02/1999", "good worker", 10000);
        dao.deleteTableDataWithDTO(userDTO);
        dao.insert(userDTO);


    }
}