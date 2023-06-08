package BusinessLayer.HR;

import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserController;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DalService.*;
import DataLayer.Util.DAO;
import UtilSuper.ServiceFactory;
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
        Connection connection = ServiceFactory.makeCon();
        this.dalUserService = new DalUserService(connection);
        this.dalEmployeeService = new DalEmployeeService(connection,dalUserService);
        employeeController = new EmployeeController(dalEmployeeService , dalUserService);
        this.dalDriverService = new DalDriverService(connection,dalUserService);
        User HRuser = new User(1,"HRManeger","123456","cool",1000,null,"1", UserType.HRManager);
        User TRuser = new User(2,"TrManeger","123456","cool",1000,null,"2", UserType.TransportManager);
        this.userController = new UserController(employeeController,TRuser,HRuser,dalUserService);
        this.dao = new DAO(connection);
        dao.deleteTableDataWithTableName("User");
    }
    @Test
    public void testLoginHR() throws Exception {
        UserType u = userController.login(1,"1");
        Assert.assertTrue(u.toString().equals(UserType.HRManager.toString()));
    }
    @Test
    public void testLoginTM() throws Exception {
        UserType u = userController.login(2,"2");
        Assert.assertTrue(u.toString().equals(UserType.TransportManager.toString()));
    }
    @Test
    public void testLoginWithoutPreData() {
        Exception exception = Assert.assertThrows(NoSuchFieldException.class, () -> {
            userController.login(11,"11");
        });
        String expectedMessage = "";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }






}
