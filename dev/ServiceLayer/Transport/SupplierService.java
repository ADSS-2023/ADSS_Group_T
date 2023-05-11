package ServiceLayer.Transport;

import BusinessLayer.Transport.SupplierController;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;

import java.util.LinkedHashMap;

public class SupplierService {
    private final SupplierController supplierController;
    private TransportJsonConvert transportJsonConvert;

    public SupplierService(SupplierController supplierController) {
        this.supplierController = supplierController;
        this.transportJsonConvert = new TransportJsonConvert();
    }



    public String getSupplierProducts(String supplier) {
        Response response = new Response();
        try {
            response.setReturnValue(TransportJsonConvert.productsToString(supplierController.getSupplierProducts(supplier).values()));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String getSupplierAddress(String supplier) {
        Response response = new Response();
        try {
            response.setReturnValue(supplierController.getSupplier(supplier).getAddress());
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String getAllSuppliers() {
        Response response = new Response();
        try {
            response.setReturnValue(TransportJsonConvert.convertCollectionToString(supplierController.getAllSuppliers().keySet()));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {
        Response response = new Response();
        try {
            supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
            response.setReturnValue("Supplier added");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String addProducts(String supplierAddress, LinkedHashMap<String, Integer> productMap1) {
        Response response = new Response();
        try {
            supplierController.addProductsToSupplier(supplierAddress, productMap1);
            response.setReturnValue("Products added");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }




//    public String getSupplierProducts(String supplier) {
//        try {
//            return (supplierController.getSupplierProducts(supplier));
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String getSupplierAddress(String supplier) {
//        try {
//            return (supplierController.getSupplier(supplier).getAddress());
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String getAllSuppliers() {
//        try {
//            return  TransportJsonConvert.convertCollectionToString(supplierController.getAllSuppliers().keySet());
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) {
//        try {
//            supplierController.addSupplier(supplierAddress, telNumber, contactName, x, y);
//            return "Supplier added";
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String addProducts(String supplierAddress, LinkedHashMap<String, Integer> productMap1) {
//        try {
//            supplierController.addProductsToSupplier(supplierAddress, productMap1);
//            return "products added";
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
}
