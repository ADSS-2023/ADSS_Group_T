package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;

public class MangerService {
    private EmployeeController ec;
    public ShiftController shiftController;
    public MangerService(ShiftController shiftController){}

}
