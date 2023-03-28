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

    public void addProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount){
        sc.addProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount);
    }

    public void editProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount){
        sc.editProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount);
    }

    public void deleteProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount){
        sc.deleteProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount);
    }
}