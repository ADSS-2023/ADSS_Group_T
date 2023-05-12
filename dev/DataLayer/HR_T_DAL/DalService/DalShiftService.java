package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.User.PositionType;
import DataLayer.HR_T_DAL.DAOs.ShiftDAO;
import DataLayer.HR_T_DAL.DTOs.DriverRequirementDTO;
import DataLayer.HR_T_DAL.DTOs.ShiftDTO;
import DataLayer.HR_T_DAL.DTOs.ShiftRequirementsDTO;
import UtilSuper.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DalShiftService {

    private Connection connection ;
    private ShiftDAO shiftDAO;
    private DalUserService dalUserService;
    public DalShiftService(Connection connection) {
        this.connection = connection;
        this.shiftDAO = new ShiftDAO(connection);
    }

    public List<ShiftDTO> getshifsByDate(LocalDate localDate) throws SQLException {
        return shiftDAO.getShiftsByDate(localDate);
    }


    // TODO - israel
    public void addRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(date,shiftType,positionType,amount);
        shiftDAO.insert(shiftRequirementsDTO);

    }

    // TODO - israel
    public void updateRequierement(String branch, String date, String shiftType, String positionType, int amount) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(date,shiftType,positionType,amount);
        shiftDAO.update(shiftRequirementsDTO , shiftRequirementsDTO);
    }

    // TODO - israel
    public void deleteRequierement(String branch, String date, String shiftType, String positionType) throws SQLException {
        ShiftRequirementsDTO shiftRequirementsDTO = new ShiftRequirementsDTO(date,shiftType,positionType,0);
        shiftDAO.delete(shiftRequirementsDTO);
    }

    // TODO - israel
    public LinkedHashMap<String, Integer> findRequiermentsBtDateAndShiftType(String branch, LocalDate localDate, boolean shiftTpe) throws SQLException {
        return null;
    }

    // TODO - israel
    public LinkedHashMap<String, Integer> findAllSubmissionByDateAndShiftType(String branch, LocalDate localDate, boolean shiftTpe) throws SQLException {
        return null;
    }

    // TODO - israel do not throwe Exeption
    public void addEmployeeToShift(String branch, LocalDate localDate, boolean shiftTpe, int id, String position, boolean isAssined) throws SQLException {
    }


    // TODO - israel
    public void updateEmployeeToShift(LocalDate localDate, boolean shiftTpe, int id, String position, boolean isAssined) throws SQLException {

    }


    // TODO - israel
    public LinkedHashMap<LocalDate, ArrayList<Shift>> findAllShifsByBranch(String branch) throws SQLException {
        LinkedHashMap<LocalDate, ArrayList<Shift>> shifsByBranch = new LinkedHashMap<>();
        ArrayList<ShiftDTO> shifsByBranchDto = shiftDAO.findAll("Shift", date.toString(), DriverRequirementDTO.class );
    }

    // TODO - israel
    public LinkedHashMap < String,  HashMap<LocalDate, ArrayList<Shift>>> findAllShifsByDateInAllBranches(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> shifsByDateInAllBranches = new LinkedHashMap<>();
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


