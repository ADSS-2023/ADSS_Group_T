package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.Driver;
import BusinessLayer.Transport.Site;
import DataLayer.HR_T_DAL.DTOs.CounterDTO;
import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import DataLayer.Util.DAO;
import UtilSuper.Location;

import java.sql.SQLException;
import java.time.LocalDate;

public class Data_init {

    public static void DeleteAllData(){

    }
    public static void initBasicData(DAO dao) throws SQLException {


        SiteDTO logisticCenter = new SiteDTO("logistic center address","0000000000","logistic center contact",0,0,0,"logistic center");
        dao.deleteTableDataWithDTO(logisticCenter);
        dao.insert(logisticCenter);

        CounterDTO dateCounter = new CounterDTO("date counter", LocalDate.now().toString());
        dao.deleteTableDataWithDTO(dateCounter);
        dao.insert(dateCounter);

        CounterDTO fileCounter = new CounterDTO("file counter","0");
        dao.insert(fileCounter);

        CounterDTO deliveryCounter = new CounterDTO("delivery counter","0");
        dao.insert(deliveryCounter);






    }
    public static void initOldData(DAO dao) throws SQLException {


        dao.insert(new SiteDTO("b1", "000000001", "Contact B1", 1, 30, Location.getShippingArea(1,30),"branch"));
        dao.insert(new SiteDTO("b2", "000000002", "Contact B2", 30, 34, Location.getShippingArea(30, 34), "branch"));
        dao.insert(new SiteDTO("b3", "000000003", "Contact B3", 11, 40, Location.getShippingArea(11, 40), "branch"));
        dao.insert(new SiteDTO("b4", "000000004", "Contact B4", -41, -30, Location.getShippingArea(-41, -30), "branch"));
        dao.insert(new SiteDTO("b5", "000000005", "Contact B5", 24, -50, Location.getShippingArea(24, -50), "branch"));
        dao.insert(new SiteDTO("b6", "000000006", "Contact B6", -20, 10, Location.getShippingArea(-20, 10), "branch"));
        dao.insert(new SiteDTO("b7", "000000007", "Contact B7", 45, -23, Location.getShippingArea(45, -23), "branch"));
        dao.insert(new SiteDTO("b8", "000000008", "Contact B8", -13, -7, Location.getShippingArea(-13, -7), "branch"));
        dao.insert(new SiteDTO("b9", "000000009", "Contact B9", 17, 5, Location.getShippingArea(17, 5), "branch"));

        dao.insert(new SiteDTO("s1", "000000011", "Contact S1", 1, -30, Location.getShippingArea(1, -30), "supplier"));
        dao.insert(new SiteDTO("s2", "000000012", "Contact S2", -10, 20, Location.getShippingArea(-10, 20), "supplier"));
        dao.insert(new SiteDTO("s3", "000000013", "Contact S3", 15, 35, Location.getShippingArea(15, 35), "supplier"));
        dao.insert(new SiteDTO("s4", "000000014", "Contact S4", -25, 5, Location.getShippingArea(-25, 5), "supplier"));
        dao.insert(new SiteDTO("s5", "000000015", "Contact S5", 40, -20, Location.getShippingArea(40, -20), "supplier"));
        dao.insert(new SiteDTO("s6", "000000016", "Contact S6", 25, 10, Location.getShippingArea(25, 10), "supplier"));
        dao.insert(new SiteDTO("s7", "000000017", "Contact S7", 30, 30, Location.getShippingArea(30, 30), "supplier"));
        dao.insert(new SiteDTO("s8", "000000018", "Contact S8", -20, -10, Location.getShippingArea(-20, -10), "supplier"));
        dao.insert(new SiteDTO("s9", "000000019", "Contact S9", 5, -5, Location.getShippingArea(5, -5), "supplier"));

        dao.deleteTableDataWithTableName(TruckDTO.getTableNameStatic());
        dao.insert(new TruckDTO(1001, "t1", 4000, 8000, Driver.LicenseType.intToLicenseType(4000), Driver.CoolingLevel.intToCoolinglevel(1)));
        dao.insert(new TruckDTO(1002, "t2", 6000, 12000, Driver.LicenseType.intToLicenseType(5000), Driver.CoolingLevel.intToCoolinglevel(2)));
        dao.insert(new TruckDTO(1003, "t3", 8000, 160000, Driver.LicenseType.intToLicenseType(6000), Driver.CoolingLevel.intToCoolinglevel(3)));
        dao.insert(new TruckDTO(1004, "t4", 10000, 20000, Driver.LicenseType.intToLicenseType(7000), Driver.CoolingLevel.intToCoolinglevel(1)));
        dao.insert(new TruckDTO(1005, "t5", 12000, 24000, Driver.LicenseType.intToLicenseType(8000), Driver.CoolingLevel.intToCoolinglevel(2)));
        dao.insert(new TruckDTO(1006, "t6", 14000, 28000, Driver.LicenseType.intToLicenseType(9000), Driver.CoolingLevel.intToCoolinglevel(3)));
        dao.insert(new TruckDTO(1007, "t7", 160000, 32000, Driver.LicenseType.intToLicenseType(10000), Driver.CoolingLevel.intToCoolinglevel(1)));
        dao.insert(new TruckDTO(1008, "t8", 18000, 36000, Driver.LicenseType.intToLicenseType(11000), Driver.CoolingLevel.intToCoolinglevel(2)));
        dao.insert(new TruckDTO(1009, "t9", 20000, 40000, Driver.LicenseType.intToLicenseType(12000), Driver.CoolingLevel.intToCoolinglevel(3)));

    }





}
