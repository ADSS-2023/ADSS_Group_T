package DataLayer.HR_T_DAL.DalService;

import DataLayer.HR_T_DAL.DAOs.ShiftDAO;

import java.sql.Connection;

public class DalShiftService {

    private Connection connection ;
    private ShiftDAO shiftDAO;
    private DalUserService dalUserService;
    public DalShiftService(Connection connection) {
        this.connection = connection;
        this.shiftDAO = new ShiftDAO(connection);
    }
}


