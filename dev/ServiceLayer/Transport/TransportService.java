package ServiceLayer.Transport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;


import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;
import com.google.gson.Gson;

public class TransportService {

    public TransportController tc;
    private TransportJsonConvert transportJsonConvert;
    private LinkedHashMap<Supplier, Integer> weightOfOrder;

    public TransportService() {
        tc = new TransportController();
        transportJsonConvert = new TransportJsonConvert();
    }

    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, String>> suppliersString,
                                String requiredDateString) {
        Branch branch = tc.getBranch(branchString);

        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        for (String supplierName : suppliersString.keySet()) {
            Supplier supplier = tc.getSupplier(supplierName);
            if (supplier != null) {
                LinkedHashMap<String, String> productsString = suppliersString.get(supplierName);
                LinkedHashMap<Product, Integer> products = new LinkedHashMap<>();
                for (String productID : productsString.keySet()) {
                    String quantityString = productsString.get(productID);
                    if (quantityString != null) {
                        int quantity = Integer.parseInt(quantityString);
                        products.put(tc.getProduct(productID,supplierName), quantity);
                    }
                }
                suppliers.put(supplier, products);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(requiredDateString, formatter);

        Gson gson = new Gson();
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> orderDelivery =  tc.orderDelivery(branch,suppliers,date);new LinkedHashMap<>();
        String json = gson.toJson(orderDelivery);
        return json;

    }


    public String skipDay() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(tc.skipDay());
            return json;
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight,
                           LicenseType licenseType, CoolingLevel coolingLevel) {
        try {
            tc.addTruck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeTruck(int licenseNumber) {
        try {
            tc.removeTruck(licenseNumber);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addDriver(int id, String name, LicenseType licenseType, CoolingLevel coolingLevel) {
        try {
            tc.addDriver(id, name, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeDriver(int id) {
        try {
            tc.removeDriver(id);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String replaceTruck(int deliveryID) {
        try {
            tc.replaceTruck(deliveryID);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

//    public String replaceOrDropSite(int deliveryID) {
//        try {
//            tc.replaceOrDropSite(deliveryID);
//            return "good";
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }

    public String overWeightAction(int id, int action,String address, int weight) {
        try {
            tc.overWeightAction(id, action,address,weight);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllDeliverys() {
        try {
            //tc.getAllDeliverys();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllTrucks() {
        Gson gson = new Gson();
        String json = gson.toJson(tc.getAllTrucks());
        return  json;

    }


    public String getDeliveryDetail(int deliveryID) {
        return transportJsonConvert.deliveryToString(tc.getDelivery(deliveryID));
    }
    public String getLoadedProducts(int deliveryID,String address) {
        return transportJsonConvert.fileToString(tc.getLoadedProducts(deliveryID,address));
    }

    public String addBranch(Branch branch) {
        tc.addBranch(branch);
        return "D";
    }

    public void addSupplier(Supplier supplier, ArrayList<Product> produces) {
        tc.addSupplier(supplier, produces);
    }




    public String loadWeight(int id, String address, int weight) {
        if (tc.loadWeight(id, address, weight))
            return "true";
        else
            return "false";

    }

}


























