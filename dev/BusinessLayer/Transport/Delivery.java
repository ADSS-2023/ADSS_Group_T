package BusinessLayer.Transport;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.DeliveryDTO;
import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Delivery {
    private final int id;
    private final LocalDate date;
    private final LocalTime departureTime;
    private final LinkedHashMap<Supplier, File> unHandledSuppliers;
    private final LinkedHashMap<Supplier, File> handledSuppliers;
    private final LinkedHashMap<Branch, File> unHandledBranches;
    private final LinkedHashMap<Branch, File> handledBranches;
    private File toLogisticsCenterFile;
    private File fromLogisticsCenterFile;
    private final int shippingArea;
    private int truckWeight;
    private Site source;
    private int driverID;
    private int truckNumber;
    private String note;
    private Driver.CoolingLevel coolingLevel;
    private DalDeliveryService dalDeliveryService;

    public Delivery(int id, LocalDate date, LocalTime departureTime, int truckWeight, LinkedHashMap<Supplier, File> suppliers,
                    LinkedHashMap<Branch, File> branches, Site source, int truckNumber, int shippingArea, DalDeliveryService dalDeliveryService) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.unHandledSuppliers = suppliers;
        this.source = source;
        this.truckNumber = truckNumber;
        this.handledSuppliers = new LinkedHashMap<>();
        this.unHandledBranches = branches;
        this.handledBranches = new LinkedHashMap<>();
        this.shippingArea = shippingArea;
        this.note = "";
        this.dalDeliveryService = dalDeliveryService;
    }

    public Delivery(DeliveryDTO dto,DalDeliveryService dalDeliveryService) throws SQLException {
        this(dto.getId(), Time.stringToDate(dto.getDeliveryDate()),Time.stringToTime(dto.getDepartureTime()), dto.getTruckWeight(),new LinkedHashMap<>(),
                new LinkedHashMap<>(), dalDeliveryService.findSite(dto.getSource()), dto.getTruckNumber(), dto.getShippingArea(), dalDeliveryService);
    }

    public void addHandledSupplier(Supplier supplier, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.insertHandledSupplier(id,supplier.getAddress(),p.getName(),products.get(p));
        }
        handledSuppliers.put(supplier, f);
    }

    public int supplierHandled(Supplier supplier, int fileCounter, HashMap<Product, Integer> copyOfSupplierFileProducts) {
        ArrayList<Branch> branchesTmp = new ArrayList<>(unHandledBranches.keySet());
        for (Branch b : branchesTmp) {
            if (!copyOfSupplierFileProducts.isEmpty())
                fileCounter = checkBranch(b, fileCounter, copyOfSupplierFileProducts);
        }
        return fileCounter;
    }

    private int checkBranch(Branch b, int fileCounter, HashMap<Product, Integer> copyOfSupplierFileProducts) {
        File branchFile = unHandledBranches.get(b);
        ArrayList<Product> productsTmp = new ArrayList<>(branchFile.getProducts().keySet());
        for (Product branchProduct : productsTmp) {
            if (copyOfSupplierFileProducts.containsKey(branchProduct)) {
                if (!handledBranches.containsKey(b)) {
                    File newBranchFile = new File(fileCounter);
                    fileCounter++;
                    handledBranches.put(b, newBranchFile);
                }
                int amount = Math.min(copyOfSupplierFileProducts.get(branchProduct), unHandledBranches.get(b).getProducts().get(branchProduct));
                handleBranch(b, branchProduct, amount, copyOfSupplierFileProducts);
            }
        }
        return fileCounter;
    }

    private void handleBranch(Branch b, Product branchProduct, int amount, HashMap<Product, Integer> copyOfSupplierFileProducts) {
        handledBranches.get(b).addProduct(branchProduct, amount);
        unHandledBranches.get(b).removeProduct(branchProduct, amount);
        copyOfSupplierFileProducts.replace(branchProduct, copyOfSupplierFileProducts.get(branchProduct) - amount);
        if (copyOfSupplierFileProducts.get(branchProduct) == 0)
            copyOfSupplierFileProducts.remove(branchProduct);
    }

    /**
     * remove the first supplier from the suppliers map
     */
    public void removeSupplier(String address) {
        unHandledSuppliers.remove(unHandledSuppliers.get(address));
    }

    /**
     * add branch to the branches map
     *
     * @param branch
     * @param fileID
     */
    public void addBranch(Branch branch, int fileID) {
        File f = new File(fileID);
        unHandledBranches.put(branch, f);
    }

    /**
     * add products in required amount to the supplier file for this delivery
     *
     * @param supplier
     * @param p        - the product to add
     * @param amount
     */
    public int addProductToSupplier(Supplier supplier, Product p, int amount, int fileCounter) {
        if (!unHandledSuppliers.containsKey(supplier))
            unHandledSuppliers.put(supplier, new File(fileCounter++));
        unHandledSuppliers.get(supplier).addProduct(p, amount);
        return fileCounter;
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


    public LinkedHashMap<Branch, File> getHandledBranches() {
        return handledBranches;
    }

    public LinkedHashMap<Branch, File> getUnHandledBranches() {
        return unHandledBranches;
    }

    public LinkedHashMap<Supplier, File> getHandledSuppliers() {
        return handledSuppliers;
    }

    public LinkedHashMap<Supplier, File> getUnHandledSuppliers() {
        return unHandledSuppliers;
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public int getTruckNumber() {
        return this.truckNumber;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

    /**
     * add supplier to the suppliers list of this delivery
     *
     * @param supplier
     * @param fileID
     */
    public void addSupplier(Supplier supplier, int fileID) {
        this.unHandledSuppliers.put(supplier, new File(fileID));
    }

    public int getDriverID() {
        return this.driverID;
    }

    public void addNote(String s) {
        note = note + "\n" + s;
    }

    public LinkedHashMap<Supplier, File> getSuppliers() {
        return null;
    }


    public void setDriver(Driver driver) {
        this.driverID = driver.getId();
    }

    public void addLogisticCenterDestination(int fileCounter){
        toLogisticsCenterFile = new File(fileCounter);
        //TODO: add to dal
    }
}