package BusinessLayer.Transport;

import java.util.LinkedHashMap;

public class TruckController {


    private LinkedHashMap<Integer,Truck> trucks;
    public  TruckController() {
        this.trucks = new LinkedHashMap<Integer, Truck>();
    }
    /**
     * add a new truck to the trucks map
     *
     * @param licenseNumber - the license number of the truck
     * @param model         - the truck model
     * @param weight        - the weight of the truck without supply
     * @param maxWeight     - max weight of the truck with supply
     * @param    - the license type required to drive the truck
     * @param coolingLevel  - the cooling level of the truck
     * @return true if the truck added successfully , and false otherwise
     */
    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        if(trucks.containsKey(licenseNumber))
            throw new IllegalArgumentException("truck num taken");
        else{
            Truck truck = new Truck(licenseNumber,model,weight,maxWeight,coolingLevel);
            trucks.put(licenseNumber, truck);
            return true;
        }
    }

    /**
     * remove a truck from the trucks map
     *
     * @param licenseNumber of the truck
     * @return true if the truck removed successfully , false otherwise
     */


    public boolean removeTruck(int licenseNumber) {
        if(!trucks.containsKey(licenseNumber))
            throw new IllegalArgumentException("no such truck num");
        else{
            trucks.remove(licenseNumber);
            return true;
        }
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() {
        return trucks;
    }

    public Truck getTruck(int licenseNumber) {
        if(!trucks.containsKey(licenseNumber))
            throw new IllegalArgumentException("no such truck num");
        else
            return trucks.get(licenseNumber);
    }
}
