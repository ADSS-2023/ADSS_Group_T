package ServiceLayer;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.UserController;
import ServiceLayer.HR.Response;

public class UserService {
    public UserService(UserController userController) {
        this.userController = userController;
    }
    private UserController userController;
    public String login(int id, String password) {
        Response res = new Response();
//        try {
//            if (userController.login(id, password))
//                return "m";
//            else return "e";
//        } catch (Exception ex) {
//        }
        return null;
    }

}
