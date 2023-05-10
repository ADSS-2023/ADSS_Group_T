package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DalController.OrderDalController;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.*;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collections;

public class ServiceFactory {
    private Util_Supplier_Stock uss;
    public SupplierController sc;
    public OrderController oc;
    public SupplierService supplierService;
    public OrderService orderService;
    public InventoryService inventoryService;
    public CategoryService categoryService;
    public DamagedService damagedService;
    public ItemService itemService;
    public ManageOrderService manageOrderService;

    public SupplierDalController supplierDalController;

    public OrderDalController orderDalController;

    public Connection connection;

    public ServiceFactory() throws Exception {

        this.inventoryService = new InventoryService();
        this.categoryService = new CategoryService(inventoryService.get_inventory());
        this.damagedService = new DamagedService(inventoryService.get_inventory());
        this.itemService = new ItemService(inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService();
        this.supplierDalController = new SupplierDalController(connection);
        this.orderDalController = new OrderDalController(connection);
        this.sc = new SupplierController(connection, supplierDalController);
        this.oc = new OrderController(sc,manageOrderService, connection, orderDalController);
        this.supplierService = new SupplierService(sc,oc);
        this.orderService = new OrderService(oc,sc);

        manageOrderService.setOrderController(inventoryService.get_inventory(),orderService);
    }

    private Connection makeCon(){
        try {
            String dbFile = "dev/DataLayer/stock_supplier_db.db"; // JDBC conection parameters
            String url = "jdbc:sqlite:" + dbFile;
            // Register the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // sen it to the constructor
            // Establish the connection
            return DriverManager.getConnection(url);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public String nextDay(){
        try{
            uss.nextDay();
            return "Action succeeded";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
