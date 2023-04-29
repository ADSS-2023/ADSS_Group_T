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
    private LinkedHashMap<Supplier, File> handledSuppliers;
    private LinkedHashMap<Branch, File> branches;
    private LinkedHashMap<Branch, File> handledBranches;
    private Site source;
    private String driverName;
    private int truckNumber;
    private int shippingArea;
    private String note;

    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, LinkedHashMap<Supplier, File> suppliers,
                    Site source, String driverName, int truckNumber, int shippingArea) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.suppliers = suppliers;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
        this.suppliers = new LinkedHashMap<>();
        this.handledSuppliers = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.handledBranches = new LinkedHashMap<>();
        this.shippingArea = shippingArea;
        this.note = "";
    }

    public int supplierHandled(Supplier supplier,int fileCounter){
        File f = suppliers.get(supplier);
        handledSuppliers.put(supplier,f);
        suppliers.remove(supplier);
        HashMap<Product,Integer> copyOfSupplierFileProducts = new HashMap<>(f.getProducts());
        ArrayList<Branch> branchesTmp = new ArrayList<>(branches.keySet());
        for(Branch b : branchesTmp){
            if(!copyOfSupplierFileProducts.isEmpty())
                fileCounter = checkBranch(b,fileCounter,copyOfSupplierFileProducts);
        }
        return  fileCounter;
    }

    private int checkBranch(Branch b,int fileCounter, HashMap<Product,Integer> copyOfSupplierFileProducts){
        File branchFile = branches.get(b);
        ArrayList<Product> productsTmp = new ArrayList<>(branchFile.getProducts().keySet());
        for(Product branchProduct : productsTmp) {
            if(copyOfSupplierFileProducts.containsKey(branchProduct)){
                if(!handledBranches.containsKey(b)){
                    File newBranchFile = new File(fileCounter);
                    fileCounter++;
                    handledBranches.put(b,newBranchFile);
                }
                int amount = Math.min(copyOfSupplierFileProducts.get(branchProduct), branches.get(b).getProducts().get(branchProduct));
                handleBranch(b,branchProduct,amount,copyOfSupplierFileProducts);
            }
        }
        return fileCounter;
    }

    private void handleBranch(Branch b,Product branchProduct, int amount, HashMap<Product,Integer> copyOfSupplierFileProducts){
        handledBranches.get(b).addProduct(branchProduct, amount);
        branches.get(b).removeProduct(branchProduct, amount);
        copyOfSupplierFileProducts.replace(branchProduct,copyOfSupplierFileProducts.get(branchProduct) - amount);
        if(copyOfSupplierFileProducts.get(branchProduct) == 0)
            copyOfSupplierFileProducts.remove(branchProduct);
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
    public int getShippingArea() {
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