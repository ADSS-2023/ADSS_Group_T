package ServiceLayer.Supplier;

import PresentationLayer.Supplier.SupplierManager;

public class Main {
    public static void main(String[] args) throws Exception {
        SupplierManager supplierManager = new SupplierManager();
        supplierManager.start();
    }
}
