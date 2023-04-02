package BusinessLayer.Supplier;

import Util.Discounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class    SupplierBusiness {
    private String name;
    private String address;
    private int supplierNum;
    private int bankAccountNum;
    private HashMap<String, Integer> contacts;
    private List<String> constDeliveryDays;
    private boolean selfDelivery;
    private HashMap<Integer, SupplierProductBusiness> products;

    private HashMap<Integer, Integer> discountPerTotalQuantity;

    private HashMap<Integer, Integer> discountPerTotalPrice;

    public SupplierBusiness(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products, HashMap<Integer, Integer> discountPerTotalQuantity, HashMap<Integer, Integer> discountPerTotalPrice){
        this.name = name;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.constDeliveryDays = constDeliveryDays;
        this.selfDelivery = selfDelivery;
        this.products = products;
        this.discountPerTotalQuantity = discountPerTotalQuantity;
        this.discountPerTotalPrice = discountPerTotalPrice;
    }

    public boolean isProductExists(String productName, String manufacturer) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            SupplierProductBusiness sp = entry.getValue();
            if (sp.getManufacturer().equals(manufacturer) && sp.getName().equals(productName))
                return true;
        }
        return false;
    }

    public void addProduct(int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        products.put(productNum, new SupplierProductBusiness( supplierNum,productName,productNum, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate));
    }
    public void editProduct(int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        products.put(productNum, new SupplierProductBusiness(supplierNum, productName,productNum, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate));
    }

    public void deleteProduct(int productNum){
        products.remove(productNum);
    }
    public void editProductDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).editProductDiscount(productAmount, discount);
    }

    public void addProductDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).addProductDiscount(productAmount, discount);
    }

    public void deleteProductDiscount(int productNum, int productAmount, int discount){
        getSupplierProduct(productNum).deleteProductDiscount(productAmount, discount);
    }

    public void editSupplierDiscount(Discounts discountEnum, int amount, int discount) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                if (discountPerTotalPrice.containsKey(amount))
                    discountPerTotalPrice.put(amount, discount);
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                if (discountPerTotalQuantity.containsKey(amount))
                    discountPerTotalQuantity.put(amount, discount);
                break;
        }
    }

    public void addSupplierDiscount(Discounts discountEnum, int amount, int discount) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                discountPerTotalPrice.put(amount, discount);
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                discountPerTotalQuantity.put(amount, discount);
                break;
        }
    }

    public void deleteSupplierDiscount(Discounts discountEnum, int amount, int discount) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                discountPerTotalPrice.remove(amount, discount);
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                discountPerTotalQuantity.remove(amount, discount);
                break;
        }
    }

    public SupplierProductBusiness getSupplierProduct(int productNumber){
        return products.get(productNumber);
    }

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products, HashMap<Integer, Integer> discountPerTotalQuantity, HashMap<Integer, Integer> discountPerTotalPrice){
        this.name = name;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.constDeliveryDays = constDeliveryDays;
        this.selfDelivery = selfDelivery;
        this.products = products;
        this.discountPerTotalQuantity = discountPerTotalQuantity;
        this.discountPerTotalPrice = discountPerTotalPrice;
    }

    public SupplierProductBusiness getProduct(String productName, String manufacturer) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            SupplierProductBusiness sp = entry.getValue();
            if (sp.getManufacturer().equals(manufacturer) && sp.getName().equals(productName))
                return sp;
        }
        return null;
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

    public HashMap<Integer, SupplierProductBusiness> getProducts() {
        return products;
    }
}
