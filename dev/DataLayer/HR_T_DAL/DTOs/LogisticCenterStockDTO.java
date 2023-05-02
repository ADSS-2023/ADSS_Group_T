package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class LogisticCenterStockDTO extends DTO {

    private String productName;
    private int amount;

    public LogisticCenterStockDTO(String productName, int amount) {
        super("LogisticCenterStock");
        this.productName = productName;
        this.amount = amount;
    }
}
