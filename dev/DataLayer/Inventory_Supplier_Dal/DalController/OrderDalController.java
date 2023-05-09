package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderDAO;
import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderProductDAO;
import DataLayer.Util.DAO;

import java.sql.Connection;

public class OrderDalController {
    private DAO dao;
    private OrderDAO orderDAO;
    private OrderProductDAO orderProductDAO;
    private Connection connection;

    public OrderDalController(Connection connection, DAO dao) {
        this.orderDAO = new OrderDAO();
        this.orderProductDAO = new OrderProductDAO();
        this.connection = connection;
        this.dao = dao;
    }
}
