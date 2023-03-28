package BusinessLayer.Supplier;

import java.util.HashMap;

public class SupplierController {
    HashMap<Integer, SupplierBusiness> suppliers;

    public SupplierController(){
        suppliers = new HashMap<>();
    }

    public void addProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        suppliers.get(supplierNum).addProduct(productName, manufacturer, price, maxAmount);
    }

    public void editProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        suppliers.get(supplierNum).editProduct(productName, manufacturer, price, maxAmount);
    }

    public void deleteProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        suppliers.get(supplierNum).deleteProduct(productName, manufacturer, price, maxAmount);
    }
}
