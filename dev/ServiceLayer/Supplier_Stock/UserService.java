package ServiceLayer.Supplier_Stock;

import BusinessLayer.Supplier_Stock.Employee;
import BusinessLayer.Supplier_Stock.UserController;

import java.sql.SQLException;

public class UserService {
    private UserController uc;
    public UserService(UserController uc){
        this.uc = uc;
    }

    public boolean isManager() {
        return uc.isManager();
    }

    public boolean isWareHouse() {
        return uc.isWareHouseEmployee();
    }

    public boolean isSupplier() {
        return uc.isSupplierEmployee();
    }

    public Response login(String id){
        ///TODO implement this ido!
        try{return Response.okResponse(uc.login(id));}
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response register(String id, Employee.Occupation occupation) {
        try {
            uc.register(id, occupation);
            return Response.okResponse("user signed up successfully.");
        }
        catch (Exception e) {
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
