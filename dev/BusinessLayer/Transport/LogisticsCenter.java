package BusinessLayer.Transport;
import java.time.LocalDate;
import java.util.*;

public class LogisticsCenter {

private  HashMap<Integer,Truck> trucks;
private  HashMap<Integer,Delivery> deliveries;
private  HashMap<Product,Integer> products;
private  HashMap<Integer,Driver> drivers;
private  HashMap<LocalDate,ArrayList<Truck>> date2trucks;
private  HashMap<LocalDate,ArrayList<Driver>> date2drivers;
private  HashMap<LocalDate,ArrayList<Delivery>> date2deliveries;

private int deliveryCounter = 0;



public LogisticsCenter( HashMap<Integer,Truck> trucks,
        HashMap<Product,Integer> products,HashMap<Integer,Driver> drivers){
        
                this.trucks = trucks;
                this.products = products;
                this.drivers = drivers;    
                this.deliveries = new HashMap<>();        
    }
 

   
    public boolean addNewOrder(Site site,Map<Product, Integer> productQuantities, LocalDate date){
        return true;
    }

    public boolean addNewTruck(Truck newTruck) {
        return true;
    }

    public boolean addNewDriver(Driver newDriver) {
        return true;
    }

    public boolean addNewProduct(Product newProduct, int amount) {
        return true;
    }

    



    
}
