package BuisnessLayerTests;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.Shift;
import UtilSuper.PositionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {
    private Shift shift;
    private final int shiftId = 1;
    private final String date = "2023-04-09";
    private final int managerID = 123;
    private final boolean shiftType = true;

    @BeforeEach
    void setUp() {
        shift = new Shift(shiftId, managerID, date, shiftType);
    }

    @Test
    void createNewEmployeesRequirement() {
        HashMap<String, Integer> employeeRequirements = shift.createNewEmployeesRequirement();
        assertNotNull(employeeRequirements);
        assertEquals(6, employeeRequirements.size());
        assertNull(employeeRequirements.get(PositionType.cleaning));
        assertNull(employeeRequirements.get(PositionType.cashier));
        assertNull(employeeRequirements.get(PositionType.storekeeper));
        assertNull(employeeRequirements.get(PositionType.security));
        assertNull(employeeRequirements.get(PositionType.orderly));
        assertNull(employeeRequirements.get(PositionType.general_worker));

    }

    @Test
    void createNewFulfillPositionByEmployeesTest() {
        HashMap<String, List<Employee>> fulfillPositionByEmployees = shift.createNewFulfillPositionByEmployees();

        // Check that the fulfillPositionByEmployees HashMap has a key for each PositionType
        assertEquals(PositionType.values().length, fulfillPositionByEmployees.size());
        for (PositionType positionType : PositionType.values()) {
            assertTrue(fulfillPositionByEmployees.containsKey(positionType.name()));
        }

        // Check that the list for each PositionType is initially empty
        for (List<Employee> employees : fulfillPositionByEmployees.values()) {
            assertTrue(employees.isEmpty());
        }
    }

    @Test
    public void testCreateNewSubmittedPositionByEmployees() {
        HashMap<String, List<Employee>> submittedPositionByEmployees = shift.createNewSubmittedPositionByEmployees();
        assertNotNull(submittedPositionByEmployees);
        assertEquals(PositionType.values().length, submittedPositionByEmployees.size());

        for (PositionType positionType : PositionType.values()) {
            assertTrue(submittedPositionByEmployees.containsKey(positionType.name()));
            List<Employee> employees = submittedPositionByEmployees.get(positionType.name());
            assertNotNull(employees);
            assertTrue(employees.isEmpty());
        }
    }

    @Test
    public void testAddEmployeeRequirements() {
        // Create a map of employee requirements for each position type
        HashMap<String, Integer> employeeRequirements = new HashMap<>();
        employeeRequirements.put(PositionType.cleaning.name(), 2);
        employeeRequirements.put(PositionType.cashier.name(), 3);
        employeeRequirements.put(PositionType.storekeeper.name(), 1);
        employeeRequirements.put(PositionType.security.name(), 1);
        employeeRequirements.put(PositionType.orderly.name(), 2);
        employeeRequirements.put(PositionType.general_worker.name(), 3);


        // Add the employee requirements to the shift
        shift.addEmployeeRequirements(employeeRequirements);

        // Check that the employee requirements were added correctly
        HashMap<String, Integer> shiftRequirements = shift.getEmployeesRequirement();
        assertEquals(employeeRequirements, shiftRequirements);
    }

    @Test
    public void testAddNewSubmittedPositionByEmployee() {
        Employee emp1 = new Employee("John", "12345", Arrays.asList(PositionType.cashier), "2022-01-01", 1, "password1");
        Employee emp2 = new Employee("Jane", "67890", Arrays.asList(PositionType.cashier, PositionType.orderly), "2022-01-01", 2, "password2");
        List<String> positions = Arrays.asList(PositionType.cashier.toString(), PositionType.orderly.toString());

        shift.addNewSubmittedPositionByEmpoyee(emp1, true, positions);
        shift.addNewSubmittedPositionByEmpoyee(emp2, true, positions);

        assertEquals(2, shift.getSubmittedPositionByEmployees().get(PositionType.cashier.toString()).size());
        assertEquals(2, shift.getSubmittedPositionByEmployees().get(PositionType.orderly.toString()).size());
        assertTrue(shift.getSubmittedPositionByEmployees().get(PositionType.cashier.toString()).contains(emp1));
        assertTrue(shift.getSubmittedPositionByEmployees().get(PositionType.cashier.toString()).contains(emp2));
        assertTrue(shift.getSubmittedPositionByEmployees().get(PositionType.orderly.toString()).contains(emp2));
    }










}





