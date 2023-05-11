package ServiceLayer;

import BusinessLayer.HR.User.UserController;
import BusinessLayer.HR.User.UserType;
import ServiceLayer.HR.Response;
import UtilSuper.Response;

public class UserService {
    public UserService(UserController userController) {
        this.userController = userController;
    }
    private UserController userController;
    public String login(int id, String password) {
        Response res = new Response();
        try {
           return userController.login(id, password).toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

}
