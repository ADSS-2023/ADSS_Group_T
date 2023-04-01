package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;
public class Supplier extends Site{

    CoolingLevel coolingLevel;
    public Supplier(String address,String telNumber,String contactName, CoolingLevel coolingLevel){
        super(address,telNumber,contactName);
        this.coolingLevel = coolingLevel;
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }
}
