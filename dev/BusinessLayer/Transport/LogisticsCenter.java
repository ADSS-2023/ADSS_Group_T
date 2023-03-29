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



public LogisticsCenter( HashMap<Integer,Truck> trucks,HashMap<Integer,Delivery> deliveries,
                        HashMap<Product,Integer> products,HashMap<Integer,Driver> drivers){
        
                this.trucks = trucks;
                this.deliveries = deliveries;
                this.products = products;
                this.drivers = drivers;            
    }
 

    //s1,<>,1.1
    //s2,<>,1.1
    //s3,<>,1.2
    public boolean orderDelivery(Site site,ArrayList<Product> products, LocalDate date){
        //if(date2deliveries.containsKey(date))
        return true;

    }

    public void addTruck(int licenseNumber, String model, int weight, int maxWeight ,
                         LicenseType licenseType, CoolingLevel coolingLevel){
        trucks.put(licenseNumber,new Truck(licenseNumber,model,weight,maxWeight,licenseType,coolingLevel));
    }
    



    
}
