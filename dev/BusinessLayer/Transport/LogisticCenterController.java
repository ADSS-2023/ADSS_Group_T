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
            return logisticCenter.addTruck(licenseNumber,model,weight,maxWeight,coolingLevel);
        }

        /**
         * remove a truck from the trucks map
         *
         * @param licenseNumber of the truck
         * @return true if the truck removed successfully , false otherwise
         */


        public boolean removeTruck(int licenseNumber) {
            return  logisticCenter.removeTruck(licenseNumber);
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

