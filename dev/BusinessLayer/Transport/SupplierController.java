package BusinessLayer.Transport;

import java.util.LinkedHashMap;

public class SupplierController {
    private final LinkedHashMap<String, Supplier> suppliers;

    public SupplierController() {
        this.suppliers = new LinkedHashMap<>();
    }


    // without products
    public boolean addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {
        if (suppliers.containsKey(supplierAddress)) {
            throw new IllegalArgumentException("supplayer address is taken");
        }
        Supplier supplier = new Supplier(supplierAddress, telNumber, contactName, x, y);
        suppliers.put(supplier.getAddress(), supplier);
        return true;
    }

    /**
     * add a product to the supplier
     *
     * @param address - the address of the supplier
     * @param product - the product to add
     * @return true if the product added successfully, and false otherwise
     */
    public boolean addProductToSupplier(String address, String productName, int coolingLevel) {
        Supplier supplier = suppliers.get(address);
        if (supplier == null) {
            throw new IllegalArgumentException("no such address");
        }
        supplier.addProduct(productName, coolingLevel);
        return true;
    }

    public LinkedHashMap<String, Supplier> getAllSuppliers() {
        return suppliers;
    }

    public Supplier getSupplier(String supplierAddress) {
        if (!suppliers.containsKey(supplierAddress))
            throw new IllegalArgumentException("no such supp");
        else
            return suppliers.get(supplierAddress);
    }

    public String getSupplierProducts(String supplier) {
        if (!suppliers.containsKey(supplier))
            throw new IllegalArgumentException("no such supp");
        else
            return suppliers.get(supplier).getAllProducts().toString();
    }

    /**
     * Adds a set of products to a supplier.
     *
     * @param supplierAddress - the address of the supplier to add the products to
     * @param productMap1     - a map from product names to cooling levels
     * @throws IllegalArgumentException if the supplier does not exist
     */
    public void addProductsToSupplier(String supplierAddress, LinkedHashMap<String, Integer> productMap1) {

        if (!suppliers.containsKey(supplierAddress))
            throw new IllegalArgumentException("no such supp");
        else {
            Supplier supplier = suppliers.get(supplierAddress);
            for (String productName : productMap1.keySet()) {
                Integer coolingLevel = productMap1.get(productName);
                supplier.addProduct(productName, coolingLevel);
            }
        }
    }
}
