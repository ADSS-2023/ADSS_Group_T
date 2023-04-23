package ServiceLayer.Transport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;


import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;


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

        tc.orderDelivery(branchString,suppliersString,requiredDateString);
        return "";

    }


    public String skipDay() {
        try {
            tc.skipDay();
            return "";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight,
                            int licenseType, int coolingLevel) {
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

    public String addDriver(int id, String name, int licenseType, int coolingLevel) {
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

       tc.getAllTrucks();
        return  "";
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

    public void addSupplier(String supplierAddress,String telNumber,String contactName,int coolingLevel, ArrayList<String> produces) {

        // tc.addSupplier(supplierAddress,String telNumber,int coolingLevel, produces);
    }




    public String loadWeight(int id, String address, int weight) {
        if (tc.loadWeight(id, address, weight))
            return "true";
        else
            return "false";

    }

}


























