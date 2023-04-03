package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;

public class ServiceFactory {
    public SupplierController sc;
    public OrderController oc;
    public SupplierService supplierService;
    public OrderService orderService;

    public ServiceFactory(){
        this.sc = new SupplierController();
        this.oc = new OrderController(sc);
        this.supplierService = new SupplierService(sc);
        this.orderService = new OrderService(oc);
    }
}
