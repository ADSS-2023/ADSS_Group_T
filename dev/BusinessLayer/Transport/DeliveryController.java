package BusinessLayer.Transport;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Driver.CoolingLevel;
import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.ShiftController;
import UtilSuper.EnterWeightInterface;
import UtilSuper.EnterOverWeightInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DeliveryController {

    private final DriverController driverController;
    private final ShiftController shiftController;
    private final SupplierController supplierController;
    private final BranchController branchController;
    private final LinkedHashMap<Integer, Delivery> deliveries;
    private final LinkedHashMap<LocalDate, ArrayList<Truck>> date2trucks;
    private final LinkedHashMap<LocalDate, ArrayList<Delivery>> date2deliveries;
    private LogisticCenterController logisticCenterController;
    private int deliveryCounter = 0;
    private int filesCounter = 0;
    private LocalDate currDate;

    // private Listener listener;
    private EnterWeightInterface enterWeightInterface;
    private EnterOverWeightInterface overweightAction;


    public DeliveryController(LogisticCenterController logisticCenterController, SupplierController supplierController,
                              BranchController branchController, DriverController driverController, ShiftController shiftController) {

        this.deliveries = new LinkedHashMap<>();
        this.date2trucks = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.currDate = LocalDate.of(2023, 1, 1);
        this.branchController = branchController;
        this.driverController = driverController;
        this.supplierController = supplierController;
        this.logisticCenterController = logisticCenterController;
        this.shiftController = shiftController;
    }

    public void initLogisticCenterController(LogisticCenterController lcC) {
        this.logisticCenterController = lcC;
    }

    private LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> getSuppliersAnsProducts(LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString) {
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = new LinkedHashMap<>();
        for (String supplierAddress : suppliersString.keySet()) {
            Supplier supplier = this.supplierController.getSupplier(supplierAddress);
            if (supplier != null) {
                LinkedHashMap<String, Integer> productsString = suppliersString.get(supplierAddress);
                LinkedHashMap<Product, Integer> products = new LinkedHashMap<>();
                for (String productID : productsString.keySet()) {
                    int quantity = productsString.get(productID);
                    products.put(supplier.getProduct(productID), quantity);
                }
                suppliers.put(supplier, products);
            }
        }
        return suppliers;
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
    public LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> orderDelivery(String destinationString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
                                                                                  String requiredDateString) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate requiredDate = LocalDate.parse(requiredDateString, formatter);
        boolean isDestinationIsLogisticCenter = destinationString.equals(logisticCenterController.getAddress());
        boolean isSupplierIsLogisticCenter = suppliersString.containsKey(logisticCenterController.getAddress());
        if(isDestinationIsLogisticCenter && isSupplierIsLogisticCenter)
            throw new Exception("cant delivery from LC to LC");
        if(isDestinationIsLogisticCenter)
            return orderDeliveryToLogisticCenter(suppliersString,requiredDate);
        if(isSupplierIsLogisticCenter);
            //return orderDeliveryFromLogisticCenter();

        Branch branch = this.branchController.getBranch(destinationString);
        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers = getSuppliersAnsProducts(suppliersString);//convert the string

        if (date2deliveries.containsKey(requiredDate)) { // there is delivery in this date
            for (Delivery d : date2deliveries.get(requiredDate)) { // the delivery is to the required date
                if (branch.getShippingArea() == d.getShippingArea()) { // the delivery is to the required branch
                    if (!d.getUnHandledBranches().containsKey(branch)) {
                        d.addBranch(branch, filesCounter++);
                        shiftController.addStoreKeeperRequirement(requiredDate, branch.getAddress());
                    }

                    for (Supplier supplier : new ArrayList<>(suppliers.keySet())) {
                        Map<Product, Integer> products = suppliers.get(supplier);
                        for (Product product : new LinkedHashSet<>(products.keySet())) {
                            if (product.getCoolingLevel() == logisticCenterController.getTruck(d.getTruckNumber()).getCoolingLevel()) {
                                this.filesCounter = d.addProductToSupplier(supplier, product, products.get(product), filesCounter);
                                products.remove(product);
                            }
                        }
                        if (products.isEmpty()) {
                            suppliers.remove(supplier);
                        }
                    }
                }
            }
        }
        if (!suppliers.isEmpty()) { // open new delivery
            Set<CoolingLevel> newDeliveriesCoolingLevels = countCoolingOptions(suppliers);
            for (CoolingLevel coolingLevel : newDeliveriesCoolingLevels) {
                if (!date2deliveries.containsKey(requiredDate))
                    date2deliveries.put(requiredDate, new ArrayList<>());
                Truck truck = scheduleTruck(requiredDate, coolingLevel);
                if (truck == null) // in case there is no truck available for this delivery
                    continue;

                Delivery delivery = new Delivery(deliveryCounter++, requiredDate, LocalTime.NOON, truck.getWeight(), new LinkedHashMap<>(),
                        new LinkedHashMap<>(), null, truck.getLicenseNumber(), branch.getShippingArea());
                shiftController.addDirverRequirement(requiredDate, truck.getLicenseType(), truck.getCoolingLevel());
                shiftController.addStoreKeeperRequirement(requiredDate, branch.getAddress());
                delivery.addBranch(branch, filesCounter++);
                deliveries.put(delivery.getId(), delivery);
                date2deliveries.get(requiredDate).add(delivery);
                for (Supplier supplier : new ArrayList<>(suppliers.keySet())) {
                    Map<Product, Integer> products = suppliers.get(supplier);
                    for (Product product : new LinkedHashSet<>(products.keySet())) {
                        if (product.getCoolingLevel() == coolingLevel) {
                            this.filesCounter = delivery.addProductToSupplier(supplier, product, products.get(product), filesCounter);
                            products.remove(product);
                        }
                    }
                    if (products.isEmpty()) {
                        suppliers.remove(supplier);
                    }
                }
            }
        }
        return suppliers;
    }

    private LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> orderDeliveryToLogisticCenter(LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString, LocalDate requiredDate) {
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
     * the function gathered the cooling levels that new delivery should be opened for them
     *
     * @param suppliers - map that holds the products who steel need a delivery for each supplier
     * @return Set with the cooling levels founds
     */
    public Set<CoolingLevel> countCoolingOptions(LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> suppliers) {
        Set<CoolingLevel> s = new HashSet<>();
        for (Supplier supplier : suppliers.keySet())
            for (Product product : suppliers.get(supplier).keySet())
                s.add(product.getCoolingLevel());
        return s;
    }


    public LocalDate getCurrDateDetails() {
        return currDate;
    }

    /**
     * advanced to the next day and checks if there are deliveries with overweight problem in this day
     *
     * @return List of the delivery ids that scheduled for the new day and have overweight problem
     */
    public ArrayList<Delivery> skipDay() throws SQLException {
        this.currDate = this.currDate.plusDays(1);
        if (date2deliveries.get(currDate) == null || date2deliveries.get(currDate).isEmpty())
            return null;
        ArrayList<Integer> overWeightDeliveries = new ArrayList<>();
        for (Delivery d : date2deliveries.get(currDate)) {
            executeDelivery(d);
        }
        return null;
    }

    private ArrayList<Delivery> scheduleDriversForTomorrow() throws SQLException {
        LocalDate tomorrow = this.currDate.plusDays(1);
        ArrayList<Driver> driversTomorrow = sortDriversByLicenseLevel(driverController.getDriversAssignedByDate(tomorrow));

        ArrayList<Delivery> deliveriesTomorrow = sortDeliveriesByTruckWeight(date2deliveries.get(tomorrow));
        ArrayList<Delivery> deliveriesWithoutDriveries = new ArrayList<>();
        for (Delivery delivery : deliveriesTomorrow) {
            Truck truck = logisticCenterController.getTruck(delivery.getTruckNumber());
            for (Driver driver : driversTomorrow) {
                if (driver.getLicenseLevel().compareTo(truck.getLicenseType()) >= 0) {
                    if (driver.getCoolingLevel().equals(truck.getCoolingLevel())) {
                        delivery.setDriver(driver);
                        break;
                    }
                }
            }
            deliveriesWithoutDriveries.add(delivery);
        }
        return deliveriesWithoutDriveries;
    }

    public ArrayList<Delivery> sortDeliveriesByTruckWeight(ArrayList<Delivery> deliveries) {
        deliveries.sort(new Comparator<Delivery>() {
            public int compare(Delivery d1, Delivery d2) {
                Truck t1 = logisticCenterController.getTruck(d1.getTruckNumber());
                Truck t2 = logisticCenterController.getTruck(d2.getTruckNumber());
                return t1.compareTo(t2);
            }
        });
        return deliveries;
    }


    public ArrayList<Driver> sortDriversByLicenseLevel(ArrayList<Driver> drivers) {
        drivers.sort(new Comparator<Driver>() {
            public int compare(Driver d1, Driver d2) {
                return d1.compareTo(d2);
            }
        });
        return drivers;
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
    public void unloadProducts(int deliveryID, int weight, String supplierAddress) {
        LinkedHashMap<String, Supplier> suppliers = supplierController.getAllSuppliers();
        double currWeight = deliveries.get(deliveryID).getTruckWeight();
        int maxWeight = logisticCenterController.getAllTrucks().get(deliveries.get(deliveryID).getTruckNumber()).getMaxWeight();
        double unloadFactor = (currWeight + weight - maxWeight) / weight;
        File loadedProducts = new File(filesCounter++);
        for (Product p : deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().keySet()) {
            int amount = deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().get(p);
            int unloadAmount = (int) Math.ceil(amount * unloadFactor);
            loadedProducts.addProduct(p, amount - unloadAmount);
            deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(supplierAddress)).getProducts().replace(p, unloadAmount);
        }
        deliveries.get(deliveryID).addHandledSupplier(suppliers.get(supplierAddress), loadedProducts);
        HashMap<Product, Integer> copyOfSupplierFileProducts = new HashMap<>(loadedProducts.getProducts());
        filesCounter = deliveries.get(deliveryID).supplierHandled(suppliers.get(supplierAddress), filesCounter, copyOfSupplierFileProducts);
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
            if (!replaceTruck(deliveryID)) {
                int newAction = overweightAction.EnterOverweightAction(deliveryID);
                if (newAction == 3)
                    unloadProducts(deliveryID, weight, address);
            }
        } else if (action == 3)
            unloadProducts(deliveryID, weight, address);
    }


    public Delivery getDelivery(int id) {
        return deliveries.get(id);
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


    public void executeDelivery(Delivery delivery) throws SQLException {
        ArrayList<Supplier> suppliersTmp = new ArrayList<>(delivery.getUnHandledSuppliers().keySet());
        for (Supplier supplier : suppliersTmp) {
            int productsWeight = enterWeightInterface.enterWeightFunction(supplier.getAddress(), delivery.getId());
            int currentWeight = delivery.getTruckWeight();
            int maxWeight = logisticCenterController.getAllTrucks().get(delivery.getTruckNumber()).getMaxWeight();
            if (maxWeight < currentWeight + productsWeight) {
                overWeightAction(delivery.getId(), overweightAction.EnterOverweightAction(delivery.getId()), supplier.getAddress(), productsWeight);
                if (delivery.getTruckWeight() == logisticCenterController.getAllTrucks().get(delivery.getTruckNumber()).getMaxWeight())
                    break;
            } else {
                delivery.setTruckWeight(currentWeight + productsWeight);
                File f = delivery.getUnHandledSuppliers().get(supplier);
                delivery.getHandledSuppliers().put(supplier, f);
                delivery.getUnHandledSuppliers().remove(supplier);
                HashMap<Product, Integer> copyOfSupplierFileProducts = new HashMap<>(f.getProducts());
                filesCounter = delivery.supplierHandled(supplier, filesCounter, copyOfSupplierFileProducts);
            }
        }
        reScheduleDelivery(delivery.getUnHandledSuppliers(), delivery.getUnHandledBranches());
    }

    private void reScheduleDelivery(LinkedHashMap<Supplier, File> suppliers, LinkedHashMap<Branch, File> branches) throws SQLException {
        boolean found = false;
        LocalDate newDeliveredDate = this.currDate.plusDays(2);
        CoolingLevel coolingLevel = CoolingLevel.non;
        Truck t;
        while (!found) {
            t = scheduleTruck(newDeliveredDate, coolingLevel);
            if (t == null) {
                newDeliveredDate = newDeliveredDate.plusDays(1);
                continue;
            }
            shiftController.addDirverRequirement(newDeliveredDate, t.getLicenseType(), t.getCoolingLevel());
            for (Branch branch : branches.keySet()) {
                shiftController.addStoreKeeperRequirement(newDeliveredDate, branch.getAddress());
            }

            Delivery newDelivery = new Delivery(deliveryCounter, newDeliveredDate, LocalTime.NOON, t.getWeight(), suppliers, branches,
                    suppliers.entrySet().iterator().next().getKey(), t.getLicenseNumber(), branches.entrySet().iterator().next().getKey().getShippingArea());
            if (!date2deliveries.containsKey(newDeliveredDate))
                date2deliveries.put(newDeliveredDate, new ArrayList<>());
            date2deliveries.get(newDeliveredDate).add(newDelivery);
            found = true;
        }
    }

    public File getLoadedProducts(int deliveryID, String address) {
        LinkedHashMap<String, Supplier> suppliers = supplierController.getAllSuppliers();
        return deliveries.get(deliveryID).getUnHandledSuppliers().get(suppliers.get(address));
    }

    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public void setOverweightAction(EnterOverWeightInterface overweightAction) {
        this.overweightAction = overweightAction;
    }

    public ArrayList<Delivery> getNextDayDeatails() throws SQLException {
        ArrayList<Delivery> deliveriesThatReScheduleDelivery = new ArrayList<>();
        deliveriesThatReScheduleDelivery.addAll(checkStoreKeeperForTomorrow());
        deliveriesThatReScheduleDelivery.addAll(scheduleDriversForTomorrow());
        return deliveriesThatReScheduleDelivery;
    }

    private ArrayList<Delivery> checkStoreKeeperForTomorrow() throws SQLException {
        LocalDate tomorrow = this.currDate.plusDays(1);
        ArrayList<String> branchWithoutStoreKeeper = shiftController.getBranchesWithoutStoreKeeper(tomorrow);
        ArrayList<Delivery> deliveriesTomorrow = new ArrayList<>(date2deliveries.get(tomorrow));
        ArrayList<Delivery> deliveriesWithoutStoreKeeper = new ArrayList<>();
        for (Delivery delivery : deliveriesTomorrow) {
            LinkedHashSet<Branch> branchesOfDeliveries = new LinkedHashSet<>(delivery.getUnHandledBranches().keySet());
            for (Branch branch : branchesOfDeliveries) {
                if (branchWithoutStoreKeeper.contains(branch.getAddress())) {
                    reScheduleDelivery(delivery.getUnHandledSuppliers(), delivery.getUnHandledBranches());
                    date2deliveries.get(tomorrow).remove(deliveries.get(delivery.getId()));
                    date2trucks.get(tomorrow).remove(logisticCenterController.getTruck(delivery.getTruckNumber()));
                    deliveriesWithoutStoreKeeper.add(delivery);
                    break;
                }
            }
        }
        return deliveriesWithoutStoreKeeper;
    }

    public Collection<Delivery> getAllDeliveries() {
        return deliveries.values();
    }

    public LocalDate getCurrDate() {
        return this.currDate;
    }
}







