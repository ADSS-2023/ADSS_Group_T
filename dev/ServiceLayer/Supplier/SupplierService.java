package ServiceLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import Util.Discounts;
import netscape.javascript.JSObject;

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
            return "Supplier added successfully";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    public String deleteSupplier(int supplierNum){
        try {
            sc.deleteSupplier(supplierNum);
            return "Supplier deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public String editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products) throws Exception {
        try {
            sc.getSupplier(supplierNum).editSupplier(name, address, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products);
            return "Supplier edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public List<String> getProducts(int supplierNum) {
        List<String> products = new ArrayList<String>();
        try {
            HashMap<Integer, SupplierProductBusiness> productMap = sc.getProducts(supplierNum);
            for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet())
                products.add(entry.getKey() + " " + entry.getValue().toString() + '\n');
        }
        catch (Exception e){
            products.add(e.getMessage());
        }
        finally {
            return products;
        }

    }

    public String addProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        try {
            sc.getSupplier(supplierNum).addProduct(productNum, productName, manufacturer, price, maxAmount, expiredDate);
            return "Product added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) throws Exception {
        try {
            sc.getSupplier(supplierNum).editProduct(productNum, productName, manufacturer, price, maxAmount, expiredDate);
            return "Product edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteProduct(int supplierNum, int productNum) throws Exception {
        try {
            sc.getSupplier(supplierNum).deleteProduct(productNum);
            return "Product deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discountToChange,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).editSupplierDiscount(discountEnum, amount, discountToChange,isPercentage);
            return "Supplier discount edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).addSupplierDiscount(discountEnum, amount, discount,isPercentage);
            return "Supplier discount added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).deleteSupplierDiscount(discountEnum, amount,isPercentage);
            return "Supplier discount deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).editProductDiscount(productNum, productAmount, discount,isPercentage);
            return "Product discount edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).addProductDiscount(productNum, productAmount, discount,isPercentage);
            return "Product discount added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage) throws Exception {
        try {
            sc.getSupplier(supplierNum).deleteProductDiscount(productNum, productAmount, discount,isPercentage);
            return "Product discount deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}