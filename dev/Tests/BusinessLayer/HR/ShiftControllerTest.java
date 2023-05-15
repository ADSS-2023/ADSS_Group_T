//package BusinessLayer.HR;
//
//import BusinessLayer.HR.User.UserType;
//import BusinessLayer.Transport.BranchController;
//import DataLayer.HR_T_DAL.DalService.*;
//import DataLayer.Util.DAO;
//import junit.framework.TestCase;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.AfterEach;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.LinkedHashMap;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//public class ShiftControllerTest extends TestCase {
//    private DalEmployeeService dalEmployeeService;
//    private DalUserService dalUserService;
//    private DalShiftService dalShiftService;
//
//    private DAO dao;
//    private ShiftController shiftController;
//
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
//        Connection connection = DriverManager.getConnection(testDBUrl);
//        this.dalUserService = new DalUserService(connection);
//        this.dalEmployeeService = new DalEmployeeService(connection,dalUserService);
//        this.dalShiftService = new DalShiftService(connection,dalEmployeeService);
//        this.dao = new DAO(connection);
//        shiftController = new ShiftController()
//        dao.deleteTableDataWithTableName("User");
//        dao.deleteTableDataWithTableName("QualifiedPosition");
//        dao.deleteTableDataWithTableName("Shift");
//    }
//    @Test
//    public void testAddNewEmployee() throws Exception {
//        employeeController.addNewEmployee(44, "John", "1234567890", "Manager", 10000, LocalDate.now(), "password123", UserType.employee);
//        Employee employee = employeeController.getEmployeeById(44);
//        assertEquals("John", employee.getEmployeeName());
//        assertEquals(10000, employee.getSalary());
//    }
//    @AfterEach
//    public void deleteChanges () throws SQLException {
//        dao.deleteTableDataWithTableName("User");
//        dao.deleteTableDataWithTableName("QualifiedPosition");
//    }
//
//    @Test
//    public void testAddQualificationToEmployee() throws Exception {
//        employeeController.addNewEmployee(75, "John", "1234567890", "Manager", 10000, LocalDate.now(), "password123", UserType.employee);
//        employeeController.addQualification(75, "cashier");
//        Employee employee = employeeController.getEmployeeById(75);
//        Assert.assertTrue(employee.getQualifiedPositions().contains("cashier"));
//    }
//    @Test
//    public void testGetNonExistingEmployeeById() {
//        Exception exception = Assert.assertThrows(Exception.class, () -> {
//            employeeController.getEmployeeById(8484);
//        });
//        String expectedMessage = "no employee with that id found";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//
//
//}
