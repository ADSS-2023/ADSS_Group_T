package BusinessLayer.Transport;

public class Driver {
    private int id;
    private String name;
    private LicenseType licenseType;
    private CoolingLevel coolingLevel;
    

    public Driver(String name, LicenseType licenseType) {
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
    enum LicenseType {
        C, C1, E
    }
    enum CoolingLevel {
       non ,fridge , freezer
    }
}
