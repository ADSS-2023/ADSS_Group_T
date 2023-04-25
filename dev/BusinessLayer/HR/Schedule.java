/*
package BusinessLayer.HR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<String, Map<String, List<Employee>>> schedule; // Map<date, Map<shiftType, List<Employee>>>

    public Schedule() {
        this.schedule = new HashMap<>();
    }

    public void assignEmployee(String date, String shiftType, Employee employee) {
        schedule.computeIfAbsent(date, k -> new HashMap<>()).computeIfAbsent(shiftType, k -> new ArrayList<>()).add(employee);
    }

    public void removeEmployee(String date, String shiftType, Employee employee) {
        if (schedule.containsKey(date) && schedule.get(date).containsKey(shiftType)) {
            schedule.get(date).get(shiftType).remove(employee);
        }
    }

    public List<Employee> getEmployees(String date, String shiftType) {
        return schedule.getOrDefault(date, new HashMap<>()).getOrDefault(shiftType, new ArrayList<>());
    }
}
*/
