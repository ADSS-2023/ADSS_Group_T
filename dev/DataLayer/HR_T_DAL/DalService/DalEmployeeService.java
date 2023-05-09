package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class DalEmployeeService {

    private EmployeeDAO employeeDAO;
    private Connection connection;

    private DalUserService dalUserService;

    public DalEmployeeService(Connection connection) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
    }


    // TODO - israel
    public boolean addConstraint(int employeeId, String branchAdress, LocalDate date, boolean shiftType) throws SQLException {
          return true;
    }

    // TODO - israel

    public boolean addSubmittesdShift(String branchAdress,  LocalDate date, boolean shiftType,  int employeeId ) throws SQLException {
        return  true;

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




}
