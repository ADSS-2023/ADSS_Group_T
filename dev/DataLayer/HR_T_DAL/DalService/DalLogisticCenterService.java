package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.Truck;
import DataLayer.HR_T_DAL.DAOs.LogisticDAO;
import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.LogisticCenterStockDTO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DalLogisticCenterService {
    private TruckDAO truckDAO;
    private LogisticDAO logisticDAO;
    private Connection connection;
    private DAO dao;


    public DalLogisticCenterService(Connection connection) {
        this.connection = connection;
        this.dao = new DAO(connection);
    }
    public boolean insertTruck(Truck truck) throws SQLException {
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
}
