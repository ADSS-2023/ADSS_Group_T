package ServiceLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import Util.Discounts;

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

    public String addSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products) throws Exception {
        try{
            sc.addSupplier(name,address,supplierNum,bankAccountNum,contacts,constDeliveryDays, selfDelivery,products);
            return "";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    public void deleteSupplier(int supplierNum){
        sc.deleteSupplier(supplierNum);
    }

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products) throws Exception {
        sc.getSupplier(supplierNum).editSupplier(name, address, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products);
    }
    public List<String> getProducts(int supplierNum){
        HashMap<Integer, SupplierProductBusiness> productMap =  sc.getProducts(supplierNum);
        List<String> products = new ArrayList<String>();
        for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet()) {
            products.add(entry.getKey() + entry.getValue().toString());
        }
        return products;
    }

    public void addProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        sc.getSupplier(supplierNum).addProduct(productNum, productName, manufacturer, price, maxAmount, expiredDate);
    }

    public void editProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        sc.getSupplier(supplierNum).editProduct(productNum, productName, manufacturer, price, maxAmount, expiredDate);
    }

    public void deleteProduct(int supplierNum, int productNum) throws Exception {
        sc.getSupplier(supplierNum).deleteProduct(productNum);
    }

    public void editSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discountToChange,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).editSupplierDiscount(discountEnum, amount, discountToChange,isPercentage);
    }

    public void addSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).addSupplierDiscount(discountEnum, amount, discount,isPercentage);
    }

    public void deleteSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).deleteSupplierDiscount(discountEnum, amount,isPercentage);
    }

    public void editProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).editProductDiscount(productNum, productAmount, discount,isPercentage);
    }

    public void addProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).addProductDiscount(productNum, productAmount, discount,isPercentage);
    }

    public void deleteProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        sc.getSupplier(supplierNum).deleteProductDiscount(productNum, productAmount, discount,isPercentage);
    }
}