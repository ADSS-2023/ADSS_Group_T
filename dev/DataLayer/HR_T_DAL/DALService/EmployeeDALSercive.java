package DataLayer.HR_T_DAL.DALService;

import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeDALSercive {
    private Connection connection;
    public EmployeeDALSercive(Connection connection){
        this.connection = connection;
    }

    public void insert_TO_Data (DTO dto) throws SQLException {
        DAO.insert(connection,dto);
    }

    public void delete (DTO dto) throws SQLException {
        DAO.delete(connection,dto);
    }

    public void getByID (DTO dto , int id) throws SQLException {
        DAO.getById(connection,dto,id);
    }

    public void getByID (DTO dto , String id) throws SQLException {
    }





}
