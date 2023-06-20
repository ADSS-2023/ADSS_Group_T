package DataLayer.HR_T_DAL.DB_init;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.SupplierService;
import UtilSuper.Location;
import UtilSuper.Time;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Data_init {

    public static void initBasicData(DAO dao) throws SQLException {

        dao.deleteAllDataFromDatabase();

        SiteDTO logisticCenter = new SiteDTO("logistic center address","0000000000","logistic center contact",0,0,0,"logistic center");
        dao.insert(logisticCenter);

        CounterDTO dateCounter = new CounterDTO("date counter", LocalDate.now().toString());
        dao.insert(dateCounter);

        CounterDTO fileCounter = new CounterDTO("file counter","0");
        dao.insert(fileCounter);

        CounterDTO deliveryCounter = new CounterDTO("delivery counter","0");
        dao.insert(deliveryCounter);

    }

    public static void initOldData(DAO dao,SupplierService supplierService,DeliveryService deliveryService) throws SQLException {
        initBasicData(dao);
        initSites(dao);
        initTrucks(dao);
        initSupplierProducts(supplierService);
        initDelivery(deliveryService);
    }



    public static void initSites(DAO dao) throws SQLException {


        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(0), "000000001", "Contact B1", 1, 30, Location.getShippingArea(1,30),"branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(1), "000000002", "Contact B2", 30, 34, Location.getShippingArea(30, 34), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(2), "000000003", "Contact B3", 11, 40, Location.getShippingArea(11, 40), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(3), "000000004", "Contact B4", -41, -30, Location.getShippingArea(-41, -30), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(4), "000000005", "Contact B5", 24, -50, Location.getShippingArea(24, -50), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(5), "000000006", "Contact B6", -20, 10, Location.getShippingArea(-20, 10), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(6), "000000007", "Contact B7", 45, -23, Location.getShippingArea(45, -23), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(7), "000000008", "Contact B8", -13, -7, Location.getShippingArea(-13, -7), "branch"));
        dao.insert(new SiteDTO(SiteAddresses.getBranchAddress(8), "000000009", "Contact B9", 17, 5, Location.getShippingArea(17, 5), "branch"));

        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(0), "000000011", "Contact S1", 1, -30, Location.getShippingArea(1, -30), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(1), "000000012", "Contact S2", -10, 20, Location.getShippingArea(-10, 20), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(2), "000000013", "Contact S3", 15, 35, Location.getShippingArea(15, 35), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(3), "000000014", "Contact S4", -25, 5, Location.getShippingArea(-25, 5), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(4), "000000015", "Contact S5", 40, -20, Location.getShippingArea(40, -20), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(5), "000000016", "Contact S6", 25, 10, Location.getShippingArea(25, 10), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(6), "000000017", "Contact S7", 30, 30, Location.getShippingArea(30, 30), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(7), "000000018", "Contact S8", -20, -10, Location.getShippingArea(-20, -10), "supplier"));
        dao.insert(new SiteDTO(SiteAddresses.getSupplierAddress(8), "000000019", "Contact S9", 5, -5, Location.getShippingArea(5, -5), "supplier"));
    }

    public static void  initTrucks(DAO dao) throws SQLException {
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

    public static void initSupplierProducts(SupplierService supplierService){

// Supplier 0 Tnuva
        LinkedHashMap<String,Integer> productMap1 = new LinkedHashMap<>();
        productMap1.put("Milk", 2);
        productMap1.put("Cheese", 2);
        productMap1.put("Eggs", 1);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(0), productMap1);

// Supplier 1 - Coca Cola
        LinkedHashMap<String,Integer> productMap2 = new LinkedHashMap<>();
        productMap2.put("Coke", 1);
        productMap2.put("Diet Coke", 1);
        productMap2.put("Coke Zero", 1);
        productMap2.put("Sprite", 1);
        productMap2.put("Diet Sprite", 1);
        productMap2.put("Fanta", 1);
        productMap2.put("Diet Fanta", 1);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(1), productMap2);

// Supplier 2 - Fish Market
        LinkedHashMap<String,Integer> productMap3 = new LinkedHashMap<>();
        productMap3.put("Salmon", 2);
        productMap3.put("Shrimp", 3);
        productMap3.put("Tuna", 2);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(2), productMap3);

// Supplier 3 - Fruit Stand
        LinkedHashMap<String,Integer> productMap4 = new LinkedHashMap<>();
        productMap4.put("Apple", 1);
        productMap4.put("Mango", 2);
        productMap4.put("Orange", 1);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(3), productMap4);

// Supplier 4 - Green Grocer
        LinkedHashMap<String,Integer> productMap5 = new LinkedHashMap<>();
        productMap5.put("Broccoli", 2);
        productMap5.put("Carrots", 1);
        productMap5.put("Kale", 2);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(4), productMap5);

// Supplier 5 - Wine and Spirits
        LinkedHashMap<String,Integer> productMap6 = new LinkedHashMap<>();
        productMap6.put("Red Wine", 2);
        productMap6.put("Whiskey", 3);
        productMap6.put("Tequila", 2);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(5), productMap6);

// Supplier 6 - Deli
        LinkedHashMap<String,Integer> productMap7 = new LinkedHashMap<>();
        productMap7.put("Ham", 1);
        productMap7.put("Turkey", 1);
        productMap7.put("Pastrami", 2);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(6), productMap7);

// Supplier 7 - Asian Market
        LinkedHashMap<String,Integer> productMap8 = new LinkedHashMap<>();
        productMap8.put("Sushi", 2);
        productMap8.put("Ramen", 2);
        productMap8.put("Tofu", 1);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(7), productMap8);

// Supplier 8 - Coffee Roastery
        LinkedHashMap<String,Integer> productMap9 = new LinkedHashMap<>();
        productMap9.put("Espresso", 1);
        productMap9.put("Latte", 1);
        productMap9.put("Cold Brew", 2);
        supplierService.addProducts(SiteAddresses.getSupplierAddress(8), productMap9);

    }
    public static void initDelivery(DeliveryService deliveryService){
        // Delivery 1
        String branch1 = SiteAddresses.getBranchAddress(0);
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("Milk", 500);
        supplierProducts1.put("Cheese", 1000);
        supplierProducts1.put("Eggs", 200);
        products1.put(SiteAddresses.getSupplierAddress(0), supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("Coke", 1000);
        supplierProducts2.put("Diet Coke", 250);
        supplierProducts2.put("Coke Zero", 1000);
        supplierProducts2.put("Sprite", 300);
        supplierProducts2.put("Diet Sprite", 200);
        supplierProducts2.put("Fanta", 200);
        supplierProducts2.put("Diet Fanta", 150);
        products1.put(SiteAddresses.getSupplierAddress(1), supplierProducts2);
        String date1 = Time.localDateToString(LocalDate.now().plusDays(2));
        deliveryService.orderDelivery(branch1, products1, date1);

// Delivery 2
        String branch2 = SiteAddresses.getBranchAddress(1);
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products2 = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> supplierProducts3 = new LinkedHashMap<>();
        supplierProducts3.put("Salmon", 10);
        supplierProducts3.put("Shrimp", 5);
        supplierProducts3.put("Tuna", 3);
        products2.put(SiteAddresses.getSupplierAddress(2), supplierProducts3);

        LinkedHashMap<String, Integer> supplierProducts4 = new LinkedHashMap<>();
        supplierProducts4.put("Apple", 20);
        supplierProducts4.put("Orange", 15);
        supplierProducts4.put("Mango", 10);
        products2.put(SiteAddresses.getSupplierAddress(3), supplierProducts4);

        LinkedHashMap<String, Integer> supplierProducts5 = new LinkedHashMap<>();
        supplierProducts5.put("Coke", 1000);
        supplierProducts5.put("Diet Coke", 250);
        products2.put(SiteAddresses.getSupplierAddress(1), supplierProducts5);

        String date2 = Time.localDateToString(LocalDate.now().plusDays(3));
        deliveryService.orderDelivery(branch2, products2, date2);
    }






}
