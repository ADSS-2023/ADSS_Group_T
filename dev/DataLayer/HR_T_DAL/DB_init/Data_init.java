package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.Transport.Site;
import DataLayer.HR_T_DAL.DTOs.CounterDTO;
import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import DataLayer.Util.DAO;
import UtilSuper.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Data_init {

    public static void DeleteAllData(){

    }
    public static void initBasicData(DAO dao) throws SQLException {
        SiteDTO logisticCenter = new SiteDTO("logistic center address","0000000000","logistic center contact",0,0,0,"logistic center");
        dao.insert(logisticCenter);

        CounterDTO dateCounter = new CounterDTO("date counter", LocalDate.now().toString());
        dao.insert(dateCounter);

        CounterDTO fileCounter = new CounterDTO("file counter","0");
        dao.insert(fileCounter);

        CounterDTO deliveryCounter = new CounterDTO("file counter","0");
        dao.insert(fileCounter);






    }
    public static void initOldData(DAO dao) throws SQLException {
        //branches
        SiteDTO b1 = new SiteDTO("b1", "000000001", "Contact B1", 1, 30,Location.getShippingArea(1,30),"branch");
        dao.insert(b1);
        SiteDTO b2 = new SiteDTO("b2", "000000002", "Contact B2", 30, 34, Location.getShippingArea(30, 34), "branch");
        dao.insert(b2);
        SiteDTO b3 = new SiteDTO("b3", "000000003", "Contact B3", 11, 40, Location.getShippingArea(11, 40), "branch");
        dao.insert(b3);
        SiteDTO b4 = new SiteDTO("b4", "000000004", "Contact B4", -41, -30, Location.getShippingArea(-41, -30), "branch");
        dao.insert(b4);
        SiteDTO b5 = new SiteDTO("b5", "000000005", "Contact B5", 24, -50, Location.getShippingArea(24, -50), "branch");
        dao.insert(b5);
        SiteDTO b6 = new SiteDTO("b6", "000000006", "Contact B6", -20, 10, Location.getShippingArea(-20, 10), "branch");
        dao.insert(b6);
        SiteDTO b7 = new SiteDTO("b7", "000000007", "Contact B7", 45, -23, Location.getShippingArea(45, -23), "branch");
        dao.insert(b7);
        SiteDTO b8 = new SiteDTO("b8", "000000008", "Contact B8", -13, -7, Location.getShippingArea(-13, -7), "branch");
        dao.insert(b8);
        SiteDTO b9 = new SiteDTO("b9", "000000009", "Contact B9", 17, 5, Location.getShippingArea(17, 5), "branch");
        dao.insert(b9);

        //Suppliers
        SiteDTO s1 = new SiteDTO("s1", "000000011", "Contact S1", 1, -30, Location.getShippingArea(1, -30), "supplier");
        dao.insert(s1);
        SiteDTO s2 = new SiteDTO("s2", "000000012", "Contact S2", -10, 20, Location.getShippingArea(-10, 20), "supplier");
        dao.insert(s2);
        SiteDTO s3 = new SiteDTO("s3", "000000013", "Contact S3", 15, 35, Location.getShippingArea(15, 35), "supplier");
        dao.insert(s3);
        SiteDTO s4 = new SiteDTO("s4", "000000014", "Contact S4", -25, 5, Location.getShippingArea(-25, 5), "supplier");
        dao.insert(s4);
        SiteDTO s5 = new SiteDTO("s5", "000000015", "Contact S5", 40, -20, Location.getShippingArea(40, -20), "supplier");
        dao.insert(s5);
        SiteDTO s6 = new SiteDTO("s6", "000000016", "Contact S6", 25, 10, Location.getShippingArea(25, 10), "supplier");
        dao.insert(s6);
        SiteDTO s7 = new SiteDTO("s7", "000000017", "Contact S7", 30, 30, Location.getShippingArea(30, 30), "supplier");
        dao.insert(s7);
        SiteDTO s8 = new SiteDTO("s8", "000000018", "Contact S8", -20, -10, Location.getShippingArea(-20, -10), "supplier");
        dao.insert(s8);
        SiteDTO s9 = new SiteDTO("s9", "000000019", "Contact S9", 5, -5, Location.getShippingArea(5, -5), "supplier");
        dao.insert(s9);

    }





}
