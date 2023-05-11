package BusinessLayer.Transport;

import BusinessLayer.HR.Driver.CoolingLevel;
import DataLayer.HR_T_DAL.DTOs.ProductDTO;

public class Product {
    private final String name;
    private final CoolingLevel coolingLevel;

    public Product(String name, int coolingLevel) {
        this.name = name;
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }

    public Product(ProductDTO dto){
        this.name = dto.getProductName();
        this.coolingLevel = CoolingLevel.valueOf(dto.getCoolingLevel());
    }

    public String getName() {
        return this.name;
    }

    public CoolingLevel getCoolingLevel() {
        return this.coolingLevel;
    }

}
