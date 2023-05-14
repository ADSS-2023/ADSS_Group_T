package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BranchControllerTest extends TestCase {
    private BranchController branchController;
    private DalDeliveryService dalDeliveryService;

    private DalLogisticCenterService dalLogisticCenterService;
    public void setUp() throws Exception {
        super.setUp();
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        dalLogisticCenterService = new DalLogisticCenterService(connection);
        dalDeliveryService = new DalDeliveryService(connection, dalLogisticCenterService);
        this.branchController = new BranchController(dalDeliveryService);
    }

    public void tearDown() throws Exception {
       // dalDeliveryService.deleteAllData();
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
        dalDeliveryService.insertBranch(branch);

        // call the method under test
        Branch result = branchController.getBranch(branchAddress);

        // check if the returned branch is correct
        assertEquals(branch, result);
    }


    public void testGetAllBranches() throws SQLException {
//        // create test data
//        String branchAddress1 = "Test Branch Address 1";
//        String telNumber1 = "123456789";
//        String contactName1 = "Test Contact Name 1";
//        int x1 = 1;
//        int y1 = 2;
//
//        String branchAddress2 = "Test Branch Address 2";
//        String telNumber2 = "987654321";
//        String contactName2 = "Test Contact Name 2";
//        int x2 = 3;
//        int y2 = 4;
//
//        // add branches to the system
//        Branch branch1 = new Branch(branchAddress1, telNumber1, contactName1, x1, y1);
//        dalDeliveryService.insertBranch(branch1);
//        branches.put(branchAddress1, branch1);
//
//        Branch branch2 = new Branch(branchAddress2, telNumber2, contactName2, x2, y2);
//        dalDeliveryService.insertBranch(branch2);
//        branches.put(branchAddress2, branch2);
//
//        // call the method under test
//        LinkedHashMap<String, Branch> result = branchController.getAllBranches();
//
//        // check if the returned map is correct
//        assertEquals(branches, result);
    }






}