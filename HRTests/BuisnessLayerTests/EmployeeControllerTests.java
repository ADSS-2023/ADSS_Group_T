package BuisnessLayerTests;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {
    EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController();
    }

    @Test
    void testAddNewEmployee() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        assertEquals("John Doe", employeeController.getEmployee(1).getEmployeeName());
    }

    @Test
    void testInitEmployeeController() {
        ShiftController shiftController = new ShiftController();
        employeeController.initEmployeeConroller(shiftController);
        assertSame(shiftController, employeeController.shiftController);
    }

    @Test
    void testAddRestrictionToAll() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        employeeController.addRestrictionToall("2022-01-01", true);
        assertTrue(employeeController.getEmployee(1).getShiftsRestriction().get("2022-01-01").contains(true));
    }

    @Test
    void testGetListOfSubmittedConstraints() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        assertEquals("", employeeController.getListOfSubmittedConstraints(1));
    }

    @Test
    void testGetListOfAssignedShifts() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        assertEquals("", employeeController.getListOfAssignedShifts(1));
    }

    @Test
    void testAddQualification() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        employeeController.addQualification(1, "cashier");
        assertTrue(employeeController.getEmployee(1).getListOfQualifiedPositions().contains("cashier"));
    }

    @Test
    void testGetEmployeesMapper() {
        assertNotNull(employeeController.getEmployeesMapper());
    }

    @Test
    void testGetEmployee() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", false);
        assertSame(employeeController.getEmployee(1), employeeController.getEmployeesMapper().get(1));
    }

    @Test
    void testLogin() {
        employeeController.addNewEmployee("John Doe", "123456789", new ArrayList<>(), "2022-01-01", 1, "password", true);
        assertTrue(employeeController.login(1, "password"));
        assertThrows(IllegalArgumentException.class, () -> employeeController.login(2, "password"));
        assertThrows(IllegalArgumentException.class, () -> employeeController.login(1, "wrongpassword"));
    }
}


