package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.*;
import DataLayer.HR_T_DAL.DAOs.DeliveryDAO;
import DataLayer.HR_T_DAL.DAOs.SiteDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DalDeliveryService {

    private DalLogisticCenterService dalLogisticCenterService;
    private DeliveryDAO deliveryDAO;
    private LinkedHashMap<String, Supplier> suppliers;

    private SiteDAO siteDAO;
    private DAO dao;

    public DalDeliveryService(Connection connection,DalLogisticCenterService dalLogisticCenterService) {
        this.suppliers = new LinkedHashMap<>();
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.deliveryDAO = new DeliveryDAO(connection);
        this.dao = new DAO(connection);
        this.siteDAO = new SiteDAO(connection);
    }

    public void insertDelivery(Delivery delivery) throws SQLException {
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

    public void insertSupplier(Supplier supplier) throws SQLException {
        SiteDTO dto = new SiteDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY(),supplier.getShippingArea(),"supplier");
        dao.insert(dto);
    }

    public void insertBranch(Branch branch) throws SQLException {
        SiteDTO dto = new SiteDTO(branch.getAddress(),branch.getTelNumber(),
                branch.getContactName(),branch.getLocation().getX(),branch.getLocation().getY(),branch.getShippingArea(),"branch");
        dao.insert(dto);
    }

    public void insertProduct(Product product) throws SQLException{
        ProductDTO dto = new ProductDTO(product.getName(),product.getCoolingLevel().toString());
        dao.insert(dto);
    }
    public void insertDateToDelivery(String shiftDate, int deliveryId) throws SQLException {
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate, deliveryId);
        dao.insert(dto);
    }

    public void insertDateToTruck(String shiftDate, int truckLicenseNumber) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate, truckLicenseNumber);
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

    public void deleteDateToDelivery(String shiftDate, int deliveryId) throws SQLException {
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate, deliveryId);
        dao.delete(dto);
    }

    public void deleteDateToTruck(String shiftDate, int truckId) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate, truckId);
        dao.delete(dto);
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
        suppliers.put(supplierAddress,new Supplier(dto,this));
        return suppliers.get(supplierAddress);
    }

    public Branch findBranch(String branchAddress) throws SQLException {
        SiteDTO dto = dao.find(branchAddress,SiteDTO.getPKNameStatic(),SiteDTO.getTableNameStatic(), SiteDTO.class);
        if(dto == null)
            return null;
        return new Branch(dto);
    }
    public Site findSite(String siteAddress) throws SQLException{
        SiteDTO dto = dao.find(siteAddress,SiteDTO.getPKNameStatic(),SiteDTO.getTableNameStatic(), SiteDTO.class);
        if(dto.getType().equals("Branch"))
            return new Branch(dto);
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
        ProductDTO dto = dao.find(productName,"productName","Product",ProductDTO.class);
        if(dto == null)
            return null;
        return new Product(dto);
    }

    public Delivery findDelivery(int deliveryId) throws SQLException {
        DeliveryDTO dto = dao.find(deliveryId,"id","Delivery",DeliveryDTO.class);
        if(dto == null)
            return null;
        return new Delivery(dto,this);
    }

    public DeliveryUnHandledSitesDTO findDeliveryUnHandledSites(LinkedHashMap<String,Object> pk) throws SQLException {
        return dao.find(pk,DeliveryUnHandledSitesDTO.getTableNameStatic(),DeliveryUnHandledSitesDTO.class);
    }

    public DeliveryHandledSitesDTO findDeliveryHandledSites(LinkedHashMap<String,Object> pk) throws SQLException {
        return dao.find(pk,"DeliveryHandledSites",DeliveryHandledSitesDTO.class);
    }

    public DateToTruckDTO findSpecificTruckInDate(LinkedHashMap<String,Object> pk) throws SQLException {
        return dao.find(pk,DateToTruckDTO.getTableNameStatic(),DateToTruckDTO.class);
    }
    public LinkedHashMap<String, Supplier> findAllSuppliers() throws SQLException {
        ArrayList<SiteDTO> suppliersDTOs = siteDAO.findAllSite("supplier");

        for (SiteDTO s : suppliersDTOs) {
            if (!this.suppliers.containsKey(s.getSiteAddress())) {
                Supplier supplier = new Supplier(s, this);
                suppliers.put(s.getSiteAddress(), supplier);
            }
        }
        return this.suppliers;
    }

    public LinkedHashMap<String, Product> findAllProducts() throws SQLException {
        ArrayList<ProductDTO> ProductDTOs =  dao.findAll(ProductDTO.getTableNameStatic(), ProductDTO.class);
        LinkedHashMap<String, Product> products = new LinkedHashMap<>();
        for(ProductDTO p : ProductDTOs){
            products.put(p.getProductName(),new Product(p));
        }
        return products;
    }

    public LinkedHashMap<String, Product> findAllProductsOfSupplier(String supplierAddress) throws SQLException {
        ArrayList<ProductDTO> ProductDTOs =  deliveryDAO.findAllProductsOfSupplier(supplierAddress, ProductDTO.class);
        LinkedHashMap<String, Product> products = new LinkedHashMap<>();
        for(ProductDTO p : ProductDTOs){
            products.put(p.getProductName(),new Product(p));
        }
        return products;
    }

    public LinkedHashMap<String, Branch> findAllBranch() throws SQLException {
        ArrayList<SiteDTO> branchesDTOs =  siteDAO.findAllSite("branch");
        LinkedHashMap<String, Branch> branches = new LinkedHashMap<>();
        for(SiteDTO b : branchesDTOs){
            branches.put(b.getSiteAddress(),new Branch(b));
        }
        return branches;
    }

    public ArrayList<DateToDeliveryDTO> findAllDateToDeliveries() throws SQLException {
        return dao.findAll(DateToDeliveryDTO.getTableNameStatic(),DateToDeliveryDTO.class);
    }

    public List<DateToDeliveryDTO> findAllDeliveriesByDate(String date) throws SQLException {
        return deliveryDAO.findAllDeliveriesByDate(date);
    }

    public ArrayList<DateToTruckDTO> findAllTrucksByDate(String date) throws SQLException {
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
        LinkedHashMap<Integer,Delivery> result = new LinkedHashMap<>();
        for (DeliveryDTO deliveryDTO: deliveryDTOS){
            result.put(deliveryDTO.getId(),new Delivery(deliveryDTO,this));
            result.get(deliveryDTO.getId()).setUnHandledSuppliers(findAllUnHandledSuppliersForDelivery(deliveryDTO.getId()));
            result.get(deliveryDTO.getId()).setUnHandledBranches(findAllUnHandledBranchesForDelivery(deliveryDTO.getId()));
            result.get(deliveryDTO.getId()).setHandledSuppliers(findAllHandledSuppliersForDelivery(deliveryDTO.getId()));
            result.get(deliveryDTO.getId()).setHandledBranches(findAllHandledBranchesForDelivery(deliveryDTO.getId()));
        }

     return result;
    }

    public void deleteAllData() throws SQLException {
        dao.deleteAllDataFromDatabase();
    }

    public void addSupplier(String supplierAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        Supplier supplier = new Supplier(supplierAddress,telNumber,contactName,x,y,this);
        suppliers.put(supplierAddress,supplier);
        insertSupplier(supplier);
    }


}
