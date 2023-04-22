package ServiceLayer.Transport;
import BusinessLayer.Transport.Delivery;
import BusinessLayer.Transport.Driver;
import BusinessLayer.Transport.Truck;
import com.google.gson.Gson;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;
public class TestGoogleGson {

    public void start() {
        TransportService ts = new TransportService();
        ts.addTruck(1, "ford", 12000,20000,LicenseType.C,CoolingLevel.fridge);
        ts.addTruck(2, "chev", 18000,22000,LicenseType.C,CoolingLevel.fridge);
        ts.addTruck(3, "fiat", 22000,30000,LicenseType.E,CoolingLevel.non);
        System.out.println( ts.getAllTrucks());
        System.out.println("ok");


    }
}
