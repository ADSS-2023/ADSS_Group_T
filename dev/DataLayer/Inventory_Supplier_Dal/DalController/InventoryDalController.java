package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.CategoryDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemOrderdDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.ItemToOrderDTO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class InventoryDalController{
    private CategoryDAO category_DAO;
    private DamagedItemDAO damaged_item_DAO;
    private DiscountDAO discount_DAO;
    private DAO generic_DAO;
    private Connection connection;

    public InventoryDalController(Connection connection){
        category_DAO = new CategoryDAO();
        generic_DAO = new DAO();
        this.connection = connection;
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public void delete(DTO dto) throws SQLException {
        generic_DAO.delete(connection,dto);
    }

    public void insert(DTO new_dto) throws SQLException {
        generic_DAO.insert(connection, new_dto);
    }

    public void update(DTO old_dto , DTO new_dto) throws SQLException {
        generic_DAO.update(connection , old_dto , new_dto);
    }

    public DAO getDAO() {
        return generic_DAO;
    }
    public <T extends DTO> T find(Object pk,String pkName, String tableName, Class<T> dtoClass) throws SQLException {
        return generic_DAO.find(pk,pkName,tableName,dtoClass,connection);
    }
    public <T extends DTO> ArrayList<T> findAll(String tableName, Class<T> dtoClass) throws SQLException {
        return generic_DAO.findAll(tableName,dtoClass,connection);
    }
    public <T extends DTO> ArrayList<T> findAllOfCondition(String tableName, String conditionKey,Object conditionValue,Class<T> classDTo) throws SQLException{
        return generic_DAO.findAllOfCondition(tableName,conditionKey,conditionValue,classDTo,connection);
    }
    public <T extends DTO> ArrayList<T> findAllCategories(String tableName, Class<T> dtoClass) throws SQLException {
        return category_DAO.findAll(tableName,dtoClass,connection);
    }


}
