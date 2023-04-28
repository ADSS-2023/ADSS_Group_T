package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;

import java.util.ArrayList;

public class Supplier extends Site{

    CoolingLevel coolingLevel;
    public Supplier(String address,String telNumber,String contactName, int coolingLevel,int x,int y){
        super(address,telNumber,contactName,x,y);
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }

}
