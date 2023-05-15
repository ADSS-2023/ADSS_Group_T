package BusinessLayer.HR;

import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DalService.*;
import DataLayer.Util.DAO;
import UtilSuper.Pair;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class DriverControllerTest extends TestCase {
    private DalEmployeeService dalEmployeeService;
    private DalUserService dalUserService;

    private DalDriverService dalDriverService;
    private DAO dao;
    private DriverController driverController;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        this.dalUserService = new DalUserService(connection);
        this.dalEmployeeService = new DalEmployeeService(connection,dalUserService);
        this.dalDriverService = new DalDriverService(connection,dalUserService);
        driverController = new DriverController(dalDriverService);
        this.dao = new DAO(connection);
        dao.deleteTableDataWithTableName("Driver");
    }
    @Test
    public void testAddNewDriver() throws Exception {
        driverController.addDriver(84,"shimon","124515","good worker",15151,"2020-05-14","84",UserType.driver,"C",2);
        Driver driver = driverController.lazyLoadDriver(84);
        assertEquals("shimon", driver.getEmployeeName());
        assertEquals(15151, driver.getSalary());
    }
    @AfterEach
    public void deleteChanges () throws SQLException {
        dao.deleteTableDataWithTableName("Driver");
    }
    @Test
    public void testAddRequirment() throws SQLException {
        driverController.addDriverRequirement(LocalDate.now(), Driver.LicenseType.C1, Driver.CoolingLevel.freezer);
        String s = driverController.getRequirementsByDate(LocalDate.now());
        Assert.assertNotEquals(s,"");
    }
    public void testDeleteRequirmentNotWorking() {
        Exception exception = Assert.assertThrows(NullPointerException.class, () -> {
            Pair p = new Pair(Driver.LicenseType.C1, Driver.CoolingLevel.freezer);
            driverController.deleteRequirement(p,LocalDate.now().plusDays(7));
        });
        String expectedMessage = "";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetNonExistingEmployeeById() {
        Exception exception = Assert.assertThrows(SQLException.class, () -> {
            driverController.getDriverById(15151);
        });
        String expectedMessage = "no driver with that id found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
