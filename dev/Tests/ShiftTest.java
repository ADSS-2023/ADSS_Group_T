import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.User.HRManager;
import BusinessLayer.HR.User.UserType;
import org.junit.Before;
import org.junit.Test;

public class ShiftTest {
    private Shift shift;
    private Employee cashier1;
    private Employee cashier2;
    private Employee chef1;
    private Employee chef2;
    private Employee chef3;
    private Employee shiftManager;

    @Before
    public void setUp() {
        shift = new Shift("Branch 1", LocalDate.now(), true);
        Employee cashier1 = new Employee(1, "chashier1", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee cashier2 = new Employee(1, "chashier2", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef1 = new Employee(1, "chashier3", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef2 = new Employee(1, "chashier4", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef3 = new Employee(1, "chashier5", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee shiftManager = new Employee(1, "shiftManager", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);

    @Test
    public void testAddEmployeeRequirements() {
        HashMap<String, Integer> requirements = new HashMap<>();
        requirements.put("Manager", 1);
        requirements.put("Cashier", 2);
        requirements.put("Chef", 3);
        shift.addEmployeeRequirements(requirements);

        HashMap<String, Integer> expectedRequirements = new HashMap<>();
        expectedRequirements.put("Manager", 1);
        expectedRequirements.put("Cashier", 2);
        expectedRequirements.put("Chef", 3);

        assertEquals(expectedRequirements, shift.getEmployeesRequirement());
    }

    @Test
    public void testIsLegalShift1() {
        // No manager assigned
        HashMap<String, Integer> requirements = new HashMap<>();
        shift.addEmployeeRequirements(requirements);
        assertEquals("Noticed- the shift must have a manager!!!\n", shift.isLegalShift());
    }


    @Test
    public void testIsLegalShift2() {

        // All requirements fulfilled
        HashMap<String, Integer> requirements = new HashMap<>();
        requirements.put("Cashier", 2);
        requirements.put("Chef", 3);
        shift.addEmployeeRequirements(requirements);



        HashMap<String, HashMap<Employee, Boolean>> submittedPositions = new HashMap<>();
        submittedPositions.put("Cashier", new HashMap<>());
        submittedPositions.put("Chef", new HashMap<>());
        submittedPositions.put("Shift Manager", new HashMap<>());

        submittedPositions.get("Cashier").put(cashier1, true);
        submittedPositions.get("Cashier").put(cashier2, true);
        submittedPositions.get("Chef").put(chef1, true);
        submittedPositions.get("Chef").put(chef2, true);
        submittedPositions.get("Chef").put(chef3, true);
        submittedPositions.get("Shift Manager").put(shiftManager, true);

        shift.setSubmittedPositionByEmployees(submittedPositions);

        assertEquals("All the requirements of the employees in the shift are fulfilled", shift.isLegalShift());

    }


    @Test
    public void testIsLegalShift3() {
        // Some requirements not fulfilled
        Employee cashier1 = new Employee(1, "chashier1", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee cashier2 = new Employee(1, "chashier2", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef1 = new Employee(1, "chashier3", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef2 = new Employee(1, "chashier4", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        Employee chef3 = new Employee(1, "chashier5", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        HashMap<String, Integer> requirements = new HashMap<>();
        requirements.put("Cashier", 2);
        requirements.put("Chef", 3);
        shift.addEmployeeRequirements(requirements);

        HashMap<String, HashMap<Employee, Boolean>> submittedPositions = new HashMap<>();
        submittedPositions.put("Cashier", new HashMap<>());
        submittedPositions.put("Chef", new HashMap<>());

        submittedPositions.get("Cashier").put(cashier1, true);
        submittedPositions.get("Chef").put(chef1, true);
        submittedPositions.get("Chef").put(chef2, true);

        shift.setSubmittedPositionByEmployees(submittedPositions);

        String expectedMessage = "1 employees are missing in the position of Cashier\n"
                + "1 employees are missing in the position of Chef\n";
        assertEquals(expectedMessage, shift.isLegalShift());
    }
}
