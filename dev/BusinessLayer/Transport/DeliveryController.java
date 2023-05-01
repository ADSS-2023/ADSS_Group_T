package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import BusinessLayer.HR.ShiftController;
import BusinessLayer.Transport.Driver.CoolingLevel;
import UtilSuper.EnterWeightInterface;
import UtilSuper.OverweightActionInterface;


public class DeliveryController {

    private  LogisticCenterController logisticCenterController;
    private ShiftController shiftController;
    private LinkedHashMap<Integer, Delivery> deliveries;

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


    // private Listener listener;
    private EnterWeightInterface enterWeightInterface;
    private OverweightActionInterface overweightAction;


    public DeliveryController( LinkedHashMap<Integer, Delivery> deliveries) {
        this.deliveries = deliveries;
        this.date2trucks = new LinkedHashMap<>();
        this.date2drivers = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.suppliersProducts = new LinkedHashMap<>();
        this.suppliers = new LinkedHashMap<>();
        this.products = new LinkedHashMap<>();
        this.currDate = LocalDate.of(1,1,2023);
    }

    public DeliveryController() {
        this.deliveries = new LinkedHashMap<>();
        this.date2trucks = new LinkedHashMap<>();
        this.date2drivers = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.suppliersProducts = new LinkedHashMap<>();
        this.suppliers = new LinkedHashMap<>();
        this.products = new LinkedHashMap<>();
        this.currDate = LocalDate.of(2023,1,1);
    }
    public void initLogisticCenterController (LogisticCenterController lcC){
        this.logisticCenterController = lcC;
    }

    /**
     * handle the request for a new delivery
     * <p>
     * //* @param branch       - the branch to deliver the products to
     * //* @param suppliers    - the products ordered from each supplier
     * //* @param requiredDate - required date for delivery
     *
     * @return map of the suppliers products that have not been schedule for delivery due to lack of drivers/trucks in that date
     */
    public LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
                                                                                  String requiredDateString) {
        Branch branch = this.branches.get(branchString);
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        for (String supplierAddress : suppliersString.keySet()) {
            Supplier supplier = this.suppliers.get(supplierAddress);
            if (supplier != null) {
                LinkedHashMap<String, Integer> productsString = suppliersString.get(supplierAddress);
                LinkedHashMap<Product, Integer> products = new LinkedHashMap<>();
                for (String productID : productsString.keySet()) {
                    int quantity = productsString.get(productID);
                    products.put(getProduct(productID, supplierAddress), quantity);
                }
                suppliers.put(supplier, products);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate requiredDate = LocalDate.parse(requiredDateString, formatter);


        if (date2deliveries.containsKey(requiredDate)) {          //there is delivery in this date
            for (Delivery d : date2deliveries.get(requiredDate)) {     //the delivery is to the required date
                if (branch.getShippingArea() == d.getShippingArea()) {        //the delivery is to the required branch
                    if (!d.getUnHandledBranches().containsKey(branch)) {
                        d.addBranch(branch, filesCounter);
                        filesCounter++;
                    }
                    ArrayList<Supplier> suppliersTmp = new ArrayList<>(suppliers.keySet());
                    for (Supplier supplier : suppliersTmp) {
                        if (supplier.getCoolingLevel() == logisticCenterController.getTruck(d.getTruckNumber()).getCoolingLevel()) {
                            Set<Product> productsTmp = new LinkedHashSet<>(suppliers.get(supplier).keySet());
                            d.addSupplier(supplier, filesCounter);
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
                        new LinkedHashMap<>(), null, driver.getName(), t.getLicenseNumber(), branch.getShippingArea());
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
                            if (!d.getUnHandledSuppliers().containsKey(supplier))
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
        if (logisticCenterController.getAllTrucks() != null) {
            for (Truck truck : logisticCenterController.getAllTrucks().values()) {
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
        if (logisticCenterController.getAllDrivers() != null) {
            for (Driver driver : logisticCenterController.getAllDrivers().values()) {
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
     * @param address - the branch to add
     * @param telNumber - the branch tel number
     * @param contactName - the branch contact name
     * @param x - the x coordinate of the branch
     * @param y - the y coordinate of the branch
     * @return true if the branch added successfully , and false otherwise
     */
    public boolean addBranch(String address, String telNumber, String contactName, int x, int y) {

        if (branches.containsKey(address))
            return false;
        Branch branch = new Branch(address, telNumber, contactName, x, y);
        branches.put(branch.getAddress(), branch);
        return true;
    }

    /**
     * add a new supplier to the suppliers map
     *
     * @param supplierAddress - the supplier to add
     * @param telNumber - the tel number if the supplier
     * @param contactName - the contact name of the supplier
     * @param coolingLevel - the cooling level of the supplier
     * @param productsOfSupplier - the products of the supplier
     * @param x - the x coordinate of the supplier
     * @param y - the y coordinate of the supplier
     * @return true if the supplier added successfully , and false otherwise
     */
    public boolean addSupplier(String supplierAddress, String telNumber, String contactName, int coolingLevel, ArrayList<String> productsOfSupplier, int x, int y) {
        if (suppliers.containsKey(supplierAddress))
            return false;
        Supplier supplier = new Supplier(supplierAddress, telNumber, contactName, x, y, new ArrayList<Product>());
        ArrayList<Product> products = new ArrayList<Product>();
        for (String productString : productsOfSupplier) {
            products.add(new Product(productString));
        }

        suppliers.put(supplier.getAddress(), supplier);
        suppliersProducts.put(supplier, products);
        return true;
    }




    /**
     * replace a truck for a delivery
     *
     * @param deliveryID - the delivery who needed a truck replacement
     * @return true if the truck was replaced, false otherwise(there is no available truck)
     */

    public boolean replaceTruck(int deliveryID) {
        Truck t = logisticCenterController.getAllTrucks().get(deliveries.get(deliveryID).getTruckNumber());
        LocalDate date = deliveries.get(deliveryID).getDate();
        for (int licenseNumber : logisticCenterController.getAllTrucks().keySet()) {
            Truck optionalTruck = logisticCenterController.getAllTrucks().get(licenseNumber);
            if ((optionalTruck.getMaxWeight() >= deliveries.get(deliveryID).getTruckWeight()) &&
                    !date2trucks.get(date).contains(optionalTruck) &&
                    optionalTruck.getCoolingLevel() == t.getCoolingLevel() &&
                    optionalTruck.getLicenseType().ordinal() >= t.getLicenseType().ordinal()) {
                deliveries.get(deliveryID).setTruckNumber(optionalTruck.getLicenseNumber());
                date2trucks.get(date).remove(t);
                date2trucks.get(date).add(logisticCenterController.getAllTrucks().get(licenseNumber));
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
    public void unloadProducts(int deliveryID,int weight, String supplierAddress) {
        double currWeight = deliveries.get(deliveryID).getTruckWeight();
        int maxWeight = logisticCenterController.getAllTrucks().get(deliveries.get(deliveryID).getTruckNumber()).getMaxWeight();
        double unloadFactor = (currWeight + weight - maxWeight) / weight;
        File loadedProducts = new File(filesCounter++);
        for (Product p : deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().keySet()) {
            int amount = deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().get(p);
            int unloadAmount = (int) Math.ceil(amount * unloadFactor);
            loadedProducts.addProduct(p,amount - unloadAmount);
            deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().replace(p,unloadAmount);
        }
        deliveries.get(deliveryID).addHandledSupplier(suppliers.get(supplierAddress),loadedProducts);
        HashMap<Product,Integer> copyOfSupplierFileProducts = new HashMap<>(loadedProducts.getProducts());
        filesCounter = deliveries.get(deliveryID).supplierHandled(suppliers.get(supplierAddress),filesCounter, copyOfSupplierFileProducts);
        deliveries.get(deliveryID).setTruckWeight(maxWeight);
    }

    /**
     * replace or drop one of the suppliers from the delivery due to overweight
     *
     * @param deliveryID - the id of the delivery that the action required for
     */
    public void DropSite(int deliveryID, String address) {
        Delivery delivery = deliveries.get(deliveryID);
        delivery.removeSupplier(address);
        delivery.addNote("over load in " + address);
    }

    /**
     * handle the required action for the overweight problem for specific delivery
     *
     * @param deliveryID - the id of the delivery that has overweight
     * @param action     - the action required
     */
    public void overWeightAction(int deliveryID, int action, String address, int weight) {
        //if (action == 1)
            //do nothing?
            //DropSite(deliveryID, address);
        if (action == 2) {
            if(!replaceTruck(deliveryID)){
                int newAction = overweightAction.EnterOverweightAction(deliveryID);
                if(newAction == 3)
                    unloadProducts(deliveryID,weight,address);
            }
        }
        else if (action == 3)
            unloadProducts(deliveryID,weight,address);
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



    /**
     * the function checks if the received date is after the current date
     *
     * @param date - the date to check
     * @return true if the received date is after the current date, false otherwise
     */
    public boolean checkDate(LocalDate date) {
        return date.isAfter(currDate);
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
        // TODO: Process the supplier weight
        return "OK";
    }


    public boolean loadWeight(int id, String address, int productsWeight) {
        Delivery delivery = deliveries.get(id);
        int currentWeight = delivery.getTruckWeight();
        int maxWeight = logisticCenterController.getAllTrucks().get(delivery.getTruckNumber()).getMaxWeight();
        if (maxWeight < currentWeight + productsWeight)
            return false;
        else {
            delivery.setTruckWeight(currentWeight + productsWeight);
            return true;
        }

    }


    public void executeDelivery(Delivery delivery) {
        ArrayList<Supplier> suppliersTmp = new ArrayList<>(delivery.getUnHandledSuppliers().keySet());
        for (Supplier supplier : suppliersTmp) {
            int productsWeight = enterWeightInterface.enterWeightFunction(supplier.getAddress(), delivery.getId());
            int currentWeight = delivery.getTruckWeight();
            int maxWeight = logisticCenterController.getAllTrucks().get(delivery.getTruckNumber()).getMaxWeight();
            if (maxWeight < currentWeight + productsWeight) {
                overWeightAction(delivery.getId(),overweightAction.EnterOverweightAction(delivery.getId()),supplier.getAddress(),productsWeight);
                if(delivery.getTruckWeight() == logisticCenterController.getAllTrucks().get(delivery.getTruckNumber()).getMaxWeight())
                    break;
            }
            else {
                delivery.setTruckWeight(currentWeight + productsWeight);
                File f = delivery.getUnHandledSuppliers().get(supplier);
                delivery.getHandledSuppliers().put(supplier,f);
                delivery.getUnHandledSuppliers().remove(supplier);
                HashMap<Product,Integer> copyOfSupplierFileProducts = new HashMap<>(f.getProducts());
                filesCounter = delivery.supplierHandled(supplier,filesCounter,copyOfSupplierFileProducts);
            }
        }
        reScheduleDelivery(delivery.getUnHandledSuppliers(),delivery.getUnHandledBranches());
    }

    private void reScheduleDelivery(LinkedHashMap<Supplier,File> suppliers,LinkedHashMap<Branch,File> branches){
        //TODO: implement method
        boolean found = false;
        LocalDate date = LocalDate.now();
        CoolingLevel coolingLevel = CoolingLevel.non;
        for(Supplier s : suppliers.keySet()){
            if(s.getCoolingLevel().ordinal() > coolingLevel.ordinal())
                coolingLevel = s.getCoolingLevel();
        }
        Truck t;
        Driver d;
        while(!found){
            t = scheduleTruck(date,coolingLevel);
            if(t == null){
                date = date.plusDays(1);
                continue;
            }
            d = scheduleDriver(date,coolingLevel);
            if(d == null){
                date2trucks.get(date).remove(t);
                date = date.plusDays(1);
                continue;
            }
            Delivery newDelivery = new Delivery(deliveryCounter,date,LocalTime.NOON,t.getWeight(),suppliers,branches,
                    suppliers.entrySet().iterator().next().getKey(),d.getName(),t.getLicenseNumber(),branches.entrySet().iterator().next().getKey().getShippingArea());
            if(!date2deliveries.containsKey(date))
                date2deliveries.put(date,new ArrayList<>());
            date2deliveries.get(date).add(newDelivery);
            found = true;
        }
    }


    public File getLoadedProducts(int deliveryID, String address) {
        return deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(address));
    }


    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public void setOverweightAction(OverweightActionInterface overweightAction) {
        this.overweightAction = overweightAction;
    }

    public Collection<Branch> getAllBranches() {
        return branches.values();
    }

    public LocalDate getNextDayDeatails() {
        return currDate.plusDays(1);
    }

    public ArrayList<Product> getSupplierProducts(String supplier) {
        return this.suppliersProducts.get(suppliers.get(supplier));
    }

    public Collection<Supplier> getAllSuppliers() {
        return suppliers.values();
    }


}
