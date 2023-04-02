package ServiceLayer.Supplier;

import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.SupplierController;

public class ServiceFactory {
    private SupplierController sc;
    private OrderController oc;
    private SupplierService supplierService;
    private OrderService orderService;

    public ServiceFactory(SupplierController sc, OrderController oc, SupplierService supplierService, OrderService orderService){
        this.sc = sc;
        this.oc = oc;
        this.supplierService = supplierService;
        this.orderService = orderService;
    }

    public void setUpData(){

    }
}
