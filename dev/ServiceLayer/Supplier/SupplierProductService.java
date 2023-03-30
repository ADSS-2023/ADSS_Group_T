package ServiceLayer.Supplier;

import BusinessLayer.Supplier.SupplierController;

public class SupplierProductService {
    private SupplierController sc;

    public SupplierProductService(SupplierController sc){
        this.sc = sc;
    }
    public void editDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.editDiscount(vendorNum, productNum, productAmount, discount);
    }

    public void addDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.addDiscount(vendorNum, productNum, productAmount, discount);
    }

    public void deleteDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        sc.deleteDiscount(vendorNum, productNum, productAmount, discount);
    }
}
