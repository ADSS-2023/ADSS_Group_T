package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DateToDriverDTO extends DTO {
    private String shiftDate;
    private int driverId;

    private String isAssigned;

    public DateToDriverDTO(String shiftDate, int driverId, String isAssigned) {
        super("DateToDriver");
        this.shiftDate = shiftDate;
        this.driverId = driverId;
        this.isAssigned = isAssigned;

    }

    public DateToDriverDTO(String shiftDate, int driverId) {
        super("DateToDriver");
        this.shiftDate = shiftDate;
        this.driverId = driverId;
    }

    public DateToDriverDTO() {
        super("DateToDriver");


    }

    public String getShiftDate() {
        return shiftDate;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getIsAssigned() {
        return isAssigned;
    }
}
