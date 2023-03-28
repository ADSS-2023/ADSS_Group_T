package BusinessLayer.Transport;

import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

public class Truck {
    private int licenseNumber;
    private String model;
    private int weight;
    private int maxWeight;
    private LicenseType licenseType;
    private CoolingLevel coolingLevel;

    public Truck(int licenseNumber, String model, int weight, int maxWeight, LicenseType licenseType, CoolingLevel coolingLevel){
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.licenseNumber = licenseNumber;
        this.coolingLevel = coolingLevel;
    }
}
