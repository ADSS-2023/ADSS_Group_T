package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Stock.ManageOrderService;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.*;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class ServiceFactory {
    public SupplierController sc;
    public OrderController oc;
    public SupplierService supplierService;
    public OrderService orderService;
    public InventoryService inventoryService;
    public CategoryService categoryService;
    public DamagedService damagedService;
    public ItemService itemService;
    public ManageOrderService manageOrderService;
    public InventoryDalController inventoryDalController;
    public Connection connection = this.makeCon();
    public InventoryDalController inventoryDalController;
    public Connection connection;

    public ServiceFactory() {
        this.categoryService = new CategoryService(this.inventoryService.get_inventory());
        this.damagedService = new DamagedService(this.inventoryService.get_inventory());
        this.itemService = new ItemService(this.inventoryService.get_inventory());
    public ServiceFactory(){
        this.connection = makeCon();
        this.inventoryService = new InventoryService();
        this.categoryService = new CategoryService(inventoryService.get_inventory());
        this.damagedService = new DamagedService(inventoryService.get_inventory());
        this.itemService = new ItemService(inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService();
        this.sc = new SupplierController();
        this.oc = new OrderController(sc,manageOrderService);
        this.supplierService = new SupplierService(sc);
        this.orderService = new OrderService(oc,sc);
        DAO dao = new DAO();
        manageOrderService.setOrderController(inventoryService.get_inventory(),orderService);
        this.inventoryDalController = new InventoryDalController(connection , dao);
        inventoryService.get_inventory().setInventoryDalController(inventoryDalController);
    }

    private Connection makeCon(){
        try {
            String dbFile = "C:/liran/Program/SMSRT4/ADSS/ADSS_Group_T/dev/DataLayer/stock_supplier_db.db";
            // JDBC conection parameters
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

    public void dataSetUp() throws Exception {
        //need to clean the data manually!
        inventoryService.get_inventory().add_category("", "milk-product");
        itemService.addItem(".0",3, "Milky", 3, "Liran LTD", 2.0);
        this.oc = new OrderController(this.sc, this.manageOrderService);
        this.supplierService = new SupplierService(this.sc, this.oc);
        this.orderService = new OrderService(this.oc, this.sc);
        DAO dao = new DAO();
        this.manageOrderService.setOrderController(this.inventoryService.get_inventory(), this.orderService);
        this.inventoryDalController = new InventoryDalController(this.connection, dao);
        this.inventoryService.get_inventory().setInventoryDalController(this.inventoryDalController);
    }

    private Connection makeCon() {
        try {
            String dbFile = "C:/liran/Program/SMSRT4/ADSS/ADSS_Group_T/dev/DataLayer/stock_supplier_db.db";
            String url = "jdbc:sqlite:" + dbFile;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
            return null;
        }
    }

    public void dataSetUp() throws Exception {
        this.inventoryService.get_inventory().add_category("", "milk-product");
        this.itemService.addItem(".0", 3, "Milky", 3, "Liran LTD", 2.0D);
    }

    public String nextDay() {
        try {
            this.uss.nextDay();
            return "Action succeeded";
        } catch (Exception var2) {
            return var2.getMessage();
        }
    }
}