//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Stock.ManageOrderService;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;
import java.sql.Connection;
import java.sql.DriverManager;

public class ServiceFactory {
    public SupplierController sc;
    public OrderController oc;
    public SupplierService supplierService;
    public OrderService orderService;
    public InventoryService inventoryService = new InventoryService();
    public CategoryService categoryService;
    public DamagedService damagedService;
    public ItemService itemService;
    public ManageOrderService manageOrderService;
    public InventoryDalController inventoryDalController;
    public Connection connection = this.makeCon();
    public Util_Supplier_Stock uss;

    public ServiceFactory() {
        this.categoryService = new CategoryService(this.inventoryService.get_inventory());
        this.damagedService = new DamagedService(this.inventoryService.get_inventory());
        this.itemService = new ItemService(this.inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService();
        this.sc = new SupplierController();
        this.oc = new OrderController(this.sc, this.manageOrderService);
        this.supplierService = new SupplierService(this.sc, this.oc);
        this.orderService = new OrderService(this.oc, this.sc);
        uss = new Util_Supplier_Stock();
        DAO dao = new DAO();
        this.manageOrderService.setOrderController(this.inventoryService.get_inventory(), this.orderService);
        this.inventoryDalController = new InventoryDalController(connection, dao);
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