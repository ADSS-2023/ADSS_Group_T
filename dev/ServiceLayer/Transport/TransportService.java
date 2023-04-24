
package ServiceLayer.Transport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import ServiceLayer.HR.Response;
import com.google.gson.*;



import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;
import UtilSuper.EnterWeightInterface;



public class TransportService {
    private static final Gson gson = new Gson();
    public TransportController tc;
    private EnterWeightInterface enterWeightInterface;
    private TransportJsonConvert transportJsonConvert;

    public TransportService() {
        tc = new TransportController();
        tc.setEnterWeightInterface((String address, int deliveryID) -> enterWeightInterface.enterWeightFunction(address,deliveryID));
        transportJsonConvert = new TransportJsonConvert();
    }

    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, String>> suppliersString,
                                String requiredDateString) {
        Response res = new Response();
        try {
            tc.orderDelivery(branchString,suppliersString,requiredDateString);
            return "";
        }
        catch (Exception ex) {
            return ex.toString();
        }

    }


    public String skipDay() {
        Response res = new Response();
        try {
            tc.skipDay();
            return "";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        Response res = new Response();
        try {
            tc.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeTruck(int licenseNumber) {
        Response res = new Response();
        try {
            tc.removeTruck(licenseNumber);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addDriver(int id, String name, int licenseType, int coolingLevel) {
        Response res = new Response();
        try {
            tc.addDriver(id, name, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeDriver(int id) {
        Response res = new Response();
        try {
            tc.removeDriver(id);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String replaceTruck(int deliveryID) {
        Response res = new Response();
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
        Response res = new Response();
        try {
            tc.overWeightAction(id, action,address,weight);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllDeliverys() {
        Response res = new Response();
        try {
            //tc.getAllDeliverys();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllTrucks() {

        Response res = new Response();
        try {
            tc.getAllTrucks();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }


    public String getDeliveryDetail(int deliveryID) {
        return transportJsonConvert.deliveryToString(tc.getDelivery(deliveryID));
    }
    public String getLoadedProducts(int deliveryID,String address) {
        return transportJsonConvert.fileToString(tc.getLoadedProducts(deliveryID,address));
    }

    public String addBranch(String branch) {
        tc.addBranch(gson.fromJson(branch,Branch.class));
        return "D";
    }

    public void addSupplier(String supplierAddress,String telNumber,String contactName,int coolingLevel, ArrayList<String> produces) {

        tc.addSupplier(supplierAddress,telNumber,contactName, coolingLevel, produces);
    }




    public String loadWeight(int id, String address, int weight) {
        if (tc.loadWeight(id, address, weight))
            return "true";
        else
            return "false";

    }

    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public String getAllBranches() {
        tc.getAllBranches();
        return "";
    }

    public String getNextDayDeatails() {
         tc.getNextDayDeatails();
         return "good";
    }
}


























