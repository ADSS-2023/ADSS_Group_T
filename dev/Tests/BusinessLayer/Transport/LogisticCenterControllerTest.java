package BusinessLayer.Transport;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import DataLayer.Util.DAO;
import ServiceLayer.Transport.SupplierService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class LogisticCenterControllerTest {

    private LogisticCenterController logisticCenterController;
    private DalLogisticCenterService dalLogisticCenterService;
    private DalDeliveryService dalDeliveryService;


    @BeforeEach
    void setUp() throws Exception {
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        dalLogisticCenterService = new DalLogisticCenterService(connection);
        dalDeliveryService = new DalDeliveryService(connection,dalLogisticCenterService);
        dalLogisticCenterService.deleteAllData();
        Data_init.initBasicData(new DAO(connection));
        Data_init.initTrucks(new DAO(connection));
        Data_init.initSites(new DAO(connection));
        SupplierController supplierController = new SupplierController(dalDeliveryService);
        logisticCenterController = new LogisticCenterController(dalLogisticCenterService);
        SupplierService supplierService = new SupplierService(supplierController);
        // Supplier 1 - Butcher Shop
        LinkedHashMap<String,Integer> productMap1 = new LinkedHashMap<String, Integer>();
        productMap1.put("Product A", 2);
        productMap1.put("Product B", 2);
        productMap1.put("Product C", 1);
        supplierService.addProducts("s1", productMap1);
    }

    @AfterEach
    void tearDown() throws SQLException {
        dalLogisticCenterService.deleteAllData();
    }

    @Test
    void storeProducts() throws Exception {
        // Set up test data
        LinkedHashMap<Product, Integer> newSupply = new LinkedHashMap<>();
        Product p1 = dalDeliveryService.findProduct("Product A");
        Product p2 = dalDeliveryService.findProduct("Product B");
        newSupply.put(p1, 10);
        newSupply.put(p2, 20);

        // Call the function being tested
        logisticCenterController.storeProducts(newSupply);

        // Assert that the products have been stored correctly
        LinkedHashMap<Product, Integer> productsInStock = logisticCenterController.getProductsInStock();
        assertEquals(2, productsInStock.size());
        assertEquals(10, productsInStock.values().toArray()[0]);
        assertEquals(20, productsInStock.values().toArray()[1]);
    }

    @Test
    void addTruck() throws Exception {
        // Call the function being tested
        boolean result = logisticCenterController.addTruck(12345, "Model A", 5000, 10000, 2);

        // Assert that the truck has been added successfully
        assertTrue(result);

        // Assert that the truck can be retrieved
        Truck truck = logisticCenterController.getTruck(12345);
        assertEquals("Model A", truck.getModel());
    }

    @Test
    void getAllTrucks() throws Exception {
        LinkedHashMap<Integer, Truck> trucks = logisticCenterController.getAllTrucks();
        assertEquals(9, trucks.size());
    }
  
    public void testGetProductsInStock() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            logisticCenterController.getProductsInStock();
        });
        assertEquals("no products in stock", exception.getMessage());
    }
}