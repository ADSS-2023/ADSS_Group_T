package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import BusinessLayer.Transport.Driver.LicenseType;
import BusinessLayer.Transport.Driver.CoolingLevel;

public class TransportController {

    private LinkedHashMap<Integer, Truck> trucks;
    private LinkedHashMap<Integer, Delivery> deliveries;
    private LinkedHashMap<Product, Integer> productsInStock;
    private LinkedHashMap<Integer, Driver> drivers;
    private LinkedHashMap<LocalDate, ArrayList<Truck>> date2trucks;
    private LinkedHashMap<LocalDate, ArrayList<Driver>> date2drivers;
    private LinkedHashMap<LocalDate, ArrayList<Delivery>> date2deliveries;
    private LinkedHashMap<String, Branch> branches;
    private LinkedHashMap<String, Supplier> suppliers;
    private LinkedHashMap<Supplier, ArrayList<Product>> suppliersProducts;
    private LinkedHashMap<String, Product> products;
    private int deliveryCounter = 0;
    private int filesCounter = 0;
    private LocalDate currDate;

    private Listener listener;


    public TransportController(LinkedHashMap<Integer, Truck> trucks, LinkedHashMap<Integer, Delivery> deliveries,
                               LinkedHashMap<Product, Integer> productsInStock, LinkedHashMap<Integer, Driver> drivers) {
        this.trucks = trucks;
        this.deliveries = deliveries;
        this.productsInStock = productsInStock;
        this.drivers = drivers;
        this.date2trucks = new LinkedHashMap<>();
        this.date2drivers = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.suppliersProducts = new LinkedHashMap<>();
        this.suppliers = new LinkedHashMap<>();
        this.products = new LinkedHashMap<>();
        this.currDate = LocalDate.now();
    }

    public TransportController() {
        this.trucks = new LinkedHashMap<>();
        this.deliveries = new LinkedHashMap<>();
        this.productsInStock = new LinkedHashMap<>();
        this.drivers = new LinkedHashMap<>();
        this.date2trucks = new LinkedHashMap<>();
        this.date2drivers = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.suppliersProducts = new LinkedHashMap<>();
        this.suppliers = new LinkedHashMap<>();
        this.products = new LinkedHashMap<>();
        this.currDate = LocalDate.now();
    }

    /**
     * handle the request for a new delivery
     *
     * @param branch       - the branch to deliver the products to
     * @param suppliers    - the products ordered from each supplier
     * @param requiredDate - required date for delivery
     * @return map of the suppliers products that have not been schedule for delivery due to lack of drivers/trucks in that date
     */
    public LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> orderDelivery(Branch branch, LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers, LocalDate requiredDate) {
        if (date2deliveries.containsKey(requiredDate)) {          //there is delivery in this date
            for (Delivery d : date2deliveries.get(requiredDate)) {     //the delivery is to the required date
                if (branch.getShippingArea().equals(d.getShippingArea())) {        //the delivery is to the required branch
                    if (!d.getBranches().containsKey(branch)) {
                        d.addBranch(branch, filesCounter);
                        filesCounter++;
                    }
                    ArrayList<Supplier> suppliersTmp = new ArrayList<>(suppliers.keySet());
                    for (Supplier supplier : suppliersTmp) {
                        if (supplier.getCoolingLevel() == trucks.get(d.getTruckNumber()).getCoolingLevel()) {
                            Set<Product> productsTmp = new LinkedHashSet<>(suppliers.get(supplier).keySet());
                            d.addSupplier(supplier,filesCounter);
                            for (Product p : productsTmp) {
                                d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
                                suppliers.get(supplier).remove(p);
                            }
                        }
                        if (suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
                            suppliers.remove(supplier);
                    }
                }
            }
        }
        if (!suppliers.isEmpty()) {   //open new delivery
            Set<CoolingLevel> newDeliveriesCoolingLevels = countCoolingOptions(suppliers);
            for (CoolingLevel coolingLevel : newDeliveriesCoolingLevels) {
                if (!date2deliveries.containsKey(requiredDate))
                    date2deliveries.put(requiredDate, new ArrayList<>());
                Truck t = scheduleTruck(requiredDate, coolingLevel);
                if (t == null)       //in case there is no truck available for this delivery
                    continue;
                Driver driver = scheduleDriver(requiredDate, coolingLevel);
                if (driver == null) {     //in case there is no driver available for this delivery
                    date2trucks.get(requiredDate).remove(t); //remove truck from this day deliveries list
                    continue;
                }
                Delivery d = new Delivery(deliveryCounter, requiredDate, LocalTime.NOON, t.getWeight(), new LinkedHashMap<>(),
                        null, driver.getName(), t.getLicenseNumber(), branch.getShippingArea());
                deliveryCounter++;
                deliveries.put(d.getId(), d);
                date2deliveries.get(requiredDate).add(d);
                //add supply to the new delivery
                ArrayList<Supplier> suppliersTmp = new ArrayList<>(suppliers.keySet());
                for (Supplier supplier : suppliersTmp) {
                    if (supplier.getCoolingLevel() == coolingLevel) {
                        Set<Product> productsTmp = new LinkedHashSet<>(suppliers.get(supplier).keySet());
                        for (Product p : productsTmp) {//change
                            if (d.getSource() == null)
                                d.setSource(supplier);
                            if (!d.getSuppliers().containsKey(supplier))
                                d.addSupplier(supplier, filesCounter++);
                            d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
                            suppliers.get(supplier).remove(p);
                        }
                    }
                    d.setTruckWeight(d.getTruckWeight());
                    if (suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
                        suppliers.remove(supplier);
                }
            }
            if (!suppliers.isEmpty())
                return suppliers;
        }
        return null;
    }

    /**
     * schedule an available truck to a delivery
     *
     * @param date         - the delivery date
     * @param coolingLevel - the cooling level required for this delivery
     * @return the Truck that scheduled for the delivery if exist , null otherwise
     */
    public Truck scheduleTruck(LocalDate date, CoolingLevel coolingLevel) {
        if (trucks != null) {
            for (Truck truck : trucks.values()) {
                if (date2trucks.containsKey(date) &&
                        !date2trucks.get(date).contains(truck) && truck.getCoolingLevel() == coolingLevel) {
                    date2trucks.get(date).add(truck);
                    return truck;
                } else if (!date2trucks.containsKey(date) && truck.getCoolingLevel() == coolingLevel) {
                    date2trucks.put(date, new ArrayList<>());
                    date2trucks.get(date).add(truck);
                    return truck;
                }
            }
        }
        return null;
    }

    /**
     * schedule an available driver to a delivery
     *
     * @param date         - the delivery date
     * @param coolingLevel - the cooling level required for this delivery
     * @return the Driver that scheduled for the delivery if exist , null otherwise
     */
    public Driver scheduleDriver(LocalDate date, CoolingLevel coolingLevel) {
        if (drivers != null) {
            for (Driver driver : drivers.values()) {
                if (date2drivers.containsKey(date) &&
                        !date2drivers.get(date).contains(driver) && driver.getCoolingLevel().ordinal() >= coolingLevel.ordinal()) {
                    date2drivers.get(date).add(driver);
                    return driver;
                } else if (!date2drivers.containsKey(date) && driver.getCoolingLevel().ordinal() >= coolingLevel.ordinal()) {
                    date2drivers.put(date, new ArrayList<>());
                    date2drivers.get(date).add(driver);
                    return driver;
                }
            }
        }
        return null;
    }

    /**
     * the function gathered the cooling levels that new delivery should be opened for them
     *
     * @param suppliers - map that holds the products who steel need a delivery for each supplier
     * @return Set with the cooling levels founds
     */
    public Set<CoolingLevel> countCoolingOptions(LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers) {
        Set<CoolingLevel> s = new HashSet<>();
        for (Supplier supplier : suppliers.keySet())
            s.add(supplier.getCoolingLevel());
        return s;
    }



    public LocalDate getCurrDate() {
        return currDate;
    }

    /**
     * advanced to the next day and checks if there are deliveries with overweight problem in this day
     *
     * @return List of the delivery ids that scheduled for the new day and have overweight problem
     */
    public ArrayList<Integer> skipDay() {
        this.currDate = this.currDate.plusDays(1);
        if (date2deliveries.get(currDate) == null || date2deliveries.get(currDate).isEmpty())
            return null;
        ArrayList<Integer> overWeightDeliveries = new ArrayList<>();
        for (Delivery d : date2deliveries.get(currDate)) {
            executeDelivery(d);
        }
        return null;
    }

    /**
     * add a new truck to the trucks map
     *
     * @param licenseNumber - the license number of the truck
     * @param model         - the truck model
     * @param weight        - the weight of the truck without supply
     * @param maxWeight     - max weight of the truck with supply
     * @param licenseType   - the license type required to drive the truck
     * @param coolingLevel  - the cooling level of the truck
     * @return true if the truck added successfully , and false otherwise
     */
    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight,
                            LicenseType licenseType, CoolingLevel coolingLevel) {
        if (trucks.containsKey(licenseNumber))
            return false;
        trucks.put(licenseNumber, new Truck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel));
        return true;
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
    public boolean addDriver(int id, String name, LicenseType licenseType, CoolingLevel coolingLevel) {
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
     * add product to the products map
     *
     * @param name - the name of the product
     * @return true if the product added successfully , and false otherwise
     */
    public boolean addProduct(String name) {
        if (products.containsKey(name))
            return false;
        products.put(name, new Product(name));
        return true;
    }

    /**
     * add a new branch to the branches map
     *
     * @param branch - the branch to add
     * @return true if the branch added successfully , and false otherwise
     */
    public boolean addBranch(Branch branch) {
        if (branches.containsKey(branch.getAddress()))
            return false;
        branches.put(branch.getAddress(), branch);
        return true;
    }

    /**
     * add a new supplier to the suppliers map
     *
     * @param supplier         - the supplier to add
     * @param supplierProducts - List of the products of the supplier
     * @return true if the supplier added successfully , and false otherwise
     */
    public boolean addSupplier(Supplier supplier, ArrayList<Product> supplierProducts) {
        if (suppliers.containsKey(supplier.getAddress()))
            return false;
        suppliers.put(supplier.getAddress(), supplier);
        suppliersProducts.put(supplier, supplierProducts);
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

    /**
     * replace a truck for a delivery
     *
     * @param deliveryID - the delivery who needed a truck replacement
     * @return true if the truck was replaced, false otherwise(there is no available truck)
     */
    public boolean replaceTruck(int deliveryID) {
        Truck t = trucks.get(deliveries.get(deliveryID).getTruckNumber());
        LocalDate date = deliveries.get(deliveryID).getDate();
        for (int licenseNumber : this.trucks.keySet()) {
            Truck optionalTruck = trucks.get(licenseNumber);
            if ((optionalTruck.getMaxWeight() >= deliveries.get(deliveryID).getTruckWeight()) &&
                    !date2trucks.get(date).contains(optionalTruck) &&
                    optionalTruck.getCoolingLevel() == t.getCoolingLevel() &&
                    optionalTruck.getLicenseType().ordinal() >= t.getLicenseType().ordinal()) {
                deliveries.get(deliveryID).setTruckNumber(optionalTruck.getLicenseNumber());
                date2trucks.get(date).remove(t);
                date2trucks.get(date).add(trucks.get(licenseNumber));
                return true;
            }
        }
        return false;
    }

    /**
     * unload products from the delivery due to overweight
     *
     * @param deliveryID the delivery id to unload products from
     */
    public void unloadProducts(int deliveryID) {
        double currWeight = deliveries.get(deliveryID).getTruckWeight();
        int maxWeight = trucks.get(deliveries.get(deliveryID).getTruckNumber()).getMaxWeight();
        double unloadFactor = (currWeight - maxWeight) / currWeight;
        for (Supplier supplier : deliveries.get(deliveryID).getSuppliers().keySet()) {
            for (Product p : deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().keySet()) {
                int amount = deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().get(p);
                int unloadAmount = (int) Math.ceil(amount * unloadFactor);
                deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().replace(p, amount - unloadAmount);
            }
        }
        deliveries.get(deliveryID).setTruckWeight(maxWeight);
    }

    /**
     * replace or drop one of the suppliers from the delivery due to overweight
     *
     * @param deliveryID - the id of the delivery that the action required for
     */
    public void replaceOrDropSite(int deliveryID,String address) {
        Delivery delivery = deliveries.get(deliveryID);
        delivery.removeSupplier();
        delivery.addNote("over load in " + address);
    }

    /**
     * handle the required action for the overweight problem for specific delivery
     *
     * @param deliveryID - the id of the delivery that has overweight
     * @param action     - the action required
     */
    public void overWeightAction(int deliveryID, int action,String address, int weight) {
        if (action == 1)
            replaceOrDropSite(deliveryID,address);
        else if (action == 2)
            replaceTruck(deliveryID);
        else if (action == 3)
            unloadProducts(deliveryID);
    }

    public Branch getBranch(String address) {
        return branches.get(address);
    }

    public Supplier getSupplier(String address) {
        return suppliers.get(address);
    }

    public Delivery getDelivery(int id) {
        return deliveries.get(id);
    }

    /**
     * gathered all the sites in the logistics center data
     *
     * @return List of all sites saved in the logistics center(suppliers and branches)
     */
    public ArrayList<Site> getSites() {
        ArrayList<Site> sites = new ArrayList<>();
        sites.addAll(suppliers.values());
        sites.addAll(branches.values());
        return sites;
    }

    public LinkedHashMap<Supplier, ArrayList<Product>> getSuppliers() {
        return suppliersProducts;
    }

    public ArrayList<Branch> getBranches() {
        return new ArrayList<>(branches.values());
    }

    public HashMap<Product, Integer> getProductsInStock() {
        return productsInStock;
    }

    /**
     * the function checks if the received date is after the current date
     *
     * @param date - the date to check
     * @return true if the received date is after the current date, false otherwise
     */
    public boolean checkDate(LocalDate date) {
        return date.isAfter(currDate);
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() {
        return trucks;
    }

    public Product getProduct(String productID, String Suppleirddress) {
        ArrayList<Product> products = suppliersProducts.get(suppliers.get(Suppleirddress));
        for (Product product : products) {
            if (product.getName().equals(productID)) {
                return product;
            }
        }
        return null;
    }


    public String processSupplierWeight(String supplier, int weight) {
        // Process the supplier weight
        return "OK";
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean loadWeight(int id, String address, int productsWeight) {
        Delivery delivery = deliveries.get(id);
        int currentWeight = delivery.getTruckWeight();
        int maxWeight = trucks.get(delivery.getTruckNumber()).getMaxWeight();
        if (maxWeight < currentWeight+productsWeight)
            return false;
        else
        {
            delivery.setTruckWeight(currentWeight+productsWeight);
            return true;
        }

    }

    public void enterActionWheneOverWeight1(int id, String address, int action) {
    }


    public interface Listener {
        void enterWeight(String address,int id);
    }

    public void executeDelivery(Delivery delivery) {
        for (Supplier supplier : delivery.getSuppliers().keySet()) {
            listener.enterWeight(supplier.getAddress(),delivery.getId());
        }
    }

    public File getLoadedProducts (int deliveryID, String address){
        return deliveries.get(deliveryID).getSuppliers().get(suppliers.get(address));
    }



}

