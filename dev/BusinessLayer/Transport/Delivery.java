package BusinessLayer.Transport;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.DeliveryDTO;
import DataLayer.HR_T_DAL.DTOs.DeliveryUnHandledSitesDTO;
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
    private LinkedHashMap<Supplier, File> unHandledSuppliers;
    private LinkedHashMap<Supplier, File> handledSuppliers;
    private LinkedHashMap<Branch, File> unHandledBranches;
    private LinkedHashMap<Branch, File> handledBranches;
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
        this(dto.getId(), Time.stringToLocalDate(dto.getDeliveryDate()),Time.stringToLocalTime(dto.getDepartureTime()), dto.getTruckWeight(),new LinkedHashMap<>(),
                new LinkedHashMap<>(), dalDeliveryService.findSite(dto.getSource()), dto.getTruckNumber(), dto.getShippingArea(), dalDeliveryService);
    }

    public int supplierHandled(int fileCounter, HashMap<Product, Integer> copyOfSupplierFileProducts) throws SQLException {
        ArrayList<Branch> branchesTmp = new ArrayList<>(getUnHandledBranches().keySet());
        for (Branch b : branchesTmp) {
            if (!copyOfSupplierFileProducts.isEmpty())
                fileCounter = checkBranch(b, fileCounter, copyOfSupplierFileProducts);
        }
        return fileCounter;
    }

    private int checkBranch(Branch b, int fileCounter, HashMap<Product, Integer> copyOfSupplierFileProducts) throws SQLException {
        File branchFile = getHandledBranches().get(b);
        ArrayList<Product> productsTmp = new ArrayList<>(branchFile.getProducts().keySet());
        for (Product branchProduct : productsTmp) {
            if (copyOfSupplierFileProducts.containsKey(branchProduct)) {
                int amount = Math.min(copyOfSupplierFileProducts.get(branchProduct), getUnHandledBranches().get(b).getProducts().get(branchProduct));
                fileCounter = handleBranch(b, branchProduct, amount, copyOfSupplierFileProducts, fileCounter);
            }
        }
        return fileCounter;
    }

    private int handleBranch(Branch b, Product product, int amount, HashMap<Product, Integer> copyOfSupplierFileProducts, int fileCounter) throws SQLException {
        fileCounter = addProductToHandledBranch(b,product,amount,fileCounter);
        removeProductFromUnHandledBranch(b,product, amount);
        copyOfSupplierFileProducts.replace(product, copyOfSupplierFileProducts.get(product) - amount);
        if (copyOfSupplierFileProducts.get(product) == 0)
            copyOfSupplierFileProducts.remove(product);
        return fileCounter;
    }

//    /**
//     * remove the first supplier from the suppliers map
//     */
//    public void removeSupplier(String address) throws SQLException {
//        getUnHandledSuppliers().remove(unHandledSuppliers.get(address));
//    }

    /**
     * add branch to the branches map
     * @param branch
     * @param fileID
     */
    public void addUnHandledBranch(Branch branch, int fileID) throws SQLException {
        File f = new File(fileID);
        unHandledBranches.put(branch, f);
        dalDeliveryService.updateCounter("file counter",fileID+1);
    }

    /**
     * add products in required amount to the supplier file for this delivery
     * @param supplier
     * @param p        - the product to add
     * @param amount
     */
    public int addProductToUnHandledSupplier(Supplier supplier, Product p, int amount, int fileCounter) throws SQLException {
        if (!unHandledSuppliers.containsKey(supplier)) {
            unHandledSuppliers.put(supplier, new File(fileCounter++));
            dalDeliveryService.updateCounter("file counter",fileCounter);
            dalDeliveryService.insertUnHandledSite(id,supplier.getAddress(),p.getName(),fileCounter - 1,amount);
        }
        else
            dalDeliveryService.updateUnHandledSite(id,supplier.getAddress(),p.getName(), unHandledSuppliers.get(supplier).getId() ,amount);
        unHandledSuppliers.get(supplier).addProduct(p, amount);
        return fileCounter;
    }

    public void addProductToUnHandledBranch(Branch branch, Product p, int amount) throws SQLException {
        File file = unHandledBranches.get(branch);
        if (file.getProducts().isEmpty())
            dalDeliveryService.insertUnHandledSite(id,branch.getAddress(),p.getName(),file.getId(),amount);
        else
            dalDeliveryService.updateUnHandledSite(id,branch.getAddress(),p.getName(), file.getId() ,amount);
        file.addProduct(p, amount);
    }

    public int addProductToHandledBranch(Branch branch, Product p, int amount, int fileCounter) throws SQLException {
        if (!handledBranches.containsKey(branch)) {
            handledBranches.put(branch, new File(fileCounter++));
            dalDeliveryService.updateCounter("file counter",fileCounter);
            dalDeliveryService.insertHandledSite(id,branch.getAddress(),p.getName(),fileCounter - 1,amount);
        }
        else
            dalDeliveryService.updateHandledSite(id,branch.getAddress(),p.getName(), handledBranches.get(branch).getId() ,amount);
        handledBranches.get(branch).addProduct(p, amount);
        return fileCounter;
    }

    public void removeProductFromUnHandledBranch(Branch branch, Product product, int amount) throws SQLException {
        LinkedHashMap<String,Object> pk = new LinkedHashMap<>();
        pk.put("deliveryId",id);
        pk.put("siteAddress",branch.getAddress());
        pk.put("productName",product.getName());
        DeliveryUnHandledSitesDTO dto = dalDeliveryService.findDeliveryUnHandledSites(pk);
        if(dto.getAmount() == amount)
            dalDeliveryService.deleteUnHandledSite(getId(), branch.getAddress(), product.getName(), dto.getFileId(),amount);
        else
            dalDeliveryService.updateUnHandledSite(id, branch.getAddress(), product.getName(), dto.getFileId(),dto.getAmount() - amount);
        unHandledBranches.get(branch).removeProduct(product, amount);
    }

    public void removeUnHandledSupplier(Supplier supplier, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.deleteUnHandledSite(id,supplier.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        getUnHandledSuppliers().remove(supplier);
    }

    public int addProductToLogisticCenterFromFile(String logisticCenterAddress,Product p, int amount, int fileCounter) throws SQLException {
        if (fromLogisticsCenterFile == null){
            fromLogisticsCenterFile = new File(fileCounter++);
            dalDeliveryService.updateCounter("file counter",fileCounter);
        }
        dalDeliveryService.insertUnHandledSite(id,logisticCenterAddress,p.getName(),fromLogisticsCenterFile.getId(),amount);
        fromLogisticsCenterFile.addProduct(p, amount);
        return fileCounter;
    }

    public int addProductToLogisticCenterToFile(String logisticCenterAddress,Product p, int amount, int fileCounter) throws SQLException {
        if (toLogisticsCenterFile == null){
            toLogisticsCenterFile = new File(fileCounter++);
            dalDeliveryService.updateCounter("file counter",fileCounter);
            dalDeliveryService.insertUnHandledSite(id,logisticCenterAddress,p.getName(),toLogisticsCenterFile.getId(),amount);
        }
        dalDeliveryService.updateUnHandledSite(getId(),logisticCenterAddress,p.getName(),toLogisticsCenterFile.getId(),
                toLogisticsCenterFile.getProducts().get(p) + amount);
        toLogisticsCenterFile.addProduct(p, amount);
        return fileCounter;
    }

    public void addNote(String s) {
        note = note + "\n" + s;
    }

    public void addLogisticCenterDestination(int fileCounter) throws SQLException {
        toLogisticsCenterFile = new File(fileCounter);
        dalDeliveryService.updateCounter("file counter",fileCounter);
    }

    public void addHandledSupplier(Supplier supplier, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.insertHandledSite(id,supplier.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        handledSuppliers.put(supplier, f);
    }

    public void addHandledBranch(Branch branch, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.insertHandledSite(id,branch.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        handledBranches.put(branch, f);
    }

    private DeliveryDTO createDeliveryDTO(){
        return new DeliveryDTO(getId(),getDate().toString(),getDepartureTime().toString(), getTruckWeight(),
                getSource().getAddress(),getDriverID(),getTruckNumber(),getShippingArea());
    }

    public LinkedHashMap<Branch, File> getHandledBranches() throws SQLException {
        handledBranches = dalDeliveryService.findAllHandledBranchesForDelivery(id);
        return handledBranches;
    }

    public LinkedHashMap<Supplier, File> getHandledSuppliers() throws SQLException {
        handledSuppliers = dalDeliveryService.findAllUnHandledSuppliersForDelivery(id);
        return handledSuppliers;
    }

    public LinkedHashMap<Supplier, File> getUnHandledSuppliers() throws SQLException {
        unHandledSuppliers = dalDeliveryService.findAllUnHandledSuppliersForDelivery(id);
        return unHandledSuppliers;
    }

    public LinkedHashMap<Branch, File> getUnHandledBranches() throws SQLException {
        unHandledBranches = dalDeliveryService.findAllUnHandledBranchesForDelivery(id);
        return unHandledBranches;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public Site getSource() {
        return this.source;
    }

    public int getTruckNumber() {
        return this.truckNumber;
    }

    public int getDriverID() {
        return this.driverID;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int getTruckWeight() {
        return this.truckWeight;
    }

    public int getShippingArea() {
        return shippingArea;
    }

    public int getId() {
        return this.id;
    }

    public void setTruckWeight(int truckWeight) throws SQLException {
        DeliveryDTO oldDTO = createDeliveryDTO();
        this.truckWeight = truckWeight;
        dalDeliveryService.updateDelivery(oldDTO,createDeliveryDTO());
    }

    public void setSource(Site source) throws SQLException {
        DeliveryDTO oldDTO = createDeliveryDTO();
        this.source = source;
        dalDeliveryService.updateDelivery(oldDTO,createDeliveryDTO());
    }

    public void setTruckNumber(int truckNumber) throws SQLException {
        DeliveryDTO oldDTO = createDeliveryDTO();
        this.truckNumber = truckNumber;
        dalDeliveryService.updateDelivery(oldDTO,createDeliveryDTO());
    }

    public void setDriver(Driver driver) throws SQLException {
        DeliveryDTO oldDTO = createDeliveryDTO();
        this.driverID = driver.getId();
        dalDeliveryService.updateDelivery(oldDTO,createDeliveryDTO());
    }

    public File getToLogisticsCenterFile() {
        return toLogisticsCenterFile;
    }

    public File getFromLogisticsCenterFile() {
        return fromLogisticsCenterFile;
    }
}