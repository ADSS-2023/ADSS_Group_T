package Tests;

import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TruckDAOTest {
    private static final String TEST_DB_URL = "jdbc:sqlite:DataLayer/HR_Transport_DB.db";
    private static Connection connection;
    private static TruckDAO truckDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection(TEST_DB_URL);
        truckDAO = new TruckDAO(connection);
        //truckDAO.createTable();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        //truckDAO.dropTable();
        connection.close();
    }

    @BeforeEach
    public void resetTable() throws SQLException {
        //truckDAO.clearTable();
    }

    @Test
    public void testFindAll() throws SQLException {
        // insert some test data
        TruckDTO truck1 = new TruckDTO("trucks", 1, "Volvo", 5000, 10000, "B", "MEDIUM");
        TruckDTO truck2 = new TruckDTO("trucks", 2, "Mercedes", 7500, 12000, "C", "HIGH");
        TruckDTO truck3 = new TruckDTO("trucks", 3, "Scania", 10000, 15000, "C", "LOW");
        truckDAO.insert(truck1);
        truckDAO.insert(truck2);
        truckDAO.insert(truck3);

        // test findAll method
        List<TruckDTO> trucks = truckDAO.findAll("Truck",TruckDTO.class);
        Assertions.assertEquals(3, trucks.size());
        Assertions.assertEquals(truck1.getLicenseNumber(), trucks.get(0).getLicenseNumber());
        Assertions.assertEquals(truck2.getLicenseNumber(), trucks.get(1).getLicenseNumber());
        Assertions.assertEquals(truck3.getLicenseNumber(), trucks.get(2).getLicenseNumber());
    }

    @Test
    public void testFind() throws SQLException {
        // insert some test data
        TruckDTO truck1 = new TruckDTO("trucks", 1, "Volvo", 5000, 10000, "B", "MEDIUM");
        TruckDTO truck2 = new TruckDTO("trucks", 2, "Mercedes", 7500, 12000, "C", "HIGH");
        TruckDTO truck3 = new TruckDTO("trucks", 3, "Scania", 10000, 15000, "C", "LOW");
        truckDAO.insert(truck1);
        truckDAO.insert(truck2);
        truckDAO.insert(truck3);

        // test find method
        TruckDTO foundTruck = truckDAO.find(2,"Truck",TruckDTO.class);
        Assertions.assertNotNull(foundTruck);
        Assertions.assertEquals(truck2.getLicenseNumber(), foundTruck.getLicenseNumber());
        Assertions.assertEquals(truck2.getModel(), foundTruck.getModel());
        Assertions.assertEquals(truck2.getWeight(), foundTruck.getWeight());
        Assertions.assertEquals(truck2.getMaxWeight(), foundTruck.getMaxWeight());
        Assertions.assertEquals(truck2.getLicenseType(), foundTruck.getLicenseType());
        Assertions.assertEquals(truck2.getCoolingLevel(), foundTruck.getCoolingLevel());

        // test find method with non-existing key
        TruckDTO notFoundTruck = truckDAO.find(4,"Truck",TruckDTO.class);
        Assertions.assertNull(notFoundTruck);
    }
}