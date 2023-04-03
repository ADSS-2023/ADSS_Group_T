package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;


public class LogisticsCenterTest {

    private LogisticsCenter lc;

 
        
        
    @Before
    public void setUp() {
        lc = new LogisticsCenter();
    }

    //----------- scheduleTruck ----------
    @Test
    public void testScheduleTruckWithoutTrucks() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        assertNull("Expected no truck to be scheduled when there are no trucks available.", lc.scheduleTruck(date, CoolingLevel.non));
    }

    @Test
    public void testScheduleTruckWithoutThrCorrectCoolingTrucks() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addTruck(1,"model",0,0,LicenseType.C,CoolingLevel.non);
        lc.addTruck(2,"model",0,0,LicenseType.C,CoolingLevel.fridge);
        assertNull("Expected no truck to be scheduled when there are no correct cooling trucks available.", lc.scheduleTruck(date, CoolingLevel.freezer));
    }

    @Test
    public void testScheduleTruckWithTrucks() {
        int licenseNumber = 1;
        lc.addTruck(licenseNumber,"model",0,0,LicenseType.C,CoolingLevel.non);
        LocalDate date = LocalDate.of(2023, 4, 2);
        assertEquals(licenseNumber,lc.scheduleTruck(date,CoolingLevel.non).getLicenseNumber());
    }

    //----------- scheduleDriver ----------
//        @Test
//        public void testScheduleDriverWithoutDrivers() {
//            LocalDate date = LocalDate.of(2023, 4, 2);
//            assertNull("Expected no driver to be scheduled when there are no drivers available.", lc.scheduleDriver(date, CoolingLevel.non));
//        }

    @Test
    public void testScheduleDriverWithNonCoolingDrivers() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.non);
        assertEquals("Expected driver with non cooling level to be scheduled.", "name1", lc.scheduleDriver(date, CoolingLevel.non).getName());
    }

    @Test
    public void testScheduleDriverWithFreezerDriverForFreezer() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.freezer);
        assertEquals("Expected driver with freezer cooling level to be scheduled for freezer job.", "name1", lc.scheduleDriver(date, CoolingLevel.freezer).getName());
    }

    @Test
    public void testScheduleDriverWithFreezerDriverForFridge() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.freezer);
        assertEquals("Expected driver with freezer cooling level to be scheduled for fridge job.", "name1", lc.scheduleDriver(date, CoolingLevel.fridge).getName());
    }

    @Test
    public void testScheduleDriverWithFreezerDriverForNon() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.freezer);
        assertEquals("Expected driver with freezer cooling level to be scheduled for non job.", "name1", lc.scheduleDriver(date, CoolingLevel.non).getName());
    }

    @Test
    public void testScheduleDriverWithFridgeDriverForFridge() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.fridge);
        assertEquals("Expected driver with fridge cooling level to be scheduled for fridge job.", "name1", lc.scheduleDriver(date, CoolingLevel.fridge).getName());
    }

    @Test
    public void testScheduleDriverWithFridgeDriverForNon() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.fridge);
        assertEquals("Expected driver with fridge cooling level to be scheduled for non job.", "name1", lc.scheduleDriver(date, CoolingLevel.non).getName());
    }

    @Test
    public void testScheduleDriverWithNonCoolingDriverForNon() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.non);
        assertEquals("Expected driver with non cooling level to be scheduled for non job.", "name1", lc.scheduleDriver(date, CoolingLevel.non).getName());
    }

    @Test
    public void testScheduleDriverWithNonCoolingDriverForFridge() {
        LocalDate date = LocalDate.of(2023, 4, 2);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.non);
        assertNull("Expected no driver to be scheduled for fridge job when only non cooling driver is available.", lc.scheduleDriver(date, CoolingLevel.fridge));
    }



//        @Test
//        public void testScheduleDriverWithCoolingDriverForNon() {
//            Driver driver = new Driver(1, "name1", LicenseType.C, CoolingLevel.non);
//            lc.addDriver(1, "name1", LicenseType.C, CoolingLevel.non);
//            LocalDate date = LocalDate.of(2023, 4, 2);
//            assertEquals(driver.getName(), lc.scheduleDriver(date, CoolingLevel.non).getName());
//        }

//        @Test
//        public void testScheduleDriverWithCoolingDriverForFridge() {
//            Driver driver = new Driver(1, "name1", LicenseType.C, CoolingLevel.fridge);
//            lc.addDriver(1, "name1", LicenseType.C, CoolingLevel.fridge);
//            LocalDate date = LocalDate.of(2023, 4, 2);
//            assertEquals(driver.getName(), lc.scheduleDriver(date, CoolingLevel.fridge).getName());
//        }

//        @Test
//        public void testScheduleDriverWithCoolingDriverForFreezer() {
//            Driver driver = new Driver(1, "name1", LicenseType.C, CoolingLevel.freezer);
//            lc.addDriver(1, "name1", LicenseType.C, CoolingLevel.freezer);
//            LocalDate date = LocalDate.of(2023, 4, 2);
//            assertEquals(driver.getName(), lc.scheduleDriver(date, CoolingLevel.freezer).getName());
//        }

    @Test
    public void testScheduleDriverWithCoolingDriverForFridgeAndNon() {
        String name1 = "name1";
        lc.addDriver(1, name1, LicenseType.C, CoolingLevel.fridge);
        lc.addDriver(2, "name2", LicenseType.C, CoolingLevel.non);
        LocalDate date = LocalDate.of(2023, 4, 2);
        assertEquals(name1, lc.scheduleDriver(date, CoolingLevel.fridge).getName());
    }

    @Test
    public void testScheduleDriverWithCoolingDriverForFreezerAndFridgeAndNon() {
        String name1 = "name1";
        lc.addDriver(1, name1, LicenseType.C, CoolingLevel.freezer);
        lc.addDriver(2, "name2", LicenseType.C, CoolingLevel.fridge);
        lc.addDriver(3, "name3", LicenseType.C, CoolingLevel.non);
        LocalDate date = LocalDate.of(2023, 4, 2);
        assertEquals(name1, lc.scheduleDriver(date, CoolingLevel.freezer).getName());
    }

    //----------- countCoolingOptions ----------
    @Test
    public void testCountCoolingOptionsEmptyList() {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        Set<CoolingLevel> expectedCoolingOptions = new HashSet<>();
        Set<CoolingLevel> actualCoolingOptions = lc.countCoolingOptions(suppliers);
        assertEquals(expectedCoolingOptions, actualCoolingOptions);
    }

    @Test
    public void testCountCoolingOptions() {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        Supplier supplier1 = new Supplier("Address1", "123456789", "Contact1", CoolingLevel.non);
        Supplier supplier2 = new Supplier("Address2", "123456789", "Contact2", CoolingLevel.fridge);
        Supplier supplier3 = new Supplier("Address3", "123456789", "Contact3", CoolingLevel.freezer);

        suppliers.put(supplier1, new LinkedHashMap<>());
        suppliers.put(supplier2, new LinkedHashMap<>());
        suppliers.put(supplier3, new LinkedHashMap<>());

        Set<CoolingLevel> expectedCoolingOptions = new HashSet<>();
        expectedCoolingOptions.add(CoolingLevel.non);
        expectedCoolingOptions.add(CoolingLevel.fridge);
        expectedCoolingOptions.add(CoolingLevel.freezer);

        Set<CoolingLevel> actualCoolingOptions = lc.countCoolingOptions(suppliers);

        assertEquals(expectedCoolingOptions, actualCoolingOptions);
    }
    @Test
    public void testCountCoolingOptionsOneNonCoolingSupplier() {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        Supplier supplier1 = new Supplier("Supplier1", "123456789", "name", CoolingLevel.non);
        suppliers.put(supplier1, new LinkedHashMap<>());
        Set<CoolingLevel> expectedCoolingOptions = new HashSet<>();
        expectedCoolingOptions.add(CoolingLevel.non);
        Set<CoolingLevel> actualCoolingOptions = lc.countCoolingOptions(suppliers);
        assertEquals(expectedCoolingOptions, actualCoolingOptions);
    }
    @Test
    public void testCountCoolingOptionsOneFreezerCoolingSupplier() {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        Supplier supplier1 = new Supplier("Supplier1", "123456789", "name", CoolingLevel.freezer);
        suppliers.put(supplier1, new LinkedHashMap<>());
        Set<CoolingLevel> expectedCoolingOptions = new HashSet<>();
        expectedCoolingOptions.add(CoolingLevel.freezer);
        Set<CoolingLevel> actualCoolingOptions = lc.countCoolingOptions(suppliers);
        assertEquals(expectedCoolingOptions, actualCoolingOptions);
    }

    @Test
    public void testCountCoolingOptionsMultipleSuppliersSameCoolingLevel() {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        Supplier supplier1 = new Supplier("Supplier1", "1234567891", "name1", CoolingLevel.fridge);
        Supplier supplier2 = new Supplier("Supplier2", "1234567892", "name1", CoolingLevel.fridge);
        suppliers.put(supplier1, new LinkedHashMap<>());
        suppliers.put(supplier2, new LinkedHashMap<>());
        Set<CoolingLevel> expectedCoolingOptions = new HashSet<>();
        expectedCoolingOptions.add(CoolingLevel.fridge);
        Set<CoolingLevel> actualCoolingOptions = lc.countCoolingOptions(suppliers);
        assertEquals(expectedCoolingOptions, actualCoolingOptions);
    }

    @Test
    public void skipDay() {
    }

    @Test
    public void addTruck() {
        assertTrue(lc.addTruck(1, "skoda", 10000, 15000, LicenseType.C, CoolingLevel.fridge));
        assertTrue(lc.addTruck(2,"skoda",10000,15000,LicenseType.C,CoolingLevel.freezer));
        assertTrue(lc.addTruck(3,"skoda",10000,15000,LicenseType.C1,CoolingLevel.fridge));
        assertFalse(lc.addTruck(3,"kia",10000,15000,LicenseType.E,CoolingLevel.non));
    }

    @Test
    public void removeTruck() {
        assertTrue(lc.addTruck(1, "skoda", 10000, 15000, LicenseType.C, CoolingLevel.fridge));
        assertTrue(lc.addTruck(2,"skoda",10000,15000,LicenseType.C,CoolingLevel.freezer));
        assertTrue(lc.removeTruck(1));
        assertTrue(lc.removeTruck(2));
        assertFalse(lc.removeTruck(1));
    }

    @Test
    public void addDriver() {
        assertTrue(lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.freezer));
        assertTrue(lc.addDriver(2,"name2",LicenseType.C1,CoolingLevel.fridge));
        assertTrue(lc.addDriver(3,"name3",LicenseType.E,CoolingLevel.non));
        assertFalse(lc.addDriver(3,"name4",LicenseType.C,CoolingLevel.fridge));
    }

    @Test
    public void removeDriver() {
        assertTrue(lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.freezer));
        assertTrue(lc.addDriver(2,"name2",LicenseType.C1,CoolingLevel.fridge));
        assertTrue(lc.removeDriver(1));
        assertTrue(lc.removeDriver(2));
        assertFalse(lc.removeDriver(1));
    }

    @Test
    public void addProduct() {
        assertTrue(lc.addProduct("milk"));
        assertTrue(lc.addProduct("soy milk"));
        assertTrue(lc.addProduct("cheese"));
        assertFalse(lc.addProduct("milk"));
    }

    @Test
    public void addBranch() {
        Branch b = new Branch("address1","0000000","name1","south");
        lc.addBranch(b);
        assertEquals(1, lc.getBranches().size());
    }

    @Test
    public void addSupplier() {
    }

    @Test
    public void storeProducts() {
    }

    @Test
    public void loadProducts() {
    }

    @Test
    public void replaceTruck() {
    }

    @Test
    public void unloadProducts() {
    }

    @Test
    public void replaceOrDropSite() {
    }

    @Test
    public void getSites() {
    }
}

