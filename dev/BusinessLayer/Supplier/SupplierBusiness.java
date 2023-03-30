package BusinessLayer.Supplier;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class SupplierBusiness {
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

    public boolean isProductExists(String productName, String manufacturer) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            SupplierProductBusiness sp = entry.getValue();
            if (sp.getManufacturer() == manufacturer && sp.getProductName() == productName)
                return true;
        }
        return false;
    }

    private void addProduct(String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){

    }

    private void deleteProduct(int productNum){
        products.remove(productNum);
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

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        this.name = name;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.constDeliveryDays = constDeliveryDays;
        this.selfDelivery = selfDelivery;
        this.products = products;
    }

    public int getBankAccountNum() {
        return bankAccountNum;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public List<String> getConstDeliveryDays() {
        return constDeliveryDays;
    }

    public Map<String, Integer> getContacts() {
        return contacts;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
    public boolean getSelfDelivery(){
        return selfDelivery;
    }

    public Map<Integer, SupplierProductBusiness> getProducts() {
        return products;
    }
}
