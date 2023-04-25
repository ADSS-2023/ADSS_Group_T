package DataLayer.HR_T_DAL;

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
}
