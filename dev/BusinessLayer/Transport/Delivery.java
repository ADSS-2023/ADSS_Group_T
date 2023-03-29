package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;;

public class Delivery {
    private int id;
    private LocalDate date;
    private LocalTime departureTime;
    private int truckWeight;
    private HashMap<Site, File> destinations;
    private Site source;
    private String driverName;
    private int truckNumber;
       
    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, HashMap<Site, File> destinations,
            Site source, String driverName, int truckNumber) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.destinations = destinations;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
    }

    public HashMap<Product,Integer> getProductsOfSupplier(Site supplier){
        return destinations.get(supplier).getProducts();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getTruckWeight() {
        return this.truckWeight;
    }

    public void setTruckWeight(int truckWeight) {
        this.truckWeight = truckWeight;
    }

    public Map<Site, File> getDestinations() {
        return this.destinations;
    }

    public void setDestinations(HashMap<Site, File> destinations) {
        this.destinations = destinations;
    }

    public Site getSource() {
        return this.source;
    }

    public void setSource(Site source) {
        this.source = source;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getTruckNumber() {
        return this.truckNumber;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

    public static void main(String[] args) {
        
    }
}