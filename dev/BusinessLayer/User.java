package BusinessLayer;

import BusinessLayer.HR.Constraint;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.List;
import java.util.Map;

public abstract class  User {
    private int id;
    private String employeeName;
    private String bankAccount;
    private List<PositionType> qualifiedPositions;
    private String description;
    private int salary;
    private String joiningDay;
    private String password;
    private UserType userType;

    public User(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, String joiningDay, String password, UserType userType) {
        this.id = id;
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.description = description;
        this.salary = salary;
        this.joiningDay = joiningDay;
        this.password = password;
        this.userType =  userType;
    }

    public int getId() {
        return id;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<PositionType> getQualifiedPositions() {
        return qualifiedPositions;
    }

    public void setQualifiedPositions(List<PositionType> qualifiedPositions) {
        this.qualifiedPositions = qualifiedPositions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getJoiningDay() {
        return joiningDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
