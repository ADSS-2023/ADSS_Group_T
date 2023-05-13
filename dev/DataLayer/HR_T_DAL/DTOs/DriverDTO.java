package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DriverDTO extends DTO {
    private int driverId;
    private String licenseType;
    private String coolingLevel;

    public DriverDTO(int driverId, String licenseType, String coolingLevel) {
        super("Driver");
        this.driverId = driverId;
        this.licenseType = licenseType;
        this.coolingLevel = coolingLevel;
    }

    public DriverDTO() {
    }

    public int getDriverId() {
        return driverId;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getCoolingLevel() {
        return coolingLevel;
    }
}
