package BuisnessLayerTests;

import BusinessLayer.HR.EmployeeController;
import UtilSuper.PositionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        employeeController = new EmployeeController();
        List<PositionType> pt1 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);
        pt1.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker1","123456",pt1,"11.03.2023",1 , "1",true);

        List<PositionType> pt2 = new ArrayList<PositionType>();
        pt2.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker2","123456",pt2,"11.01.2021",2 , "2",false);

        List<PositionType> pt3 = new ArrayList<PositionType>();
        pt3.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker3","123456",pt3,"12.02.2022",3 , "3",false);

        List<PositionType> pt4 = new ArrayList<PositionType>();
        pt4.add(PositionType.cleaning);
        employeeController.addNewEmployee("Worker4","123456",pt4,"12.02.2022",4 , "4",false);

        List<PositionType> pt5 = new ArrayList<PositionType>();
        pt5.add(PositionType.general_worker);
        employeeController.addNewEmployee("Worker5","123456",pt5,"12.02.2022",5 , "5",false);

        List<PositionType> pt6 = new ArrayList<PositionType>();
        pt6.add(PositionType.orderly);
        pt6.add(PositionType.general_worker);
        employeeController.addNewEmployee("Worker6","123456",pt6,"12.02.2022",6 , "6",false);

        List<PositionType> pt7 = new ArrayList<PositionType>();
        pt7.add(PositionType.security);
        pt7.add(PositionType.general_worker);
        employeeController.addNewEmployee("Worker7","123456",pt7,"12.02.2022",7 , "7",false);

        List<PositionType> pt8 = new ArrayList<PositionType>();
        pt8.add(PositionType.storekeeper);
        employeeController.addNewEmployee("Worker8","123456",pt8,"12.02.2022",8 , "8",false);
    }

    @Test
    public void testAddNewEmployee() {
        List<PositionType> pt9 = new ArrayList<PositionType>();
        pt9.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker9","123456",pt9,"11.03.2023",9 , "9",true);
        Employee e = employeeController.getEmployeeById(9);
        assertEquals("Worker9", e.getName());
        assertEquals("123456", e.getPassword());
        assertEquals(pt9, e.getPositions());
        assertEquals("11.03.2023", e.getStartingDate());
        assertEquals(9, e.getId());
        assertEquals("9", e.getPhone());
        assertTrue(e.isAvailable());
    }

    @Test
    public void testRemoveEmployee() {
        employeeController.removeEmployee(3);
        assertNull(employeeController.getEmployeeById(3));
    }

    @Test
    public void testUpdateEmployeePosition() {
        employeeController.updateEmployeePosition(1, PositionType.cashier);
        Employee e = employeeController.getEmployeeById(1);
        assertTrue(e.getPositions
