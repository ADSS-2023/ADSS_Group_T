package BusinessLayer.Supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        suppliers.put(supplierNum, new SupplierBusiness(name,address,supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products));
    }

    public void deleteSupplier(int supplierNum){
        suppliers.remove(supplierNum);
    }

    public void editSupplier(addSupplier(String name, String address, int supplierNum,int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        suppliers.get(supplierNum).editSupplier(name,address, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products);
    }
}
