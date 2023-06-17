package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier_Stock.UserController;

public class UserService {
    private UserController uc;
    public UserService(UserController uc){
        this.uc = uc;
    }
    public Response login(String id){
        ///TODO implement this ido!
        try{return Response.okResponse(uc.login(id));}
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }

    }
    public Response loadData(){
        try {
            uc.loadData();
            return Response.okResponse("load data succeeded");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }

    }

}
