package ServiceLayer.Transport;

import BusinessLayer.HR.DriverController;
import ServiceLayer.HR.Response;


import BusinessLayer.Transport.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LogisticCenterService {
    private LogisticCenterController logisticCenterController;

    public LogisticCenterService(LogisticCenterController logisticCenterController){
        this.logisticCenterController = logisticCenterController;

    }

    /**
     * store products in the logistics center stocks
     *
     * @param newSupply - map with the amount for each product required to store
     */
    public String storeProducts(LinkedHashMap<Product, Integer> newSupply) {
        logisticCenterController.storeProducts(newSupply);
        return "";
    }

    /**
     * load products from the stock of the logistics center
     *
     * @param requestedSupply - map of the products and amounts required to load
     * @return map of products and amounts that are not available in the logistics center stock
     */
    public String removeProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) {
        return logisticCenterController.removeProductsFromStock(requestedSupply).toString();
    }

    public String getProductsInStock() {
        return logisticCenterController.getProductsInStock().toString();
    }




}
