package BusinessLayer.Transport;

import java.util.*;


public class LogisticCenterController {

        private  LogisticCenter logisticCenter;
        private LinkedHashMap<Integer,Truck> trucks;

        public LogisticCenterController() {
            this.logisticCenter = new LogisticCenter();
        }

        /**
         * store products in the logistics center stocks
         *
         * @param newSupply - map with the amount for each product required to store
         */
        public void storeProducts(LinkedHashMap<Product, Integer> newSupply) {
            logisticCenter.storeProducts(newSupply);
        }

        /**
         * load products from the stock of the logistics center
         *
         * @param requestedSupply - map of the products and amounts required to load
         * @return map of products and amounts that are not available in the logistics center stock
         */
        public LinkedHashMap<Product, Integer> removeProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) {
            return logisticCenter.loadProductsFromStock(requestedSupply);
        }

        public HashMap<Product, Integer> getProductsInStock() {
            return logisticCenter.getProductsInStock();
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

