package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderDAO;
import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderProductDAO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDalController {
    private DAO dao;
    private OrderDAO orderDAO;
    private OrderProductDAO orderProductDAO;
    private Connection connection;

    public OrderDalController(Connection connection) {
        this.orderDAO = new OrderDAO();
        this.orderProductDAO = new OrderProductDAO();
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


}
