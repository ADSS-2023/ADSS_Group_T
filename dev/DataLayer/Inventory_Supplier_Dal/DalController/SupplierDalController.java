package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.*;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.SQLException;

public class SupplierDalController {

    private DAO dao;
    private SupplierDAO supplierDAO;
    private SupplierProductDAO supplierProductDAO;
    private SupplierContactDAO supplierContactDAO;
    private SupplierDiscountDAO supplierDiscountDAO;
    private ProductDiscountDAO productDiscountDAO;

    private Connection connection;

    public SupplierDalController(Connection connection) {
        this.supplierDAO = new SupplierDAO();
        this.supplierProductDAO = new SupplierProductDAO();
        this.supplierContactDAO = new SupplierContactDAO();
        this.supplierDiscountDAO = new SupplierDiscountDAO();
        this.productDiscountDAO = new ProductDiscountDAO();
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
