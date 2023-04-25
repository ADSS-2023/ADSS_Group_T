package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;
import ServiceLayer.Stock.*;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;

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

    public ServiceFactory(){
        this.sc = new SupplierController();
        this.oc = new OrderController(sc);
        this.supplierService = new SupplierService(sc);
        this.orderService = new OrderService(oc,sc);
        this.inventoryService = new InventoryService();
        this.categoryService = new CategoryService(inventoryService.get_inventory());
        this.damagedService = new DamagedService(inventoryService.get_inventory());
        this.itemService = new ItemService(inventoryService.get_inventory());
        this.manageOrderService = new ManageOrderService(inventoryService.get_inventory(),orderService);
    }
}
