package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Shift;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;
import DataLayer.HR_T_DAL.DTOs.ShiftDTO;
import DataLayer.Util.DAO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


    public class ShiftDAO extends DAO {

        public ShiftDAO(Connection connection) {
            super(connection);
        }

        public List<ShiftDTO> getShiftsByDate(LocalDate localDate) throws SQLException {
            List<ShiftDTO> result = new ArrayList<>();
            String sql = "SELECT * FROM shifts WHERE shiftDate = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, localDate.toString());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ShiftDTO shift = new ShiftDTO(
                            resultSet.getString("shiftDate"),
                            resultSet.getString("shiftType"),
                            resultSet.getInt("managerId"),
                            resultSet.getString("branch")
                    );
                    shift.setTableName("Shift");
                    result.add(shift);
                }
            }
            return result;
        }
    }


