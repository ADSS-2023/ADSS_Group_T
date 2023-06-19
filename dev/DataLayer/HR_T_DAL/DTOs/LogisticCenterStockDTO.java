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

    public LogisticCenterStockDTO() {
        super("LogisticCenterStock");
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }
    public static String getPKStatic(){return "productsName";}
    public static String getTableNameStatic(){return "LogisticCenterStock";}
}
