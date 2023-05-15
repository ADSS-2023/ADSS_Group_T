package BusinessLayer.HR_Transport;
import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.ShiftController;
import BusinessLayer.HR.User.UserController;
import BusinessLayer.Transport.BranchController;
import BusinessLayer.Transport.DeliveryController;
import BusinessLayer.Transport.LogisticCenterController;
import BusinessLayer.Transport.SupplierController;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import DataLayer.HR_T_DAL.DalService.*;
import DataLayer.Util.DAO;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import ServiceLayer.UserService;
import UtilSuper.Time;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class OrderDeliveryTest {
    private ShiftController shiftController;
    private ShiftService shiftService;
    private EmployeeController employeeController;
    private EmployeeService employeeService;
    private LogisticCenterController logisticCenterController;
    private LogisticCenterService logisticCenterService;
    private DeliveryController deliveryController;
    private DeliveryService deliveryService;
    private UserService userService;
    private UserController userController;
    private BranchService branchService;
    private BranchController branchController;
    private SupplierService supplierService;
    private SupplierController supplierController;
    private Connection connection;
    private DalLogisticCenterService dalLogisticCenterService;
    private DalDeliveryService dalDeliveryService;
    private DalUserService dalUserService;
    private DalEmployeeService dalEmployeeService;

    private DalShiftService dalShiftService;
    private DalDriverService dalDriverService;
    private DAO dao;
    private Shift shift;
    private DriverController driverController;

    @Before
    public void setUp() throws Exception {

        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        this.dao = new DAO(connection);
        dao.deleteAllDataFromDatabase();
        Data_init.initBasicData(dao);

        dalLogisticCenterService = new DalLogisticCenterService(connection);
        dalDeliveryService = new DalDeliveryService(connection, dalLogisticCenterService);
        dalUserService = new DalUserService(connection);
        dalDriverService = new DalDriverService(connection, dalUserService);
        dalEmployeeService = new DalEmployeeService(connection, dalUserService);
        dalShiftService = new DalShiftService(connection, dalEmployeeService);
        employeeController = new EmployeeController(dalEmployeeService, dalUserService);
        driverController = new DriverController(dalDriverService);
        branchController = new BranchController(dalDeliveryService);
        shiftController = new ShiftController(this.driverController, this.dalEmployeeService, this.dalShiftService, this.branchController, this.employeeController.getEmployeesMapper());
        employeeService = new EmployeeService(employeeController, driverController, shiftController);

        logisticCenterController = new LogisticCenterController(dalLogisticCenterService);
        logisticCenterService = new LogisticCenterService(logisticCenterController);
        branchService = new BranchService(branchController);
        supplierController = new SupplierController(dalDeliveryService);
        supplierService = new SupplierService(supplierController);
        shiftController = new ShiftController(driverController, dalEmployeeService, dalShiftService, branchController, employeeController.getEmployeesMapper());
        shiftService = new ShiftService(shiftController);
        deliveryController = new DeliveryController(logisticCenterController, supplierController, branchController, driverController, shiftController, dalDeliveryService);
        deliveryService = new DeliveryService(deliveryController);


        Data_init.initSites(dao);
        Data_init.initTrucks(dao);
        Data_init.initSupplierProducts(supplierService);

        Data_init_HR.initBasicData(dao,shiftService);
        Data_init_HR.initOldData(dao,employeeService,shiftService,employeeController,shiftController,dalShiftService);
    }
    @Test
    public void orderDelivery() throws SQLException {
        String branch1 = "b1";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("Beef", 5);
        supplierProducts1.put("Pork", 10);
        supplierProducts1.put("Chicken", 20);
        products1.put("s1", supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("Bread", 8);
        supplierProducts2.put("Cupcake", 5);
        products1.put("s2", supplierProducts2);
        String date1 = Time.localDateToString(LocalDate.now().plusDays(2));
        try {
            deliveryController.orderDelivery(branch1, products1, date1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, deliveryController.getAllDeliveries().size());
        driverController.getMinRequirementsCoolingLevelByDate(LocalDate.now().plusDays(2));
        int numOfDriversReq =  driverController.getMinRequirementsLicenseLevelByDate(LocalDate.now().plusDays(2));
        assertEquals(2,numOfDriversReq);



    }

//    @Test
//    public void testAddBranch() throws SQLException {
//        branchController.addBranch("testBranch", "testBranchAddress", "testBranchPhone",1,1);
//        assertTrue(branchController.getAllBranches().containsKey("testBranch"));
//        assertEquals(1, branchController.getAllBranches().size());
//        }
//    @Test
//    public void testAddSupplier() throws SQLException {
//        supplierController.addSupplier("testSupplier", "testSupplier", "testSupplierPhone",1,1);
//        assertTrue(supplierController.getAllSuppliers().containsKey("testSupplier"));
//        assertEquals(1, supplierController.getAllSuppliers().size());
//    }



}


