package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import org.junit.Before;
import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

public class LogisticsCenterTest {

    private LogisticsCenter lc;

    @Before
    public void setUp() {
        lc = new LogisticsCenter();
    }

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
        assertNull(lc.skipDay());
        orderDelivery();
        ArrayList<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(0);
        assertEquals(expectedResult,lc.skipDay());
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
        assertTrue(lc.addBranch(new Branch("address1","0000000","name1","south")));
        assertTrue(lc.addBranch(new Branch("address2","0000000","name1","south")));
        assertFalse(lc.addBranch(new Branch("address1","0000000","name1","south")));
        assertEquals(2, lc.getBranches().size());
    }

    @Test
    public void addSupplier() {
        Product p = new Product("milk");
        ArrayList<Product> arr = new ArrayList<>();
        arr.add(p);
        assertTrue(lc.addSupplier(new Supplier("address1","0000000","name1", CoolingLevel.fridge),arr));
        assertTrue(lc.addSupplier(new Supplier("address2","0000000","name1", CoolingLevel.fridge),arr));
        assertFalse(lc.addSupplier(new Supplier("address2","0000000","name1", CoolingLevel.fridge),arr));
        assertEquals(2, lc.getSuppliers().size());
    }

    @Test
    public void storeProducts() {
        assertTrue(lc.getProductsInStock().isEmpty());
        Product p1 = new Product("milk");
        Product p2 = new Product("cheese");
        LinkedHashMap<Product,Integer> products = new LinkedHashMap<>();
        products.put(p1,500);
        products.put(p2,800);
        lc.storeProducts(products);
        assertEquals(2,lc.getProductsInStock().size());
    }

    @Test
    public void loadProductsFromStock() {
        Product p1 = new Product("milk");
        Product p2 = new Product("cheese");
        LinkedHashMap<Product,Integer> productsToStore = new LinkedHashMap<>();
        productsToStore.put(p1,500);
        productsToStore.put(p2,800);
        lc.storeProducts(productsToStore);
        LinkedHashMap<Product,Integer> productsToLoad = new LinkedHashMap<>();
        productsToLoad.put(p1,200);
        productsToLoad.put(p2,400);
        assertTrue((lc.loadProductsFromStock(productsToLoad)).isEmpty());
        assertEquals(2,lc.getProductsInStock().size());
        productsToLoad.put(p1,200);
        productsToLoad.put(p2,400);
        assertTrue((lc.loadProductsFromStock(productsToLoad)).isEmpty());
        assertEquals(1,lc.getProductsInStock().size());
        productsToLoad.put(p1,200);
        productsToLoad.put(p2,400);
        LinkedHashMap<Product,Integer> unavailableProducts = lc.loadProductsFromStock(productsToLoad);
        LinkedHashMap<Product,Integer> compare = new LinkedHashMap<>();
        compare.put(p1,100);
        compare.put(p2,400);
        assertEquals(compare,unavailableProducts);
    }

    @Test
    public void replaceTruck() {
        lc.addTruck(1,"kia",6000,9000,LicenseType.C,CoolingLevel.fridge);
        lc.addTruck(2,"kia",6000,9000,LicenseType.C,CoolingLevel.fridge);
        orderDelivery();
        assertFalse(lc.replaceTruck(0));
        lc.addTruck(3,"kia",6000,20000,LicenseType.C,CoolingLevel.freezer);
        assertFalse(lc.replaceTruck(0));
        lc.addTruck(4,"kia",6000,20000,LicenseType.C,CoolingLevel.fridge);
        assertTrue(lc.replaceTruck(0));
    }

    @Test
    public void getSites() {
        assertTrue(lc.addBranch(new Branch("address1","0000000","name1","south")));
        assertTrue(lc.addBranch(new Branch("address2","0000000","name1","south")));
        Product p = new Product("milk");
        ArrayList<Product> arr = new ArrayList<>();
        arr.add(p);
        assertTrue(lc.addSupplier(new Supplier("address1","0000000","name1", CoolingLevel.fridge),arr));
        assertTrue(lc.addSupplier(new Supplier("address2","0000000","name1", CoolingLevel.fridge),arr));
        assertEquals(4,lc.getSites().size());
    }

    @Test
    public void orderDelivery() {
        Branch b1 = new Branch("address1","0000000","name1","south");
        Supplier s1 = new Supplier("address1","0000000","name1", CoolingLevel.fridge);
        Product p1 = new Product("milk");
        lc.addTruck(1,"skoda",8000,15000,LicenseType.C,CoolingLevel.fridge);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.fridge);
        lc.addBranch(b1);
        LinkedHashMap<Product,Integer> supplier1Products = new LinkedHashMap<>();
        supplier1Products.put(p1,750);
        ArrayList<Product> productsOfSupplier1 = new ArrayList<>();
        productsOfSupplier1.add(p1);
        lc.addSupplier(s1,productsOfSupplier1);
        LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> suppliers = new LinkedHashMap<>();
        suppliers.put(s1,supplier1Products);
        LinkedHashMap<Supplier,Integer> suppliersWeight = new LinkedHashMap<>();
        suppliersWeight.put(s1,8000);
        LocalDate date = LocalDate.now();
        assertNull(lc.orderDelivery(b1,suppliers,date.plusDays(2),suppliersWeight));
    }

    @Test
    public void orderDeliveryWhenNotAllProductsSchedule() {
        Branch b1 = new Branch("address1","0000000","name1","south");
        Supplier s1 = new Supplier("address1","0000000","name1", CoolingLevel.fridge);
        Supplier s2 = new Supplier("address1","0000000","name1", CoolingLevel.freezer);
        Product p1 = new Product("milk");
        Product p2 = new Product("ice-cream");
        lc.addTruck(1,"skoda",8000,15000,LicenseType.C,CoolingLevel.fridge);
        lc.addDriver(1,"name1",LicenseType.C,CoolingLevel.fridge);
        lc.addBranch(b1);
        LinkedHashMap<Product,Integer> supplier1Products = new LinkedHashMap<>();
        LinkedHashMap<Product,Integer> supplier2Products = new LinkedHashMap<>();
        supplier1Products.put(p1,750);
        supplier2Products.put(p2,500);
        ArrayList<Product> productsOfSupplier1 = new ArrayList<>();
        ArrayList<Product> productsOfSupplier2 = new ArrayList<>();
        productsOfSupplier1.add(p1);
        productsOfSupplier2.add(p2);
        lc.addSupplier(s1,productsOfSupplier1);
        lc.addSupplier(s2,productsOfSupplier2);
        LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> suppliers = new LinkedHashMap<>();
        suppliers.put(s1,supplier1Products);
        suppliers.put(s2,supplier2Products);
        LinkedHashMap<Supplier,Integer> suppliersWeight = new LinkedHashMap<>();
        suppliersWeight.put(s1,5000);
        suppliersWeight.put(s2,2000);
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> expectedResult = new LinkedHashMap<>();
        expectedResult.put(s2,supplier2Products);
        assertEquals(expectedResult,lc.orderDelivery(b1,suppliers,LocalDate.now(),suppliersWeight));
    }
}

