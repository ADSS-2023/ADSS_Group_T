package Initialization;

import ServiceLayer.Transport.LogisticCenterService;

public class LogisticCenter_init {
    public static void init(LogisticCenterService logisticCenterService){
        logisticCenterService.addTruck(2001, "Truck1", 4000 , 8000, 3);
        logisticCenterService.addTruck(2002, "Truck2", 8000, 13000, 2);
        logisticCenterService.addTruck(2003, "Truck3", 12500, 20000, 1);
        logisticCenterService.addTruck(2004, "Truck4", 15000, 22000, 3);
        logisticCenterService.addTruck(2005, "Truck5", 20000, 30000, 1);
    }
}
