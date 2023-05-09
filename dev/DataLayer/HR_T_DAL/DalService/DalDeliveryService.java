package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.*;
import DataLayer.HR_T_DAL.DAOs.DeliveryDAO;
import DataLayer.HR_T_DAL.DAOs.SiteDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DalDeliveryService {

    private Connection connection;

    private DalLogisticCenterService dalLogisticCenterService;
    private DeliveryDAO deliveryDAO;

    private SiteDAO siteDAO;
    private DAO dao;

    public DalDeliveryService(Connection connection,DalLogisticCenterService dalLogisticCenterService) {
        this.connection = connection;
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.deliveryDAO = new DeliveryDAO(connection);
        this.dao = new DAO(connection);
        this.siteDAO = new SiteDAO(connection);
    }

    public void insertDelivery(Delivery delivery) throws SQLException {
        DeliveryDTO dto = new DeliveryDTO(delivery.getId(),delivery.getDate().toString(),
                delivery.getDepartureTime().toString(),delivery.getTruckWeight(),
                delivery.getSource().getAddress(),delivery.getDriverID(),delivery.getTruckNumber(),
                delivery.getShippingArea());
       dao.insert(dto);
    }

    public void insertUnHandledSite(int deliveryId, String siteAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertHandledSite(int deliveryId, String siteAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertSupplier(Supplier supplier) throws SQLException {
        SiteDTO dto = new SiteDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY(),supplier.getShippingArea(),"Supplier");
        dao.insert(dto);
    }

    public void insertBranch(Branch branch) throws SQLException {
        SiteDTO dto = new SiteDTO(branch.getAddress(),branch.getTelNumber(),
                branch.getContactName(),branch.getLocation().getX(),branch.getLocation().getY(),branch.getShippingArea(),"Branch");
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

    public void insertDateToTruck(String shiftDate, int truckId) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate, truckId);
        dao.insert(dto);
    }

    public void insertSupplierToProducts(String supplierAddress, String productName) throws SQLException {
        SupplierToProductsDTO dto = new SupplierToProductsDTO(supplierAddress, productName);
        dao.insert(dto);
    }

    public void deleteUnHandledBranch(int deliveryId, String siteAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,amount);
        dao.delete(dto);
    }

    public void deleteHandledBranch(int deliveryId, String siteAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSitesDTO dto = new DeliveryHandledSitesDTO(deliveryId,siteAddress,productName,amount);
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

    public Supplier findSupplier(String supplierAddress) throws SQLException {
        SiteDTO dto = dao.find(supplierAddress,"SiteAddress","Site",SiteDTO.class);
        if(dto == null)
            throw new RuntimeException("there is no supplier with address: " + supplierAddress);
        return new Supplier(dto,this);
    }

    public Branch findBranch(String branchAddress) throws SQLException {
        SiteDTO dto = dao.find(branchAddress,"siteAddress","Site", SiteDTO.class);
        if(dto == null)
            throw new RuntimeException("there is no branch with address: " + branchAddress);
        return new Branch(dto);
    }
    public Site findSite(String siteAddress) throws SQLException{
        SiteDTO dto = dao.find(siteAddress,"siteAddress","Site", SiteDTO.class);
        if(dto.getType().equals("Branch"))
            return new Branch(dto);
        else if(dto.getType().equals("Supplier"))
            return new Supplier(dto,this);
        return new LogisticCenter(dto,dalLogisticCenterService);
    }

    public Product findProduct(String productName) throws SQLException {
        ProductDTO dto = dao.find(productName,"productName","Product",ProductDTO.class);
        if(dto == null)
            throw new RuntimeException("there is no product with name: " + productName);
        return new Product(dto);
    }

    public Delivery findDelivery(int deliveryId) throws SQLException {
        DeliveryDTO dto = dao.find(deliveryId,"id","Delivery",DeliveryDTO.class);
        if(dto == null)
            throw new RuntimeException("there is no delivery with id: " + deliveryId);
        return new Delivery(dto,this);
    }
    public LinkedHashMap<String, Supplier> findAllSupplier() throws SQLException {
        ArrayList<SiteDTO> suppliersDTOs =  siteDAO.findAllSite("Supplier");
        LinkedHashMap<String, Supplier> suppliers = new LinkedHashMap<>();
        for(SiteDTO s : suppliersDTOs){
            suppliers.put(s.getSiteAddress(),new Supplier(s,this));
        }
        return suppliers;
    }

    public LinkedHashMap<String, Product> findAllProducts() throws SQLException {
        ArrayList<ProductDTO> ProductDTOs =  dao.findAll("Product", ProductDTO.class);
        LinkedHashMap<String, Product> products = new LinkedHashMap<>();
        for(ProductDTO p : ProductDTOs){
            products.put(p.getProductName(),new Product(p));
        }
        return products;
    }

    public LinkedHashMap<String, Branch> findAllBranch() throws SQLException {
        ArrayList<SiteDTO> branchesDTOs =  siteDAO.findAllSite("Branch");
        LinkedHashMap<String, Branch> branches = new LinkedHashMap<>();
        for(SiteDTO b : branchesDTOs){
            branches.put(b.getSiteAddress(),new Branch(b));
        }
        return branches;
    }




    public ArrayList<DateToDeliveryDTO> findAllDateToDeliveries() throws SQLException {
        return dao.findAll(DateToDeliveryDTO.getTableNameStatic(),DateToDeliveryDTO.class);
    }

    public CounterDTO findTime() throws SQLException {
        return dao.find("dateCounter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }

    public CounterDTO findFilesCounter() throws SQLException {
        return dao.find("fileCounter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }
    public CounterDTO findDeliveryCounter() throws SQLException {
        return dao.find("dekiveryCounter",CounterDTO.getPKStatic(),CounterDTO.getTableNameStatic(), CounterDTO.class);
    }
}
