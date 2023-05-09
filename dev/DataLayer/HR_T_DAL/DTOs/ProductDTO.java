package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class ProductDTO extends DTO {

    private String productName;
    private String coolingLevel;

    public ProductDTO(String productName, String coolingLevel) {
        super("Product");
        this.productName = productName;
        this.coolingLevel = coolingLevel;
    }

    public static String getPKStatic(){return "productName";}
    public static String getTableNameStatic(){return "Product";}


    public String getProductName() {
        return productName;
    }

    public String getCoolingLevel() {
        return coolingLevel;
    }

}
