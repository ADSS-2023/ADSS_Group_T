package BusinessLayer.HR;

import BusinessLayer.HR.User.UserController;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DalService.*;
import DataLayer.Util.DAO;
import junit.framework.TestCase;
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
public class UserControllerTest extends TestCase {
    private DalEmployeeService dalEmployeeService;
    private DalUserService dalUserService;

    private DalDriverService dalDriverService;
    private DAO dao;
    private EmployeeController employeeController;
    private UserController userController;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        this.dalUserService = new DalUserService(connection);
        this.dalEmployeeService = new DalEmployeeService(connection,dalUserService);
        employeeController = new EmployeeController(dalEmployeeService , dalUserService);
        this.dalDriverService = new DalDriverService(connection,dalUserService);

        this.userController = new UserController(employeeController,null,null,dalUserService);
        this.dao = new DAO(connection);
        dao.deleteTableDataWithTableName("User");
    }
    @Test
    public void testLogin() throws Exception {

    }
    @AfterEach
    public void deleteChanges () throws SQLException {
        dao.deleteTableDataWithTableName("User");
        dao.deleteTableDataWithTableName("QualifiedPosition");
    }

    @Test
    public void testLoginWithoutPreData() {
        Exception exception = Assert.assertThrows(SQLException.class, () -> {
            userController.login(1,"1");
        });
        String expectedMessage = "no employee with that id found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }






}
