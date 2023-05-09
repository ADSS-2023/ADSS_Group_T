package BusinessLayer.HR;

import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;

import java.time.LocalDate;
import java.util.List;

public class Driver extends User {
    private final CoolingLevel coolingLevel;
    private int id;
    private String name;
    private LicenseType licenseType;
    //    private Map<LocalDate, boolean > submittedShifts; // TODO- impliment:  submit shift, assignShift and assignAll
    private List<LocalDate> restrictions; // TODo- is neccessary?

    public Driver(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, LocalDate joiningDay, String password, UserType userType, LicenseType licenseType, CoolingLevel coolingLevel) {
        super(id, employeeName, bankAccount, description, salary, joiningDay, password, userType);
        this.licenseType = licenseType;
        this.coolingLevel = coolingLevel;
        // restrictions = new ArrayList<>();
    }


//    public boolean isLegalDate(LocalDate date) {
//        return !restrictions.contains(date);
//    }


    public CoolingLevel getCoolingLevel() {
        return coolingLevel;
    }

    public LicenseType getLicenseLevel() {
        return this.licenseType;
    }

    public void setLicenseLevel(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public int compareTo(Driver d2) {
        Driver.LicenseType licenseLevel2 = d2.getLicenseLevel();
        if (this.licenseType == licenseLevel2)
            return 0;
        else if (this.licenseType == LicenseType.E)
            return 1;
        else if (this.licenseType == LicenseType.C) {
            if (licenseLevel2 == LicenseType.E)
                return -1;
            else
                return 1;
        } else
            return -1;
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
            if (weight < 12000)
                return LicenseType.C1;
            else if (weight < 20000)
                return LicenseType.C;
            else
                return LicenseType.E;
        }
    }

    public enum CoolingLevel {
        non, fridge, freezer;

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