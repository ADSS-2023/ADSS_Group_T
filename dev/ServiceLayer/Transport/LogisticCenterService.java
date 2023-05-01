package ServiceLayer.Transport;

import BusinessLayer.HR.DriverController;
import ServiceLayer.HR.Response;


import BusinessLayer.Transport.*;

public class LogisticCenterService {
    private LogisticCenterController logisticCenterController;
    private DriverController driverController;
    public LogisticCenterService(LogisticCenterController logisticCenterController,DriverController driverController){
        this.logisticCenterController = logisticCenterController;
        this.driverController = driverController;
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
            DriverController.addDriver(id, name, licenseType, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeDriver(int id) {
        Response res = new Response();
        try {
            DriverController.removeDriver(id);
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
