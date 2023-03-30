package BusinessLayer.Supplier;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class    SupplierBusiness {
    private String name;
    private String address;
    private int supplierNum;
    private int bankAccountNum;
    private Map<String, Integer> contacts;
    private List<String> constDeliveryDays;
    private boolean selfDelivery;
    private Map<Integer, SupplierProductBusiness> products;

    public SupplierBusiness(String name, String address, int supplierNum,int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        this.name = name;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.constDeliveryDays = constDeliveryDays;
        this.selfDelivery = selfDelivery;
        this.products = products;
    }

    private void addProduct(String productName, String manufacturer, int price, int maxAmount){

    }
    private void editDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).editDiscount(productAmount, discount);
    }

    private void addDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).addDiscount(productAmount, discount);
    }
    private void deleteDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).deleteDiscount(productAmount, discount);
    }

    private SupplierProductBusiness getSupplierProduct(int productNumber){
        return products.get(productNumber);
    }
    public Map<Integer, SupplierProductBusiness> getProducts(){

    }
}
