package ServiceLayer.Supplier;

import BusinessLayer.Supplier.SupplierController;

public class Main {
    public static void main(String[] args) {
        SupplierManager supplierManager = new SupplierManager();
        supplierManager.start();
    }
}
