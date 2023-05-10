package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DAOs.ConstraintDao;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;
import DataLayer.HR_T_DAL.DTOs.ConstraintDTO;
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

    private DAO dao;

    private DalUserService dalUserService;

    public DalEmployeeService(Connection connection) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
    }

    public void addConstraint(int employeeId, String branchAdress, LocalDate date, boolean shiftType) throws SQLException {
        String s ="evening";
        if (shiftType)
            s="morning";
        ConstraintDTO constraintDTO = new ConstraintDTO(employeeId,branchAdress,date.toString(),s);
        constraintDao.insert(constraintDTO);
    }
    public void addSubmittesdShift(String branchAdress,  LocalDate date, boolean shiftType,  int employeeId ) throws SQLException {
        String s ="evening";
        if (shiftType)
            s="morning";
        SubmittedShiftDTO submittedShiftDTO = new SubmittedShiftDTO(employeeId,branchAdress,date.toString(),s);
        dao.insert(submittedShiftDTO);
    }

    // TODO - israel
    public Constraint findConstraintByIdAndDate(int employeeId,   LocalDate date) throws SQLException {
        return null;

    }

    // TODO - israel
    public Constraint setAssignedPosition(int employeeId,  LocalDate date, String positionType) throws SQLException {
        return null;
    }

    // TODO - israel
    public Constraint addQualification(int employeeId,   String positionType) throws SQLException {
        return null;
    }

    // TODO - israel
    public ArrayList<String> findQualificationsBtId(int employeeId) throws SQLException {
        return null;
    }

    // TODO - israel
    public Map<LocalDate,Constraint> findSubmittedShiftsByid(int employeeId) throws SQLException {
        return null;
    }

    public Employee findEmployeeById(int employeeId) throws SQLException {
        return null;
    }
    // TODO - israel
    public Employee deleteEmployee(int employeeId) throws SQLException {
        return null;
    }




}
