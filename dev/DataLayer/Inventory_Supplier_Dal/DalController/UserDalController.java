package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.*;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserDalController {
    private DAO dao;

    private Connection connection;

    public UserDalController(Connection connection) {
        this.connection = connection;
        this.dao = new DAO();
    }

    public void insert(DTO dto) throws SQLException {
        dao.insert(connection, dto);
    }
    public void update(DTO oldDto, DTO newDto) throws SQLException {
        dao.update(connection, oldDto, newDto);
    }

    public void delete(DTO dto) throws SQLException {
        dao.delete(connection, dto);
    }

    public <T extends DTO> T find(LinkedHashMap<String,Object> pk, String tableName, Class<T> dtoClass) throws SQLException {
        return dao.find(pk, tableName,dtoClass, connection);
    }
    public <T extends DTO> ArrayList<T> findAll  (String tableName, Class<T> DTOName) throws SQLException {
        return dao.findAll(tableName,DTOName,connection);
    }

    public <T extends DTO> ArrayList<T> findAllOfCondition(String tableName, String conditionKey,Object conditionValue,Class<T> classDTO) throws SQLException {
        return dao.findAllOfCondition(tableName, conditionKey, conditionValue, classDTO, connection);
    }
}
