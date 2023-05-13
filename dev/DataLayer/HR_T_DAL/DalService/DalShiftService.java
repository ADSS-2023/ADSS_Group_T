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

    private Connection connection ;
    private ShiftDAO shiftDAO;
    private DalUserService dalUserService;

    private DalEmployeeService employeeService;
    public DalShiftService(Connection connection) throws SQLException {
        this.connection = connection;
        this.shiftDAO = new ShiftDAO(connection);
        this.dalUserService = new DalUserService(connection);
    }

    public List<ShiftDTO> getshifsByDate(LocalDate localDate) throws SQLException {
        return shiftDAO.getShiftsByDate(localDate);
    }

    public void addRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch,date,shiftType,positionType,amount);
        shiftDAO.insert(shiftRequirementsDTO);
    }

    public void updateRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch,date,shiftType,positionType,amount);
        shiftDAO.update(shiftRequirementsDTO , shiftRequirementsDTO);
    }

    public void deleteRequierement(String branch, String date, String shiftType, String positionType) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(branch,date,shiftType,positionType,0);
        shiftDAO.delete(shiftRequirementsDTO);
    }

    public LinkedHashMap<String, Integer> findRequiermentsByDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
        LinkedHashMap<String,Integer> ret = new LinkedHashMap<>();
        HashMap<String, Object> wherecond = new HashMap<>();
        wherecond.put("branch",branch);
        wherecond.put("shiftDate", Time.localDateToString(localDate));
        wherecond.put("shiftType",shiftType);
        ArrayList<ShiftRequirementsDTO> req = shiftDAO.findAll("ShiftRequirements",wherecond,ShiftRequirementsDTO.class);
        if (req!= null && !req.isEmpty()){
            for (ShiftRequirementsDTO shiftReq: req) {
                ret.put(shiftReq.getPositionName(),shiftReq.getAmount());
            }
            return ret;
        }
        else return null;
    }

    public LinkedHashMap<String, Integer> findAllSubmissionByDateAndShiftType(String branch, LocalDate localDate, String shiftType) throws SQLException {
        LinkedHashMap<String,Integer> ret = new LinkedHashMap<>();
        HashMap<String, Object> wherecond = new HashMap<>();
        wherecond.put("branch",branch);
        wherecond.put("shiftDate", Time.localDateToString(localDate));
        wherecond.put("shiftType",shiftType);
        //TODO name by table
        ArrayList<SubmittedShiftDTO> req = shiftDAO.findAll("",wherecond,SubmittedShiftDTO.class);
        if (req!= null && !req.isEmpty()){
            for (SubmittedShiftDTO submittedShiftDTO: req) {
                //ret.put(submittedShiftDTO,submittedShiftDTO);
            }
            return ret;
        }
        else return null;
    }


    // TODO - israel do not throwe Exeption
    public void addEmployeeToShift(String branch, LocalDate localDate, boolean shiftTpe, int id, String position, boolean isAssined) throws SQLException {
    }

    // TODO - israel
    public void updateEmployeeToShift(LocalDate localDate, boolean shiftTpe, int id, String position, boolean isAssined) throws SQLException {

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
                    boolean shiftType = subEmp.getShiftType().equals("m");
                    boolean isAssigned = Boolean.parseBoolean(subEmp.getIsAssigned());
                    User user = dalUserService.findUserById(subEmp.getEmployeeId());
                    Employee employee = new Employee(user.getId(), user.getEmployeeName(), user.getBankAccount(), user.getDescription(), user.getSalary(), user.getJoiningDay(),
                            user.getPassword(), user.getUserType());

                    if (submittedPositionByEmployees.containsKey(position)) {
                        submittedPositionByEmployees.get(position).put(employee, isAssigned);
                    } else {
                        LinkedHashMap<Employee, Boolean> employeeAssignments = new LinkedHashMap<>();
                        employeeAssignments.put(employee, isAssigned);
                        submittedPositionByEmployees.put(position, employeeAssignments);
                    }
                }
            }

            // Create a new Shift object from the retrieved data
            LocalDate shiftDate = LocalDate.parse(shiftDTO.getShiftDate());
            boolean shiftType = shiftDTO.getShiftType().equals("m");
            int managerId  = shiftDTO.getManagerId();
            Shift shift = new Shift(branch, shiftDate, shiftType, shiftRequirements, submittedPositionByEmployees, managerId);

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
                            user.getPassword(), user.getUserType());

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

            Shift shift = new Shift(branch, shiftDate, isManagerShift, shiftRequirements, submittedPositionByEmployees, managerId);

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


