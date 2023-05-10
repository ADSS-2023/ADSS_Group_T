package DataLayer.HR_T_DAL.DAOs;

import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QualificationDAO extends DAO {
    public QualificationDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<String> findQualificationsById(int employeeId) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT positionName FROM QualifiedPosition WHERE employeeId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String positionName = resultSet.getString("positionName");
                result.add(positionName);
            }
        }

        return result;
    }


}
