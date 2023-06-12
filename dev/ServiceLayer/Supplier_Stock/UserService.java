package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier_Stock.UserController;

public class UserService {
    private UserController uc;
    public UserService(UserController uc){
        this.uc = uc;
    }
    public Response login(String id){
        try{uc.login(id);}
        catch (Exception e){

        }
    }
}
