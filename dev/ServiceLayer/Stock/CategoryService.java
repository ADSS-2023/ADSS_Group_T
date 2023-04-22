package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

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

    public String show_data(String index){
        try {
            return inventory.show_data(index);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String add_category(String index,String name){
        try {
              inventory.add_category(index,name);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Category added";
    }
}