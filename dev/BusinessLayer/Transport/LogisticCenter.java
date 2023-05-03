package BusinessLayer.Transport;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class LogisticCenter extends Site {
    private final LinkedHashMap<Integer, Truck> trucks;
    private final LinkedHashMap<Product, Integer> productsInStock;

    public LogisticCenter() {
        super("Main address", "0000000000", "logictic center manager", 0, 0);
        this.trucks = new LinkedHashMap<>();
        this.productsInStock = new LinkedHashMap<>();
    }

    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        if (trucks.containsKey(licenseNumber))
            return false;
        trucks.put(licenseNumber, new Truck(licenseNumber, model, weight, maxWeight, coolingLevel));
        return true;
    }

    public boolean removeTruck(int licenseNumber) {
        if (!trucks.containsKey(licenseNumber))
            return false;
        trucks.remove(licenseNumber);
        return true;
    }

    public void storeProducts(LinkedHashMap<Product, Integer> newSupply) {
        newSupply.forEach((key, value) -> {
            if (productsInStock.containsKey(key))                           //product exist in stock - update amount
                productsInStock.replace(key, productsInStock.get(key) + value);
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
