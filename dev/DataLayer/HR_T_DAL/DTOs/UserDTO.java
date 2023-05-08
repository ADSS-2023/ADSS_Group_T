package DataLayer.HR_T_DAL.DTOs;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.User;
import DataLayer.Util.DTO;

public class UserDTO extends DTO {

    private String userName;
    private String password;
    private String userType;

    /**
     * DTO constructor, must get a table name as 'name_of_object'
     *
     * @param tableName
     */
    public UserDTO(String tableName,String userName , String password ,String userType) {
        super(tableName);
        this.userType = userType;
        this.userName = userName;
        this.password = password;
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
}
