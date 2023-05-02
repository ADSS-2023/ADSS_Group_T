package BusinessLayer.HR;

import BusinessLayer.Transport.Driver;
import UtilSuper.PositionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftController {
    private HashMap < String,  HashMap<LocalDate, ArrayList<Shift>> > shifts; // branch, shiftDate, shiftType

    DriverController driverController;

    private HashMap<String, HashMap<Employee, Boolean>> submittedPositionByDriver; // positionType, Empoyee, isAssigned


    private HashMap<Integer,Employee> employeesMapper;


    public ShiftController(){
        shifts = new HashMap<>();
    }

    public void init( HashMap<Integer,Employee> employeesMapper, DriverController driverController){
       this.employeesMapper = employeesMapper;
       this.driverController     = driverController;
    }


    public void ensureStorekeeperRequirements(LocalDate localDate, List<String> branches) {
        driverController.addDriverRequirement(localDate);
        for (String branch : branches) {
            HashMap<LocalDate, ArrayList<Shift>> shiftsBranch = shifts.get(branch);
            ArrayList<Shift> shiftsOnDate = shiftsBranch.get(localDate);
            if (shiftsOnDate != null) {
                for (Shift shift : shiftsOnDate) {
                    if (shift != null) {
                        shift.makeSureThereIsStorekeeperRequirement();
                    }
                }
            }
        }
    }



    public String showShiftStatus(String branchId, LocalDate date, boolean shiftType){
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = shifts.get(branchId);
        Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
        return shift.showShiftStatus();
    }





    public String submitShiftForEmployee(String branch, int id, LocalDate date, boolean shiftType) throws Exception {
        Employee employee =    employeesMapper.get(id);
        employee.addSubmittedShift(branch, id, date, shiftType);
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = shifts.get(branch);
        Shift shift = shiftType ?  branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
        return shift.submitShiftForEmployee( employeesMapper.get(id),  employee.getListOfQualifiedPositions());
    }




    // TODo- fix it
    public String assignEmployeeForShift(int id, String date, boolean shiftType, String positionType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignEmployeeForShift(positionType, employeesMapper.get(id));
        return st;
    }

    public String assignAll(int branch LocalDate date, boolean shiftType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignAll();
        return st;
    }



    public void addRequirements(String branch, LocalDate shiftDate, boolean shiftType, HashMap<String, Integer> requirements) throws IllegalArgumentException {
        if (shifts.containsKey(branch)) {
            HashMap<LocalDate, ArrayList<Shift>> branchShifts = shifts.get(branch);
            if (branchShifts.containsKey(shiftDate)) {
                ArrayList<Shift> shiftList = branchShifts.get(shiftDate);
                Shift shift = shiftList.get(shiftType ? 0 : 1);
                shift.addEmployeeRequirements(requirements);
            } else {
                throw new IllegalArgumentException("No shifts available for the given date");
            }
        } else {
            throw new IllegalArgumentException("Invalid branch ID");
        }
    }


    //todo from Noam Gilad pls
    public void addDirverRequirement(LocalDate requiredDate, Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) {
    }
    //todo from Noam Gilad pls
    public void addStoreKeeperRequirement(LocalDate requiredDate, String address) {
    }
    //todo from Noam Gilad pls
    public ArrayList<String> getBranchesWithoutStoreKeeper(LocalDate tomorrow) {
        return null;
    }
}
