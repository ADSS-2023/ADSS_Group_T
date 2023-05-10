package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DateToDeliveryDTO extends DTO {

    private String shiftDate;
    private int deliveryId;

    public String getShiftDate() {
        return shiftDate;
    }

    public int getDeliveryId() {
        return deliveryId;
    }


    public DateToDeliveryDTO(String shiftDate, int deliveryId) {
        super("DateToDelivery");
        this.shiftDate = shiftDate;
        this.deliveryId = deliveryId;
    }

    public DateToDeliveryDTO() {
    }

    public static String getTableNameStatic(){
        return "DateToDelivery";
    }

}
