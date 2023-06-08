package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import UtilSuper.ServiceFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class SupplierControllerTest {

    SupplierController supplierController;
    DalDeliveryService dalDeliveryService;

    @BeforeEach
    void setUp() throws SQLException {
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = ServiceFactory.makeCon();
        dalDeliveryService = new DalDeliveryService(connection,new DalLogisticCenterService(connection));
        supplierController = new SupplierController(dalDeliveryService);
        dalDeliveryService.deleteAllData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        dalDeliveryService.deleteAllData();
    }

    @Test
    void addSupplier() throws SQLException {
        // Arrange
        String supplierAddress = "123 Main St";
        String telNumber = "555-555-5555";
        String contactName = "John Doe";
        int x = 10;
        int y = 20;

        // Act
        supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);

        // Assert
        assertNotNull(supplierController.getSupplier(supplierAddress));
    }

    @Test
    void testAddSupplierWithExistingAddress() throws SQLException {
        // Arrange
        String supplierAddress = "123 Main St";
        String telNumber = "555-555-5555";
        String contactName = "John Doe";
        int x = 10;
        int y = 20;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
            supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
        });
    }

    @Test
    void getAllSuppliers() throws SQLException {
        String supplierAddress = "123 Main St";
        String telNumber = "555-555-5555";
        String contactName = "John Doe";
        int x = 10;
        int y = 20;
        supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
        supplierAddress = "124 Main St";
        telNumber = "222-222-2222";
        contactName = "Bob Dilan";
        x = 30;
        y = 40;
        supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
        supplierAddress = "125 Main St";
        telNumber = "333-333-3333";
        contactName = "Jon Snow";
        x = 50;
        y = 60;
        supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
        // Act
        LinkedHashMap<String, Supplier> suppliers = supplierController.getAllSuppliers();

        // Assert
        assertEquals(3, suppliers.size());
    }

    @Test
    void getSupplier() throws SQLException {
        // Arrange
        addSupplier();
        String supplierAddress = "123 Main St";

        // Act
        Supplier supplier = supplierController.getSupplier(supplierAddress);

        // Assert
        assertNotNull(supplier);
        assertEquals(supplierAddress, supplier.getAddress());
    }

    @Test
    void testGetSupplierWithNonExistentAddress() throws SQLException {
        // Arrange
        addSupplier();
        String supplierAddress = "456 Second St";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            supplierController.getSupplier(supplierAddress);
        });
    }

    @Test
    void getSupplierProducts() throws SQLException {
        // Arrange
        addProductsToSupplier();
        String supplierAddress = "123 Main St";

        // Act
        LinkedHashMap<String, Product> products = supplierController.getSupplierProducts(supplierAddress);

        // Assert
        assertNotNull(products);
        assertEquals(4, products.size());
    }

    @Test
    void addProductsToSupplier() throws SQLException {
        // Arrange
        addSupplier();
        String supplierAddress = "123 Main St";
        LinkedHashMap<String, Integer> productMap = new LinkedHashMap<>();
        productMap.put("Water", 0);
        productMap.put("fanta", 0);
        productMap.put("Milk", 1);
        productMap.put("Cheese", 1);

        // Act
        supplierController.addProductsToSupplier(supplierAddress, productMap);

        // Assert
        assertEquals(4, supplierController.getSupplierProducts(supplierAddress).size());
    }
}