package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.User;
import DataLayer.HR_T_DAL.DAOs.ShiftDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DTO;
import UtilSuper.Pair;
import UtilSuper.Time;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DalShiftService {

    private Connection connection;
    private ShiftDAO shiftDAO;
    private DalUserService dalUserService;

    private DalEmployeeService dalEmployeeService;

    public DalShiftService(Connection connection, DalEmployeeService dalEmployeeService) throws SQLException {
        this.connection = connection;
        this.shiftDAO = new ShiftDAO(connection);
        this.dalUserService = new DalUserService(connection);
        this.dalEmployeeService = dalEmployeeService;
    }

    public List<ShiftDTO> getshifsByDate(LocalDate localDate) throws SQLException {
        return shiftDAO.getShiftsByDate(localDate);
    }

    public void addNotification(LocalDate localDate, String notification) throws SQLException {
        NotificationsDTO notificationsDTO = new NotificationsDTO(localDate.toString(), notification);
        shiftDAO.insert(notificationsDTO);
    }

    public HashMap<LocalDate, String> getNotifications(String fromDate, String toDate) throws SQLException {
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("date", fromDate + " TO " + toDate);
        ArrayList<NotificationsDTO> notificationsDTOs = shiftDAO.findAll("Notifications", conditions, NotificationsDTO.class);

        HashMap<LocalDate, String> notifications = new HashMap<>();
        for (NotificationsDTO notificationsDTO : notificationsDTOs) {
            notifications.put(LocalDate.parse(notificationsDTO.getDate()), notificationsDTO.getNotification());
        }
        return notifications;
    }



    public void addRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch, date, shiftType, positionType, amount);
        shiftDAO.insert(shiftRequirementsDTO);
    }

    public void updateRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch, date, shiftType, positionType, amount);
        shiftDAO.update(shiftRequirementsDTO, shiftRequirementsDTO);
    }

    public void deleteRequierement(String branch, String date, String shiftType, String positionType) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch, date, shiftType, positionType, 0);
        shiftDAO.delete(shiftRequirementsDTO);
    }

    public LinkedHashMap<String, Integer> findRequiermentsByDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
        LinkedHashMap<String, Integer> ret = new LinkedHashMap<>();
        HashMap<String, Object> wherecond = new HashMap<>();
        wherecond.put("branch", branch);
        wherecond.put("shiftDate", Time.localDateToString(localDate));
        wherecond.put("shiftType", shiftType);
        ArrayList<ShiftRequirementsDTO> req = shiftDAO.findAll("ShiftRequirements", wherecond, ShiftRequirementsDTO.class);
        if (req != null && !req.isEmpty()) {
            for (ShiftRequirementsDTO shiftReq : req) {
                ret.put(shiftReq.getPositionName(), shiftReq.getAmount());
            }
            return ret;
        } else return null;
    }

//    public LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> findAllSubmissionByBranchDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
//
//        HashMap<String, Object> wherecond = new HashMap<>();
//        wherecond.put("branch",branch);
//        wherecond.put("shiftDate", Time.localDateToString(localDate));
//        wherecond.put("shiftType",shiftType);
//        ArrayList<ShiftToEmployeeDTO> shiftToEmployee = shiftDAO.findAll("ShiftToEmployee", wherecond ,ShiftToEmployeeDTO.class);
//        LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> ret = new LinkedHashMap<>();
//        if (shiftToEmployee!= null && !shiftToEmployee.isEmpty()){
//            for (ShiftToEmployeeDTO val : shiftToEmployee) {
//                Employee employee = employeeDAO.find(val.getEmployeeId());
//                String position = val.getPosition();
//                if (!ret.containsKey(position)) {
//                    LinkedHashMap<Employee, Boolean> employeeBooleanHashMap = new LinkedHashMap<>();
//                    employeeBooleanHashMap.put(employee, val.getAssigned());
//                    ret.put(position, employeeBooleanHashMap);
//                } else {
//                    LinkedHashMap<Employee, Boolean> employeeBooleanHashMap = ret.get(position);
//                    employeeBooleanHashMap.put(employee, val.getAssigned());
//                }
//            }
//            return ret;
//        }
//        else return null;
//    }

    public LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> findAllSubmissionByBranchDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
        // Set up the fields needed to retrieve ShiftRequirements and ShiftToEmployee objects
        Map<String, Object> fieldsForShiftRequirements = new HashMap<>();
        fieldsForShiftRequirements.put("shiftDate", localDate.toString());
        fieldsForShiftRequirements.put("shiftType", shiftType);
        fieldsForShiftRequirements.put("branch", branch);

        // Retrieve the ShiftToEmployee objects for the current ShiftDTO
        ArrayList<ShiftToEmployeeDTO> submittedPositionByEmployeesByDateShiftTypeAndBranchDTO = shiftDAO.findAll("ShiftToEmployee",
                fieldsForShiftRequirements, ShiftToEmployeeDTO.class);


        // Create a LinkedHashMap to hold the employee assignments for the current Shift object
        LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositionByEmployees = new LinkedHashMap<>();
        for (ShiftToEmployeeDTO subEmp : submittedPositionByEmployeesByDateShiftTypeAndBranchDTO) {
            if (subEmp.getPosition() != null && subEmp.getIsAssigned() != null) {
                String position = subEmp.getPosition();
                boolean isAssigned = Boolean.parseBoolean(subEmp.getIsAssigned());
                User user = dalUserService.findUserById(subEmp.getEmployeeId());
                Employee employee = new Employee(user.getId(), user.getEmployeeName(), user.getBankAccount(), user.getDescription(), user.getSalary(), user.getJoiningDay(),
                        user.getPassword(), user.getUserType() , dalEmployeeService);

                if (submittedPositionByEmployees.containsKey(position)) {
                    submittedPositionByEmployees.get(position).put(employee, isAssigned);
                } else {
                    LinkedHashMap<Employee, Boolean> employeeAssignments = new LinkedHashMap<>();
                    employeeAssignments.put(employee, isAssigned);
                    submittedPositionByEmployees.put(position, employeeAssignments);
                }
            }
        }
        return  submittedPositionByEmployees;
    }



//    public LinkedHashMap<String, Integer> findAllSubmissionByDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
//        LinkedHashMap<String, Integer> ret = new LinkedHashMap<>();
//        HashMap<String, Object> wherecond = new HashMap<>();
//        wherecond.put("branch", branch);
//        wherecond.put("shiftDate", Time.localDateToString(localDate));
//        wherecond.put("shiftType", shiftType);
//        ArrayList<SubmittedShiftDTO> req = shiftDAO.findAll("",   wherecond, SubmittedShiftDTO.class);
//        if (req != null && !req.isEmpty()) {
//            for (SubmittedShiftDTO submittedShiftDTO : req) {
//                String position = submittedShiftDTO.get
//                if (ret.containsKey(position)) {
//                    ret.put(position, ret.get(position) + 1);
//                } else {
//                    ret.put(position, 1);
//                }
//            }
//            return ret;
//        } else {
//            return null;
//        }
//    }



    public void addEmployeeToShift(String branch, LocalDate localDate, boolean shiftType, int id, String position, boolean isAssined) throws SQLException {
        String sht = shiftType ? "m" : "e";
        String isAss = isAssined ? "true" : "false";
        ShiftToEmployeeDTO shiftToEmployeeDTO = new ShiftToEmployeeDTO(localDate.toString(), sht, id, position, isAss, branch);
        shiftDAO.insert(shiftToEmployeeDTO);
    }


    public void updateEmployeeToShift(LocalDate localDate, boolean shiftType, int id, String position, boolean isAssined) throws SQLException {
        String sht = shiftType ? "m" : "e";
        String isAss = isAssined ? "true" : "false";
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("shiftDate", localDate.toString());
        pk.put("shiftType", sht);
        pk.put("employeeId", id);
        pk.put("position", position);;
        ShiftToEmployeeDTO shiftToEmployeeDTO = shiftDAO.find(pk, "ShiftToEmployee", ShiftToEmployeeDTO.class);
        if (shiftToEmployeeDTO != null){
            String toAss = isAssined ? "true" : "false";
            ShiftToEmployeeDTO newShiftToEmployeeDto = new ShiftToEmployeeDTO(shiftToEmployeeDTO.getShiftDate(), shiftToEmployeeDTO.getShiftType(), shiftToEmployeeDTO.getEmployeeId(), shiftToEmployeeDTO.getPosition(), toAss, shiftToEmployeeDTO.getBranch());
            shiftDAO.update(shiftToEmployeeDTO, newShiftToEmployeeDto);
        }

    }


    public LinkedHashMap<LocalDate, ArrayList<Shift>> findAllShiftsByBranch(String branch) throws SQLException {
        // Initialize the LinkedHashMap that will hold the shifts by date
        LinkedHashMap<LocalDate, ArrayList<Shift>> shiftsByBranch = new LinkedHashMap<>();

        // Retrieve all the ShiftDTO objects for the given branch
        ArrayList<ShiftDTO> shiftDTOs = shiftDAO.findAll("Shift", "branch", branch, ShiftDTO.class);

        // Loop through each ShiftDTO object and create a Shift object from it
        for (ShiftDTO shiftDTO : shiftDTOs) {
            // Set up the fields needed to retrieve ShiftRequirements and ShiftToEmployee objects
            Map<String, Object> fieldsForShiftRequirements = new HashMap<>();
            fieldsForShiftRequirements.put("shiftDate", shiftDTO.getShiftDate());
            fieldsForShiftRequirements.put("shiftType", shiftDTO.getShiftType());
           fieldsForShiftRequirements.put("branch", shiftDTO.getBranch());

            // Retrieve the ShiftRequirements objects for the current ShiftDTO
            ArrayList<ShiftRequirementsDTO> shiftRequirementsByDateShiftTypeAndBranchDTO = shiftDAO.findAll("ShiftRequirements",
                    fieldsForShiftRequirements, ShiftRequirementsDTO.class);

            fieldsForShiftRequirements.put("branch", shiftDTO.getBranch());
            // Retrieve the ShiftToEmployee objects for the current ShiftDTO
            ArrayList<ShiftToEmployeeDTO> submittedPositionByEmployeesByDateShiftTypeAndBranchDTO = shiftDAO.findAll("ShiftToEmployee",
                    fieldsForShiftRequirements, ShiftToEmployeeDTO.class);

            // Create a LinkedHashMap to hold the shift requirements for the current Shift object
            LinkedHashMap<String, Integer> shiftRequirements = new LinkedHashMap<>();
            for (ShiftRequirementsDTO shiftReqDTO : shiftRequirementsByDateShiftTypeAndBranchDTO){
                if (shiftReqDTO.getPositionName() != null){
                    String positionName = shiftReqDTO.getPositionName();
                    int amount = shiftReqDTO.getAmount();
                    shiftRequirements.put(positionName, amount);
                }
            }

            // Create a LinkedHashMap to hold the employee assignments for the current Shift object
            LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositionByEmployees = new LinkedHashMap<>();
            for (ShiftToEmployeeDTO subEmp  : submittedPositionByEmployeesByDateShiftTypeAndBranchDTO){
                if (subEmp.getPosition() != null && subEmp.getIsAssigned() != null ){
                    String position = subEmp.getPosition();
                   // boolean shiftType = subEmp.getShiftType().equals("m");
                    boolean isAssigned = Boolean.parseBoolean(subEmp.getIsAssigned());
                    User user = dalUserService.findUserById(subEmp.getEmployeeId());
                    if (user != null){
                        Employee employee = new Employee(user.getId(), user.getEmployeeName(), user.getBankAccount(), user.getDescription(), user.getSalary(), user.getJoiningDay(),
                                user.getPassword(), user.getUserType(), dalEmployeeService);

                        if (submittedPositionByEmployees.containsKey(position)) {
                            submittedPositionByEmployees.get(position).put(employee, isAssigned);
                        } else {
                            LinkedHashMap<Employee, Boolean> employeeAssignments = new LinkedHashMap<>();
                            employeeAssignments.put(employee, isAssigned);
                            submittedPositionByEmployees.put(position, employeeAssignments);
                        }
                    }

                }
            }

            // Create a new Shift object from the retrieved data
            LocalDate shiftDate = LocalDate.parse(shiftDTO.getShiftDate());
            boolean shiftType = shiftDTO.getShiftType().equals("m");
            int managerId  = shiftDTO.getManagerId();
            Shift shift = new Shift(branch, shiftDate, shiftType, shiftRequirements, submittedPositionByEmployees, managerId, this);

            // Add the Shift object to the LinkedHashMap holding shifts by date
            if (shiftsByBranch.containsKey(shiftDate)) {
                shiftsByBranch.get(shiftDate).add(shift);
            } else {
                ArrayList<Shift> shiftsList =new ArrayList<>();
                shiftsList.add(shift);
                shiftsByBranch.put(shiftDate, shiftsList);
            }
        }
        return shiftsByBranch;
    }

    public LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> findAllShiftsByDateInAllBranches(LocalDate date) throws SQLException {
        LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByBranch = new LinkedHashMap<>();

        // Get all shifts for the given date from all branches
        ArrayList<ShiftDTO> shiftDTOs = shiftDAO.findAll("Shift", "shiftDate", date.toString(), ShiftDTO.class);

        for (ShiftDTO shiftDTO : shiftDTOs) {
            String branch = shiftDTO.getBranch();
            if (!shiftsByBranch.containsKey(branch)) {
                shiftsByBranch.put(branch, new HashMap<>());
            }
            HashMap<LocalDate, ArrayList<Shift>> shiftsByDate = shiftsByBranch.get(branch);

            Map<String, Object> fieldsForShiftRequirements = new HashMap<>();
            fieldsForShiftRequirements.put("shiftDate", shiftDTO.getShiftDate());
            fieldsForShiftRequirements.put("shiftType", shiftDTO.getShiftType());
            fieldsForShiftRequirements.put("branch", branch);

            // Get the shift requirements for the shift
            ArrayList<ShiftRequirementsDTO> shiftRequirementsByDateShiftTypeAndBranchDTO = shiftDAO.findAll("ShiftRequirements",
                    fieldsForShiftRequirements, ShiftRequirementsDTO.class);

            LinkedHashMap<String, Integer> shiftRequirements = new LinkedHashMap<>();
            for (ShiftRequirementsDTO shiftReqDTO : shiftRequirementsByDateShiftTypeAndBranchDTO){
                if (shiftReqDTO.getPositionName() != null){
                    String positionName = shiftReqDTO.getPositionName();
                    int amount = shiftReqDTO.getAmount();
                    shiftRequirements.put(positionName, amount);
                }
            }

            // Get the submitted positions and their assignments for the shift
            ArrayList<ShiftToEmployeeDTO> submittedPositionByEmployeesByDateShiftTypeAndBranchDTO = shiftDAO.findAll("ShiftToEmployee",
                    fieldsForShiftRequirements, ShiftToEmployeeDTO.class);

            LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositionByEmployees = new LinkedHashMap<>();
            for (ShiftToEmployeeDTO subEmp  : submittedPositionByEmployeesByDateShiftTypeAndBranchDTO){
                if (subEmp.getPosition() != null && subEmp.getIsAssigned() != null ){
                    String position = subEmp.getPosition();
                    boolean isManager = subEmp.getShiftType().equals("m");
                    boolean isAssigned = Boolean.parseBoolean(subEmp.getIsAssigned());
                    User user = dalUserService.findUserById(subEmp.getEmployeeId());
                    Employee employee = new Employee(user.getId(), user.getEmployeeName(), user.getBankAccount(), user.getDescription(), user.getSalary(), user.getJoiningDay(),
                            user.getPassword(), user.getUserType(), dalEmployeeService);

                    if (submittedPositionByEmployees.containsKey(position)) {
                        submittedPositionByEmployees.get(position).put(employee, isAssigned);
                    } else {
                        LinkedHashMap<Employee, Boolean> employeeAssignments = new LinkedHashMap<>();
                        employeeAssignments.put(employee, isAssigned);
                        submittedPositionByEmployees.put(position, employeeAssignments);
                    }
                }
            }

            // Create the shift object
            LocalDate shiftDate = LocalDate.parse(shiftDTO.getShiftDate());
            boolean isManagerShift = shiftDTO.getShiftType().equals("m");
            int managerId  = shiftDTO.getManagerId();

            Shift shift = new Shift(branch, shiftDate, isManagerShift, shiftRequirements, submittedPositionByEmployees, managerId, this);

            if (shiftsByDate.containsKey(shiftDate)) {
                shiftsByDate.get(shiftDate).add(shift);
            } else {
                ArrayList<Shift> shiftsList = new ArrayList<>();
                shiftsList.add(shift);
                shiftsByDate.put(shiftDate, shiftsList);
            }
        }

        return shiftsByBranch;
    }

    public List<ShiftDTO> getshifsByDateAndShiftType(LocalDate localDate, boolean shiftType) {
        String LocalDatestring = localDate.toString();
        String shiftTypestring ;
        if (shiftType) shiftTypestring = "morning"; else shiftTypestring = "evening";

        //shiftDAO.find()
        return null;
    }

    public void addNewShift (Shift shift) throws SQLException {
        ShiftDTO shiftDTO = new ShiftDTO(shift);
        shiftDAO.insert(shiftDTO);
    }
}


