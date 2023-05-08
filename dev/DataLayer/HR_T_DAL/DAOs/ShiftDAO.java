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
import java.util.List;


    public class ShiftDAO extends DAO {

        public ShiftDAO() {}

        public Shift getShiftByDateAndBranch(LocalDate date, String branch) {
            // Retrieve a shift's details from the database by its date and branch
            return null;
        }

        public List<ShiftDTO> getshifsByDate(LocalDate localDate) {
            // Retrieve all shifts from the database
            return null;
        }

        public List<ShiftDTO> getshifsByDateAndShiftType(LocalDate localDate, boolean shiftType) {
            // Retrieve all shifts from the database
            return null;
        }

        public static EmployeeDTO find(Connection connection, String tableName, int id) throws SQLException {
            EmployeeDTO dto = null;
            String sql = "SELECT * FROM " + tableName + " WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                dto = new EmployeeDTO();
                Field[] fields = dto.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value;
                    try {
                        value = rs.getObject(field.getName());
                    } catch (SQLException e) {
                        throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
                    }
                    try {
                        field.set(dto, value);
                    } catch (IllegalAccessException e) {
                        throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
                    }
                }
            }
            return dto;
        }


    }


