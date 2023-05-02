package ServiceLayer.Transport;


import BusinessLayer.Transport.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransportJsonConvert {
    public TransportJsonConvert(){

    }
    public String deliveryToString(Delivery delivery) {
        StringBuilder sb = new StringBuilder();
        // Append delivery details to the string builder
        sb.append("Delivery ID: ").append(delivery.getId()).append("\n");
        sb.append("Date: ").append(delivery.getDate()).append("\n");
        sb.append("Truck Weight: ").append(delivery.getTruckWeight()).append("\n");
        sb.append(suppliersAndProductsToString(delivery.getSuppliers()));



        //sb.append("Source: ").append(delivery.getSource().getAddress()).append("\n");
        sb.append("Driver Name: ").append(delivery.getDriverName()).append("\n");
        sb.append("Truck Number: ").append(delivery.getTruckNumber()).append("\n");
        sb.append("Shipping Area: ").append(delivery.getShippingArea()).append("\n");
        // Return the generated string
        return sb.toString();
    }

    public String fileToString(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + file.getId() + "\n");
        // Append file details to the string builder
        LinkedHashMap<Product, Integer> products = file.getProducts();
        sb.append(productAndAmountToString(products));
        // Return the generated string
        return sb.toString();
    }


    public  String suppliersAndProductsToString (LinkedHashMap<Supplier,File> suppliersAndProductsToString) {
        if (suppliersAndProductsToString == null)
            return "all good";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Supplier, File> entry : suppliersAndProductsToString.entrySet()) {
            Supplier supplier = entry.getKey();
            sb.append("Suppliers:").append(supplier.getAddress()).append("\n");
            sb.append(fileToString(entry.getValue()));
        }
        return sb.toString();
    }

    public String productAndAmountToString(LinkedHashMap<Product, Integer> productAndAmount) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : productAndAmount.entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public LinkedHashMap<Supplier, File> mapToFile(LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliersAndProducts) {
        LinkedHashMap<Supplier, File> supplierAndFile = new LinkedHashMap<>();
        for (Map.Entry<Supplier, LinkedHashMap<Product, Integer>> entry : suppliersAndProducts.entrySet()) {
            Supplier supplier = entry.getKey();
            LinkedHashMap<Product, Integer> productAndAmount = entry.getValue();
            File file = new File(-1);
            file.getProducts().putAll(productAndAmount);
            supplierAndFile.put(supplier, file);
        }
        return supplierAndFile;
    }

    public String branchesToString(Collection<Branch> allBranches) {
        StringBuilder sb = new StringBuilder();
        for (Branch branch: allBranches) {
            sb.append(branch.getAddress()).append("\n");
        }
        return sb.toString();
    }
    public String branchToString(Branch branch){
        StringBuilder sb = new StringBuilder();
        sb.append(branch.getAddress()).append("\n");
        sb.append(branch.getContactName()).append("\n");
        sb.append(branch.getTelNumber()).append("\n");
        sb.append(branch.getShippingArea()).append("\n");
        return sb.toString();
    }

    public String getAllSuppliersAddress(Collection<Supplier> allSuppliers) {
        StringBuilder sb = new StringBuilder();
        for (Supplier supplier: allSuppliers) {
            sb.append(supplier.getAddress()).append("\n");
        }
        return sb.toString();
    }

    public String productsToString(Collection<Product> products){
        StringBuilder sb = new StringBuilder();
        for (Product product: products) {
            sb.append(product.getName()).append("\n");
        }
        return sb.toString();
    }
}
