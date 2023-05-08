package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import ServiceLayer.Stock.*;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;

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

    public ServiceFactory(){
        uss = new Util_Supplier_Stock();
        this.inventoryService = new InventoryService();
        this.categoryService = new CategoryService(inventoryService.get_inventory());
        this.damagedService = new DamagedService(inventoryService.get_inventory());
        this.itemService = new ItemService(inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService();
        this.sc = new SupplierController();
        this.oc = new OrderController(sc,manageOrderService);
        this.supplierService = new SupplierService(sc);
        this.orderService = new OrderService(oc,sc);

        manageOrderService.setOrderController(inventoryService.get_inventory(),orderService);
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
