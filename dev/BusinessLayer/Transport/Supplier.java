package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DTOs.SupplierDTO;

import java.util.LinkedHashMap;

public class Supplier extends Site {


    private final LinkedHashMap<String, Product> produces;

    public Supplier(String address, String telNumber, String contactName, int x, int y) {
        super(address, telNumber, contactName, x, y);
        this.produces = new LinkedHashMap<String, Product>();
    }

    public Supplier(SupplierDTO dto){
        super(dto.getSupplierAddress(), dto.getTelNumber(), dto.getContactName(), dto.getX(), dto.getY());
        this.produces = new LinkedHashMap<String, Product>();
    }

    public void addProduct(String productName, int productCoolingLevel) {
        if (produces.containsKey(productName))
            throw new IllegalArgumentException("product already exist");
        else {
            Product product = new Product(productName, productCoolingLevel);
            produces.put(productName, product);
        }
    }

    public Product getProduct(String productID) {
        if (!produces.containsKey(productID))
            throw new IllegalArgumentException("no such product");
        else
            return produces.get(productID);
    }

    public LinkedHashMap<String, Product> getAllProducts() {
        return produces;
    }

}

