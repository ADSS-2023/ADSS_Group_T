package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;
public class Product {
    private String name;
    private CoolingLevel coolingLevel;

    public Product(String name,CoolingLevel coolingLevel) {
        this.name = name;
        this.coolingLevel = coolingLevel;
    }
    public String getName() {
        return this.name;
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }
}
