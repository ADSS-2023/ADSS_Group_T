package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.PercentDiscount;
import BusinessLayer.Supplier.Discounts.PercentDiscount;
import BusinessLayer.Supplier.Discounts.QuantityDiscount;
import Util.Discounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class    SupplierBusiness {
    private String supplierName;
    private String address;
    private int supplierNum;
    private int bankAccountNum;
    private HashMap<String, String> contacts;
    private List<String> constDeliveryDays;
    private boolean selfDelivery;
    private HashMap<Integer, SupplierProductBusiness> products;

    private List<Discount> discountPerTotalQuantity;

    private List<Discount> discountPerTotalPrice;

    public SupplierBusiness(String supplierName, String address, int supplierNum,int bankAccountNum, HashMap<String, String> contacts, List<String> constDeliveryDays, boolean selfDelivery){
        this.supplierName = supplierName;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.constDeliveryDays = constDeliveryDays;
        this.selfDelivery = selfDelivery;
        this.products = new HashMap<>();
        this.discountPerTotalQuantity = new ArrayList<>();
        this.discountPerTotalPrice = new ArrayList<>();
    }

    public SupplierProductBusiness getProduct(String productName, String manufacturer) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            SupplierProductBusiness sp = entry.getValue();
            if (sp.getManufacturer().equals(manufacturer) && sp.getName().equals(productName))
                return sp;
        }
        return null;
    }

    public void addProduct(int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        if (getProduct(productName,manufacturer) != null)
            throw new Exception("product already exists.");
        else if(expiredDate.isBefore(LocalDateTime.now()))
            throw new Exception("expired date has passed.");
        else
            products.put(productNum, new SupplierProductBusiness( supplierNum,productName,productNum, manufacturer, price, maxAmount, expiredDate));
    }
    public void editProduct(String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        if(expiredDate.isBefore(LocalDateTime.now()))
            throw new Exception("expired date has passed.");
        SupplierProductBusiness sp = getProduct(productName,manufacturer);
        if (sp != null)
            sp.editProduct(supplierNum, productName, manufacturer, price, maxAmount, expiredDate);
        else
            throw new Exception("product is not exists.");
    }

    public void deleteProduct(int productNum) throws Exception {
        if(!products.containsKey(productNum))
            throw new Exception("product is not exists.");
        products.remove(productNum);
    }

    public void editProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product is not exists.");
        getSupplierProduct(productNum).editProductDiscount(productAmount, discount, isPercentage);
    }

    public void addProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product is not exists.");
        getSupplierProduct(productNum).addProductDiscount(productAmount, discount, isPercentage);
    }

    public void deleteProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product is not exists.");
        getSupplierProduct(productNum).deleteProductDiscount(productAmount, discount, isPercentage);
    }



    public void editSupplierDiscount(Discounts discountEnum, int amount, int discountToChange, boolean isPercentage) throws Exception {
        if(!isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("No such Discount");
        getDiscount(discountEnum,amount,isPercentage).editDiscount(amount,discountToChange);
    }

    public void addSupplierDiscount(Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        if(isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("Discount is already exists");
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                if(isPercentage)
                     discountPerTotalPrice.add(new PercentDiscount(amount,discount,true));
                else
                    discountPerTotalPrice.add(new QuantityDiscount(amount,discount,false));

            case DISCOUNT_BY_TOTAL_QUANTITY:
                if(isPercentage)
                    discountPerTotalQuantity.add(new PercentDiscount(amount,discount,true));
                else
                    discountPerTotalQuantity.add(new QuantityDiscount(amount,discount, false));
                break;
        }
    }

    public void deleteSupplierDiscount(Discounts discountEnum, int amount, boolean isPercentage) throws Exception {
        if(!isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("No such Discount");
        switch (discountEnum) {
            case DISCOUNT_BY_TOTAL_PRICE:
                for (Discount dis : discountPerTotalPrice) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        discountPerTotalPrice.remove(dis);
                }
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                for (Discount dis : discountPerTotalQuantity) {
                    if (dis.getAmount() == amount&& dis.isPercentage() == isPercentage)
                        discountPerTotalQuantity.remove(dis);
                    break;
                }
        }
    }

    public boolean isDiscountExist(Discounts discountEnum,int amount, boolean isPercentage) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                for (Discount dis : discountPerTotalPrice) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return true;
                }
            case DISCOUNT_BY_TOTAL_QUANTITY:
                for (Discount dis : discountPerTotalQuantity) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return true;
                }
        }
        return false;
    }

    public Discount getDiscount(Discounts discountEnum,int amount, boolean isPercentage) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                for (Discount dis : discountPerTotalPrice) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                       return dis;
                }
            case DISCOUNT_BY_TOTAL_QUANTITY:
                for (Discount dis : discountPerTotalQuantity) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return dis;
                }
        }
        return null;
    }

    public SupplierProductBusiness getSupplierProduct(int productNumber){
        return products.get(productNumber);
    }

    public SupplierProductBusiness getSupplierProduct(String productName, String manufacture) throws Exception {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            if(entry.getValue().getName().equals(productName) && entry.getValue().getManufacturer().equals(manufacture))
                return entry.getValue();
        }
        throw new Exception("the product is not exists.");
    }

    public boolean isSupplierProuctExist(String productName, String manufacture) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            if(entry.getValue().getName().equals(productName) && entry.getValue().getManufacturer().equals(manufacture))
               return true;
        }
        return false;
    }

    public void editSupplier(String supplierName, String address, int bankAccountNum, boolean selfDelivery){
        this.supplierName = supplierName;
        this.address = address;
        this.bankAccountNum = bankAccountNum;
        this.selfDelivery = selfDelivery;
    }

    //this function gets products number in the order, and old total price and returns new price
//this function gets products number in the order, and old total price and returns new price
    public float getPriceAfterTotalDiscount(int totalProductsNum, float totalOrderPrice) {
        float discount1 = 0;
        float discount2 = 0;

        //loop for number of products discounts
        for (Discount currentDiscount : discountPerTotalQuantity) {
            if (totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice) > discount1 && currentDiscount.getAmount() <= totalProductsNum) {
                discount1 = totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice);
            }
        }
        totalOrderPrice = totalOrderPrice- discount1;
        //loop for total price discounts
        for (Discount currentDiscount : discountPerTotalPrice) {
            if (totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice) > discount2 && currentDiscount.getAmount() <= totalOrderPrice) {
                discount2 = currentDiscount.getDiscount();
            }
        }
        return totalOrderPrice - discount2;
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

    public Map<String, String> getContacts() {
        return contacts;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return supplierName;
    }

    public List<Discount> getDiscountPerTotalQuantity() {
        return discountPerTotalQuantity;
    }

    public List<Discount> getDiscountPerTotalPrice() {
        return discountPerTotalPrice;
    }

    public boolean isDelivering(){
        return selfDelivery;
    }

    public HashMap<Integer, SupplierProductBusiness> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "{" +
                "Supplier Name:" + supplierName + '\'' +
                ", Address:'" + address + '\'' +
                ", Supplier Number: " + supplierNum +
                '}';
    }

}
