package ServiceLayer.Transport;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ServiceLayer.HR.Response;


import BusinessLayer.Transport.*;
import UtilSuper.EnterWeightInterface;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class LogisticCenterService {
    public LogisticCenterController lcC;
    public LogisticCenterService(){
        this.lcC = new LogisticCenterController();
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        Response res = new Response();
        try {
            lcC.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeTruck(int licenseNumber) {
        Response res = new Response();
        try {
            lcC.removeTruck(licenseNumber);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addDriver(int id, String name, int licenseType, int coolingLevel) {
        Response res = new Response();
        try {
            lcC.addDriver(id, name, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeDriver(int id) {
        Response res = new Response();
        try {
            lcC.removeDriver(id);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    public String getAllTrucks() {

        Response res = new Response();
        try {
            lcC.getAllTrucks();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    
    
    
    
}
