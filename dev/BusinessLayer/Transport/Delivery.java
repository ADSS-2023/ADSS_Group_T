package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;;

public class Delivery {
    private int id;
    private LocalDate date;
    private LocalTime departureTime;
    private int truckWeight;
    private HashMap<Site, File> suppliers;
    private HashMap<Site, File> branches;
    private Site source;
    private String driverName;
    private int truckNumber;
       
    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, HashMap<Site, File> suppliers,
            Site source, String driverName, int truckNumber) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.suppliers = suppliers;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
    }

    public HashMap<Product,Integer> getProductsOfSupplier(Site supplier){
        return suppliers.get(supplier).getProducts();
    }
    public int getId() {
        return this.id;
    }

    public HashMap<Site, File> getBranches() {
        return branches;
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
        return this.suppliers;
    }

    public void setDestinations(HashMap<Site, File> destinations) {
        this.suppliers = destinations;
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