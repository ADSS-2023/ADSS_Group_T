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

        private LinkedHashMap<Integer, Truck> trucks;
        private LinkedHashMap<Product, Integer> productsInStock;
        private LinkedHashMap<Integer, Driver> drivers;

        public LogisticCenterController(LinkedHashMap<Integer, Truck> trucks,  LinkedHashMap<Product, Integer> productsInStock, LinkedHashMap<Integer, Driver> drivers) {
            this.trucks = trucks;
            this.productsInStock = productsInStock;
            this.drivers = drivers;
        }

        public LogisticCenterController() {
            this.trucks = new LinkedHashMap<>();
            this.productsInStock = new LinkedHashMap<>();
            this.drivers = new LinkedHashMap<>();
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
            if (trucks.containsKey(licenseNumber))
                return false;
            else{
                try {
                    Truck t =  new Truck(licenseNumber, model, weight, maxWeight, coolingLevel);

                    trucks.put(licenseNumber,t);
                    return true;
            }
            catch (Exception ex){
            }
        }
            return false;////////////////////////////////////////
        }

        /**
         * remove a truck from the trucks map
         *
         * @param licenseNumber of the truck
         * @return true if the truck removed successfully , false otherwise
         */


        public boolean removeTruck(int licenseNumber) {
            if (!trucks.containsKey(licenseNumber))
                return false;
            trucks.remove(licenseNumber);
            return true;
        }

        /**
         * add a new driver to the drivers map
         *
         * @param id           - the id of the driver
         * @param name         - the name of the driver
         * @param licenseType  - the license type of the driver
         * @param coolingLevel - the cooling level to which the driver is qualified
         * @return true if the driver added successfully , and false otherwise
         */
        public boolean addDriver(int id, String name, int licenseType, int coolingLevel) {
            if (drivers.containsKey(id))
                return false;
            drivers.put(id, new Driver(id, name, licenseType, coolingLevel));
            return true;
        }

        /**
         * remove a driver from the drivers map
         *
         * @param id - thr id of the driver
         * @return true if the driver removed successfully , and false otherwise
         */
        public boolean removeDriver(int id) {
            if (!drivers.containsKey(id))
                return false;
            drivers.remove(id);
            return true;
        }

        /**
         * store products in the logistics center stocks
         *
         * @param newSupply - map with the amount for each product required to store
         */
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

        public HashMap<Product, Integer> getProductsInStock() {
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

    public LinkedHashMap<Integer, Driver> getAllDrivers() {return drivers;}
}

