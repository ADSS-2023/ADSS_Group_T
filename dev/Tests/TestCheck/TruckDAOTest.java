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
import java.util.ArrayList;

public class TruckDAOTest {

    private final String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB .db";

    Connection conn;

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Before
    public void setUp() throws Exception {
        conn = DriverManager.getConnection(testDBUrl);
        // Insert TruckDTO objects into Truck table
        TruckDTO truckDTO1 = new TruckDTO("Truck", 1, "Model1", 1000, 2000, "A", "HIGH");
        TruckDTO truckDTO2 = new TruckDTO("Truck", 2, "Model2", 2000, 3000, "B", "MEDIUM");
        TruckDTO truckDTO3 = new TruckDTO("Truck", 3, "Model3", 3000, 4000, "C", "LOW");

        String insertSql = "INSERT INTO Truck (licenseNumber, model, weight, maxWeight, licenseType, coolingLevel) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, truckDTO1.getLicenseNumber());
            stmt.setString(2, truckDTO1.getModel());
            stmt.setInt(3, truckDTO1.getWeight());
            stmt.setInt(4, truckDTO1.getMaxWeight());
            stmt.setString(5, truckDTO1.getLicenseType());
            stmt.setString(6, truckDTO1.getCoolingLevel());
            stmt.executeUpdate();

            stmt.setInt(1, truckDTO2.getLicenseNumber());
            stmt.setString(2, truckDTO2.getModel());
            stmt.setInt(3, truckDTO2.getWeight());
            stmt.setInt(4, truckDTO2.getMaxWeight());
            stmt.setString(5, truckDTO2.getLicenseType());
            stmt.setString(6, truckDTO2.getCoolingLevel());
            stmt.executeUpdate();

            stmt.setInt(1, truckDTO3.getLicenseNumber());
            stmt.setString(2, truckDTO3.getModel());
            stmt.setInt(3, truckDTO3.getWeight());
            stmt.setInt(4, truckDTO3.getMaxWeight());
            stmt.setString(5, truckDTO3.getLicenseType());
            stmt.setString(6, truckDTO3.getCoolingLevel());
            stmt.executeUpdate();
        }
    }



    @Test
    public void testFindAll() throws Exception {
        TruckDAO truckDAO = new TruckDAO(conn);

        ArrayList<TruckDTO> truckDTOs = truckDAO.findAll("Truck",TruckDTO.class);

        Assert.assertEquals("There should be 3 trucks in the database", 3, truckDTOs.size());

        TruckDTO truck1 = truckDTOs.get(0);
        Assert.assertEquals("Truck 1 should have license number 12345", 12345, truck1.getLicenseNumber());
        Assert.assertEquals("Truck 1 should have model F150", "F150", truck1.getModel());
        Assert.assertEquals("Truck 1 should have weight 2000", 2000, truck1.getWeight());
        Assert.assertEquals("Truck 1 should have max weight 3000", 3000, truck1.getMaxWeight());
        Assert.assertEquals("Truck 1 should have license type C", "C", truck1.getLicenseType());
        Assert.assertEquals("Truck 1 should have cooling level HIGH", "HIGH", truck1.getCoolingLevel());

        // Repeat for truck2 and truck3
        // ...

    }

    @Test
    public void testFind() throws Exception {
        TruckDAO truckDAO = new TruckDAO(conn);

        TruckDTO truckDTO = truckDAO.find(12345,"Truck",TruckDTO.class);

        Assert.assertNotNull("Truck with license number 12345 should exist in the database", truckDTO);
        Assert.assertEquals("Truck with license number 12345 should have model F150", "F150", truckDTO.getModel());
        Assert.assertEquals("Truck with license number 12345 should have weight 2000", 2000, truckDTO.getWeight());
        Assert.assertEquals("Truck with license number 12345 should have max weight 3000", 3000, truckDTO.getMaxWeight());
        Assert.assertEquals("Truck with license number 12345 should have license type C", "C", truckDTO.getLicenseType());
        Assert.assertEquals("Truck with license number 12345 should have cooling level HIGH", "HIGH", truckDTO.getCoolingLevel());

        TruckDTO truckDTO2 = truckDAO.find(99999,"Truck",TruckDTO.class);
        Assert.assertNull("Truck with license number 99999 should not exist in the database", truckDTO2);
    }

}