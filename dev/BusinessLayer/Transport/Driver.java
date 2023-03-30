package BusinessLayer.Transport;

public class Driver {
    private int id;
    private String name;
    private LicenseType licenseType;
    private CoolingLevel coolingLevel;
    

    public Driver(int driverId, String name, LicenseType licenseType) {
        this.name = name;
        this.licenseType = licenseType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LicenseType getLicenseLevel() {
        return this.licenseType;
    }

    public void setLicenseLevel(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
    public enum LicenseType {
        C, C1, E
    }
    public enum CoolingLevel {
       non ,fridge , freezer
    }
}
