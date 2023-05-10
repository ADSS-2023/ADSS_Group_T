package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DAOs.ConstraintDao;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;
import DataLayer.HR_T_DAL.DAOs.QualificationDAO;
import DataLayer.HR_T_DAL.DAOs.UserDAO;
import DataLayer.HR_T_DAL.DTOs.ConstraintDTO;
import DataLayer.HR_T_DAL.DTOs.QualifiedPositionDTO;
import DataLayer.HR_T_DAL.DTOs.SubmittedShiftDTO;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class DalEmployeeService {

    private EmployeeDAO employeeDAO;
    private ConstraintDao constraintDao;
    private Connection connection;

    private QualificationDAO qualificationDAO;
    private DAO dao;

    private DalUserService dalUserService;

    public DalEmployeeService(Connection connection) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
        this.constraintDao = new ConstraintDao(connection);
        this.dao = new DAO(connection);
        this.qualificationDAO = new QualificationDAO(connection);
    }

    public void addConstraint(int employeeId, String branchAdress, LocalDate date, boolean shiftType,String positionType) throws SQLException {
        String s ="evening";
        if (shiftType)
            s="morning";
        ConstraintDTO constraintDTO = new ConstraintDTO(employeeId,branchAdress,date.toString(),s ,positionType);
        dao.insert(constraintDTO);
    }
    public void addSubmittesdShift(String branchAdress,  LocalDate date, boolean shiftType,  int employeeId ) throws SQLException {
        String s ="evening";
        if (shiftType)
            s="morning";
        SubmittedShiftDTO submittedShiftDTO = new SubmittedShiftDTO(employeeId,branchAdress,date.toString(),s);
        dao.insert(submittedShiftDTO);
    }

    public Constraint findConstraintByIdAndDate(int employeeId,   LocalDate date) throws SQLException {
        ConstraintDTO constraintDTO = constraintDao.findConstraintByIdAndDate(employeeId,date.toString());
        Constraint constraint = new Constraint(constraintDTO);
        return constraint;
    }

    // TODO - israel
    public void setAssignedPosition(int employeeId,  LocalDate date, String positionType) throws SQLException {

    }

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
        Employee employee = new Employee(dalUserService.findUserDTOById(employeeId));
        if(employee != null) return employee;
        else return null;
    }

}
