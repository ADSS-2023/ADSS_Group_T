package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.Product;
import BusinessLayer.Transport.Supplier;
import BusinessLayer.Transport.Truck;
import DataLayer.HR_T_DAL.DAOs.LogisticDAO;
import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.LogisticCenterStockDTO;
import DataLayer.HR_T_DAL.DTOs.ProductDTO;
import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import DataLayer.Util.DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DalLogisticCenterService {
    private TruckDAO truckDAO;
    private LogisticDAO logisticDAO;
    private Connection connection;
    private DAO dao;
    private LinkedHashMap<Integer, Truck> trucks;

    public DalLogisticCenterService(Connection connection) {
        this.trucks = new LinkedHashMap<>();
        this.connection = connection;
        this.dao = new DAO(connection);
    }

    public Truck findTruck(int licenseNumber) throws SQLException {
        if(this.trucks.containsKey(licenseNumber))
            return this.trucks.get(licenseNumber);
        TruckDTO truckDTO = dao.find(licenseNumber,TruckDTO.getPKStatic(),TruckDTO.getTableNameStatic(),TruckDTO.class);
        if(truckDTO == null)
            return null;
        Truck truck = new Truck(truckDTO);
        this.trucks.put(licenseNumber,truck);
        return truck;
    }

    private boolean insertTruck(Truck truck) throws SQLException {
        TruckDTO truckDTO = new TruckDTO(truck);
        DAO dao = new DAO(connection);
        dao.insert(truckDTO);
        return true;
    }
    public boolean insertProductToStock(String productName,int amount ) throws SQLException {
        LogisticCenterStockDTO logisticCenterStockDTO = new LogisticCenterStockDTO(productName,amount);
        dao.insert(logisticCenterStockDTO);
        return true;
    }

    public void updateProductToStock(String productName, int oldAmount, int newAmount) throws SQLException {
        LogisticCenterStockDTO oldLogisticCenterStockDTO = new LogisticCenterStockDTO(productName,oldAmount);
        LogisticCenterStockDTO newLogisticCenterStockDTO = new LogisticCenterStockDTO(productName,newAmount);
        dao.update(oldLogisticCenterStockDTO,newLogisticCenterStockDTO);
    }

    public void deleteProductFromStock(String productName, int Amount) throws SQLException {
        LogisticCenterStockDTO oldLogisticCenterStockDTO = new LogisticCenterStockDTO(productName,Amount);
        dao.delete(oldLogisticCenterStockDTO);
    }

    public LinkedHashMap<Integer, Truck> findAllTrucks() throws SQLException {
        ArrayList<TruckDTO> truckDTOS =  dao.findAll(TruckDTO.getTableNameStatic(), TruckDTO.class);
        for(TruckDTO truckDTO : truckDTOS){
            if(!this.trucks.containsKey(truckDTO.getLicenseNumber()))
                this.trucks.put(truckDTO.getLicenseNumber(),new Truck(truckDTO));
        }
        return this.trucks;
    }

    public LinkedHashMap<Product, Integer> findAllProductsInStock() throws SQLException {
        ArrayList<LogisticCenterStockDTO> stringInStock = dao.findAll(LogisticCenterStockDTO.getTableNameStatic(), LogisticCenterStockDTO.class);
        LinkedHashMap<Product,Integer> productsInStock = new LinkedHashMap<>();
        for (LogisticCenterStockDTO logisticCenterStockDTO: stringInStock) {
            ProductDTO productDTO = dao.find(logisticCenterStockDTO.getProductName(),ProductDTO.getPKStatic()
                                            ,ProductDTO.getTableNameStatic(), ProductDTO.class);
            productsInStock.put(new Product(productDTO), logisticCenterStockDTO.getAmount());
        }
        return productsInStock;
    }

    public SiteDTO findLogisticCenter() throws SQLException {
        return dao.find("logistic center address","siteAddress","Site", SiteDTO.class);
    }

    public void addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) throws SQLException {
        Truck truck = new Truck(licenseNumber, model, weight, maxWeight, coolingLevel);
        trucks.put(licenseNumber,truck);
        insertTruck(truck);
    }
}
