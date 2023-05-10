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
        this.userType = userType;
        this.userName = userName;
        this.password = password;
        this.id =id;
        this.bankAccount = bankAccount;
        this.joiningDay = joiningDay;
        this.salary = salary;
        this.description = description;
    }
    public UserDTO(User user) {
        super("User");
        this.userType = user.getUserType().toString();
        this.userName = user.getEmployeeName();
        this.password = user.getPassword();
    }
    public UserDTO(Employee employee) {
        super("User");
        this.userType = employee.getUserType().toString();
        this.userName = employee.getEmployeeName();
        this.password = employee.getPassword();
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
