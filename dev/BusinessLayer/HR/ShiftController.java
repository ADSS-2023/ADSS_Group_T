package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftController {
    private HashMap<String, ArrayList<Shift>> shifts;
    private HashMap<String, ArrayList<Shift>> approvedShifts;
    private HashMap<Integer,Employee> employeesMapper;

    private int shiftId;

    public ShiftController(){
        shifts = new HashMap<>();
        shiftId = 0;
    }

    public void init( HashMap<String, ArrayList<Shift>> shifts, HashMap<Integer,Employee> employeesMapper){
        this.shifts = shifts;
       this.employeesMapper = employeesMapper;
    }



    public String showShiftStatus(String date, boolean shiftType){
        Shift shift = shiftType ? shifts.get(date).get(0) : shifts.get(date).get(1);
        return shift.showShiftStatus();
    }


    public String approveShift(String date, boolean shiftType) {
        Shift shift = shiftType ? shifts.get(date).get(0) : shifts.get(date).get(1);
        String isApproved = shift.isLegalShift();
        if (isApproved.equals("0")) {
            int num = shiftType ? 0 : 1 ;
            ArrayList<Shift> approvedShiftList = approvedShifts.getOrDefault(date, new ArrayList<>(2));
            approvedShiftList.add(num , shift);
            approvedShifts.put(date, approvedShiftList);
            shift.setApproved(true);
        }
        return isApproved;
    }

    public void approveShiftAnyWay(String date, boolean shiftType) {
        Shift shift = shiftType ? shifts.get(date).get(0) : shifts.get(date).get(1);
        int num = shiftType ? 0 : 1 ;
        ArrayList<Shift> approvedShiftList = approvedShifts.getOrDefault(date, new ArrayList<>(2));
        approvedShiftList.add(num , shift);
        approvedShifts.put(date, approvedShiftList);
        shift.setApproved(true);
    }

    public  String shiftHistory(String date, boolean shiftType) {
        int num = shiftType ? 0 : 1 ;
        return shifts.get(date).get(num).shiftHistory();
    }

//    public void addSubmittedShift(Constraint cons) {
//        if (shifts.containsKey(date)) {
//            Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
//            shift.addEmployeeRequirements(requirements);
//        }
//        else
//            throw new IllegalArgumentException("wrong date");
//    }

/*
    public void DeleteShift(String date, String shiftType) {
        if (shifts.containsKey(date)) {
            ArrayList<Shift> shiftList = shifts.get(date);
            int index = shiftType.equals("Morning") ? 0 : 1;
            shiftList.set(index, null); // set the shift to null to delete it

        }
    }*/

    public void submitShift(int id, String date, boolean shiftType, boolean isTemp) {
        Employee employee =    employeesMapper.get(id);
        employee.addSubmittedShift(date, shiftType, isTemp);
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        shift.addNewSubmittedPositionByEmpoyee( employeesMapper.get(id), isTemp, employee.getListOfQualifiedPositions());

    }

    public String assignEmployeeForShift(int id, String date, boolean shiftType, String positionType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignEmployeeForShift(positionType, employeesMapper.get(id));
        return st;
    }

    public String assignAll(String date, boolean shiftType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignAll();
        return st;
    }

        public void addRequirements(HashMap<String, Integer> requirements, String date, boolean shiftType) {
            if (shifts.containsKey(date)) {
                Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
                shift.addEmployeeRequirements(requirements);
            }
            else
                throw new IllegalArgumentException("wrong date");
        }



        public List<Shift> getShiftsByDateAndShiftType(String date, String shiftType) {
        List<Shift> shiftsList = new ArrayList<Shift>();
        if (shifts.containsKey(date)) {
            ArrayList<Shift> shiftList = shifts.get(date);
            int index = shiftType.equals("Morning") ? 0 : 1;
            Shift shift = shiftList.get(index);
            if (shift != null) {
                shiftsList.add(shift);
            }
        }
        return shiftsList;
    }
}