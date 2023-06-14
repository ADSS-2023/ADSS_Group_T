package ServiceLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.OrderController;
import BusinessLayer.Supplier.Suppliers.ConstantSupplier;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import ServiceLayer.Supplier_Stock.Response;

import java.rmi.server.RemoteRef;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupplierService {
    private SupplierController sc;
    private OrderController oc;

    public SupplierService(SupplierController sc,OrderController oc)
    {
        this.sc = sc;
        this.oc=oc;
    }

    public Response loadSuppliers(){
        try {
            sc.loadSuppliers();
            return Response.okResponse("suppliers successfully loaded");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response addSupplier(String name, String address, int supplierNum, int bankAccountNum, int daysToDeliver, HashMap<String, String> contacts, List<DayOfWeek> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms){
        try{
            sc.addSupplier(name,address,supplierNum,bankAccountNum,contacts,constDeliveryDays, selfDelivery, paymentTerms, daysToDeliver);
            return Response.okResponse("Supplier added successfully");
        }
        catch(Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response deleteSupplier(int supplierNum){
        try {
            sc.deleteSupplier(supplierNum);
            return Response.okResponse("Supplier deleted successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response deleteAll(){
        try {
            sc.deleteAll();
            return Response.okResponse("Suppliers data deleted successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response editSupplier(String name, String address, int supplierNum, int bankAccountNum, boolean selfDelivery, PaymentTerms paymentTerms){
        try {
            sc.getSupplier(supplierNum).editSupplier(name, address, bankAccountNum, selfDelivery,paymentTerms);
            return Response.okResponse("Supplier edited successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response getProducts(int supplierNum) {
        List<String> products = new LinkedList<>();
        try {
            ConcurrentHashMap<Integer, SupplierProductBusiness> productMap = sc.getProducts(supplierNum);
            for (Map.Entry<Integer, SupplierProductBusiness> entry : productMap.entrySet())
                products.add(entry.getValue().toString() + '\n');
                return Response.okResponse(products);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }



    public Response addProduct(int supplierNum, int productNum, String productName, String manufacturer, float price, int maxAmount, LocalDate expiryDate){
        try {
            sc.getSupplier(supplierNum).addProduct(productNum, productName, manufacturer, price, maxAmount, expiryDate);
            return Response.okResponse("Product added successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response editProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount, LocalDate expiryDate){
        try {
            sc.getSupplier(supplierNum).editProduct(productName, manufacturer, price, maxAmount, expiryDate);
            if(sc.getSupplier(supplierNum) instanceof ConstantSupplier){
                SupplierProductBusiness sProduct = sc.getSupplier(supplierNum).getProduct(productName, manufacturer);
                oc.editRegularItem(sProduct.getName(), sProduct.getManufacturer(), supplierNum, ((ConstantSupplier) sc.getSupplier(supplierNum)).getConstDeliveryDays());
            }
            return Response.okResponse("Product edited successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response deleteProduct(int supplierNum, int productNum){
        try {
            if(sc.getSupplier(supplierNum) instanceof ConstantSupplier){
                SupplierProductBusiness sProduct = sc.getSupplier(supplierNum).getProduct(productNum);
                oc.removeRegularItem(sProduct.getName(), sProduct.getManufacturer(), supplierNum, ((ConstantSupplier) sc.getSupplier(supplierNum)).getConstDeliveryDays());
            }
            sc.getSupplier(supplierNum).deleteProduct(productNum);
            return Response.okResponse("Product deleted successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response editSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discountToChange,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).editSupplierDiscount(discountEnum, amount, discountToChange,isPercentage);
            return Response.okResponse("Supplier discount edited successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response addSupplierDiscount(int supplierNum, Discounts discountEnum, int amount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).addSupplierDiscount(discountEnum, amount, discount,isPercentage);
            return Response.okResponse("Supplier discount added successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response deleteSupplierDiscount(int supplierNum, Discounts discountEnum, int amount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).deleteSupplierDiscount(discountEnum, amount,isPercentage);
            return Response.okResponse("Supplier discount deleted successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response editProductDiscount(int supplierNum, int productNum, int productAmount, int discount, boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).editProductDiscount(productNum, productAmount, discount, isPercentage);
            return Response.okResponse("Product discount edited successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response addProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).addProductDiscount(productNum, productAmount, discount,isPercentage);
            return Response.okResponse("Product discount added successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response deleteProductDiscount(int supplierNum, int productNum, int productAmount, int discount,boolean isPercentage){
        try {
            sc.getSupplier(supplierNum).deleteProductDiscount(productNum, productAmount, discount,isPercentage);
            return Response.okResponse("Product discount deleted successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response getSuppliers(){
       List<String> suppliersStrings = new LinkedList<>();
        try {
            ConcurrentHashMap<Integer, SupplierBusiness> suppliers = sc.getSuppliers();
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                suppliersStrings.add(entry.getValue().toString()+"\n");
            }
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        finally {
            return Response.okResponse(suppliersStrings);
        }
    }

    public Response getSupplierDiscounts(int supplierNum) {
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
            return Response.errorResponse(e.getMessage());
        }
        finally {
            return Response.okResponse(discounts);
        }

    }

    public Response getProductDiscounts(int supplierNum,int productNum) {
        List<String> discounts = new LinkedList<>();
        try {
            List<Discount> discountList = sc.getSupplier(supplierNum).getSupplierProduct(productNum).getQuantitiesAgreement();
            for (Discount dis: discountList){
                discounts.add(dis.toString());
                discounts.add("\n");
            }
            return Response.okResponse(discounts);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

}