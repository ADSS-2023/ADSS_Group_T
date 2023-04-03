package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftController {
    private HashMap<String, ArrayList<Shift>> shifts;
    private int shiftId;

    public ShiftController(){
        shifts = new HashMap<>();
        shiftId = 0;
    }

    public void loadDataShiftController(HashMap<String, ArrayList<Shift>> shifts){
        this.shifts = shifts;
    }



    public String approveShift (String date, boolean shiftType) {
        Shift shift = shiftType ? shifts.get(date).get(0) : shifts.get(date).get(1);
        return shift.isLegalShift();
    }




    public void DeleteShift(String date, String shiftType) {
        if (shifts.containsKey(date)) {
            ArrayList<Shift> shiftList = shifts.get(date);
            int index = shiftType.equals("Morning") ? 0 : 1;
            shiftList.set(index, null); // set the shift to null to delete it
        }
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
