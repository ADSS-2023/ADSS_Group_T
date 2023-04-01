package BusinessLayer.Transport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

public class DeliveryController {
    private LogisticsCenter lc;

    public DeliveryController(){
        lc = new LogisticsCenter();
    }
    
    public boolean orderDelivery(Site branch, HashMap<Site,HashMap<Product,Integer>> suppliers, LocalDate date, String coolingLevel){
        return lc.orderDelivery(branch,suppliers,date,coolingLevel);
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

    public boolean replaceTruck(int deliveryID, int weight,LocalDate date){
        return lc.replaceTruck(deliveryID, weight, date);
    }

    public void unloadProducts(int deliveryID, Site site){
        lc.unloadProducts(deliveryID, site);
    }

    public void replaceOrDropSite(){
        lc.replaceOrDropSite();
    }

    public LocalDate getCurrDate()
    {
        return lc.getCurrDate();
    }

    public boolean checkDate(LocalDate deliveryDate){
        return true;
    }
    // public List<Site> getSites(){
    //     lc.getSites();
    // }
    // public List<Site> getSuppliers(){
    //     lc.getSuppliers();
    // }

    public void handleProblem(int id, String string) {
    }

    // public HashMap<Site,list<product>> getBranches(){
    //     lc.getBranches();
    // }

    // public void addSite(Site newSite){
    //     lc.addSite(newSite);
    // }
    // public void addSupplier(Site supplier,List <Product> listOfProducts ){
    //     lc.addSupplier(supplier,listOfProducts);
    // }



    
}
