package BusinessLayer.Transport;

import UtilSuper.EnterWeightInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import BusinessLayer.Transport.Driver.CoolingLevel;
import UtilSuper.EnterWeightInterface;


public class LogisticCenterController {


        private  LogisticCenter logisticCenter;

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
        public LinkedHashMap<Product, Integer> loadProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) {
            return logisticCenter.loadProductsFromStock(requestedSupply);
        }

        public HashMap<Product, Integer> getProductsInStock() {
            return logisticCenter.getProductsInStock();
        }

        public LinkedHashMap<Integer, Truck> getAllTrucks() {
            return logisticCenter.getAllTrucks();
        }

        public String processSupplierWeight(String supplier, int weight) {
            // Process the supplier weight
            return "OK";
        }

    public Truck getTruck(int licenseNumber) {
            return logisticCenter.getTruck(licenseNumber);
    }


}

