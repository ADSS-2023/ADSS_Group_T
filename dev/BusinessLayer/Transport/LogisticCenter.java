package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class LogisticCenter extends Site {

    private final LinkedHashMap<Integer, Truck> trucks;
    private final LinkedHashMap<Product, Integer> productsInStock;
    private DalLogisticCenterService dalLogisticCenterService;

    public LogisticCenter(DalLogisticCenterService dalLogisticCenterService) {
        super("Main address", "0000000000", "logictic center manager", 0, 0);
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.trucks = new LinkedHashMap<>();
        this.productsInStock = new LinkedHashMap<>();
    }


    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) throws Exception {
        if (trucks.containsKey(licenseNumber))
            throw new Exception("trucks contains licenseNumber");
        Truck truck = new Truck(licenseNumber, model, weight, maxWeight, coolingLevel);
        dalLogisticCenterService.addNewTruck(truck);
        trucks.put(licenseNumber,truck);
        return true;
    }

    public boolean removeTruck(int licenseNumber) throws Exception {
        if (!trucks.containsKey(licenseNumber))
            throw new Exception("trucks Not contains licenseNumber");
        Truck truck = trucks.get(licenseNumber);
        dalLogisticCenterService.removeTruck(truck);
        trucks.remove(licenseNumber);
        return true;
    }

    public void storeProducts(LinkedHashMap<Product, Integer> newSupply) {
        newSupply.forEach((key, value) -> {
            if (productsInStock.containsKey(key)) {
                productsInStock.replace(key, productsInStock.get(key) + value);//product exist in stock - update amount
            }

            else
                productsInStock.put(key, value);
        });
    }

    /**
     * load products from the stock of the logistics center
     *
     * @param requestedSupply - map of the products and amounts required to load
     * @return map of products and amounts that are not available in the logistics center stock
     */
    public LinkedHashMap<Product, Integer> loadProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) {
        HashSet<Product> keys = new HashSet<>(requestedSupply.keySet());
        for (Product p : keys) {
            if (productsInStock.containsKey(p) && productsInStock.get(p) >= requestedSupply.get(p)) {    //product exist in stock in the requested amount
                productsInStock.replace(p, productsInStock.get(p) - requestedSupply.get(p));
                requestedSupply.remove(p);
                if (productsInStock.get(p) == 0)
                    productsInStock.remove(p);
            } else if (productsInStock.containsKey(p)) {  //product exist in stock but not in the requested amount
                requestedSupply.replace(p, requestedSupply.get(p) - productsInStock.get(p));
                productsInStock.remove(p);
            }
        }
        return requestedSupply;
    }

    public LinkedHashMap<Product, Integer> getProductsInStock() {
        return productsInStock;
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() {
        return trucks;
    }

    public String processSupplierWeight(String supplier, int weight) {
        // Process the supplier weight
        return "OK";
    }

    public Truck getTruck(int licenseNumber) {
        return trucks.get(licenseNumber);
    }
}
