package ServiceLayer.Transport;

import BusinessLayer.Transport.TruckController;
import ServiceLayer.HR.Response;

public class TruckService {
    private TruckController truckController;

    public TruckService(TruckController truckController){
        this.truckController = truckController;
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        Response res = new Response();
        try {
           truckController.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String removeTruck(int licenseNumber) {
        Response res = new Response();
        try {
           truckController.removeTruck(licenseNumber);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
