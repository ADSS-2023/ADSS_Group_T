package ServiceLayer.Transport;


import BusinessLayer.Transport.*;

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
        sb.append("Suppliers: \n");
        for (Map.Entry<Supplier, File> entry : delivery.getSuppliers().entrySet()) {
            Supplier supplier = entry.getKey();
            File file = entry.getValue();
            sb.append(" - ").append(supplier.getAddress()).append(": \n");
//            LinkedHashMap<Product, Integer> products = file.getProducts();
//            for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
//                Product product = productEntry.getKey();
//                int quantity = productEntry.getValue();
//                sb.append("    - ").append(quantity).append(" x ").append(product.getName()).append("\n");
//            }
        }
        sb.append("Branches: \n");

//        for (Map.Entry<Branch, File> entry : delivery.getBranches().entrySet()) {
//            Branch branch = entry.getKey();
//            File file = entry.getValue();
//            sb.append(" - ").append(branch.getAddress()).append(": \n");
//            LinkedHashMap<Product, Integer> products = file.getProducts();
//            for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
//                Product product = productEntry.getKey();
//                int quantity = productEntry.getValue();
//                sb.append("    - ").append(quantity).append(" x ").append(product.getName()).append("\n");
//            }
//        }
        //sb.append("Source: ").append(delivery.getSource().getAddress()).append("\n");
        sb.append("Driver Name: ").append(delivery.getDriverName()).append("\n");
        sb.append("Truck Number: ").append(delivery.getTruckNumber()).append("\n");
        sb.append("Shipping Area: ").append(delivery.getShippingArea()).append("\n");
        // Return the generated string
        return sb.toString();
    }

    public String fileToString(File file) {
        StringBuilder sb = new StringBuilder();

        // Append file details to the string builder
        LinkedHashMap<Product, Integer> products = file.getProducts();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            sb.append(" - ").append(quantity).append(" x ").append(product.getName()).append("\n");
        }

        // Return the generated string
        return sb.toString();
    }

}
