package BusinessLayer.Transport;
import java.time.LocalDate;
import java.util.*;
import BusinessLayer.Transport.Driver.LicenseType;
import BusinessLayer.Transport.Driver.CoolingLevel;

public class LogisticsCenter {

    private  HashMap<Integer,Truck> trucks;
    private  HashMap<Integer,Delivery> deliveries;
    private  HashMap<Product,Integer> products;
    private  HashMap<Integer,Driver> drivers;
    private  HashMap<LocalDate,ArrayList<Truck>> date2trucks;
    private  HashMap<LocalDate,ArrayList<Driver>> date2drivers;
    private  HashMap<LocalDate,ArrayList<Delivery>> date2deliveries;
    private int deliveryCounter = 0;

    private LocalDate currDate;

    public LogisticsCenter( HashMap<Integer,Truck> trucks,HashMap<Integer,Delivery> deliveries,
                        HashMap<Product,Integer> products,HashMap<Integer,Driver> drivers){
        this.trucks = trucks;
        this.deliveries = deliveries;
        this.products = products;
        this.drivers = drivers;
        this.date2trucks = new HashMap<>();
        this.date2drivers = new HashMap<>();
        this.date2deliveries = new HashMap<>();
        this.currDate = LocalDate.now();
    }

    public LogisticsCenter(){
        this.trucks = new HashMap<>();
        this.deliveries = new HashMap<>();
        this.products = new HashMap<>();
        this.drivers = new HashMap<>();
        this.date2trucks = new HashMap<>();
        this.date2drivers = new HashMap<>();
        this.date2deliveries = new HashMap<>();
    }
 

    //s1,<>,1.1
    //s2,<>,1.1
    //s3,<>,1.2
    public boolean orderDelivery(Site branch, HashMap<Site,HashMap<Product,Integer>> suppliers, LocalDate requiredDate, String coolingLevel){
        if(date2deliveries.containsKey(requiredDate)){
            for(Delivery d: date2deliveries.get(requiredDate)){
                if(d.get)
            }

        }
        else{   //open new delivery

        }
        return true;
    }

    public int skipDay(){
        this.currDate = this.currDate.plusDays(1);
        //TODO: check if there is delivery today
        Delivery d = null;
        return d.getId();
    }

    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight ,
                         LicenseType licenseType, String coolingLevel){
        if(trucks.containsKey(licenseNumber))
            return false;
        trucks.put(licenseNumber,new Truck(licenseNumber,model,weight,maxWeight,licenseType,CoolingLevel.valueOf(coolingLevel)));
        return true;
    }

    public boolean removeTruck(int licenseNumber){
        if(!trucks.containsKey(licenseNumber))
            return false;
        trucks.remove(licenseNumber);
        return true;
    }

    public boolean addDriver(int id, String name, LicenseType licenseType, String coolingLevel){
        if(drivers.containsKey(id))
            return false;
        drivers.put(id, new Driver(id, name, licenseType, CoolingLevel.valueOf(coolingLevel)));
        return true;
    }

    public boolean removeDriver(int id){
        if(!drivers.containsKey(id))
            return false;
        drivers.remove(id);
        return true;
    }
    
    public boolean truckOverWeight(int licenseNumber){
        return trucks.get(licenseNumber).getWeight() > trucks.get(licenseNumber).getMaxWeight();
    }

    public void storeProducts(HashMap<Product,Integer> newSupply){
        newSupply.forEach((key,value) -> {
            if(products.containsKey(key))                           //product exist in stock - update amount
                products.replace(key, products.get(key) + value);
            else
                products.put(key,value);
        });
    }

    public HashMap<Product,Integer> loadProducts(HashMap<Product,Integer> requestedSupply){
        Set<Product> keys = requestedSupply.keySet();
        for(Product p : keys){
            if(products.containsKey(p) && products.get(p) >= requestedSupply.get(p))    //product exist in stock in the requested amount
                products.replace(p, products.get(p) - requestedSupply.get(p));
            else if(products.containsKey(p)) {  //product exist in stock but not in the requested amount
                requestedSupply.replace(p,requestedSupply.get(p) - products.get(p));
                products.replace(p, 0);
            }
        }
        return requestedSupply;
    }

    public boolean replaceTruck(int deliveryID, int weight,LocalDate date){
        //TODO: is the weight already updated in the delivery form? if so remove weight param
        Truck t = trucks.get(deliveries.get(deliveryID).getTruckNumber());
        for(int licenseNumber : this.trucks.keySet()){
            Truck optionalTruck = trucks.get(licenseNumber);
            if((optionalTruck.getMaxWeight() >= weight) &&
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

    public void unloadProducts(int deliveryID, Site site){
        double currWeight = deliveries.get(deliveryID).getTruckWeight();
        int maxWeight = trucks.get(deliveries.get(deliveryID).getTruckNumber()).getMaxWeight();
        double unloadFactor = (currWeight - maxWeight) / currWeight;
        for(Product p: deliveries.get(deliveryID).getDestinations().get(site).getProducts().keySet()){
            int amount = deliveries.get(deliveryID).getDestinations().get(site).getProducts().get(p);
            int unloadAmount = (int)Math.ceil(amount * unloadFactor);
            deliveries.get(deliveryID).getDestinations().get(site).getProducts().replace(p,amount - unloadAmount);
        }
    }

    public void replaceOrDropSite(){

    }




    
}
