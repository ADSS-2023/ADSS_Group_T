package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DateToTruckDTO extends DTO {
    private String shiftDate;
    private int truckId;

    public DateToTruckDTO(String shiftDate, int truckId) {
        super("DateToTruck");
        this.shiftDate = shiftDate;
        this.truckId = truckId;
    }

    public DateToTruckDTO() {
    }

    public static String getTableNameStatic() {
        return "DateToTruck";
    }


    public String getShiftDate() {
        return shiftDate;
    }

    public int getTruckId() {
        return truckId;
    }
}
