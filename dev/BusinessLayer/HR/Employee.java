package BusinessLayer.HR;


import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Employee extends User {


    private List<PositionType> qualifiedPositions;
    private Map<LocalDate, List<Constraint>> submittedShifts; // date, list[0]=morningShift list[1]=eveningShift

    private String description;
    private int salary;


    public Employee(int id, String employeeName, String bankAccount, String description, int salary, LocalDate joiningDay, String password, UserType userType) {
        super(id, employeeName, bankAccount, description, salary, joiningDay, password, userType);
        this.qualifiedPositions = new ArrayList<>();
        this.submittedShifts = new LinkedHashMap<>();
      /*  this.shiftsRestriction = new LinkedHashMap<>();*/
        description = null;
    }

    /**
     * Adds a submitted shift to the employee's submittedShifts map, after checking if it is legal
     *
     * @param branch - the branch ID of the shift
     * @param employeeId - the ID of the employee who submits the shift
     * @param date - the date of the shift
     * @param shiftType - the shift type of the shift
     * @throws IllegalArgumentException if the shift is restricted according to shiftsRestriction map, or already exists in the submittedShifts map
     */
    /**
     * Add a new submitted shift for the employee.
     *
     * @param branch     The branch ID where the shift is submitted.
     * @param employeeId The ID of the employee submitting the shift.
     * @param date       The date of the shift.
     * @param shiftType  The type of the shift (true for morning, false for evening).
     * @throws IllegalArgumentException If the shift cannot be submitted due to a restriction or existing shift.
     */
    public void addSubmittedShift(String branch, int employeeId, LocalDate date, boolean shiftType) {
        // Check if a shift for the same date and shift type already exists
        if (submittedShifts.containsKey(date)
                && submittedShifts.get(date).stream().anyMatch(c -> c.getShiftType() == shiftType)) {
            throw new IllegalArgumentException("You have been already submit to that shift.");
        }
        // Add the new constraint to the list of submitted shifts
        else {
            Constraint cons = new Constraint(branch, employeeId, date, shiftType);
            List<Constraint> constraints = submittedShifts.get(date);
            if (constraints == null) {
                constraints = new ArrayList<>();
                constraints.add(cons);
                submittedShifts.put(date, constraints);
            } else {
                constraints.add(cons);
            }
        }
    }



    public void assignShift(String branch, LocalDate date, boolean shiftType, PositionType positionType) throws Exception{
        List<Constraint> shifts = submittedShifts.get(date);

        if (shifts == null) {
            throw new IllegalArgumentException("Wrong date");
        }

        if (shifts.stream().anyMatch(c -> c.getAssignedPosition() != null)) {
            throw new IllegalArgumentException("Employee already assigned to a shift on this day.");
        }


        // Requirement 3: An employee cannot work more than six times a week.
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        long assignedShiftsThisWeek = submittedShifts.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(startOfWeek) && !e.getKey().isAfter(endOfWeek))
                .flatMap(e -> e.getValue().stream())
                .filter(c -> c.getAssignedPosition() != null)
                .count();
        if (assignedShiftsThisWeek >= 6) {
            throw new IllegalArgumentException("Employee has already worked six shifts this week.");
        }

        // set the assigned position of the shift
        Constraint constraint =  shiftType ?  shifts.get(0) : shifts.get(1);
        constraint.setAssignedPosition(positionType);
    }


    public String showShiftsStatusByEmployee(LocalDate localDate) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            LocalDate date = localDate.plusDays(i);
            List<Constraint> morningShifts = submittedShifts.getOrDefault(date, new ArrayList<>()).stream()
                    .filter(Constraint::getShiftType)
                    .toList();
            List<Constraint> eveningShifts = submittedShifts.getOrDefault(date, new ArrayList<>()).stream()
                    .filter(c -> !c.getShiftType())
                    .toList();
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", "Date", "Shift Type", "Is Approved", "Assigned Position"));
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", date, "Morning", "", ""));
            for (Constraint c : morningShifts) {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", c.getAssignedPosition() == null ? "No" : "Yes", c.getAssignedPosition()));
            }
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "Evening", "", ""));
            for (Constraint c : eveningShifts) {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", c.getAssignedPosition() == null ? "No" : "Yes", c.getAssignedPosition()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

/*    public String addRestriction(String branch, LocalDate date, boolean isMorning) {

            if (shiftsRestriction.containsKey(branch)) {
                if (shiftsRestriction.get(branch).containsKey(date)) {
                    if (!shiftsRestriction.get(branch).get(date).contains(isMorning)) {
                        shiftsRestriction.get(branch).get(date).add(isMorning);
                    } else {
                        return "Restriction already exists for the given date and shift type.";
                    }
                } else {
                    List<Boolean> shiftTypes = new ArrayList<>();
                    shiftTypes.add(isMorning);
                    shiftsRestriction.get(branch).put(date, shiftTypes);
                }
            } else {
                HashMap<LocalDate, List<Boolean>> dateMap = new HashMap<>();
                List<Boolean> shiftTypes = new ArrayList<>();
                shiftTypes.add(isMorning);
                dateMap.put(date, shiftTypes);
                shiftsRestriction.put(branch, dateMap);
            }
            return "Restriction added successfully.";
    }*/




    public void addQualification(String position) {
        if (! isLeagalPosition(position))
            throw new IllegalArgumentException("The position " + position + " does not exist");
        qualifiedPositions.add(PositionType.valueOf(position));
    }

    public boolean isLeagalPosition(String position) {
       for (PositionType posType : PositionType.values()){
           if (posType.name().equals(position))
                   return true;
       }
       return  false;
    }

    public List<String> getQualifiedPositions() {
        List<String> positionNames = new ArrayList<>();
        for (PositionType position : qualifiedPositions) {
            positionNames.add(position.name());
        }
        return positionNames;
    }

    public Map<LocalDate, List<Constraint>> getSubmittedShifts() {
        return submittedShifts;
    }


    }



