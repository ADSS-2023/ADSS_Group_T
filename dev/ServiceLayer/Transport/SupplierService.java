package ServiceLayer.Transport;

import BusinessLayer.Transport.SupplierController;

import java.util.LinkedHashMap;

public class SupplierService {
    private final SupplierController supplierController;

    public SupplierService(SupplierController supplierController) {
        this.supplierController = supplierController;
    }

    public String getSupplierProducts(String supplier) {
        try {
            return (supplierController.getSupplierProducts(supplier));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getSupplierAddress(String supplier) {
        try {
            return (supplierController.getSupplier(supplier).getAddress());
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllSuppliers() {
        try {
            return supplierController.getAllSuppliers().toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {
        try {
            return supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y)+" ";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addProducts(String supplierAddress, LinkedHashMap<String, Integer> productMap1) {
        try {
            supplierController.addProductsToSupplier(supplierAddress, productMap1);
            return "true";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
