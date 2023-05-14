package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.Util.DTO;

import java.time.LocalDate;

public class UserDTO extends DTO {

    protected int id;
    protected String userName;
    protected String bankAccount;

    protected String description;
    protected int salary;
    protected String joiningDay;
    protected String password;
    protected String userType;
    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    public UserDTO(String tableName,String userName , String password ,String userType , int id ,String bankAccount , String joiningDay, String description , int salary) {
        super(tableName);
        this.id  = id ;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.bankAccount = bankAccount;
        this.description = description;
        this.salary = salary;
        this.joiningDay = joiningDay;
    }

    public UserDTO(Employee employee) {
        super("User");
        this.id = employee.getId();
        this.userName = employee.getEmployeeName();
        this.password = employee.getPassword();
        this.userType = employee.getUserType().name();
        this.bankAccount = employee.getBankAccount();
        this.description = employee.getDescription();
        this.salary = employee.getSalary();
        this.joiningDay = employee.getJoiningDay().toString();
    }

    public UserDTO(User user) {
        super("User");
        this.id = user.getId();
        this.userName = user.getEmployeeName();
        this.password = user.getPassword();
        this.userType = user.getUserType().name();
        this.bankAccount = user.getBankAccount();
        this.description = user.getDescription();
        this.salary = user.getSalary();
        this.joiningDay = user.getJoiningDay().toString();
    }



    public UserDTO(){}




    public int getId() {
        return id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getDescription() {
        return description;
    }

    public int getSalary() {
        return salary;
    }

    public String getJoiningDay() {
        return joiningDay;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
