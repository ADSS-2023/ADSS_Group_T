package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.*;
import DataLayer.HR_T_DAL.DAOs.DeliveryDAO;
import DataLayer.HR_T_DAL.DAOs.SiteDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DalDeliveryService {

    private DalLogisticCenterService dalLogisticCenterService;
    private DeliveryDAO deliveryDAO;
    private LinkedHashMap<String, Supplier> suppliers;
    private LinkedHashMap<String, Branch> branches;
    private LinkedHashMap<String, Product> products;
    private final LinkedHashMap<LocalDate, ArrayList<Truck>> date2trucks;
    private final LinkedHashMap<LocalDate, ArrayList<Delivery>> date2deliveries;
    private LinkedHashMap<Integer, Delivery> deliveries;
    private SiteDAO siteDAO;
    private DAO dao;

    public DalDeliveryService(Connection connection,DalLogisticCenterService dalLogisticCenterService) {
        this.suppliers = new LinkedHashMap<>();
        this.branches = new LinkedHashMap<>();
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.deliveryDAO = new DeliveryDAO(connection);
        this.dao = new DAO(connection);
        this.siteDAO = new SiteDAO(connection);
        this.date2trucks = new LinkedHashMap<>();
        this.date2deliveries = new LinkedHashMap<>();
        this.products = new LinkedHashMap<>();
        this.deliveries = new LinkedHashMap<>();
    }

    private void insertDelivery(Delivery delivery) throws SQLException {
        Site site = delivery.getSource();
        String address = null;
        if (site != null)
            address = site.getAddress();
        DeliveryDTO dto = new DeliveryDTO(delivery.getId(),delivery.getDate().toString(),
                delivery.getDepartureTime().toString(),delivery.getTruckWeight(),
                address,delivery.getDriverID(),delivery.getTruckNumber(),
                delivery.getShippingArea());
       dao.insert(dto);
    }

    public void insertUnHandledSite(int deliveryId, String siteAddress, String productName, int fileId, int amount) throws SQLException {
        DeliveryUnHandledSitesDTO dto = new DeliveryUnHandledSitesDTO(deliveryId,siteAddress,productName,fileId,amount);
        dao.insert(dto);
    }

    public void insertHandledSite(int deliveryId, String siteAddress, String productName, int fileId, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,fileId,amount);
        dao.insert(dto);
    }

    private void insertSupplier(Supplier supplier) throws SQLException {
        SiteDTO dto = new SiteDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY(),supplier.getShippingArea(),"supplier");
        dao.insert(dto);
    }

    private void insertBranch(Branch branch) throws SQLException {
        SiteDTO dto = new SiteDTO(branch.getAddress(),branch.getTelNumber(),
                branch.getContactName(),branch.getLocation().getX(),branch.getLocation().getY(),branch.getShippingArea(),"branch");
        dao.insert(dto);
    }

    private void insertProduct(Product product) throws SQLException{
        ProductDTO dto = new ProductDTO(product.getName(),product.getCoolingLevel().toString());
        dao.insert(dto);
    }
    private void insertDateToDelivery(String shiftDate, int deliveryId) throws SQLException {
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate, deliveryId);
        dao.insert(dto);
    }

    private void insertDateToTruck(LocalDate shiftDate, int truckLicenseNumber) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate.toString(), truckLicenseNumber);
        dao.insert(dto);
    }

    public void insertSupplierToProducts(String supplierAddress, String productName) throws SQLException {
        SupplierToProductsDTO dto = new SupplierToProductsDTO(supplierAddress, productName);
        dao.insert(dto);
    }

    public void deleteUnHandledSite(int deliveryId, String siteAddress, String productName, int fileId, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,fileId,amount);
        dao.delete(dto);
    }

    public void deleteHandledBranch(int deliveryId, String siteAddress, String productName, int fileId,int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,fileId,amount);
        dao.delete(dto);
    }

    public void deleteDateToDelivery(LocalDate shiftDate, Delivery delivery) throws Exception {
        if(!date2deliveries.containsKey(shiftDate) || !date2deliveries.get(shiftDate).contains(delivery))
            throw new Exception("this truck is not assigned in this date, so cannot be deleted");
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate.toString(), delivery.getId());
        dao.delete(dto);
        date2deliveries.get(shiftDate).remove(delivery);
    }

    public void deleteDateToTruck(LocalDate shiftDate, Truck truck) throws Exception {
        if(!date2trucks.containsKey(shiftDate) || !date2trucks.get(shiftDate).contains(truck))
            throw new Exception("this truck is not assigned in this date, so cannot be deleted");
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate.toString(), truck.getLicenseNumber());
        dao.delete(dto);
        date2trucks.get(shiftDate).remove(truck);
    }

    public void updateCounter(String counter, int newCounter) throws SQLException {
        dao.update(new CounterDTO(counter,Integer.toString(newCounter - 1)),new CounterDTO(counter,Integer.toString(newCounter)));
    }

    public void updateDelivery(DeliveryDTO oldDTO,DeliveryDTO newDTO) throws SQLException {
        dao.update(oldDTO,newDTO);
    }

    public void updateUnHandledSite(int deliveryId, String siteAddress, String productName, int fileId, int newAmount) throws SQLException {
        LinkedHashMap<String,Object> pk = new LinkedHashMap<>();
        pk.put("deliveryId",deliveryId);
        pk.put("siteAddress",siteAddress);
        pk.put("productName",productName);
        DeliveryUnHandledSitesDTO dto = findDeliveryUnHandledSites(pk);
        int oldAmount = dto.getAmount();
        dao.update(new DeliveryUnHandledSitesDTO(deliveryId,siteAddress,productName, fileId,oldAmount),
            new DeliveryUnHandledSitesDTO(deliveryId,siteAddress,productName, fileId, newAmount));
    }

    public void updateHandledSite(int deliveryId, String siteAddress, String productName, int fileId, int newAmount) throws SQLException {
        LinkedHashMap<String,Object> pk = new LinkedHashMap<>();
        pk.put("deliveryId",deliveryId);
        pk.put("siteAddress",siteAddress);
        pk.put("productName",productName);
        DeliveryHandledSitesDTO dto = findDeliveryHandledSites(pk);
        int oldAmount = dto.getAmount();
        dao.update(new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName, fileId,oldAmount),
                new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName, fileId, newAmount));
    }

    public Supplier findSupplier(String supplierAddress) throws SQLException {
        if(suppliers.containsKey(supplierAddress))
            return suppliers.get(supplierAddress);
        SiteDTO dto = dao.find(supplierAddress,SiteDTO.getPKNameStatic(),SiteDTO.getTableNameStatic(),SiteDTO.class);
        if(dto == null)
            return null;
        Supplier supplier = new Supplier(dto,this);
        suppliers.put(supplierAddress,supplier);
        return supplier;
    }

    public Branch findBranch(String branchAddress) throws SQLException {
        if(this.branches.containsKey(branchAddress))
            return this.branches.get(branchAddress);
        SiteDTO dto = dao.find(branchAddress,SiteDTO.getPKNameStatic(),SiteDTO.getTableNameStatic(), SiteDTO.class);
        if(dto == null)
            return null;
        Branch branch = new Branch(dto);
        this.branches.put(branchAddress,branch);
        return branch;
    }
    public Site findSite(String siteAddress) throws SQLException{
        SiteDTO dto = dao.find(siteAddress,SiteDTO.getPKNameStatic(),SiteDTO.getTableNameStatic(), SiteDTO.class);
        if(dto.getType().equals("Branch")) {
            if(this.branches.containsKey(siteAddress))
                return this.branches.get(siteAddress);
            this.branches.put(siteAddress,new Branch(dto));
            return this.branches.get(siteAddress);
        }
        else if(dto.getType().equals("Supplier")){
            if(suppliers.containsKey(siteAddress))
                return suppliers.get(siteAddress);
            suppliers.put(siteAddress,new Supplier(dto,this));
            return suppliers.get(siteAddress);
        }
        else
            return new LogisticCenter(dto,dalLogisticCenterService);
    }

    public Product findProduct(String productName) throws SQLException {
        if(this.products.containsKey(productName))
            return this.products.get(productName);
        ProductDTO dto = dao.find(productName,"productName","Product",ProductDTO.class);
        if(dto == null)
            return null;
        Product product = new Product(dto);
        this.products.put(productName,product);
        return product;
    }

    public Delivery findDelivery(int deliveryId) throws SQLException {
        if(deliveries.containsKey(deliveryId))
            return deliveries.get(deliveryId);
        DeliveryDTO dto = dao.find(deliveryId,"id","Delivery",DeliveryDTO.class);
        if(dto == null)
            return null;
        Delivery delivery = new Delivery(dto,this);
        deliveries.put(deliveryId,delivery);
        return delivery;
    }

    public DeliveryUnHandledSitesDTO findDeliveryUnHandledSites(LinkedHashMap<String,Object> pk) throws SQLException {
        return dao.find(pk,DeliveryUnHandledSitesDTO.getTableNameStatic(),DeliveryUnHandledSitesDTO.class);
    }

    public DeliveryHandledSitesDTO findDeliveryHandledSites(LinkedHashMap<String,Object> pk) throws SQLException {
        return dao.find(pk,"DeliveryHandledSites",DeliveryHandledSitesDTO.class);
    }

    public boolean findSpecificTruckInDate(LinkedHashMap<String,Object> pk) throws SQLException {
        Truck truck = dalLogisticCenterService.findTruck((Integer)pk.get("truckId"));
        if(truck != null && date2trucks.containsKey((LocalDate)pk.get("shiftDate")) && date2trucks.get((LocalDate)pk.get("shiftDate")).contains(truck))
            return true;
        DateToTruckDTO dto = dao.find(pk,DateToTruckDTO.getTableNameStatic(),DateToTruckDTO.class);
        if(dto == null)
            return false;
        date2trucks.get((LocalDate)pk.get("shiftDate")).add(truck);
        return true;
    }
    public LinkedHashMap<String, Supplier> findAllSuppliers() throws SQLException {
        ArrayList<SiteDTO> suppliersDTOs = siteDAO.findAllSite("supplier");
        for (SiteDTO s : suppliersDTOs) {
            if (!this.suppliers.containsKey(s.getSiteAddress()))
                this.suppliers.put(s.getSiteAddress(), new Supplier(s, this));
        }
        return this.suppliers;
    }

    public LinkedHashMap<String, Product> findAllProducts() throws SQLException {
        ArrayList<ProductDTO> ProductDTOs =  dao.findAll(ProductDTO.getTableNameStatic(), ProductDTO.class);
        for(ProductDTO p : ProductDTOs){
            if(!this.products.containsKey(p.getProductName()))
                this.products.put(p.getProductName(),new Product(p));
        }
        return this.products;
    }

    public LinkedHashMap<String, Product> findAllProductsOfSupplier(String supplierAddress) throws SQLException {
        ArrayList<ProductDTO> ProductDTOs =  deliveryDAO.findAllProductsOfSupplier(supplierAddress, ProductDTO.class);
        LinkedHashMap<String, Product> SupplierProducts = new LinkedHashMap<>();
        for(ProductDTO p : ProductDTOs){
            SupplierProducts.put(p.getProductName(),this.products.get(p.getProductName()));
        }
        return SupplierProducts;
    }

    public LinkedHashMap<String, Branch> findAllBranches() throws SQLException {
        ArrayList<SiteDTO> branchesDTOs =  siteDAO.findAllSite("branch");
        for(SiteDTO b : branchesDTOs){
            if(!this.branches.containsKey(b.getSiteAddress()))
                branches.put(b.getSiteAddress(),new Branch(b));
        }
        return branches;
    }

    public ArrayList<DateToDeliveryDTO> findAllDateToDeliveries() throws SQLException {
        return dao.findAll(DateToDeliveryDTO.getTableNameStatic(),DateToDeliveryDTO.class);
    }

    private List<DateToDeliveryDTO> findAllDeliveriesByDate(String date) throws SQLException {
        return deliveryDAO.findAllDeliveriesByDate(date);
    }

    private ArrayList<DateToTruckDTO> findAllTrucksByDate(String date) throws SQLException {
        return deliveryDAO.findAllTrucksByDate(date);
    }

    public LinkedHashMap<Supplier, File> findAllUnHandledSuppliersForDelivery(int deliveryId) throws SQLException {
        ArrayList<DeliveryUnHandledSitesDTO> DTOs = deliveryDAO.findAllCategorySitesForDelivery("DeliveryUnHandledSites",
                DeliveryUnHandledSitesDTO.class,deliveryId,"supplier");
        LinkedHashMap<Supplier, File> suppliers = new LinkedHashMap<>();
        for(DeliveryUnHandledSitesDTO dto : DTOs){
            Supplier s = findSupplier(dto.getSiteAddress());
            if(!suppliers.containsKey(s))
                suppliers.put(s,new File(dto.getFileId()));
            Product p = findProduct(dto.getProductName());
            suppliers.get(s).addProduct(p,dto.getAmount());
        }
        return suppliers;
    }

    public LinkedHashMap<Supplier, File> findAllHandledSuppliersForDelivery(int deliveryId) throws SQLException {
        ArrayList<DeliveryHandledSitesDTO> DTOs = deliveryDAO.findAllCategorySitesForDelivery("DeliveryHandledSites",
                DeliveryHandledSitesDTO.class,deliveryId,"supplier");
        LinkedHashMap<Supplier, File> suppliers = new LinkedHashMap<>();
        for(DeliveryHandledSitesDTO dto : DTOs){
            Supplier s = findSupplier(dto.getSiteAddress());
            if(!suppliers.containsKey(s))
                suppliers.put(s,new File(dto.getFileId()));
            Product p = findProduct(dto.getProductName());
            suppliers.get(s).addProduct(p,dto.getAmount());
        }
        return suppliers;
    }

    public LinkedHashMap<Branch, File> findAllUnHandledBranchesForDelivery(int deliveryId) throws SQLException {
        ArrayList<DeliveryUnHandledSitesDTO> DTOs = deliveryDAO.findAllCategorySitesForDelivery("DeliveryUnHandledSites",
                DeliveryUnHandledSitesDTO.class,deliveryId,"branch");
        LinkedHashMap<Branch, File> branches = new LinkedHashMap<>();
        for(DeliveryUnHandledSitesDTO dto : DTOs){
            Branch b = findBranch(dto.getSiteAddress());
            if(!branches.containsKey(b))
                branches.put(b,new File(dto.getFileId()));
            Product p = findProduct(dto.getProductName());
            branches.get(b).addProduct(p,dto.getAmount());
        }
        return branches;
    }

    public LinkedHashMap<Branch, File> findAllHandledBranchesForDelivery(int deliveryId) throws SQLException {
        ArrayList<DeliveryHandledSitesDTO> DTOs = deliveryDAO.findAllCategorySitesForDelivery("DeliveryHandledSites",
                DeliveryHandledSitesDTO.class,deliveryId,"branch");
        LinkedHashMap<Branch, File> branches = new LinkedHashMap<>();
        for(DeliveryHandledSitesDTO dto : DTOs){
            Branch b = findBranch(dto.getSiteAddress());
            if(!branches.containsKey(b))
                branches.put(b,new File(dto.getFileId()));
            Product p = findProduct(dto.getProductName());
            branches.get(b).addProduct(p,dto.getAmount());
        }
        return branches;
    }


    public CounterDTO findTime() throws SQLException {
        return dao.find("date counter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }

    public CounterDTO findFilesCounter() throws SQLException {
        return dao.find("file counter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }
    public CounterDTO findDeliveryCounter() throws SQLException {
        return dao.find("delivery counter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }

    public LinkedHashMap<Integer, Delivery> findAllDeliveries() throws SQLException {
        ArrayList<DeliveryDTO> deliveryDTOS = dao.findAll(DeliveryDTO.getTableNameStatic(),DeliveryDTO.class);
        for (DeliveryDTO deliveryDTO: deliveryDTOS){
            if(!this.deliveries.containsKey(deliveryDTO.getId())) {
                this.deliveries.put(deliveryDTO.getId(), new Delivery(deliveryDTO, this));
                this.deliveries.get(deliveryDTO.getId()).setUnHandledSuppliers(findAllUnHandledSuppliersForDelivery(deliveryDTO.getId()));
                this.deliveries.get(deliveryDTO.getId()).setUnHandledBranches(findAllUnHandledBranchesForDelivery(deliveryDTO.getId()));
                this.deliveries.get(deliveryDTO.getId()).setHandledSuppliers(findAllHandledSuppliersForDelivery(deliveryDTO.getId()));
                this.deliveries.get(deliveryDTO.getId()).setHandledBranches(findAllHandledBranchesForDelivery(deliveryDTO.getId()));
            }
        }
     return this.deliveries;
    }

    public void deleteAllData() throws SQLException {
        dao.deleteAllDataFromDatabase();
    }

    public void addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        Supplier supplier = new Supplier(supplierAddress,telNumber,contactName,x,y,this);
        suppliers.put(supplierAddress,supplier);
        insertSupplier(supplier);
    }

    public void addBranch(String branchAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        Branch branch = new Branch(branchAddress,telNumber,contactName,x,y);
        branches.put(branchAddress, branch);
        insertBranch(branch);
    }

    public void addProduct(String productName, int productCoolingLevel) throws SQLException {
        Product product = new Product(productName, productCoolingLevel);
        products.put(productName, product);
        insertProduct(product);
    }

    public void addDelivery(Delivery delivery) throws SQLException {
        deliveries.put(delivery.getId(), delivery);
        insertDelivery(delivery);
    }

    public void addTruckToDate(LocalDate requiredDate, Truck truck) throws SQLException {
        if (!date2trucks.containsKey(requiredDate))
            date2trucks.put(requiredDate, new ArrayList<>());
        date2trucks.get(requiredDate).add(truck);
        insertDateToTruck(requiredDate,truck.getLicenseNumber());
    }

    public void addDeliveryToDate(LocalDate requiredDate, Delivery delivery) throws SQLException {
        if (!date2deliveries.containsKey(requiredDate))
            date2deliveries.put(requiredDate, new ArrayList<>());
        date2deliveries.get(requiredDate).add(delivery);
        insertDateToDelivery(requiredDate.toString(),delivery.getId());
    }

    public ArrayList<Truck> getAllTrucksByDate(LocalDate date) throws SQLException {
        List<DateToTruckDTO> dateTrucksDTOs = findAllTrucksByDate(date.toString());
        ArrayList<Truck> dateTrucks = new ArrayList<>();
        for(DateToTruckDTO dto : dateTrucksDTOs){
            Truck truck = dalLogisticCenterService.findTruck(dto.getTruckId());
            if(!this.date2trucks.get(date).contains(truck))
                this.date2trucks.get(date).add(truck);
            dateTrucks.add(truck);
        }
        return dateTrucks;
    }

    public ArrayList<Delivery> getAllDeliveriesByDate(LocalDate date) throws SQLException {
        List<DateToDeliveryDTO> dateDeliveriesDTOs = findAllDeliveriesByDate(date.toString());
        ArrayList<Delivery> dateDeliveries = new ArrayList<>();
        for(DateToDeliveryDTO dto : dateDeliveriesDTOs){
            Delivery delivery = findDelivery(dto.getDeliveryId());
            if(!this.date2deliveries.get(date).contains(delivery))
                this.date2deliveries.get(date).add(delivery);
            dateDeliveries.add(delivery);
        }
        return dateDeliveries;
    }


}
