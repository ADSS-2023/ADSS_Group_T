package Initialization;




import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.TruckService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Truck_init {




    public static void init(TruckService truckService) {






        //---------- init trucks ----------//
        truckService.addTruck(2001, "Truck1", 4000 , 8000, 3);
        truckService.addTruck(2002, "Truck2", 8000, 13000, 2);
        truckService.addTruck(2003, "Truck3", 12500, 20000, 1);
        truckService.addTruck(2004, "Truck4", 15000, 22000, 3);
        truckService.addTruck(2005, "Truck5", 20000, 30000, 1);


        //---------- Add product lists to suppliers map ----------//



    }
}
