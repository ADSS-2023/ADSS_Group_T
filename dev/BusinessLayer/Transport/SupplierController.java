package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class SupplierController {
    private LinkedHashMap<String, Supplier> suppliers;
    private DalDeliveryService dalDeliveryService;

    public SupplierController(DalDeliveryService dalDeliveryService) {
        this.suppliers = new LinkedHashMap<>();
        this.dalDeliveryService = dalDeliveryService;
    }

    // without products
    public void addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        if (suppliers.containsKey(supplierAddress) ||
                dalDeliveryService.findSupplier(supplierAddress) != null) {
            throw new IllegalArgumentException("supplier address is taken");
        }
        Supplier supplier = new Supplier(supplierAddress, telNumber, contactName, x, y, dalDeliveryService);
        dalDeliveryService.insertSupplier(supplier);
        suppliers.put(supplier.getAddress(), supplier);
    }

//    /**
//     * add a product to the supplier
//     *
//     * @param address - the address of the supplier
//     * @param product - the product to add
//     * @return true if the product added successfully, and false otherwise
//     */
//    public boolean addProductToSupplier(String address, String productName, int coolingLevel) {
//        Supplier supplier = getSupplier(address);
//        if (supplier == null) {
//            throw new IllegalArgumentException("no such address");
//        }
//        supplier.addProduct(productName, coolingLevel);
//        return true;
//    }

    public LinkedHashMap<String, Supplier> getAllSuppliers() throws SQLException {
        suppliers = dalDeliveryService.findAllSupplier();
        return suppliers;
    }

    public Supplier getSupplier(String supplierAddress) throws SQLException {
        Supplier s;
        if (!suppliers.containsKey(supplierAddress)) {
            s = dalDeliveryService.findSupplier(supplierAddress);
            if(s == null)
                throw new IllegalArgumentException("no such supp");;
            suppliers.put(supplierAddress,s);
            return s;
        }
        else
            return suppliers.get(supplierAddress);
    }

    public LinkedHashMap<String, Product> getSupplierProducts(String supplierAddress) throws SQLException {
        return getSupplier(supplierAddress).getAllProducts();
    }

    /**
     * Adds a set of products to a supplier.
     *
     * @param supplierAddress - the address of the supplier to add the products to
     * @param productMap1     - a map from product names to cooling levels
     * @throws IllegalArgumentException if the supplier does not exist
     */
    public void addProductsToSupplier(String supplierAddress, LinkedHashMap<String, Integer> productMap1) throws SQLException {
        Supplier supplier = getSupplier(supplierAddress);
        for (String productName : productMap1.keySet()) {
            Integer coolingLevel = productMap1.get(productName);
            supplier.addProduct(productName, coolingLevel);
        }
    }
}
