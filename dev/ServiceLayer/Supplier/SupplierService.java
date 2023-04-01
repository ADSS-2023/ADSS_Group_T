package ServiceLayer.Supplier;

import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierService {
    private SupplierController sc;

    public SupplierService(SupplierController sc){
        this.sc = sc;
    }

    public void addSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products){
        sc.addSupplier(name,address,supplierNum,bankAccountNum,contacts,constDeliveryDays, selfDelivery,products);
    }

    public void deleteSupplier(int supplierNum){
        sc.deleteSupplier(supplierNum);
    }

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products){
        sc.editSupplier(name,address,supplierNum,bankAccountNum,contacts,constDeliveryDays, selfDelivery,products);
    }
    public List<String> getProducts(int vendorNum){
        HashMap<Integer, SupplierProductBusiness> productMap =  sc.getProducts(vendorNum);
        List<String> products = new ArrayList<String>();
        for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet()) {
            products.add(entry.getKey() + entry.getValue().toString());
        }
        return products;
    }

    public void addProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        sc.addProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void editProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        sc.editProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void deleteProduct(int supplierNum, int productNum){
        sc.deleteProduct(supplierNum, productNum);
    }
}