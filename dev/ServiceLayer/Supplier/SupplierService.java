package ServiceLayer.Supplier;

import BusinessLayer.Supplier.SupplierProductBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierService {
    private SupplierController sc;

    public SupplierService(SupplierController sc){
        this.sc = sc;
    }
    public List<String> getProducts(int vendorNum){
        HashMap<Integer, SupplierProductBusiness> productMap =  sc.getProducts(vendorNum);
        List<String> products = new ArrayList<String>();
        for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet()) {
            products.add(entry.getKey() + entry.getValue().toString());
        }
        return products;
    }

    public void addProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        sc.addProduct(supplierNum, productName, manufacturer, price, maxAmount);
    }

    public void editProduct(int supplierNum, String ProductName, String manufacturer, int price, int maxAmount){
        sc.editProduct(supplierNum, productName, manufacturer, price, maxAmount);
    }

    public void deleteProduct(int supplierNum, String ProductName, String manufacturer, int price, int maxAmount){
        sc.deleteProduct(supplierNum, productName, manufacturer, price, maxAmount);
    }
}