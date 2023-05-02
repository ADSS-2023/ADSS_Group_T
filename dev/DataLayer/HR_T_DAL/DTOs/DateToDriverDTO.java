package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DateToDriverDTO extends DTO {
    private String shiftDate;
    private int driverId;

    public DateToDriverDTO(String shiftDate, int driverId) {
        super("DateToDriver");
        this.shiftDate = shiftDate;
        this.driverId = driverId;
    }
}
