/*package BuisnessLayerTests;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.Employee;
import UtilSuper.PositionType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShiftControllerTest {
    private ShiftController shiftController;

    @BeforeEach
    void setUp() {
        shiftController = new ShiftController();
        shiftController.init(new HashMap<>(), new HashMap<>());
    }



    @Test
    void testApproveShift() {
        String date = "2023-04-07";
        boolean shiftType = true;
        Shift morningShift = new Shift(date, PositionType.MANAGER, shiftType);
        shiftController.init(new HashMap<>(){{put(date, new ArrayList<Shift>(){{add(morningShift); add(null);}});}}, new HashMap<>());

        String expected = "Shift is approved";
        String actual = shiftController.approveShift(date, shiftType);
        assertEquals(expected, actual);

        morningShift.addNewSubmittedPositionByEmpoyee(new Employee(), true, new ArrayList<PositionType>(){{add(PositionType.CASHIER);}});
        expected = "Illegal shift - Not all positions have employee";
        actual = shiftController.approveShift(date, shiftType);
        assertEquals(expected, actual);
    }

    @Test
    void testApproveShiftAnyWay() {
        String date = "2023-04-07";
        boolean shiftType = true;
        Shift morningShift = new Shift(date, PositionType.MANAGER, shiftType);
        shiftController.init(new HashMap<>(){{put(date, new ArrayList<Shift>(){{add(morningShift); add(null);}});}}, new HashMap<>());

        shiftController.approveShiftAnyWay(date, shiftType);
        assertTrue(morningShift.isApproved());
    }

    @Test
    void testShiftHistory() {
        String date = "2023-04-07";
        boolean shiftType = true;
        Shift morningShift = new Shift(date, PositionType.MANAGER, shiftType);
        shiftController.init(new HashMap<>(){{put(date, new ArrayList<Shift>(){{add(morningShift); add(null);}});}}, new HashMap<>());

        String expected = "";
        String actual = shiftController.shiftHistory(date, shiftType);
        assertEquals(expected, actual);

        Employee employee = new Employee();
        morningShift.addNewSubmittedPositionByEmpoyee(employee, true, new ArrayList<PositionType>(){{add(PositionType.CASHIER);}});
        expected = String.format("Submitted employees for shift on %s, %s shift:%n%s", date, shiftType ? "Morning" : "Evening", employee.toString());
        actual = shiftController.shiftHistory(date, shiftType);
        assertEquals(expected, actual);
    }

    @Test
    void testSubmitShift() {
        String date = "2023-04-07";
        boolean shiftType = true;
        Shift*/
