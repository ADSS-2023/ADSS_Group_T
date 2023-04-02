package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.PrecentDiscount;
import BusinessLayer.Supplier.Discounts.QuantityDiscount;
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

    private List<Discount> discountPerTotalQuantity;

    private List<Discount> discountPerTotalPrice;

    public SupplierBusiness(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice){
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

    public void addProduct(int productNum, String productName, String manufacturer, int price, int maxAmount, List<Discount> quantitiesAgreement, LocalDateTime expiredDate){
        products.put(productNum, new SupplierProductBusiness( supplierNum,productName,productNum, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate));
    }
    public void editProduct(int productNum, String productName, String manufacturer, int price, int maxAmount, List<Discount> quantitiesAgreement, LocalDateTime expiredDate){
        products.put(productNum, new SupplierProductBusiness(supplierNum, productName,productNum, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate));
    }

    public void deleteProduct(int productNum){
        products.remove(productNum);
    }

    public void editProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage){
        getSupplierProduct(productNum).editProductDiscount(productAmount, discount);
    }

    public void addProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage){
        getSupplierProduct(productNum).addProductDiscount(productAmount, discount);
    }

    public void deleteProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage){
        getSupplierProduct(productNum).deleteProductDiscount(productAmount, discount);
    }

    public void editSupplierDiscount(Discounts discountEnum, int amount, int discountToChange,boolean isPercentage) throws Exception {
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
                     discountPerTotalPrice.add(new PrecentDiscount(amount,discount));
                else
                    discountPerTotalPrice.add(new QuantityDiscount(amount,discount));

            case DISCOUNT_BY_TOTAL_QUANTITY:
                if(isPercentage)
                    discountPerTotalQuantity.add(new PrecentDiscount(amount,discount));
                else
                    discountPerTotalQuantity.add(new QuantityDiscount(amount,discount));
                break;
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

    public void deleteSupplierDiscount(Discounts discountEnum, int amount, boolean isPercentage) {
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

    public SupplierProductBusiness getSupplierProduct(int productNumber){
        return products.get(productNumber);
    }

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice){
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

    //this function gets products number in the order, and old total price and returns new price
    public int getPriceAfterTotalDiscount(int totalProductsNum, int totalOrderPrice) {
        Discount dis1 =null;
        int discount1=0;
        Discount dis2 =null;
        int discount2=0;
        //loop for number of products discounts
        for (Discount currentDiscount : discountPerTotalPrice) {
            if(currentDiscount.getAmount()<totalOrderPrice)
                dis1 = currentDiscount;
        }
        if(dis1!=null)
            discount1=totalOrderPrice-dis1.getPriceAfterDiscount(totalOrderPrice);


        for (Discount currentDiscount : discountPerTotalQuantity) {
            if(currentDiscount.getAmount()<totalProductsNum)
                dis2 = currentDiscount;
        }
        if(dis2!=null)
            discount2=totalOrderPrice-dis2.getPriceAfterDiscount(totalOrderPrice);

        return totalOrderPrice-discount1-discount2;


    }
}
