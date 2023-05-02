package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DateToDeliveryDTO extends DTO {

    private String shiftDate;
    private int deliveryId;

    public DateToDeliveryDTO(String shiftDate, int deliveryId) {
        super("DateToDelivery");
        this.shiftDate = shiftDate;
        this.deliveryId = deliveryId;
    }
}
