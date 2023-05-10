package BusinessLayer.HR;


import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Employee extends User {

    private ArrayList<PositionType> qualifiedPositions;
    private HashMap<LocalDate, Constraint> submittedShifts; // date, list[0]=morningShift list[1]=eveningShift

    private DalEmployeeService dalEmployeeService;




    public Employee(int id, String employeeName, String bankAccount, String description, int salary, LocalDate joiningDay, String password, UserType userType) {
        super(id, employeeName, bankAccount, description, salary, joiningDay, password, userType);
        this.qualifiedPositions = new ArrayList<>();
        this.submittedShifts = new LinkedHashMap<>();
      /*  this.shiftsRestriction = new LinkedHashMap<>();*/
        description = null;
    }

    public void init (DalEmployeeService dalEmployeeService){
        this.dalEmployeeService = dalEmployeeService;
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
     * @param date       The date of the shift.
     * @param shiftType  The type of the shift (true for morning, false for evening).
     * @throws IllegalArgumentException If the shift cannot be submitted due to a restriction or existing shift.
     */
    public void addSubmittedShift(String branch,  LocalDate date, boolean shiftType) throws SQLException {
        // Check if a shift for the same date  already exists
        if (submittedShifts.containsKey(date)) {
            throw new IllegalArgumentException("You have been already submit to that day.");
        }
        // Add the new constraint to the submitted shift
        else {
            Constraint cons = new Constraint(branch, id, date, shiftType);
            dalEmployeeService.addConstraint(id, branch, date, shiftType);
            submittedShifts.put(date, cons);
            dalEmployeeService.addSubmittesdShift(branch, date, shiftType, id);
        }
    }



    public void assignShift(String branch, LocalDate date, boolean shiftType, PositionType positionType) throws Exception {
        Constraint constraint = submittedShifts.get(date);
        if (constraint == null){
            constraint = dalEmployeeService.findConstraintByIdAndDate(id, date);;
            submittedShifts.put(date, constraint);
        }

        if (constraint == null) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (constraint.getAssignedPosition() != null) {
            throw new IllegalArgumentException("Employee is already assigned to a shift on this day.");
        }

        // Todo maybe to cheange i between Dates- if not work
        // Requirement 3: An employee cannot work more than six times a week.
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        HashMap<LocalDate, Constraint> driverAssignInWeek = dalEmployeeService.findAssignConstraintByIdBetwwenDates(startOfWeek, endOfWeek, id);
        if (driverAssignInWeek.keySet().size() >= 6)
            throw new IllegalArgumentException("Employee has already worked six shifts this week.");

//        for (int i = 0; i <= 6; i++){
//            if (submittedShifts.get(startOfWeek.plusDays(i)) == null){
//                constraint = dalEmployeeService.findConstraintByIdAndDate(id,   startOfWeek.plusDays(i));
//                if (constraint != null)
//                    submittedShifts.put(startOfWeek.plusDays(i), constraint);
//            }
//        }
//
//        long assignedShiftsThisWeek = submittedShifts.entrySet().stream()
//                .filter(e -> !e.getKey().isBefore(startOfWeek) && !e.getKey().isAfter(endOfWeek))
//                .map(Map.Entry::getValue)
//                .filter(c -> c.getAssignedPosition() != null)
//                .count();
//        if (assignedShiftsThisWeek >= 6) {
//            throw new IllegalArgumentException("Employee has already worked six shifts this week.");
//        }

        // Set the assigned position of the shift
        constraint.setAssignedPosition(positionType);
        dalEmployeeService.setAssignedPosition(id,  date, positionType.name());
    }



    public String showShiftsStatusByEmployee(LocalDate localDate) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            LocalDate date = localDate.plusDays(i);
            Constraint constraint = submittedShifts.getOrDefault(date, null);
            if (constraint == null)
                constraint =  dalEmployeeService.findConstraintByIdAndDate(id, date.plusDays(i));
            if (constraint != null)
                submittedShifts.put(date.plusDays(i), constraint);
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", "Date", "Shift Type", "Is Approved", "Assigned Position"));
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", date, "Morning", "", ""));
            if (constraint != null && constraint.getShiftType()) {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", constraint.getAssignedPosition() == null  ? "Yes" : "No", constraint.getAssignedPosition() == null ? "No" : "Yes"));
            } else {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", "", ""));
            }
            sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "Evening", "", ""));
            if (constraint != null && !constraint.getShiftType()) {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", constraint.getAssignedPosition() == null ? "Yes" : "No", constraint.getAssignedPosition() == null ? "No" : "Yes"));
            } else {
                sb.append(String.format("%-12s%-12s%-12s%-12s\n", "", "", "", ""));
            }
            sb.append("\n");
        }
        return sb.toString();
    }



    public void addQualification(String position) throws SQLException {
        if (! isLeagalPosition(position))
            throw new IllegalArgumentException("The position " + position + " does not exist");
        qualifiedPositions.add(PositionType.valueOf(position));
        dalEmployeeService.addQualification(id, position);
    }

    public boolean isLeagalPosition(String position) {
       for (PositionType posType : PositionType.values()){
           if (posType.name().equals(position))
                   return true;
       }
       return  false;
    }

    public ArrayList<String> getQualifiedPositions() throws SQLException {
        return dalEmployeeService.findQualificationsBtId(id);
    }

    //just for test in the meantime
//      public Map<LocalDate,Constraint> getSubmittedShifts() throws SQLException {
//        submittedShifts = dalEmployeeService.findSubmittedShiftsByid(id);
//        return submittedShifts;
//   }


    }



