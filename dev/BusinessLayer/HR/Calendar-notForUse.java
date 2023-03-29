package BusinessLayer.HR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private HashMap<String, List<Shift>> schedule; // Map<date, List<shiftType>>>  shiftType[0] = "morning", shiftType[0] = "evening"


    public Calendar() {
        this.schedule = new HashMap<String, List<Shift>>();
    }

    public Map<String, List<Shift>> getschedule() {
        return schedule;
    }

    public void assignEmployee(String date, String shiftType, Employee employee) {
        // Check if the employee is already assigned to a shift at the given date and time
        for (Employee e : schedule.getOrDefault(date, new HashMap<>()).getOrDefault(shiftType, new ArrayList<>())) {
            if (e == employee) {
                throw new IllegalArgumentException("Employee " + employee.getEmployeeName() + " is already assigned to shift " + shiftType + " at " + date);
            }
        }

        // Add the employee to the list of employees assigned to the given shift
        schedule.computeIfAbsent(date, k -> new HashMap<>()).computeIfAbsent(shiftType, k -> new ArrayList<>()).add(employee);
    }
}




}
