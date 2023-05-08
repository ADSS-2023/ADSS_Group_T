package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Shift;
import DataLayer.HR_T_DAL.DAOs.ShiftDAO;
import DataLayer.HR_T_DAL.DTOs.ShiftDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DalShiftService {

    private Connection connection ;
    private ShiftDAO shiftDAO;
    private DalUserService dalUserService;
    public DalShiftService(Connection connection) {
        this.connection = connection;
        this.shiftDAO = new ShiftDAO(connection);
    }

    public List<ShiftDTO> getshifsByDate(LocalDate localDate) {
        // Retrieve all shifts from the database with the same local date in String format
        return null;
    }

    public List<ShiftDTO> getshifsByDateAndShiftType(LocalDate localDate, boolean shiftType) {
        String LocalDatestring = localDate.toString();
        String shiftTypestring ;
        if (shiftType) shiftTypestring = "morning"; else shiftTypestring = "evening";

        //shiftDAO.find()
        return null;
    }

    public void addNewShift (Shift shift) throws SQLException {
        ShiftDTO shiftDTO = new ShiftDTO(shift);
        shiftDAO.insert(shiftDTO);
    }
}


