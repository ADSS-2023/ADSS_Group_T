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
    public boolean addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {
        Supplier s;
        if (suppliers.containsKey(supplierAddress)) {
            throw new IllegalArgumentException("supplier address is taken");
        }
        else{
            try {
                if(dalDeliveryService.findSupplier(supplierAddress) != null)
                    throw new IllegalArgumentException("supplier address is taken");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Supplier supplier = new Supplier(supplierAddress, telNumber, contactName, x, y, dalDeliveryService);
        try {
            dalDeliveryService.insertSupplier(supplier);
            suppliers.put(supplier.getAddress(), supplier);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * add a product to the supplier
     *
     * @param address - the address of the supplier
     * @param product - the product to add
     * @return true if the product added successfully, and false otherwise
     */
    public boolean addProductToSupplier(String address, String productName, int coolingLevel) {
        Supplier supplier = getSupplier(address);
        if (supplier == null) {
            throw new IllegalArgumentException("no such address");
        }
        supplier.addProduct(productName, coolingLevel);
        return true;
    }

    public LinkedHashMap<String, Supplier> getAllSuppliers() {
        if(suppliers.isEmpty()){
            try {
                suppliers = dalDeliveryService.findAllSupplier();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return suppliers;
    }

    public Supplier getSupplier(String supplierAddress) {
        Supplier s;
        if (!suppliers.containsKey(supplierAddress)) {
            try {
                s = dalDeliveryService.findSupplier(supplierAddress);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(s == null)
                return null;
            suppliers.put(supplierAddress,s);
            return s;
        }
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
