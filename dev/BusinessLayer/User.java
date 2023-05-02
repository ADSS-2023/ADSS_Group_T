package BusinessLayer;

import BusinessLayer.HR.Constraint;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class  User {
    private int id;
    private String employeeName;
    private String bankAccount;

    private String description;
    private int salary;
    private LocalDate joiningDay;
    private String password;
    private UserType userType;

    public User(int id, String employeeName, String bankAccount,  String description, int salary, LocalDate joiningDay, String password, UserType userType) {
        this.id = id;
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.description = description;
        this.salary = salary;
        this.joiningDay = joiningDay;
        this.password = password;
        this.userType =  userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
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

    public LocalDate getJoiningDay() {
        return joiningDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
