

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import BusinessLayer.HR.Employee;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee employee;
    private LocalDate testDate;
    private String testBranch;
    private int testEmployeeId;


    @Before
    public void setUp() {
        employee = new Employee(1, "John Doe", "123456789", "description", 1000, LocalDate.now(), "password", UserType.employee);
        testDate = LocalDate.now();
        testBranch = "test branch";
        testEmployeeId = 2;
    }

    @Test
    public void testAddSubmittedShift1() {
        // Test adding a shift when there are no existing shifts
        employee.addSubmittedShift(testBranch, testEmployeeId, testDate, true);
        List<Constraint> shifts = employee.getSubmittedShifts().get(testDate);
        assertEquals(1, ((List<?>) shifts).size());
        assertEquals(testBranch, shifts.get(0).getBranch());
        assertEquals(testEmployeeId, shifts.get(0).getEmployeeId());
        assertTrue(shifts.get(0).getShiftType());
    }

    @Test
    public void testAddSubmittedShift2() {
        employee.addSubmittedShift(testBranch, testEmployeeId, testDate, true);
        // Test adding a shift for the same date and shift type as an existing shift
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addSubmittedShift(testBranch, testEmployeeId, testDate, true);
        });
    }

    @Test
    public void testAssignShift1() throws Exception {
        // Test assigning a shift when there are no existing shifts
        assertThrows(IllegalArgumentException.class, () -> {
            employee.assignShift(testBranch, testDate, true, PositionType.cashier);
        });
    }

    @Test
    public void testAssignShift2() throws Exception {
        // Test assigning a shift when there are existing shifts, but the employee is already assigned to a shift on the same day
        List<Constraint> shifts = new ArrayList<>();
        shifts.add(new Constraint(testBranch, testEmployeeId, testDate, true));
        shifts.add(new Constraint(testBranch, testEmployeeId, testDate, false));
        employee.getSubmittedShifts().put(testDate, shifts);
        shifts.get(0).setAssignedPosition(PositionType.cashier);
        assertThrows(IllegalArgumentException.class, () -> {
            employee.assignShift(testBranch, testDate, false, PositionType.cashier);
        });
    }

    @Test
    public void testAssignShiftExceedWeeklyLimit() throws Exception {
        // set up employee and submitted shifts
        LocalDate startDate = LocalDate.now().with(DayOfWeek.SUNDAY);
        for (int i = 0; i < 6; i++) {
            employee.addSubmittedShift(testBranch, employee.getId(), startDate.plusDays(i), true);
            employee.assignShift(testBranch, startDate.plusDays(i), true, PositionType.cashier);
        }

        // assign another shift for the employee
        LocalDate nextWeek = startDate.plusWeeks(1);
        try {
            employee.addSubmittedShift(testBranch, employee.getId(), startDate.plusDays(6), true);
            employee.assignShift(testBranch, startDate.plusDays(6), true, PositionType.cashier);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Employee has already worked six shifts this week.", e.getMessage());
        }
    }

    @Test
    public void testAssignShift4() throws Exception {
        // Test assigning a shift when there are existing shifts and the employee is not already assigned to a shift on the same day and has not worked six shifts this week
        List<Constraint> shifts = new ArrayList<>();
        employee.addSubmittedShift(testBranch, testEmployeeId, testDate, true);
        employee.assignShift(testBranch, testDate, true, PositionType.cashier);
        shifts = employee.getSubmittedShifts().get(testDate);
        assertEquals(PositionType.cashier, shifts.get(0).getAssignedPosition());
    }
    @Test
    public void testAddQualification() {
        employee.addQualification("cashier");
        assertTrue(employee.getQualifiedPositions().contains(PositionType.cashier.name()));
    }

    @Test
    public void testAddQualificationWithInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addQualification("INVALID_POSITION");
        });
    }

    @Test
    public void testIsLegalPositionWithValidPosition() {
        assertTrue(employee.isLeagalPosition("cashier"));
    }

    @Test
    public void testIsLegalPositionWithInvalidPosition() {;
        assertFalse(employee.isLeagalPosition("INVALID_POSITION"));
    }



}
