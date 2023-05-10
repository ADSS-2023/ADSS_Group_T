package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Constraint;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SubmittedShiftDAO extends DAO {
    public SubmittedShiftDAO(Connection connection) {
        super(connection);
    }
    public HashMap<LocalDate, Constraint> findSubmittedShiftsById(int employeeId) throws SQLException {
        HashMap<LocalDate, Constraint> ret = new HashMap<>();
        return ret;
    }
}
