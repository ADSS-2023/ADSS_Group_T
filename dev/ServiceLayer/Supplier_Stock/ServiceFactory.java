package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.*;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;

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
    public Connection connection;

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
    }
}
