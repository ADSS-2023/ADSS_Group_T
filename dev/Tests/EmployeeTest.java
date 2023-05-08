import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.UserType;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void testAddSubmittedShift() {
        // Arrange
        Employee employee = new Employee(1, "John", "1234", "description", 1000,
                LocalDate.of(2022, 1, 1), "password", UserType.employee);
        LocalDate date = LocalDate.of(2022, 5, 10);

        // Act
        employee.addSubmittedShift("Branch1", 1, date, true);

        // Assert
        assertTrue(employee.getSubmittedShifts().containsKey(date));
        List<Constraint> constraints = employee.getSubmittedShifts().get(date);
        assertEquals(1, constraints.size());
        assertEquals("Branch1", constraints.get(0).getBranch());
        assertEquals(1, constraints.get(0).getEmployeeId());
        assertEquals(true, constraints.get(0).getShiftType());

        // Try to add another morning shift for the same date (should throw an exception)
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addSubmittedShift("Branch2", 2, date, true);
        });

        // Try to add an evening shift for the same date (should be allowed)
        employee.addSubmittedShift("Branch3", 3, date, false);
        constraints = employee.getSubmittedShifts().get(date);
        assertEquals(2, constraints.size());
        assertEquals("Branch1", constraints.get(0).getBranch());
        assertEquals(1, constraints.get(0).getEmployeeId());
        assertEquals(true, constraints.get(0).getShiftType());
        assertEquals("Branch3", constraints.get(1).getBranch());
        assertEquals(3, constraints.get(1).getEmployeeId());
        assertEquals(false, constraints.get(1).getShiftType());
    }
}