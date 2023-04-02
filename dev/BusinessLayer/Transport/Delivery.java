package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;;

public class Delivery {
    private int id;
    private LocalDate date;
    private LocalTime departureTime;
    private int truckWeight;
    private HashMap<Supplier, File> suppliers;
    private HashMap<Branch, File> branches;
    private Site source;
    private String driverName;
    private int truckNumber;
    private String shippingArea;
       
    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, HashMap<Supplier, File> suppliers,
            Site source, String driverName, int truckNumber, String shippingArea) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.suppliers = suppliers;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
        this.branches = new HashMap<>();
        this.shippingArea = shippingArea;
    }

    public void removeSupplier(){
        suppliers.remove(suppliers.entrySet().iterator().next().getKey());
    }

    public void addBranch(Branch branch, int fileID){
        File f = new File(fileID);
        branches.put(branch,f);
    }

    public void addProductsToSupplier(Supplier supplier, Product p, int amount){
        suppliers.get(supplier).addProduct(p,amount);
    }
    public String getShippingArea() {
        return shippingArea;
    }

    public HashMap<Product,Integer> getProductsOfSupplier(Supplier supplier){
        return suppliers.get(supplier).getProducts();
    }
    public int getId() {
        return this.id;
    }

    public HashMap<Branch, File> getBranches() {
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

    public HashMap<Supplier, File> getSuppliers() {
        return suppliers;
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

    public void addSupplier(Supplier supplier , int fileID){
        this.suppliers.put(supplier, new File(fileID));

    }
}