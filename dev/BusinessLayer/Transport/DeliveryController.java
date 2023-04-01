package BusinessLayer.Transport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

public class DeliveryController {
    private LogisticsCenter lc;

    public DeliveryController(){
        lc = new LogisticsCenter();
    }
    
    public HashMap<Supplier,HashMap<Product,Integer>> orderDelivery(Branch branch, HashMap<Supplier,HashMap<Product,Integer>> suppliers, LocalDate requiredDate, HashMap<Supplier,Integer> supplierWeight){
        lc.orderDelivery(branch, suppliers , requiredDate, supplierWeight);
        return null;
    }
    public ArrayList<Delivery> skipDay(){
        //return lc.skipDay();
        return null;
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
     public void storeProducts(HashMap<Product,Integer> newSupply){
        lc.storeProducts(newSupply);
    }
    public HashMap<Product,Integer> loadProducts(HashMap<Product,Integer> requestedSupply){
       return lc.loadProducts(requestedSupply);
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
    public HashMap<Site, List<Product>> getSuppliers(){
        return lc.getSuppliers();
    }

    public void handleProblem(int id, String string) {
    }

    public HashMap<Site,List<Product>> getBranches(){
        return lc.getBranches();
    }

    public void addBranch(Branch newBranch){
        lc.addBranch(newBranch);
    }
    public void addSupplier(Site supplier,List <Product> listOfProducts ){
        lc.addSupplier(supplier,listOfProducts);
    }
    public void addSupplier(Supplier newSupplier){
        lc.addSupplier(newSupplier);
    }



    
}
