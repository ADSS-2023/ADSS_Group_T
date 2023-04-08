package ServiceLayer.Supplier;

import BusinessLayer.Supplier.SupplierController;

public class SupplierProductService {
    private SupplierController sc;

    public SupplierProductService(SupplierController sc){
        this.sc = sc;
    }
    private void editDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.editDiscount(vendorNum, productNum, productAmount, discount);
    }

    private void addDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.addDiscount(vendorNum, productNum, productAmount, discount);
    }

    private void deleteDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.deleteDiscount(vendorNum, productNum, productAmount, discount);
    }
}
