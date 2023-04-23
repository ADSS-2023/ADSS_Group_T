package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;

import java.util.ArrayList;

public class Supplier extends Site{

    CoolingLevel coolingLevel;
    public Supplier(String address,String telNumber,String contactName, int coolingLevel){
        super(address,telNumber,contactName);
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }

}
