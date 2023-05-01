package BusinessLayer;

import UtilSuper.PositionType;

import java.util.LinkedHashMap;
import java.util.List;

public class UserController {
    private LinkedHashMap<Integer,User> users;

    public UserController() {
        this.users = new LinkedHashMap<Integer, User>();
    }

    public boolean login (int id, String password){

    }
    public String addNewUser(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, String joiningDay, String password){
        users.put(id, new User(id,employeeName,bankAccount,qualifiedPositions,description,salary,joiningDay,password));
        return null;
    }
}
