package TestCheck;

import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TruckDAOTest {

    private final String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB .db";

    private TruckDAO dao;
    Connection conn;

    @Before
    public void setUp() throws SQLException {
        conn = DriverManager.getConnection(testDBUrl);
        dao = new TruckDAO(conn);
        // Insert TruckDTO objects into Truck table
        TruckDTO truckDTO1 = new TruckDTO( 1, "Model1", 1000, 2000, "A", "HIGH");
        TruckDTO truckDTO2 = new TruckDTO( 2, "Model2", 2000, 3000, "B", "MEDIUM");
        TruckDTO truckDTO3 = new TruckDTO( 3, "Model3", 3000, 4000, "C", "LOW");

        dao.insert(truckDTO1);
        dao.insert(truckDTO2);
        dao.insert(truckDTO3);
    }

    @After
    public void tearDown() throws SQLException {
        // Delete all TruckDTO objects from Truck table
        TruckDTO truckDTO1 = new TruckDTO( 1, "Model1", 1000, 2000, "A", "HIGH");
        TruckDTO truckDTO2 = new TruckDTO( 2, "Model2", 2000, 3000, "B", "MEDIUM");
        TruckDTO truckDTO3 = new TruckDTO( 3, "Model3", 3000, 4000, "C", "LOW");

        dao.delete(truckDTO1);
        dao.delete(truckDTO2);
        dao.delete(truckDTO3);

        conn.close();
    }

    @Test
    public void testFindAll() throws Exception {
        TruckDAO truckDAO = new TruckDAO(conn);

        ArrayList<TruckDTO> truckDTOs = truckDAO.findAll("Truck",TruckDTO.class);

        Assert.assertEquals("There should be 3 trucks in the database", 3, truckDTOs.size());

        TruckDTO truck1 = truckDTOs.get(0);
        Assert.assertEquals("Truck 1 should have license number 1", 1, truck1.getLicenseNumber());
        Assert.assertEquals("Truck 1 should have model F150", "Model1", truck1.getModel());
        Assert.assertEquals("Truck 1 should have weight 2000", 1000, truck1.getWeight());
        Assert.assertEquals("Truck 1 should have max weight 3000", 2000, truck1.getMaxWeight());
        Assert.assertEquals("Truck 1 should have license type C", "A", truck1.getLicenseType());
        Assert.assertEquals("Truck 1 should have cooling level HIGH", "HIGH", truck1.getCoolingLevel());

        // Repeat for truck2 and truck3
        // ...

    }

    @Test
    public void testFind() throws Exception {
        TruckDAO truckDAO = new TruckDAO(conn);

        TruckDTO truckDTO = truckDAO.find(1, "licenseNumber","Truck",TruckDTO.class);

        Assert.assertNotNull("Truck with license number 1 should exist in the database", truckDTO);
        Assert.assertEquals("Truck with license number 12345 should have model F150", "Model1", truckDTO.getModel());
        Assert.assertEquals("Truck with license number 12345 should have weight 2000", 1000, truckDTO.getWeight());
        Assert.assertEquals("Truck with license number 12345 should have max weight 3000", 2000, truckDTO.getMaxWeight());
        Assert.assertEquals("Truck with license number 12345 should have license type C", "A", truckDTO.getLicenseType());
        Assert.assertEquals("Truck with license number 12345 should have cooling level HIGH", "HIGH", truckDTO.getCoolingLevel());

        TruckDTO truckDTO2 = truckDAO.find(99999, "licenseNumber","Truck",TruckDTO.class);
        Assert.assertNull("Truck with license number 99999 should not exist in the database", truckDTO2);
    }

}