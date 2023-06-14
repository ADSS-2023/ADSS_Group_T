package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier_Stock.UserController;

public class UserService {
    private UserController uc;
    public UserService(UserController uc){
        this.uc = uc;
    }
    public Response login(String id){
        ///TODO implement this ido!
        try{uc.login(id);}
        catch (Exception e){

        }
        return Response.errorResponse("todo");
    }
}
