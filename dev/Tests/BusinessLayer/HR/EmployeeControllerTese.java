//package BusinessLayer.HR;
//
//import BusinessLayer.HR.User.PositionType;
//import BusinessLayer.HR.User.UserType;
//import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
//import DataLayer.HR_T_DAL.DalService.DalShiftService;
//import DataLayer.HR_T_DAL.DalService.DalUserService;
//import org.junit.Test;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.HashMap;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//public class EmployeeControllerTese {
//    @Test
//    public void testAddNewEmployee() throws Exception {
//        EmployeeController employeeController = new EmployeeController(new DalEmployeeService(), new DalUserService());
//        employeeController.addNewEmployee(1, "John", "1234567890", "Manager", 10000, LocalDate.now(), "password123", UserType.ADMIN);
//
//        Employee employee = employeeController.getEmployeeById(1);
//        assertEquals("John", employee.getEmployeeName());
//        assertEquals(10000, employee.getSalary());
//    }
//    @Test(expected = IllegalArgumentException.class)
//    public void testAddQualificationToNonExistingEmployee() throws SQLException {
//        EmployeeController employeeController = new EmployeeController(new DalEmployeeService(), new DalUserService());
//        employeeController.addQualification(1, "Manager");
//    }
//
//    @Test(expected = Exception.class)
//    public void testAddEmployeeWithExistingId() throws Exception {
//        EmployeeController employeeController = new EmployeeController(new DalEmployeeService(), new DalUserService());
//        employeeController.addNewEmployee(1, "John", "1234567890", "Manager", 10000, LocalDate.now(), "password123", UserType.ADMIN);
//        employeeController.addNewEmployee(1, "Mike", "0987654321", "Clerk", 5000, LocalDate.now(), "password456", UserType.STANDARD);
//    }
//
//
//
//}
