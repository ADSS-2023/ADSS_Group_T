package BusinessLayer.Transport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

public class DeliveryController {
    private LogisticsCenter lc;

    public DeliveryController(){
        lc = new LogisticsCenter();
    }
    
    public LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> orderDelivery(Branch branch, LinkedHashMap<Supplier,LinkedHashMap<Product,Integer>> suppliers, LocalDate requiredDate, LinkedHashMap<Supplier,Integer> supplierWeight){
        return  lc.orderDelivery(branch, suppliers , requiredDate, supplierWeight);
    }
    public ArrayList<Integer> skipDay(){
        return lc.skipDay();
        
    }
    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight ,
                         LicenseType licenseType, CoolingLevel coolingLevel){
        return lc.addTruck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel);
    }
    public boolean removeTruck(int licenseNumber){
        return lc.removeTruck(licenseNumber);
    }
    public boolean addDriver(int id, String name, LicenseType licenseType, CoolingLevel coolingLevel){
       return lc.addDriver(id, name, licenseType, coolingLevel);
    }
    public boolean removeDriver(int id){
        return lc.removeDriver(id);
     }
     public void storeProducts(LinkedHashMap<Product,Integer> newSupply){
        lc.storeProducts(newSupply);
    }
    public LinkedHashMap<Product,Integer> loadProductsFromStock(LinkedHashMap<Product,Integer> requestedSupply){
       return lc.loadProductsFromStock(requestedSupply);
    }

    public boolean replaceTruck(int deliveryID){
        return lc.replaceTruck(deliveryID);
    }

    public void unloadProducts(int deliveryID){
        lc.unloadProducts(deliveryID);
    }

    public void replaceOrDropSite(int deliveryID){
        lc.replaceOrDropSite(deliveryID);
    }

    public LocalDate getCurrDate()
    {
        return lc.getCurrDate();
    }

    public boolean checkDate(LocalDate deliveryDate){
        return true;
    }
    public List<Site> getSites(){
        return lc.getSites();
    }
    public LinkedHashMap<Supplier, ArrayList<Product>> getSuppliers(){
        return lc.getSuppliers();
    }

    public void handleProblem(int id, int action) {
        lc.overWeightAction(id,action);
    }

    public ArrayList<Branch> getBranches(){
        return lc.getBranches();
    }

    public void addBranch(Branch newBranch){
        lc.addBranch(newBranch);
    }
    public void addSupplier(Supplier supplier,ArrayList <Product> listOfProducts ){
        lc.addSupplier(supplier,listOfProducts);
    }
 



    
}
