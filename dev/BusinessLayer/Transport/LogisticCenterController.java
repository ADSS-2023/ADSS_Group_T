package BusinessLayer.Transport;

import java.util.LinkedHashMap;


public class LogisticCenterController {

    private final LogisticCenter logisticCenter;

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

    public LinkedHashMap<Product, Integer> getProductsInStock() {
        return logisticCenter.getProductsInStock();
    }

    /**
     * add a new truck to the trucks map
     *
     * @param licenseNumber - the license number of the truck
     * @param model         - the truck model
     * @param weight        - the weight of the truck without supply
     * @param maxWeight     - max weight of the truck with supply
     * @param -             the license type required to drive the truck
     * @param coolingLevel  - the cooling level of the truck
     * @return true if the truck added successfully , and false otherwise
     */
    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        return logisticCenter.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
    }

    /**
     * remove a truck from the trucks map
     *
     * @param licenseNumber of the truck
     * @return true if the truck removed successfully , false otherwise
     */
    public boolean removeTruck(int licenseNumber) {
        return logisticCenter.removeTruck(licenseNumber);
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() {
        return logisticCenter.getAllTrucks();
    }

    public Truck getTruck(int licenseNumber) {
        return logisticCenter.getTruck(licenseNumber);
    }

    public String getAddress() {
        return this.getAddress();
    }
}

