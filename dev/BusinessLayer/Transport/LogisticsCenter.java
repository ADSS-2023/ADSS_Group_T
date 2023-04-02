package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import BusinessLayer.Transport.Driver.LicenseType;
import BusinessLayer.Transport.Driver.CoolingLevel;

public class LogisticsCenter {

    private  HashMap<Integer,Truck> trucks;
    private  HashMap<Integer,Delivery> deliveries;
    private  HashMap<Product,Integer> productsInStock;
    private  HashMap<Integer,Driver> drivers;
    private  HashMap<LocalDate,ArrayList<Truck>> date2trucks;
    private  HashMap<LocalDate,ArrayList<Driver>> date2drivers;
    private  HashMap<LocalDate,ArrayList<Delivery>> date2deliveries;
    private HashMap<String,Branch> branches;
    private HashMap<String,Supplier> suppliers;
    private HashMap<Supplier,ArrayList<Product>> suppliersProducts;
    private HashMap<String,Product> products;
    private int deliveryCounter = 0;
    private int filesCounter = 0;
    private LocalDate currDate;


    public LogisticsCenter( HashMap<Integer,Truck> trucks,HashMap<Integer,Delivery> deliveries,
                        HashMap<Product,Integer> productsInStock,HashMap<Integer,Driver> drivers){
        this.trucks = trucks;
        this.deliveries = deliveries;
        this.productsInStock = productsInStock;
        this.drivers = drivers;
        this.date2trucks = new HashMap<>();
        this.date2drivers = new HashMap<>();
        this.date2deliveries = new HashMap<>();
        this.branches = new HashMap<>();
        this.suppliersProducts = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.products = new HashMap<>();
        this.currDate = LocalDate.now();
    }

    public LogisticsCenter(){
        this.trucks = new HashMap<>();
        this.deliveries = new HashMap<>();
        this.productsInStock = new HashMap<>();
        this.drivers = new HashMap<>();
        this.date2trucks = new HashMap<>();
        this.date2drivers = new HashMap<>();
        this.date2deliveries = new HashMap<>();
        this.branches = new HashMap<>();
        this.suppliersProducts = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.products = new HashMap<>();
        this.currDate = LocalDate.now();
    }
 

    //s1,<>,1.1
    //s2,<>,1.1
    //s3,<>,1.2
//    public HashMap<Site,HashMap<Product,Integer>> orderDelivery(Site branch, HashMap<Site,HashMap<Product,Integer>> suppliers, LocalDate requiredDate){
//        if(date2deliveries.containsKey(requiredDate)){          //there is delivery in this date
//            for(Delivery d: date2deliveries.get(requiredDate)){     //the delivery is to the required date
//                if(branch.getShippingArea().equals(d.getShippingArea())){        //the delivery is to the required branch
//                    if(!d.getBranches().containsKey(branch)) {
//                        d.addBranch(branch, filesCounter);
//                        filesCounter ++;
//                    }
//                    for(Site supplier: suppliers.keySet()){
//                        for(Product p: suppliers.get(supplier).keySet()){
//                            if(p.getCoolingLevel() == trucks.get(d.getTruckNumber()).getCoolingLevel()){
//                                d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
//                                suppliers.get(supplier).remove(p);
//                            }
//                        }
//                        if(suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
//                            suppliers.remove(supplier);
//                    }
//                }
//            }
//        }
//        if(!suppliers.isEmpty()){   //open new delivery
//            Set<CoolingLevel> newDeliveriesCoolingLevels = countCoolingOptions(suppliers);
//            for(CoolingLevel coolingLevel: newDeliveriesCoolingLevels){
//                date2deliveries.put(requiredDate,new ArrayList<>());
//                Truck t = scheduleTruck(requiredDate,coolingLevel);
//                if(t == null)       //in case there is no truck available for this delivery
//                   return suppliers;
//                Driver driver = scheduleDriver(requiredDate,coolingLevel);
//                if(driver == null){     //in case there is no driver available for this delivery
//                    date2trucks.get(requiredDate).remove(t); //remove truck from this day deliveries list
//                    return suppliers;
//                }
//                Delivery d = new Delivery(deliveryCounter,requiredDate, LocalTime.NOON,t.getWeight(),new HashMap<>(),
//                        null,driver.getName(),t.getLicenseNumber(),branch.getShippingArea());
//                deliveryCounter++;
//                //add supply to the new delivery
//                for(Site supplier: suppliers.keySet()){
//                    for(Product p: suppliers.get(supplier).keySet()){
//                        if(p.getCoolingLevel() == coolingLevel){
//                            if(d.getSource() == null)
//                                d.setSource(supplier);
//                            d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
//                            suppliers.get(supplier).remove(p);
//                        }
//                    }
//                    if(suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
//                        suppliers.remove(supplier);
//                }
//            }
//        }
//        return null;
//    }

    public HashMap<Supplier,HashMap<Product,Integer>> orderDelivery(Branch branch, HashMap<Supplier,HashMap<Product,Integer>> suppliers, LocalDate requiredDate, HashMap<Supplier,Integer> supplierWeight){
        if(date2deliveries.containsKey(requiredDate)){          //there is delivery in this date
            for(Delivery d: date2deliveries.get(requiredDate)){     //the delivery is to the required date
                if(branch.getShippingArea().equals(d.getShippingArea())){        //the delivery is to the required branch
                    if(!d.getBranches().containsKey(branch)) {
                        d.addBranch(branch, filesCounter);
                        filesCounter ++;
                    }
                    for(Supplier supplier: suppliers.keySet()){
                        if(supplier.getCoolingLevel() == trucks.get(d.getTruckNumber()).getCoolingLevel()){
                            for(Product p: suppliers.get(supplier).keySet()) {
                                d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
                                suppliers.get(supplier).remove(p);
                            }
                        }
                        d.setTruckWeight(d.getTruckWeight() + supplierWeight.get(supplier));
                        if(suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
                            suppliers.remove(supplier);
                    }
                }
            }
        }
        if(!suppliers.isEmpty()){   //open new delivery
            Set<CoolingLevel> newDeliveriesCoolingLevels = countCoolingOptions(suppliers);
            for(CoolingLevel coolingLevel: newDeliveriesCoolingLevels){
                if(!date2deliveries.containsKey(requiredDate))
                    date2deliveries.put(requiredDate,new ArrayList<>());
                Truck t = scheduleTruck(requiredDate,coolingLevel);
                if(t == null)       //in case there is no truck available for this delivery
                    return suppliers;
                Driver driver = scheduleDriver(requiredDate,coolingLevel);
                if(driver == null){     //in case there is no driver available for this delivery
                    date2trucks.get(requiredDate).remove(t); //remove truck from this day deliveries list
                    return suppliers;
                }
                Delivery d = new Delivery(deliveryCounter,requiredDate, LocalTime.NOON,t.getWeight(),new HashMap<>(),
                        null,driver.getName(),t.getLicenseNumber(),branch.getShippingArea());
                deliveryCounter++;
                deliveries.put(d.getId(), d);
                date2deliveries.get(requiredDate).add(d);
                //add supply to the new delivery
                for(Supplier supplier: suppliers.keySet()){
                    if(supplier.getCoolingLevel() == coolingLevel){
                        for(Product p: suppliers.get(supplier).keySet()){
                            if(d.getSource() == null)
                                d.setSource(supplier);
                            if(!d.getSuppliers().containsKey(supplier))
                                d.addSupplier(supplier,filesCounter++);
                            d.addProductsToSupplier(supplier, p, suppliers.get(supplier).get(p));
                            suppliers.get(supplier).remove(p);
                        }
                    }
                    d.setTruckWeight(d.getTruckWeight() + supplierWeight.get(supplier));
                    if(suppliers.get(supplier).isEmpty())   //all the suppliers products scheduled
                        suppliers.remove(supplier);
                }
            }
        }
        return null;
    }

    private Truck scheduleTruck(LocalDate date, CoolingLevel coolingLevel){
        for(Truck truck : trucks.values()){
            if(date2trucks.containsKey(date) &&
                    !date2trucks.get(date).contains(truck) && truck.getCoolingLevel() == coolingLevel) {
                date2trucks.get(date).add(truck);
                return truck;
            }
            else if(!date2trucks.containsKey(date) && truck.getCoolingLevel() == coolingLevel){
                date2trucks.put(date,new ArrayList<>());
                date2trucks.get(date).add(truck);
                return truck;
            }
        }
        return null;
    }

    private Driver scheduleDriver(LocalDate date, CoolingLevel coolingLevel){
        for(Driver driver : drivers.values()){
            if(date2drivers.containsKey(date) &&
            !date2drivers.get(date).contains(driver) && driver.getCoolingLevel().ordinal() >= coolingLevel.ordinal()){
                date2drivers.get(date).add(driver);
                return driver;
            }
            else if(!date2drivers.containsKey(date) && driver.getCoolingLevel().ordinal() >= coolingLevel.ordinal()){
                date2drivers.put(date,new ArrayList<>());
                date2drivers.get(date).add(driver);
                return driver;
            }
        }
        return null;
    }

    private Set<CoolingLevel> countCoolingOptions(HashMap<Supplier,HashMap<Product,Integer>> suppliers){
        Set<CoolingLevel> s = new HashSet<>();
        for(Supplier supplier: suppliers.keySet())
            s.add(supplier.getCoolingLevel());
        return s;
    }

//    private Set<CoolingLevel> countCoolingOptions(HashMap<Site,HashMap<Product,Integer>> suppliers){
//        Set<CoolingLevel> s = new HashSet<>();
//        for(Site supplier: suppliers.keySet()){
//            for(Product product: suppliers.get(supplier).keySet()){
//                s.add(product.getCoolingLevel());
//            }
//        }
//        return s;
//    }

    public LocalDate getCurrDate() {
        return currDate;
    }

    public ArrayList<Integer> skipDay(){
        this.currDate = this.currDate.plusDays(1);
        if(date2deliveries.get(currDate) == null || date2deliveries.get(currDate).isEmpty())
            return null;
        ArrayList<Integer> overWeightDeliveries = new ArrayList<>();
        for(Delivery d : date2deliveries.get(currDate)){
            if(d.getTruckWeight() > trucks.get(d.getTruckNumber()).getMaxWeight())
                overWeightDeliveries.add(d.getId());
        }
        return overWeightDeliveries;
    }

    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight ,
                         LicenseType licenseType, CoolingLevel coolingLevel){
        if(trucks.containsKey(licenseNumber))
            return false;
        trucks.put(licenseNumber,new Truck(licenseNumber,model,weight,maxWeight,licenseType,coolingLevel));
        return true;
    }

    public boolean removeTruck(int licenseNumber){
        if(!trucks.containsKey(licenseNumber))
            return false;
        trucks.remove(licenseNumber);
        return true;
    }

    public boolean addDriver(int id, String name, LicenseType licenseType, CoolingLevel coolingLevel){
        if(drivers.containsKey(id))
            return false;
        drivers.put(id, new Driver(id, name, licenseType, coolingLevel));
        return true;
    }

    public boolean removeDriver(int id){
        if(!drivers.containsKey(id))
            return false;
        drivers.remove(id);
        return true;
    }

    public boolean addProduct(String name){
        if(products.containsKey(name))
            return false;
        products.put(name,new Product(name));
        return true;
    }

    public void addBranch(Branch branch){
        branches.put(branch.getAddress(),branch);
    }

    public void addSupplier(Supplier supplier, ArrayList<Product> supplierProducts){
        suppliers.put(supplier.getAddress(),supplier);
        suppliersProducts.put(supplier,supplierProducts);
    }

    //condition is wrong
//    public boolean truckOverWeight(int licenseNumber){
//        return trucks.get(licenseNumber).getWeight() > trucks.get(licenseNumber).getMaxWeight();
//    }

    public void storeProducts(HashMap<Product,Integer> newSupply){
        newSupply.forEach((key,value) -> {
            if(productsInStock.containsKey(key))                           //product exist in stock - update amount
                productsInStock.replace(key, productsInStock.get(key) + value);
            else
                productsInStock.put(key,value);
        });
    }

    public HashMap<Product,Integer> loadProducts(HashMap<Product,Integer> requestedSupply){
        Set<Product> keys = requestedSupply.keySet();
        for(Product p : keys){
            if(productsInStock.containsKey(p) && productsInStock.get(p) >= requestedSupply.get(p))    //product exist in stock in the requested amount
                productsInStock.replace(p, productsInStock.get(p) - requestedSupply.get(p));
            else if(productsInStock.containsKey(p)) {  //product exist in stock but not in the requested amount
                requestedSupply.replace(p,requestedSupply.get(p) - productsInStock.get(p));
                productsInStock.replace(p, 0);
            }
        }
        return requestedSupply;
    }

    public boolean replaceTruck(int deliveryID){
        Truck t = trucks.get(deliveries.get(deliveryID).getTruckNumber());
        LocalDate date = deliveries.get(deliveryID).getDate();
        for(int licenseNumber : this.trucks.keySet()){
            Truck optionalTruck = trucks.get(licenseNumber);
            if((optionalTruck.getMaxWeight() >= deliveries.get(deliveryID).getTruckWeight()) &&
            !date2trucks.get(date).contains(optionalTruck) &&
            optionalTruck.getCoolingLevel() == t.getCoolingLevel() &&
            optionalTruck.getLicenseType().ordinal() >= t.getLicenseType().ordinal()){
                deliveries.get(deliveryID).setTruckNumber(optionalTruck.getLicenseNumber());
                date2trucks.get(date).remove(t);
                date2trucks.get(date).add(trucks.get(licenseNumber));
                return true;
            }
        }
        return false;
    }

    public void unloadProducts(int deliveryID){
        double currWeight = deliveries.get(deliveryID).getTruckWeight();
        int maxWeight = trucks.get(deliveries.get(deliveryID).getTruckNumber()).getMaxWeight();
        double unloadFactor = (currWeight - maxWeight) / currWeight;
        for(Supplier supplier : deliveries.get(deliveryID).getSuppliers().keySet()) {
            for (Product p : deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().keySet()) {
                int amount = deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().get(p);
                int unloadAmount = (int) Math.ceil(amount * unloadFactor);
                deliveries.get(deliveryID).getSuppliers().get(supplier).getProducts().replace(p, amount - unloadAmount);
            }
        }
        deliveries.get(deliveryID).setTruckWeight(maxWeight);
    }

    public void replaceOrDropSite(int deliveryID){
        deliveries.get(deliveryID).removeSupplier();
    }

    public void overWeightAction(int deliveryID, int action){
        if(action == 1)
            replaceOrDropSite(deliveryID);
        else if(action == 2)
            replaceTruck(deliveryID);
        else if(action == 3)
            unloadProducts(deliveryID);
    }

    public Branch getBranch(String address){
        return branches.get(address);
    }

    public Supplier getSupplier(String address){
        return suppliers.get(address);
    }

    public Delivery getDelivery(int id){
        return deliveries.get(id);
    }

    public ArrayList<Site> getSites() {
        ArrayList<Site> sites = new ArrayList<>();
        sites.addAll(suppliers.values());
        sites.addAll(branches.values());
        return sites;
    }

    public HashMap<Supplier, ArrayList<Product>> getSuppliers() {

        return suppliersProducts;
    }

    public ArrayList<Branch> getBranches() {
        return new ArrayList<>(branches.values());
    }
}
