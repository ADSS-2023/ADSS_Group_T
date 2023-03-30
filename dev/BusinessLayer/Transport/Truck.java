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

    public Truck(int licenseNumber, String model, int weight, int maxWeight, LicenseType licenseType){
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.licenseNumber = licenseNumber;

    }

    public Truck(int licenseNumber2, String model2, int weight2, int maxWeight2, LicenseType licenseType2,
            BusinessLayer.Transport.Driver.CoolingLevel coolingLevel2) {
    }

    enum CoolingLevel {
        non ,fridge , freezer
     }
}
