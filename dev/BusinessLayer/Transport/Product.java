package BusinessLayer.Transport;

import BusinessLayer.HR.Driver.CoolingLevel;

public class Product {
    private final String name;
    private final CoolingLevel coolingLevel;

    public Product(String name, int coolingLevel) {
        this.name = name;
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }

    public String getName() {
        return this.name;
    }

    public CoolingLevel getCoolingLevel() {
        return this.coolingLevel;
    }

}
