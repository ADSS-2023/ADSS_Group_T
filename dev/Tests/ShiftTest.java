import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.User.HRManager;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import org.junit.Before;
import org.junit.Test;


public class ShiftTest {
    private Shift shift;
   private Employee cashier1;
   private Employee cashier2;
    private Employee cashier3;
    private Employee storeKeeper1 ;
   private Employee storeKeeper2 ;
    private Employee chef3;
    private Employee shiftManager;

    @Before
    public void setUp() throws SQLException {
        shift = new Shift("Branch 1", LocalDate.now(), true);
        cashier1 = new Employee(1, "chashier1", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        cashier2 = new Employee(1, "chashier2", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        cashier3 = new Employee(1, "chashier2", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        storeKeeper1 = new Employee(1, "storeKeeper1", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        storeKeeper2 = new Employee(1, "storeKeeper2", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        cashier1.addQualification(PositionType.cashier.name());
        cashier2.addQualification(PositionType.cashier.name());
        cashier3.addQualification(PositionType.cashier.name());
        storeKeeper1.addQualification(PositionType.storekeeper.name());
        storeKeeper2.addQualification(PositionType.storekeeper.name());

    }



    @Test
    public void testAddEmployeeRequirements1() throws SQLException {
        // Test adding requirements for a new position
        LinkedHashMap<String, Integer> requirements = new LinkedHashMap<>();
        requirements.put("cashier", 2);
        shift.addEmployeeRequirements(requirements);
        HashMap<String, Integer> expectedRequirements = new HashMap<>();
        expectedRequirements.put("cashier", 2);
        assertEquals(expectedRequirements, shift.getEmployeeRequirements());
    }


    @Test
    public void testAddEmployeeRequirements2() throws SQLException {

        // Test adding requirements for an existing position
        LinkedHashMap<String, Integer> requirements = new LinkedHashMap<>();
        requirements.put("cashier", 1);
        requirements.put("storeKeeper", 2);
        shift.addEmployeeRequirements(requirements);
        HashMap<String, Integer> expectedRequirements = new HashMap<>();
        expectedRequirements.put("cashier", 1);
        expectedRequirements.put("storeKeeper", 2);
        assertEquals(expectedRequirements, shift.getEmployeeRequirements());
    }

    @Test
    public void testAddEmployeeRequirements3() throws SQLException {
        // Test adding requirements for multiple positions
        LinkedHashMap<String, Integer> requirements = new LinkedHashMap<>();
        requirements = new LinkedHashMap<>();
        requirements.put("cleaner", 1);
        requirements.put("securityGuard", 1);
        shift.addEmployeeRequirements(requirements);
        HashMap<String, Integer> expectedRequirements = new LinkedHashMap<>();
        expectedRequirements.put("cleaner", 1);
        expectedRequirements.put("securityGuard", 1);
        assertEquals(expectedRequirements, shift.getEmployeeRequirements());
    }


    @Test
    public void testSubmitShiftForEmployee() throws Exception {
        List<String> positions = new ArrayList<>();
        positions.add(PositionType.cashier.name());
        positions.add(PositionType.storekeeper.name());
        String result = shift.submitShiftForEmployee(cashier1, positions);
        assertEquals("Shift submitted successfully", result);
        LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositions = shift.getSubmittedPositionByEmployees();
        assertTrue(submittedPositions.containsKey(PositionType.cashier.name()));
        assertTrue(submittedPositions.containsKey(PositionType.storekeeper.name()));
        assertTrue(submittedPositions.get(PositionType.cashier.name()).containsKey(cashier1));
        assertTrue(submittedPositions.get(PositionType.storekeeper.name()).containsKey(storeKeeper1));
        assertFalse(submittedPositions.get(PositionType.cashier.name()).get(cashier1));
        assertFalse(submittedPositions.get(PositionType.storekeeper.name()).get(storeKeeper1));
    }





        @Test
        public void testMakeSureThereIsStorekeeperRequirement() throws Exception {
            shift.addEmployeeRequirements(new LinkedHashMap<>());
            shift.makeSureThereIsStorekeeperRequirement();
            HashMap<String, Integer> employeeRequirements = shift.getEmployeeRequirements();
            assertTrue(employeeRequirements.containsKey(PositionType.storekeeper.name()));
            assertEquals(1, (int)employeeRequirements.get(PositionType.storekeeper.name()));
        }



    @Test
    public void testIsLegalShift() throws Exception {
        // Test missing shift manager
        assertEquals("Noticed- the shift must have a manager!!!\n", shift.isLegalShift());

        // Assign a shift manager
        shift.setShiftManagerId(cashier1.getId());



        // Test missing storekeepers
        shift.addEmployeeRequirements(new LinkedHashMap<>() {{
            put(PositionType.storekeeper.name(), 2);
        }});
        shift.submitShiftForEmployee(storeKeeper1, List.of(PositionType.cashier.name()));
        shift.submitShiftForEmployee(storeKeeper2, List.of(PositionType.cashier.name()));
        assertEquals("2 employees are missing in the position of storekeeper\n", shift.isLegalShift());


        // Assign storekeepers
        shift.submitShiftForEmployee(cashier2, List.of(PositionType.storekeeper.name()));

        // Test missing cashier
        assertEquals("2 employees are missing in the position of storekeeper\n", shift.isLegalShift());

    }

    @Test
    public void testAssignEmployeeForShift() throws Exception {
        // Add storekeeper requirement
        shift.makeSureThereIsStorekeeperRequirement();
        shift.getSubmittedPositionByEmployees().put(PositionType.cashier.name(), new LinkedHashMap<>());

        // Test assigning employee to non-existing position
        assertThrows(NoSuchElementException.class, () -> shift.assignEmployeeForShift("invalidPosition", cashier1));

        // Test assigning employee who has not submitted to the position
        assertEquals("chashier1 has not submitted to position cashier yet.", shift.assignEmployeeForShift(PositionType.cashier.name(), cashier1));

        // Submit employees to the position
        shift.submitShiftForEmployee(storeKeeper1, List.of(PositionType.storekeeper.name()));
        shift.submitShiftForEmployee(storeKeeper2, List.of(PositionType.storekeeper.name()));


        storeKeeper1.addSubmittedShift(shift.getBranch(),  shift.getDate(), shift.getShiftType());
        assertEquals("storeKeeper1 has been assigned to position storekeeper successfully.",  shift.assignEmployeeForShift(PositionType.storekeeper.name(), storeKeeper1));


        // Test assigning already assigned employee
        assertEquals("Employee already assigned to this position", shift.assignEmployeeForShift(PositionType.storekeeper.name(), storeKeeper1));

        // Test successful assignment

    }




}
