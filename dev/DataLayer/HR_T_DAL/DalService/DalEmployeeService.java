package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DAOs.ConstraintDao;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;
import DataLayer.HR_T_DAL.DAOs.QualificationDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DalEmployeeService {

    private EmployeeDAO employeeDAO;
    private ConstraintDao constraintDao;
    private Connection connection;

    private QualificationDAO qualificationDAO;
    private DAO dao;

    private DalUserService dalUserService;


    public DalEmployeeService(Connection connection,DalUserService dalUserService) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
        this.constraintDao = new ConstraintDao(connection);
        this.dao = new DAO(connection);
        this.qualificationDAO = new QualificationDAO(connection);
        this.dalUserService = dalUserService;
    }

    public void addConstraint(int employeeId, String branchAdress, LocalDate date, boolean shiftType,String positionType) throws SQLException {
        String s ="e";
        if (shiftType)
            s="m";
        ConstraintByEmployeeDTO constraintDTO = new ConstraintByEmployeeDTO(employeeId,branchAdress,date.toString(),s ,positionType);
        dao.insert(constraintDTO);
    }
//    public void addSubmittesdShift(String branchAdress,  LocalDate date, boolean shiftType,  int employeeId ) throws SQLException {
//        String s ="evening";
//        if (shiftType)
//            s="morning";
//        SubmittedShiftDTO submittedShiftDTO = new SubmittedShiftDTO(employeeId,branchAdress,date.toString(),s);
//        dao.insert(submittedShiftDTO);
//    }

    public Constraint findConstraintByIdBranchDateAndShiftType(int employeeId,  String branch,  LocalDate date, String shiftType) throws SQLException {
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("employeeId", employeeId);
        pk.put("branchAddress", branch);
        pk.put("constraintDate", date.toString());
        pk.put("shiftType", shiftType);
        ConstraintByEmployeeDTO constraintDTO = constraintDao.find( pk, "ConstraintByEmployee", ConstraintByEmployeeDTO.class);
        Constraint constraint = new Constraint(constraintDTO);
        return  constraint;
    }


    public  ArrayList<Constraint> findAllConstraintByIdBetweenDates(int id , LocalDate startDate, LocalDate endDate) throws SQLException{
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("employeeId", id);
        conditions.put("constraintDate", startDate.toString() + " TO " + endDate.toString());
        ArrayList<ConstraintByEmployeeDTO> constraintDTO = constraintDao.findAll("ConstraintByEmployee", conditions, ConstraintByEmployeeDTO.class);
        ArrayList<Constraint> constraints = new ArrayList<>();
        for (ConstraintByEmployeeDTO cons : constraintDTO) {
            Constraint constraint = new Constraint(cons);
            constraints.add(constraint);
        }
        return constraints;
    }
    public HashMap<LocalDate, Constraint> findAssignedConstraintByIdBetweenDates(LocalDate startDate, LocalDate endDate, int id) throws SQLException {
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("employeeId", id);
        conditions.put("constraintDate", startDate.toString() + " TO " + endDate.toString());
        //ToDo- check the not null
        conditions.put("positionType", "NOT null");
        ArrayList<ConstraintByEmployeeDTO> constraintDTOs = constraintDao.findAll("ConstraintByEmployee", conditions, ConstraintByEmployeeDTO.class);
        HashMap<LocalDate, Constraint> constraints = new HashMap<>();
        for (ConstraintByEmployeeDTO constraintDTO : constraintDTOs) {
            Constraint constraint = new Constraint(constraintDTO);
            constraints.put(constraint.getDate(), constraint);
        }
        return constraints;
    }



    //work?
//    public Constraint setAssignedPosition(int employeeId, LocalDate date, String positionType) throws SQLException {
//        ConstraintByEmployeeDTO constraintDTO = constraintDao.find(employeeId, date.toString());
//        if (constraintDTO != null) {
//            constraintDTO.setPositionType(positionType);
//            constraintDao.update(constraintDTO);
//            return new Constraint(constraintDTO);
//        } else {
//            return null;
//        }
//    }



    public Constraint setAssignedPosition(int employeeId, String branchAddress, String constraintDate, String shiftType, String positionType) throws SQLException {
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("employeeId", employeeId);
        pk.put("branchAddress", branchAddress);
        pk.put("constraintDate", constraintDate);
        pk.put("shiftType", shiftType);
        ConstraintByEmployeeDTO constraintDTO = constraintDao.find(pk, "ConstraintByEmployee", ConstraintByEmployeeDTO.class);
        if (constraintDTO != null) {
            ConstraintByEmployeeDTO newConstraint = new ConstraintByEmployeeDTO(employeeId, branchAddress, constraintDate, shiftType, positionType);
            // TODO - check that the assignPosition change from null to the propare position
            constraintDao.update(constraintDTO, newConstraint);
            return new Constraint(newConstraint);
        } else {
            return null;
        }
    }
//    public Constraint setAssignedPosition(int employeeId, String branchAddress, String constraintDate, String shiftType, String positionType) throws SQLException {
//        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
//        pk.put("employeeId", employeeId);
//        pk.put("branchAddress", branchAddress);
//        pk.put("constraintDate", constraintDate);
//        pk.put(shiftType, shiftType);
//        ConstraintByEmployeeDTO constraintDTO = constraintDao.find(pk, "constraints", ConstraintByEmployeeDTO.class);
//        if (constraintDTO != null) {
//            ConstraintByEmployeeDTO newConstraint = new ConstraintByEmployeeDTO(employeeId, branchAddress, constraintDate, shiftType, positionType);
//            constraintDao.update(constraintDTO, newConstraint);
//            return new Constraint(newConstraint);
//        } else {
//            return null;
//        }
//    }





    public void addQualification(int employeeId,   String positionType) throws SQLException {
        QualifiedPositionDTO qualifiedPositionDTO = new QualifiedPositionDTO(employeeId,positionType);
        dao.insert(qualifiedPositionDTO);
    }

    public ArrayList<String> findQualificationsById(int employeeId) throws SQLException {
        return qualificationDAO.findQualificationsById(employeeId);
    }

    public Map<LocalDate,Constraint> findSubmittedShiftsById(int employeeId) throws SQLException {
        return null;
    }


    public Employee findEmployeeById(int employeeId) throws SQLException {
       UserDTO userDTO = dalUserService.findUserDTOById(employeeId);
       if (userDTO != null){
           Employee employee = new Employee(userDTO, this);
           return employee;
       }
        else return null;
    }

}
