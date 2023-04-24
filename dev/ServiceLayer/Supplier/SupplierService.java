package ServiceLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.Suppliers.ConstantSupplier;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import Util.Discounts;
import Util.PaymentTerms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SupplierService {
    private SupplierController sc;
    private OrderController oc;

    public SupplierService(SupplierController sc){
        this.sc = sc;
    }

    public String addSupplier(String name, String address, int supplierNum, int bankAccountNum, int daysToDeliver, HashMap<String, String> contacts, List<String> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms){
        try{
            sc.addSupplier(name,address,supplierNum,bankAccountNum,contacts,constDeliveryDays, selfDelivery, paymentTerms, daysToDeliver);
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

    public String editSupplier(String name, String address, int supplierNum, int bankAccountNum, boolean selfDelivery, PaymentTerms paymentTerms){
        try {
            sc.getSupplier(supplierNum).editSupplier(name, address, bankAccountNum, selfDelivery,paymentTerms);
            return "Supplier edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public List<String> getProducts(int supplierNum) {
        List<String> products = new LinkedList<>();
        try {
            HashMap<Integer, SupplierProductBusiness> productMap = sc.getProducts(supplierNum);
            for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet())
                products.add(entry.getValue().toString() + '\n');
        }
        catch (Exception e){
            products = new LinkedList<>();
            products.add(e.getMessage());
        }
        finally {
            return products;
        }
    }

    public List<String> getAllProducts() {
        List<String> productsStrings = new LinkedList<>();
        HashMap<Integer, SupplierProductBusiness> products = new HashMap<>();
        try {
            HashMap<Integer, SupplierBusiness> suppliers=sc.getSuppliers();
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                products = entry.getValue().getProducts();
                for (Map.Entry<Integer, SupplierProductBusiness> entry2 : products.entrySet())
                    productsStrings.add(entry2.getValue().toString() + '\n');
            }
        }
        catch (Exception e){
            productsStrings = new LinkedList<>();
            productsStrings.add(e.getMessage());
        }
        finally {
            return productsStrings;
        }
    }

    public String addProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, LocalDate expiredDate){
        try {
            sc.getSupplier(supplierNum).addProduct(productNum, productName, manufacturer, price, maxAmount, expiredDate);
            return "Product added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount, LocalDate expiredDate){
        try {
            sc.getSupplier(supplierNum).editProduct(productName, manufacturer, price, maxAmount, expiredDate);
            if(sc.getSupplier(supplierNum) instanceof ConstantSupplier){
                SupplierProductBusiness sProduct = sc.getSupplier(supplierNum).getProduct(productNum);
                oc.editRegularItem(sProduct.getName(), sProduct.getManufacturer(), supplierNum, ((ConstantSupplier) sc.getSupplier(supplierNum)).getConstDeliveryDays());
            }
            return "Product edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteProduct(int supplierNum, int productNum){
        try {
            sc.getSupplier(supplierNum).deleteProduct(productNum);
            if(sc.getSupplier(supplierNum) instanceof ConstantSupplier){
                SupplierProductBusiness sProduct = sc.getSupplier(supplierNum).getProduct(productNum);
                oc.removeRegularItem(sProduct.getName(), sProduct.getManufacturer(), supplierNum, ((ConstantSupplier) sc.getSupplier(supplierNum)).getConstDeliveryDays());
            }
            LocalDate.now().getDayOfWeek()
            return "Product deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discountToChange,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).editSupplierDiscount(discountEnum, amount, discountToChange,isPercentage);
            return "Supplier discount edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).addSupplierDiscount(discountEnum, amount, discount,isPercentage);
            return "Supplier discount added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).deleteSupplierDiscount(discountEnum, amount,isPercentage);
            return "Supplier discount deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String editProductDiscount(int supplierNum, int productNum, int productAmount, int discount, boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).editProductDiscount(productNum, productAmount, discount, isPercentage);
            return "Product discount edited successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).addProductDiscount(productNum, productAmount, discount,isPercentage);
            return "Product discount added successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).deleteProductDiscount(productNum, productAmount, discount,isPercentage);
            return "Product discount deleted successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public List<String> getSuppliers(){
       List<String> suppliersStrings = new LinkedList<>();
        try {
            HashMap<Integer, SupplierBusiness> suppliers = sc.getSuppliers();
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                suppliersStrings.add(entry.getValue().toString()+"\n");
            }
        }
        catch (Exception e){
            List<String> err = new LinkedList<>();
            err.add(e.getMessage());
            return err;
        }
        finally {
            return suppliersStrings;
        }
    }

    public List<String> getSupplierDiscounts(int supplierNum) {
        List<String> discounts = new LinkedList<>();
        try {
            discounts.add("------Total Price Discounts------\n");
            List<Discount> discountsPerTotalPrice = sc.getSupplier(supplierNum).getDiscountPerTotalPrice();
            List<Discount> discountsPerProductQuantity = sc.getSupplier(supplierNum).getDiscountPerTotalQuantity();

            for (Discount dis: discountsPerTotalPrice){
                discounts.add(dis.toString());
                discounts.add("\n");
            }
            discounts.add("------Total Quantity Discounts------\n");
            for (Discount dis: discountsPerProductQuantity){
                discounts.add(dis.toString());
                discounts.add("\n");
            }
        }
        catch (Exception e){
            discounts = new LinkedList<>();
            discounts.add(e.getMessage());
        }
        finally {
            return discounts;
        }

    }

    public List<String> getProductDiscounts(int supplierNum,int productNum) {
        List<String> discounts = new LinkedList<>();
        try {
            List<Discount> discountList = sc.getSupplier(supplierNum).getSupplierProduct(productNum).getQuantitiesAgreement();
            for (Discount dis: discountList){
                discounts.add(dis.toString());
                discounts.add("\n");

            }
        }
        catch (Exception e){
            discounts = new LinkedList<>();
            discounts.add(e.getMessage());
        }
        finally {
            return discounts;
        }

    }

}