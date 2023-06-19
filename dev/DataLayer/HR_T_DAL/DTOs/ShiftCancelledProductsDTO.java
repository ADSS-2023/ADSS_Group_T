package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ShiftCancelledProductsDTO extends DTO {

    private String shiftDate;
    private String shiftType;
    private String productName;
    private int amount;

    public ShiftCancelledProductsDTO(String shiftDate, String shiftType, String productName, int amount) {
        super("ShiftCancelledProducts");
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.productName = productName;
        this.amount = amount;
    }
    public ShiftCancelledProductsDTO() {
        super("ShiftCancelledProducts");
    }
}
