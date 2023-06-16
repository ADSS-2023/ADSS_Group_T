package BusinessLayer.HR.User;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.UserDTO;

import java.time.LocalDate;
import java.util.Objects;

public  class  User {
    protected int id;
    protected String employeeName;
    protected String bankAccount;
    protected String description;
    protected int salary;
    protected LocalDate joiningDay;
    protected String password;
    protected UserType userType;

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

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.employeeName = userDTO.getUserName();
        this.bankAccount = userDTO.getBankAccount();
        this.description = userDTO.getDescription();
        this.salary = userDTO.getSalary();
        //this.joiningDay = userDTO.getJoiningDay();/////using time object
        this.password = userDTO.getPassword();
        this.userType =  getTypeByString (userDTO.getUserType());
    }

    public static UserType getTypeByString (String type ) {
        if (type.equals("HRManager"))
            return UserType.HRManager;
        if (type.equals("driver"))
            return UserType.driver;
        if (type.equals("TransportManager"))
            return UserType.TransportManager;
        if (type.equals("SuperManager"))
            return UserType.SuperManager;
        else
            return UserType.employee;
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
