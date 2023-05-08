//package BusinessLayer.Transport;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//
//import BusinessLayer.HR.Driver.CoolingLevel;
//import BusinessLayer.HR.Driver.LicenseType;
//
//public class DeliveryController {
//    private LogisticsCenter lc;
//
//    public DeliveryController(){
//        lc = new LogisticsCenter();
//    }
//
//      /**
//     * handle the request for a new delivery
//     * @param branch - the branch to deliver the products to
//     * @param suppliers - the products ordered from each supplier
//     * @param requiredDate - required date for delivery
//     * @param supplierWeight - map that holds the weight of the products for each supplier
//     * @return map of the suppliers products that have not been schedule for delivery due to lack of drivers/trucks in that date
//     */
//    public LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> orderDelivery(Branch branch, LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> suppliers, LocalDate requiredDate, LinkedHashMap<Supplier,Integer> supplierWeight){
//        return  lc.orderDelivery(branch, suppliers , requiredDate, supplierWeight);
//    }
//
//    /**
//     * advanced to the next day and checks if there are deliveries with overweight problem in this day
//     * @return List of the delivery ids that scheduled for the new day and have overweight problem
//     */
//    public ArrayList<Integer> skipDay(){
//        return lc.skipDay();
//
//    }
//
//      /**
//     * add a new truck to the trucks map
//     * @param licenseNumber - the license number of the truck
//     * @param model - the truck model
//     * @param weight - the weight of the truck without supply
//     * @param maxWeight - max weight of the truck with supply
//     * @param licenseType - the license type required to drive the truck
//     * @param coolingLevel - the cooling level of the truck
//     * @return true if the truck added successfully , and false otherwise
//     */
//    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight ,
//                         LicenseType licenseType, CoolingLevel coolingLevel){
//        return lc.addTruck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel);
//    }
//
//    /**
//     * remove a truck from the trucks map
//     * @param licenseNumber of the truck
//     * @return true if the truck removed successfully , false otherwise
//     */
//    public boolean removeTruck(int licenseNumber){
//        return lc.removeTruck(licenseNumber);
//    }
//
//     /**
//     * add a new driver to the drivers map
//     * @param id - the id of the driver
//     * @param name - the name of the driver
//     * @param licenseType - the license type of the driver
//     * @param coolingLevel - the cooling level to which the driver is qualified
//     * @return true if the driver added successfully , and false otherwise
//     */
//    public boolean addDriver(int id, String name, LicenseType licenseType, CoolingLevel coolingLevel){
//       return lc.addDriver(id, name, licenseType, coolingLevel);
//    }
//
//    /**
//     * remove a driver from the drivers map
//     * @param id - thr id of the driver
//     * @return true if the driver removed successfully , and false otherwise
//     */
//    public boolean removeDriver(int id){
//        return lc.removeDriver(id);
//     }
//
//     /**
//     * store products in the logistics center stocks
//     * @param newSupply - map with the amount for each product required to store
//     */
//     public void storeProducts(LinkedHashMap<Product,Integer> newSupply){
//        lc.storeProducts(newSupply);
//    }
//
//     /**
//     * load products from the stock of the logistics center
//     * @param requestedSupply - map of the products and amounts required to load
//     * @return map of products and amounts that are not available in the logistics center stock
//     */
//    public LinkedHashMap<Product,Integer> loadProductsFromStock(LinkedHashMap<Product,Integer> requestedSupply){
//       return lc.loadProductsFromStock(requestedSupply);
//    }
//
//    /**
//     * replace a truck for a delivery
//     * @param deliveryID - the delivery who needed a truck replacement
//     * @return true if the truck was replaced, false otherwise(there is no available truck)
//     */
//    public boolean replaceTruck(int deliveryID){
//        return lc.replaceTruck(deliveryID);
//    }
//
//    /**
//     * unload products from the delivery due to overweight
//     * @param deliveryID the delivery id to unload products from
//     */
//    public void unloadProducts(int deliveryID){
//        lc.unloadProducts(deliveryID);
//    }
//
//    /**
//     * replace or drop one of the suppliers from the delivery due to overweight
//     * @param deliveryID - the id of the delivery that the action required for
//     */
//    public void replaceOrDropSite(int deliveryID){
//        lc.replaceOrDropSite(deliveryID);
//    }
//
//     /**
//     * handle the required action for the overweight problem for specific delivery
//     * @param deliveryID - the id of the delivery that has overweight
//     * @param action - the action required
//     */
//    public void overWeightAction(int id, int action) {
//        lc.overWeightAction(id,action);
//    }
//
//    /**
//     * the function checks if the received date is after the current date
//     * @param date - the date to check
//     * @return true if the received date is after the current date, false otherwise
//     */
//    public boolean checkDate(LocalDate deliveryDate){
//        return lc.checkDate(deliveryDate);
//    }
//
//    public LocalDate getCurrDate()
//    {
//        return lc.getCurrDate();
//    }
//
//    public List<Site> getSites(){
//        return lc.getSites();
//    }
//    public LinkedHashMap<Supplier, ArrayList<Product>> getSuppliers(){
//        return lc.getSuppliers();
//    }
//
//    public ArrayList<Branch> getBranches(){
//        return lc.getBranches();
//    }
//
//    public void addBranch(Branch newBranch){
//        lc.addBranch(newBranch);
//    }
//    public void addSupplier(Supplier supplier,ArrayList <Product> listOfProducts ){
//        lc.addSupplier(supplier,listOfProducts);
//    }
//
//
//
//
//
//}
