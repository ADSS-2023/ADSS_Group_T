package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.Transport.Truck;
import DataLayer.Util.DTO;

public class TruckDTO extends DTO {
    private int licenseNumber;
    private String model;
    private int weight;
    private int maxWeight;
    private String licenseType;
    private String coolingLevel;
    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    public TruckDTO(int licenseNumber, String model,int weight,int maxWeight,String licenseType,String coolingLevel) {
        super("Truck");
        this.coolingLevel = coolingLevel;
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.model = model;
        this.weight = weight;
        this.maxWeight =maxWeight;
    }

    public TruckDTO(Truck truck) {
        super("Truck");
        this.coolingLevel = truck.getCoolingLevel().toString();
        this.licenseNumber = truck.getLicenseNumber();
        this.licenseType = truck.getLicenseType().toString();
        this.model = truck.getModel();
        this.weight = truck.getWeight();
        this.maxWeight = truck.getMaxWeight();
    }

    public TruckDTO() {
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

    public String getLicenseType() {
        return licenseType;
    }

    public String getCoolingLevel() {
        return coolingLevel;
    }
}
