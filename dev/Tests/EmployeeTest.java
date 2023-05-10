

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
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

//    @Test
//    public void testAddSubmittedShift1() throws SQLException {
//        // Test adding a shift when there are no existing shifts
//        employee.addSubmittedShift(testBranch,  testDate, true);
//        Constraint shifts = employee.getSubmittedShifts().get(testDate);
//        assertEquals(testBranch, shifts.getBranch());
//        assertEquals(testEmployeeId, shifts.getEmployeeId());
//        assertTrue(shifts.getShiftType());
//    }

    @Test
    public void testAddSubmittedShift2() throws SQLException {
        employee.addSubmittedShift(testBranch,  testDate, true);
        // Test adding a shift for the same date and shift type as an existing shift
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addSubmittedShift(testBranch, testDate, true);
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
    public void testAssignShiftExceedWeeklyLimit() throws Exception {
        // set up employee and submitted shifts
        LocalDate startDate = LocalDate.now().with(DayOfWeek.SUNDAY);
        for (int i = 0; i < 6; i++) {
            employee.addSubmittedShift(testBranch,  startDate.plusDays(i), true);
            employee.assignShift(testBranch, startDate.plusDays(i), true, PositionType.cashier);
        }

        // assign another shift for the employee
        LocalDate nextWeek = startDate.plusWeeks(1);
        try {
            employee.addSubmittedShift(testBranch,  startDate.plusDays(6), true);
            employee.assignShift(testBranch, startDate.plusDays(6), true, PositionType.cashier);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Employee has already worked six shifts this week.", e.getMessage());
        }
    }

//    @Test
//    public void testAssignShift4() throws Exception {
//        // Test assigning a shift when there are existing shifts and the employee is not already assigned to a shift on the same day and has not worked six shifts this week
//        Constraint shifts;
//        employee.addSubmittedShift(testBranch,  testDate, true);
//        employee.assignShift(testBranch, testDate, true, PositionType.cashier);
//        shifts = employee.getSubmittedShifts().get(testDate);
//        assertEquals(PositionType.cashier, shifts.getAssignedPosition());
//    }
    @Test
    public void testAddQualification() throws SQLException {
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
