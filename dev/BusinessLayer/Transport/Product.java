package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;
public class Product {
    private String name;
    private CoolingLevel coolingLevel;

    public Product(String name, int coolingLevel) {
        this.name = name;
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }
    public String getName() {
        return this.name;
    }
    public CoolingLevel getCoolingLevel(){return this.coolingLevel;}

}
