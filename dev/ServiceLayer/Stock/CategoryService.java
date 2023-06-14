package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Supplier_Stock.Response;

import java.time.LocalDate;
import java.util.Date;

public class CategoryService {
    private Inventory inventory;
    public CategoryService(Inventory inventory){
        this.inventory = inventory;
    }

    /**
     * This function show the details on "index" sub-category
     * @param index
     * @return
     * @throws Exception
     */

    public Response show_data(String index){
        try {
            return Response.okResponse(inventory.show_data(index));
        }
        catch (Exception e) {
            return Response.errorResponse(e.getMessage());
        }
    }
    public Response add_category(String index,String name){
        try {
              inventory.add_category(index,name);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("Category added");
    }
}
