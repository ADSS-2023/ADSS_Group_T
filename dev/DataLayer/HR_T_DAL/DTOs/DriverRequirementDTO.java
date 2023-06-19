package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.HR.Driver;
import DataLayer.Util.DTO;
import UtilSuper.Pair;

public class DriverRequirementDTO extends DTO {
    private String date;
    private String licenseType;
    private String coolingLevel;
    private int amount;

    public DriverRequirementDTO() {
        super("DriverRequirements");
    }

    public DriverRequirementDTO( String date, String licenseType, String coolingLevel, int amount) {
        super("DriverRequirements");
        this.date = date;
        this.licenseType = licenseType;
        this.coolingLevel = coolingLevel;
        this.amount = amount;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getCoolingLevel() {
        return coolingLevel;
    }

    public void setCoolingLevel(String coolingLevel) {
        this.coolingLevel = coolingLevel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
