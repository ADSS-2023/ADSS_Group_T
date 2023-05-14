package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class BranchControllerTest extends TestCase {
    private BranchController branchController;
    private DalDeliveryService dalDeliveryService;

    private DalLogisticCenterService dalLogisticCenterService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        dalLogisticCenterService = new DalLogisticCenterService(connection);
        dalDeliveryService = new DalDeliveryService(connection, dalLogisticCenterService);
        this.branchController = new BranchController(dalDeliveryService);
    }

    public void tearDown() throws Exception {
       dalDeliveryService.deleteAllData();
    }

    public void testAddBranch() throws SQLException {
        // create test data
        String branchAddress = "Test Branch Address";
        String telNumber = "123456789";
        String contactName = "Test Contact Name";
        int x = 1;
        int y = 2;

        // call the method under test
        branchController.addBranch(branchAddress, telNumber, contactName, x, y);

        // check if the branch was added successfully
        assertTrue(branchController.getAllBranches().containsKey(branchAddress));
    }

    public void testGetBranch() throws SQLException {
        // create test data
        String branchAddress = "Test Branch Address";
        String telNumber = "123456789";
        String contactName = "Test Contact Name";
        int x = 1;
        int y = 2;

        // add a branch to the system
        Branch branch = new Branch(branchAddress, telNumber, contactName, x, y);
        dalDeliveryService.addBranch(branchAddress, telNumber, contactName, x, y);

        // call the method under test
        Branch result = branchController.getBranch(branchAddress);

        // check if the returned branch is correct
        assertEquals(branch.address, result.address);
    }


    @Test
    public void testAddBranchWithDuplicateAddress() throws SQLException {
        // try to add the second branch with the same address
        try {
            // create test data
            String branchAddress = "Test Branch Address";
            String telNumber = "123456789";
            String contactName = "Test Contact Name";
            int x = 1;
            int y = 2;

            // add the first branch
            branchController.addBranch(branchAddress, telNumber, contactName, x, y);
            branchController.addBranch(branchAddress, telNumber, contactName, x, y);
            fail("Expected an SQLException to be thrown");
        } catch (Exception e) {
            assertEquals("branch address is taken", e.getMessage());
        }
    }






}