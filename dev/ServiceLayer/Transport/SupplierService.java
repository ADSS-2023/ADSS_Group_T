package ServiceLayer.Transport;

import BusinessLayer.Transport.Supplier;
import BusinessLayer.Transport.SupplierController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SupplierService {
    private SupplierController supplierController;
    public SupplierService(SupplierController supplierController){
        this.supplierController = supplierController;
    }
    public String getSupplierProducts(String supplier) {
        return supplierController.getSupplierProducts(supplier));
    }

    public String getAllSuppliers() {
        return supplierController.getAllSuppliers().toString();
    }
    public void addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {

        supplierController.addSupplier(supplierAddress,telNumber,contactName,x,y);
    }

    public void addProducts(String supplierAddress, LinkedHashMap<String, Integer> productMap1) {

    }
}
