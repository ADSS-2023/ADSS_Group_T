package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDalController {
    private ItemDAO item_DAO;
    private ItemPerOrderDAO item_per_order_DAO;
    private DAO generic_DAO;
    private Connection connection;
    public ItemDalController(Connection connection){
        item_DAO = new ItemDAO();
        item_per_order_DAO = new ItemPerOrderDAO();
        generic_DAO = new DAO();
        this.connection = connection;
    }
    public void insert(DTO new_dto) throws SQLException {
        generic_DAO.insert(connection, new_dto);
    }

    public void update(DTO old_dto , DTO new_dto) throws SQLException {
        generic_DAO.update(connection , old_dto , new_dto);
    }
}

