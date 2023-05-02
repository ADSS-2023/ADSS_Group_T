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


    public Truck(int licenseNumber, String model, int weight, int maxWeight ,int coolingLevel){
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.licenseType = LicenseType.getByWeight(weight);
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }


    public int getLicenseNumber() {
        return licenseNumber;
    }

    public String getModel() {
        return model;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }

    public int compareTo( Truck t2) {
        return Integer.compare(this.getWeight(), t2.getWeight());
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

}
