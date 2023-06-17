package BusinessLayer.HR;


import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DTOs.UserDTO;
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




    public Employee(int id, String employeeName, String bankAccount, String description, int salary, LocalDate joiningDay, String password, UserType userType, DalEmployeeService dalEmployeeService) {
        super(id, employeeName, bankAccount, description, salary, joiningDay, password, userType);
        this.qualifiedPositions = new ArrayList<>();
        this.submittedShifts = new LinkedHashMap<>();
        this.dalEmployeeService = dalEmployeeService;
      /*  this.shiftsRestriction = new LinkedHashMap<>();*/
        description = null;
    }



    public Employee(UserDTO userDTO, DalEmployeeService dalEmployeeService) {
        super(userDTO);
        this.qualifiedPositions = new ArrayList<>();
        this.submittedShifts = new LinkedHashMap<>();
        this.dalEmployeeService = dalEmployeeService;
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
            dalEmployeeService.addConstraint(id, branch, date, shiftType, "non");
            submittedShifts.put(date, cons);
            //it is added on the controller
           // dalEmployeeService.addSubmittesdShift(branch, date, shiftType, id);
        }
    }



    public void assignShift(String branch, LocalDate date, boolean shiftType, PositionType positionType) throws Exception {
        Constraint constraint = submittedShifts.get(date);
        if (constraint == null){
            String st = shiftType ? "m" : "e";
            constraint = dalEmployeeService.findConstraintByIdBranchDateAndShiftType(id, branch,  date, st);;
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
        HashMap<LocalDate, Constraint> driverAssignInWeek = dalEmployeeService.findAssignedConstraintByIdBetweenDates(startOfWeek, endOfWeek, id);
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
        String sht = shiftType? "m" : "e";
        dalEmployeeService.setAssignedPosition(id,  branch, date.toString(), sht, positionType.name());
        constraint.setAssignedPosition(positionType);
    }



    public String showShiftsStatusByEmployee(LocalDate localDate) throws SQLException {
        StringBuilder sb = new StringBuilder();
        List<Constraint> constraints = dalEmployeeService.findAllConstraintByIdBetweenDates(id, localDate, localDate.plusMonths(1));

        // Add header row
        sb.append("+------------+------------+-------------+-----------------+\n");
        sb.append("|    Date    | Shift Type | Is Approved | Assigned Position|\n");
        sb.append("+------------+------------+-------------+-----------------+\n");

        for (Constraint constraint : constraints) {
            LocalDate date = constraint.getDate();
            sb.append(String.format("| %-10s | %-10s | %-11s | %-15s |\n", date, "Morning", "", ""));
            sb.append(String.format("| %-10s | %-10s | %-11s | %-15s |\n", "", "Evening", constraint.getAssignedPosition() != null ? "Yes" : "", constraint.getAssignedPosition() != null ? constraint.getAssignedPosition() : ""));
            sb.append("+------------+------------+-------------+-----------------+\n");
        }
        return sb.toString();
    }

    public List<Map<String, String>> getShiftsStatusByEmployee(LocalDate localDate) throws SQLException {
        List<Map<String, String>> shiftsStatus = new ArrayList<>();

        List<Constraint> constraints = dalEmployeeService.findAllConstraintByIdBetweenDates(id, localDate, localDate.plusMonths(1));

        for (Constraint constraint : constraints) {
            LocalDate date = constraint.getDate();
            Map<String, String> shiftData = new HashMap<>();
            shiftData.put("Date", date.toString());
            shiftData.put("Shift Type", "Morning");
            shiftData.put("Is Approved", "");
            shiftData.put("Assigned Position", "");

            shiftsStatus.add(shiftData);

            if (constraint.getAssignedPosition()!= null) {
                shiftData = new HashMap<>();
                shiftData.put("Date", "");
                shiftData.put("Shift Type", "Evening");
                shiftData.put("Is Approved", "Yes");
                shiftData.put("Assigned Position", constraint.getAssignedPosition().toString());

                shiftsStatus.add(shiftData);
            }
        }

        return shiftsStatus;
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
        return dalEmployeeService.findQualificationsById(id);
    }


    //just for test in the meantime
//      public Map<LocalDate,Constraint> getSubmittedShifts() throws SQLException {
//        submittedShifts = dalEmployeeService.findSubmittedShiftsByid(id);
//        return submittedShifts;
//   }


    }



