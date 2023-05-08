package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.Truck;
import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class DalLogisticCenterService {
    private TruckDAO truckDAO;
    private Connection connection;
    public DalLogisticCenterService(Connection connection) {
        this.connection = connection;
        this.truckDAO = new TruckDAO(connection);
    }
    public boolean addNewTruck(Truck truck) throws SQLException {
        TruckDTO truckDTO = new TruckDTO(truck);
        truckDAO.insert(truckDTO);
        return true;
    }

    public boolean removeTruck(Truck truck) throws SQLException {
        TruckDTO truckDTO = new TruckDTO(truck);
        truckDAO.delete(truckDTO);
        return true;
    }
}
