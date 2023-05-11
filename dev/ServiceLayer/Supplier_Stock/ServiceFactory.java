package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import ServiceLayer.Supplier.OrderService;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DalController.*;
//import DataLayer.Inventory_Supplier_Dal.DalController.ItemDalController;
import DataLayer.Util.DAO;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Stock.ManageOrderService;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Inventory_Supplier_Dal.DalController.ItemDalController;
import ServiceLayer.Supplier.SupplierService;
import java.sql.*;
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
    public SupplierDalController supplierDalController;
    public OrderDalController orderDalController;

    public Connection connection;
    public Util_Supplier_Stock uss;

    public ServiceFactory()  {
        this.connection = makeCon();
        this.inventoryService = new InventoryService();
        this.categoryService = new CategoryService(this.inventoryService.get_inventory());
        this.damagedService = new DamagedService(this.inventoryService.get_inventory());
        this.itemService = new ItemService(this.inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService();
        this.supplierDalController = new SupplierDalController(connection);
        this.orderDalController = new OrderDalController(connection);
        this.sc = new SupplierController(connection, supplierDalController);
        this.oc = new OrderController(this.sc, this.manageOrderService, connection, orderDalController);
        this.supplierService = new SupplierService(this.sc, this.oc);
        this.orderService = new OrderService(this.oc, this.sc);
        inventoryDalController = new InventoryDalController(connection);
        this.manageOrderService.setOrderController(this.inventoryService.get_inventory(), this.orderService,inventoryDalController);
        this.inventoryService.get_inventory().setInventoryDalController(inventoryDalController);
        inventoryService.get_inventory().setItemDalController(new ItemDalController(connection));
        try {
            uss = new Util_Supplier_Stock(inventoryDalController);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        //this.deleteAllData();
        try {
            uss = new Util_Supplier_Stock(inventoryDalController);
        }
        catch (Exception e){

        }
        //inventoryDalController = new InventoryDalController(connection);
        //this.manageOrderService.setOrderController(this.inventoryService.get_inventory(), this.orderService,inventoryDalController);
        //this.inventoryService.get_inventory().setInventoryDalController(inventoryDalController);
        //inventoryService.get_inventory().setItemDalController(new ItemDalController(connection));
        /*this.deleteAllData();*/
    }

    private Connection makeCon() {
        try {
            String dbFile = "./dev/DataLayer/stock_supplier_db.db";
            String url = "jdbc:sqlite:" + dbFile;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
            return null;

        }
    }

    public void deleteAllData(){
        String[] table_names = {"inventory_categories" , "inventory_item", "inventory_item_ordered"
                , "inventory_item_per_order" , "inventory_damaged_items" , "inventory_discount",
                "inventory_waiting_list" , "supplier_const_delivery_days" ,
                "supplier_discount" , "supplier_order" , "supplier_order_product" , "supplier_supplier" ,
                "supplier_supplier_contact" ,"supplier_supplier_product"};
        for(int i = 0 ; i < table_names.length ; i++) {
            try {
                inventoryDalController.getDAO().deleteAll(connection, table_names[i]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

//    public void dataSetUp() throws Exception {
//        //need to clean the data manually!
//        inventoryService.get_inventory().add_category("", "milk-product");
//        itemService.addItem(".0",3, "Milky", 3, "Liran LTD", 2.0);
//        this.oc = new OrderController(this.sc, this.manageOrderService);
//        this.supplierService = new SupplierService(this.sc, this.oc);
//        this.orderService = new OrderService(this.oc, this.sc);
//        DAO dao = new DAO();
//        this.manageOrderService.setOrderController(this.inventoryService.get_inventory(), this.orderService,inventoryDalController);
//        this.inventoryDalController = new InventoryDalController(this.connection);
//        this.inventoryService.get_inventory().setInventoryDalController(this.inventoryDalController);
//    }


    public String nextDay() {
        try {
            this.uss.nextDay();
            return "Action succeeded";
        } catch (Exception var2) {
            return var2.getMessage();
        }
    }


    private Connection makeEmptyCon() {
        try {
            String dbFile = "./dev/DataLayer/stock_supplier_empty_db.db";
            String url = "jdbc:sqlite:" + dbFile;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
            return null;

        }
    }


    public void makeEmptyDB() {
        connection = makeEmptyCon();
        this.inventoryDalController.setConnection(connection);
        this.inventoryService.get_inventory().getItemDalController().setConnection(connection);
    }
}