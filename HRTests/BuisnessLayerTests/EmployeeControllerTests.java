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
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", false);
        assertEquals("Omer Tarshish", employeeController.getEmployee(1).getEmployeeName());
    }

    @Test
    void testInitEmployeeController() {
        ShiftController shiftController = new ShiftController();
        employeeController.initEmployeeConroller(shiftController);
        assertSame(shiftController, employeeController.shiftController);
    }

    @Test
    void testAddRestrictionToAll() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", false);
        employeeController.addRestrictionToall("14.03.2023", true);
        assertTrue(employeeController.getEmployee(1).getShiftsRestriction().get("14.03.2023").contains(true));
    }

    @Test
    void testGetListOfSubmittedConstraints() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", false);
        assertEquals("", employeeController.getListOfSubmittedConstraints(1));
    }

    @Test
    void testGetListOfAssignedShifts() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", false);
        assertEquals("", employeeController.getListOfAssignedShifts(1));
    }

    @Test
    void testAddQualification() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.20231", 1, "password", false);
        employeeController.addQualification(1, "cashier");
        assertTrue(employeeController.getEmployee(1).getListOfQualifiedPositions().contains("cashier"));
    }

    @Test
    void testGetEmployeesMapper() {
        assertNotNull(employeeController.getEmployeesMapper());
    }

    @Test
    void testGetEmployee() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", false);
        assertSame(employeeController.getEmployee(1), employeeController.getEmployeesMapper().get(1));
    }

    @Test
    void testLogin() {
        employeeController.addNewEmployee("Omer Tarshish", "123456789", new ArrayList<>(), "14.03.2023", 1, "password", true);
        assertTrue(employeeController.login(1, "password"));
        assertThrows(IllegalArgumentException.class, () -> employeeController.login(2, "password"));
        assertThrows(IllegalArgumentException.class, () -> employeeController.login(1, "wrongpassword"));
    }
}


