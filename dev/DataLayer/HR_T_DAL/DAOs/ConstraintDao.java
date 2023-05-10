package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Constraint;
import DataLayer.HR_T_DAL.DTOs.ConstraintDTO;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConstraintDao extends DAO {

    public ConstraintDao(Connection connection) {
        super(connection);
    }

    public ConstraintDTO findConstraintByIdAndDate(int employeeId, String date) throws SQLException {
        ConstraintDTO result = null;
        String sql = "SELECT * FROM Constraint WHERE employeeId = ? AND constraintDate = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setString(2, date);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = new ConstraintDTO(
                        resultSet.getInt("employeeId"),
                        resultSet.getString("branchAddress"),
                        resultSet.getString("constraintDate"),
                        resultSet.getString("shiftType"),
                        resultSet.getString("positionType")
                );
                result.setTableName("Constraint");
            }
        }
        return result;
    }

}
