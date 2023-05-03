package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Shift;
import DataLayer.HR_T_DAL.DTOs.ShiftDTO;
import DataLayer.Util.DAO;

import java.time.LocalDate;
import java.util.List;


    public class ShiftDAO extends DAO {

        public ShiftDAO() {}

        public Shift getShiftByDateAndBranch(LocalDate date, String branch) {
            // Retrieve a shift's details from the database by its date and branch
            return null;
        }

        public List<ShiftDTO> getshifsByDate(LocalDate localDate) {
            // Retrieve all shifts from the database
            return null;
        }

        public List<ShiftDTO> getshifsByDateAndShiftType(LocalDate localDate, boolean shiftType) {
            // Retrieve all shifts from the database
            return null;
        }


    }


