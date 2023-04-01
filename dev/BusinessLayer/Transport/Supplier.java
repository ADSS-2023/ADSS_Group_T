package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;
public class Supplier extends Site{

    CoolingLevel coolingLevel;
    public Supplier(String address,String telNumber,String contactName, CoolingLevel coolingLevel){
        this.address = address;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.coolingLevel = coolingLevel;
    }
}
