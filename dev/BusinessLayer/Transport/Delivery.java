package BusinessLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;;

public class Delivery {
    private int id;
    private LocalDate date;
    private LocalTime departureTime;
    private int truckWeight;
    private LinkedHashMap<Supplier, File> suppliers;
    private LinkedHashMap<Branch, File> branches;
    private Site source;
    private String driverName;
    private int truckNumber;
    private String shippingArea;
    private String note;
       
    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, LinkedHashMap<Supplier, File> suppliers,
            Site source, String driverName, int truckNumber, String shippingArea) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.suppliers = suppliers;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
        this.branches = new LinkedHashMap<>();
        this.shippingArea = shippingArea;
        this.note = "";
    }

    /**
     * remove the first supplier from the suppliers map
     */
    public void removeSupplier(){
        suppliers.remove(suppliers.entrySet().iterator().next().getKey());
    }

    /**
     * add branch to the branches map
     * @param branch
     * @param fileID
     */
    public void addBranch(Branch branch, int fileID){
        File f = new File(fileID);
        branches.put(branch,f);
    }

    /**
     * add products in required amount to the supplier file for this delivery
     * @param supplier
     * @param p - the product to add
     * @param amount
     */
    public void addProductsToSupplier(Supplier supplier, Product p, int amount){
         suppliers.get(supplier).addProduct(p,amount);
    }
    public String getShippingArea() {
        return shippingArea;
    }
/*
    //for future use
    public LinkedHashMap<Product,Integer> getProductsOfSupplier(Supplier supplier){
        return suppliers.get(supplier).getProducts();
    }
 */
    public int getId() {
        return this.id;
    }

    public LinkedHashMap<Branch, File> getBranches() {
        return branches;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int getTruckWeight() {
        return this.truckWeight;
    }

    public void setTruckWeight(int truckWeight) {
        this.truckWeight = truckWeight;
    }

    public LinkedHashMap<Supplier, File> getSuppliers() {
        return suppliers;
    }
    public Site getSource() {
        return this.source;
    }

    public void setSource(Site source) {
        this.source = source;
    }
/*
    //for future use
    public String getDriverName() {
        return this.driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
 */

    public int getTruckNumber() {
        return this.truckNumber;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

    /**
     * add supplier to the suppliers list of this delivery
     * @param supplier
     * @param fileID
     */
    public void addSupplier(Supplier supplier , int fileID){
        this.suppliers.put(supplier, new File(fileID));

    }

    public String getDriverName() {
        return this.driverName;
    }

    public void addNote(String s) {
        note = note + "\n" + s;
    }
}