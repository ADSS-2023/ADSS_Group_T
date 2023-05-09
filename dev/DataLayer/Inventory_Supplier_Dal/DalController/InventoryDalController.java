package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.CategoryDTO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InventoryDalController{
    private CategoryDAO category_DAO;
    private DamagedItemDAO damaged_item_DAO;
    private DiscountDAO discount_DAO;
    private DAO generic_DAO;
    private Connection connection;

    public InventoryDalController(Connection connection, DAO dao){
        category_DAO = new CategoryDAO();
        generic_DAO = dao;
        this.connection = connection;
    }

    public void insert(DTO new_dto) throws SQLException {
        generic_DAO.insert(connection, new_dto);
    }

    public void update(DTO old_dto , DTO new_dto) throws SQLException {
        generic_DAO.update(connection , old_dto , new_dto);
    }
}
