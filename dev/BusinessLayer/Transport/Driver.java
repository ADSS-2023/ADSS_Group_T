package BusinessLayer.Transport;

public class Driver {
    private int id;
    private String name;
    private LicenseType licenseType;
    private CoolingLevel coolingLevel;
    


    public Driver(int id, String name, int licenseType, int coolingLevel) {
        this.id = id;
        this.name = name;
        this.licenseType = LicenseType.get(licenseType);
        this.coolingLevel = CoolingLevel.get(coolingLevel);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }

    public LicenseType getLicenseLevel() {
        return this.licenseType;
    }

    public void setLicenseLevel(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
    public enum LicenseType {
        C, C1, E;
        public static LicenseType get(int licenseType) {
            if (licenseType == 1)
                return LicenseType.C;
            if (licenseType == 2)
                return LicenseType.C1;
            if (licenseType == 3)
                return LicenseType.E;
            else
                return LicenseType.C;
        }
    }
    public enum CoolingLevel {
       non ,fridge , freezer;

        public static CoolingLevel get(int coolingLevel) {
            if (coolingLevel == 1)
                return CoolingLevel.non;
            if (coolingLevel == 2)
                return CoolingLevel.fridge;
            if (coolingLevel == 3)
                return CoolingLevel.freezer;
            else
                return CoolingLevel.non;
        }
    }
}
