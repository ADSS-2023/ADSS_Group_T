package BusinessLayer.Transport;

import BusinessLayer.User;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.List;

public class Driver extends User {
    private int id;
    private String name;
    private LicenseType licenseType;
    private CoolingLevel coolingLevel;

    public Driver(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, String joiningDay, String password, UserType userType, LicenseType licenseType, CoolingLevel coolingLevel) {
        super(id, employeeName, bankAccount, qualifiedPositions, description, salary, joiningDay, password, userType);
        this.licenseType = licenseType;
        this.coolingLevel = coolingLevel;
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
        C1, C, E;
        public static LicenseType getByNumber(int licenseType) {
            if (licenseType == 1)
                return LicenseType.C1;
            if (licenseType == 2)
                return LicenseType.C;
            if (licenseType == 3)
                return LicenseType.E;
            else
                return LicenseType.C1;
        }

        public static LicenseType getByWeight(int weight) {
            if(weight < 12000)
                return LicenseType.C1;
            else if (weight < 20000 )
                return LicenseType.C;
            else
                return LicenseType.E;
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
