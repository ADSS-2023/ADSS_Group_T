package BuisnessLayerTests;

import BusinessLayer.HR.*;
import UtilSuper.PositionType;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTests {
    private Employee employee;

    @BeforeEach
    public void setUp() {
        List<PositionType> qualifiedPositions = new ArrayList<>();
        qualifiedPositions.add(PositionType.cashier);
        employee = new Employee("Omer Tarshish", "123456789", qualifiedPositions, "14.03.2023", 1, "password");
    }

    @Test
    public void testAddSubmittedShift_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addSubmittedShift("14.03.2023", true, false);
            employee.addSubmittedShift("14.03.2023", true, false);
        });
    }

    @Test
    public void testAddSubmittedShift_ShouldAddToSubmittedShifts() {
        employee.addSubmittedShift("14.03.2023", true, false);
        Map<String, List<Constraint>> submittedShifts = employee.getSubmittedShifts();
        assertEquals(1, submittedShifts.size());
        assertTrue(submittedShifts.containsKey("14.03.2023"));
        assertEquals(1, submittedShifts.get("14.03.2023").size());
        Constraint constraint = submittedShifts.get("14.03.2023").get(0);
        assertEquals("14.03.2023", constraint.getDate());
        assertTrue(constraint.getShiftType());
        assertFalse(constraint.isTemp());
    }

    @Test
    public void testAddAssignedShift_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addSAssignShifts("14.03.2023", true, PositionType.cashier.name());
        });
    }

    @Test
    public void testAddAssignedShift_ShouldAddToAssignedShiftsAndRemovedFromSubmittedShifts() {
        employee.addSubmittedShift("14.03.2023", true, false);
        employee.addSAssignShifts("14.03.2023", true, PositionType.cashier.name());
        Map<String, List<Constraint>> assignedShifts = employee.getAssignedShifts();
        Map<String, List<Constraint>> submittedShifts = employee.getSubmittedShifts();
        assertEquals(1, assignedShifts.size());
        assertTrue(assignedShifts.containsKey("14.03.2023"));
        assertEquals(1, assignedShifts.get("14.03.2023").size());
        Constraint constraint = assignedShifts.get("14.03.2023").get(0);
        assertEquals("14.03.2023", constraint.getDate());
        assertTrue(constraint.getShiftType());
        assertFalse(constraint.isTemp());
        assertTrue(submittedShifts.isEmpty());
    }


    @Test
    public void testGetSubmittedShiftByPosition_ShouldReturnNull() {
        assertNull(employee.getSubmittedShiftByPosition("14.03.2023", true, PositionType.cashier.name()));
    }

    @Test
    public void testGetSubmittedShiftByPosition_ShouldReturnConstraint() {
        employee.addSubmittedShift("14.03.2023", true, false);
        Constraint constraint = employee.getSubmittedShiftByPosition("14.03.2023", true, "");
        assertNotNull(constraint);
        assertEquals("14.03.2023", constraint.getDate());
        assertTrue(constraint.getShiftType());
        assertFalse(constraint.isTemp());
    }

    @Test
    public void testAddRestriction_ShouldAddNewDateAndShiftTypeToRestrictions() {
        employee.addRestriction("14.03.2023", true);
        assertTrue(employee.getShiftsRestriction().containsKey("14.03.2023"));
        assertTrue(employee.getShiftsRestriction().get("14.03.2023").contains(true));
        assertFalse(employee.getShiftsRestriction().get("14.03.2023").contains(false));

        employee.addRestriction("14.03.2023", false);
        assertTrue(employee.getShiftsRestriction().containsKey("14.03.2023"));
        assertTrue(employee.getShiftsRestriction().get("14.03.2023").contains(true));
        assertTrue(employee.getShiftsRestriction().get("14.03.2023").contains(false));

        employee.addRestriction("14.03.2023", true);
        assertTrue(employee.getShiftsRestriction().containsKey("14.03.2023"));
        assertTrue(employee.getShiftsRestriction().get("14.03.2023").contains(true));
        assertTrue(employee.getShiftsRestriction().get("14.03.2023").contains(false));
    }
    @Test
    public void testIsLegalPosition_ShouldReturnTrueForQualifiedPosition() {

        // Check that isLegalPosition returns the correct value
        assertTrue(employee.isLeagalPosition("cashier"));
        assertFalse(employee.isLeagalPosition("manager"));
        assertFalse(employee.isLeagalPosition("cleaner"));
    }


  /*  @Test
    public void testGetListOfSubmittedConstraints_ShouldReturnConcatenatedString() {
        employee.addSubmittedShift("2022-01-01", true, false);
        employee.addSubmittedShift("2022-01-02", false, false);
        String actual = employee.getListOfSubmittedConstraints();
        String expected = "Date: 2022-01-01 Type: morning\n" +
                "Date: 2022-01-02 Type: evening";
        assertEquals(expected, actual);
    }*/


   /* @Test
    public void testGetListOfAssignedConstraints_ShouldReturnConcatenatedString() {
        employee.addSubmittedShift("2022-01-01", true, false);
        employee.addSAssignShifts("2022-01-01", true, PositionType.cashier.name());
        String expected = "2022-01-01, morning shift, not temporary, assigned to: CASHIER";
        assertEquals(expected, employee.getListOfQualifiedPositions());
    }*/

/*    @Test
    public void testToString_ShouldReturnEmployeeInfo() {
        String expected = "Name: John\nID: 123456789\nQualified Positions: [CASHIER, BAKER]\n" +
                "Date Hired: 2022-01-01\nNumber of Shifts Worked: 1\n";
        assertEquals(expected, employee.toString());
    }*/

   /* @Test
    public void testGetters() {
        assertEquals("John", employee.getEmployeeName());
        assertEquals("1" ,   System.out.print(employee.getId()));
        List<PositionType> qualifiedPositions = new ArrayList<>();
        qualifiedPositions.add(PositionType.cashier);
        qualifiedPositions.add(PositionType.cashier);
        assertEquals(qualifiedPositions, employee.getListOfQualifiedPositions());
        assertEquals("2022-01-01", employee.getJoiningDay());
    }*/

   /* @Test
    public void testEquals() {
        List<PositionType> qualifiedPositions = new ArrayList<>();
        qualifiedPositions.add(PositionType.cashier);
        qualifiedPositions.add(PositionType.general_worker);
        Employee employee2 = new Employee("John", "123456789", qualifiedPositions, "2022-01-01", 1, "password");
        assertEquals(employee, employee2);
        Employee employee3 = new Employee("Jane", "123456789", qualifiedPositions, "2022-01-01", 1, "password");
        assertNotEquals(employee, employee3);
        Employee employee4 = new Employee("John", "987654321", qualifiedPositions, "2022-01-01", 1, "password");
        assertNotEquals(employee, employee4);
        Employee employee5 = new Employee("John", "123456789", new ArrayList<>(), "2022-01-01", 1, "password");
        assertNotEquals(employee, employee5);
        Employee employee6 = new Employee("John", "123456789", qualifiedPositions, "2023-01-01", 1, "password");
        assertNotEquals(employee, employee6);
    }*/

  /*  @Test
    public void testHashCode() {
        List<PositionType> qualifiedPositions = new ArrayList<>();
        qualifiedPositions.add(PositionType.cashier);
        qualifiedPositions.add(PositionType.general_worker);
        Employee employee2 = new Employee("John", "123456789", qualifiedPositions, "2022-01-01", 1, "password");
        assertEquals(employee.hashCode(), employee2.hashCode());
    }*/

}
