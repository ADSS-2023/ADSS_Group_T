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
    /** the delivery id */
    private final int id;
    /**the date of the delivery */
    private final LocalDate date;
    /** the departure time of the delivery */
    private final LocalTime departureTime;
    /** map with keys represent the suppliers that there items does not collected yet and values of files with the products and amounts*/
    private LinkedHashMap<Supplier, File> unHandledSuppliers;
    /** map with keys represent the suppliers that there items already collected and values of files with the products and amounts*/
    private LinkedHashMap<Supplier, File> handledSuppliers;
    /** map with keys represent the branches that does not receive there order yet and values of files with the products and amounts*/
    private LinkedHashMap<Branch, File> unHandledBranches;
    /** map with keys represent the branches that received there order and values of files with the products and amounts*/
    private LinkedHashMap<Branch, File> handledBranches;
    /** file with products and amount for delivery that deliver to the logistic center */
    private File toLogisticsCenterFile;
    /** file with products and amount for delivery that deliver from the logistic center */
    private File fromLogisticsCenterFile;
    /** the shipping area of the delivery*/
    private final int shippingArea;
    /** the current weight of the delivery truck */
    private int truckWeight;
    /** the first site that the delivery collect items from */
    private Site source;
    /** the id of the delivery driver */
    private int driverID;
    /** the license number of the delivery truck */
    private int truckNumber;
    /** note with the changes in the delivery*/
    private String note;
    /** responsible for the communication with the DAL layer */
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

    /**
     * handle all the actions needed after order collected from supplier
     * @param fileCounter the file counter
     * @param copyOfSupplierFileProducts the supplier products and amounts that has been collected
     * @return the new file counter
     * @throws SQLException query error
     */
    public int supplierHandled(int fileCounter, HashMap<Product, Integer> copyOfSupplierFileProducts) throws SQLException {
        ArrayList<Branch> branchesTmp = new ArrayList<>(getUnHandledBranches().keySet());
        for (Branch b : branchesTmp) {
            if (!copyOfSupplierFileProducts.isEmpty())
                fileCounter = checkBranch(b, fileCounter, copyOfSupplierFileProducts);
        }
        return fileCounter;
    }

    /**
     * check if the given branch ordered items that collected from a supplier
     * @param b the Branch to check
     * @param fileCounter the file counter
     * @param copyOfSupplierFileProducts the supplier products and amounts that has been collected
     * @return the new file counter
     * @throws SQLException query error
     */
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

    /**
     *
     * @param b the branch that should receive the order
     * @param product the product that the branch should receive
     * @param amount the amount of the product that the branch should receive
     * @param copyOfSupplierFileProducts the supplier products and amounts that has been collected
     * @param fileCounter the file counter
     * @return the new file counter
     * @throws SQLException query error
     */
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
     * add branch to the handled branches map
     * @param branch the branch to add
     * @param fileID the file of the handled products and amounts
     * @throws SQLException - query error
     */
    public void addUnHandledBranch(Branch branch, int fileID) throws SQLException {
        File f = new File(fileID);
        unHandledBranches.put(branch, f);
        dalDeliveryService.updateCounter("file counter",fileID+1);
    }

    /**
     * add product in required amount to the unHandle supplier file for this delivery
     * @param supplier the supplier
     * @param p the product to add
     * @param amount the amount to add
     * @param fileCounter the file counter
     * @return the new file counter
     * @throws SQLException query error
     */
    public int addProductToUnHandledSupplier(Supplier supplier, Product p, int amount, int fileCounter) throws SQLException {
        if (!unHandledSuppliers.containsKey(supplier)) {
            unHandledSuppliers.put(supplier, new File(fileCounter++));
            dalDeliveryService.updateCounter("file counter",fileCounter);
            dalDeliveryService.insertUnHandledSite(id,supplier.getAddress(),p.getName(),fileCounter - 1,amount);

        }
        else if(!unHandledSuppliers.get(supplier).getProducts().containsKey(p)){
            dalDeliveryService.insertUnHandledSite(id,supplier.getAddress(),p.getName(),fileCounter - 1,amount);
        }
        else
            dalDeliveryService.updateUnHandledSite(id,supplier.getAddress(),p.getName(), unHandledSuppliers.get(supplier).getId() ,amount);
        unHandledSuppliers.get(supplier).addProduct(p, amount);
        if (this.source.address.equals("logistic center address"))
            setSource(supplier);
        return fileCounter;
    }

    /**
     * add product in required amount to the unHandle branch file for this delivery
     * @param branch the branch
     * @param p the product to add
     * @param amount the amount to add
     * @throws SQLException query error
     */
    public void addProductToUnHandledBranch(Branch branch, Product p, int amount) throws SQLException {
        File file = unHandledBranches.get(branch);
        if (!file.getProducts().containsKey(p))
            dalDeliveryService.insertUnHandledSite(id,branch.getAddress(),p.getName(),file.getId(),amount);
        else
            dalDeliveryService.updateUnHandledSite(id,branch.getAddress(),p.getName(), file.getId() ,amount+file.getProducts().get(p));
        file.addProduct(p, amount);
    }

    /**
     * add product in required amount to the Handle branch file for this delivery
     * @param branch the branch
     * @param p the product to add
     * @param amount the amount to add
     * @param fileCounter the file counter
     * @return the new file counter
     * @throws SQLException query error
     */
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

    /**
     * remove product in required amount from the unHandle branch file for this delivery
     * @param branch the branch
     * @param product the product to remove
     * @param amount the amount to remove
     * @throws SQLException query error
     */
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

    /**
     * remove all supplier products from the unhandled suppliers after
     * @param supplier the supplier to remove
     * @param f the file with the products and amount to remove
     * @throws SQLException query error
     */
    public void removeUnHandledSupplier(Supplier supplier, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.deleteUnHandledSite(id,supplier.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        getUnHandledSuppliers().remove(supplier);
    }

    /**
     * add product to the file represent the products needed to be collected from the logistic center in the delivery
     * @param logisticCenterAddress the address of the logistic center
     * @param p the product to add
     * @param amount the amount to add
     * @param fileCounter the file counter
     * @return the new file counter
     * @throws SQLException query error
     */
    public int addProductToLogisticCenterFromFile(String logisticCenterAddress,Product p, int amount, int fileCounter) throws SQLException {
        if (fromLogisticsCenterFile == null){
            fromLogisticsCenterFile = new File(fileCounter++);
            dalDeliveryService.updateCounter("file counter",fileCounter);
        }
        dalDeliveryService.insertUnHandledSite(id,logisticCenterAddress,p.getName(),fromLogisticsCenterFile.getId(),amount);
        fromLogisticsCenterFile.addProduct(p, amount);
        return fileCounter;
    }

    /**
     * add product to the file represent the products needed to shipped from the logistic center in the delivery
     * @param logisticCenterAddress the address of the logistic center
     * @param p the product to add
     * @param amount the amount to add
     * @param fileCounter the file counter
     * @return the new file counter
     * @throws SQLException query error
     */
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

//    public void addNote(String s) {
//        note = note + "\n" + s;
//    }

    /**
     * create a new file for delivery to the logistic center
     * @param fileCounter the current file counter
     * @throws SQLException query error
     */
    public void addLogisticCenterDestination(int fileCounter) throws SQLException {
        toLogisticsCenterFile = new File(fileCounter);
        dalDeliveryService.updateCounter("file counter",fileCounter);
    }

    /**
     * add supplier file to the map of the handled suppliers after collecting his items
     * @param supplier the supplier that handled
     * @param f the supplier file
     * @throws SQLException query error
     */
    public void addHandledSupplier(Supplier supplier, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.insertHandledSite(id,supplier.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        handledSuppliers.put(supplier, f);
    }

    /**
     * add branch file to the map of the handled branches after collecting the items he ordered
     * @param branch the branch
     * @param f the branch file
     * @throws SQLException query error
     */
    public void addHandledBranch(Branch branch, File f) throws SQLException {
        LinkedHashMap<Product,Integer> products = f.getProducts();
        for(Product p : products.keySet()){
            dalDeliveryService.insertHandledSite(id,branch.getAddress(),p.getName(),f.getId(),products.get(p));
        }
        handledBranches.put(branch, f);
    }

    /**
     * create delivery DTO with the delivery current details
     * @return the suitable delivery DTO
     */
    private DeliveryDTO createDeliveryDTO(){
        String address = null;
        if(this.source != null)
            address = this.getSource().getAddress();

        return new DeliveryDTO(getId(),getDate().toString(),getDepartureTime().toString(), getTruckWeight(),
                address,getDriverID(),getTruckNumber(),getShippingArea());
    }

    /**
     * get all the branches with their files that their orders already collected from the suppliers
     * @return all the branches that their orders already collected from the suppliers
     * @throws SQLException query error
     */
    public LinkedHashMap<Branch, File> getHandledBranches() throws SQLException {
        handledBranches = dalDeliveryService.findAllHandledBranchesForDelivery(id);
        return handledBranches;
    }

    /**
     * get all the suppliers with their files that already collected
     * @return all the suppliers with their files that already collected
     * @throws SQLException query error
     */
    public LinkedHashMap<Supplier, File> getHandledSuppliers() throws SQLException {
        handledSuppliers = dalDeliveryService.findAllHandledSuppliersForDelivery(id);
        return handledSuppliers;
    }

    /**
     * get all the suppliers with their files that does not collected yet
     * @return all the suppliers with their files that does not collected yet
     * @throws SQLException query error
     */
    public LinkedHashMap<Supplier, File> getUnHandledSuppliers() throws SQLException {
        unHandledSuppliers = dalDeliveryService.findAllUnHandledSuppliersForDelivery(id);
        return unHandledSuppliers;
    }

    /**
     * get all the branches with their files that their orders does not collected yet from the suppliers
     * @return all the branches that their orders already collected from the suppliers
     * @throws SQLException query error
     */
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

    public void setUnHandledSuppliers(LinkedHashMap<Supplier, File> allUnHandledSuppliersForDelivery) {
        this.unHandledSuppliers = allUnHandledSuppliersForDelivery;
    }

    public void setUnHandledBranches(LinkedHashMap<Branch, File> allUnHandledBranchesForDelivery) {
        this.unHandledBranches = allUnHandledBranchesForDelivery;
    }

    public void setHandledSuppliers(LinkedHashMap<Supplier, File> allHandledSuppliersForDelivery) {
        this.handledSuppliers = allHandledSuppliersForDelivery;
    }

    public void setHandledBranches(LinkedHashMap<Branch, File> allHandledBranchesForDelivery) {
        this.handledBranches = allHandledBranchesForDelivery;
    }
}