package ServiceLayer.Transport;

import ServiceLayer.HR.Response;


import BusinessLayer.Transport.*;

public class LogisticCenterService {
    public LogisticCenterController logisticCenterController;
    public LogisticCenterService(LogisticCenterController logisticCenterController){
        this.logisticCenterController = logisticCenterController;
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        Response res = new Response();
        try {
            logisticCenterController.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeTruck(int licenseNumber) {
        Response res = new Response();
        try {
            logisticCenterController.removeTruck(licenseNumber);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String addDriver(int id, String name, int licenseType, int coolingLevel) {
        Response res = new Response();
        try {
            logisticCenterController.addDriver(id, name, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeDriver(int id) {
        Response res = new Response();
        try {
            logisticCenterController.removeDriver(id);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    public String getAllTrucks() {

        Response res = new Response();
        try {
            logisticCenterController.getAllTrucks();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    
    
    
    
}
