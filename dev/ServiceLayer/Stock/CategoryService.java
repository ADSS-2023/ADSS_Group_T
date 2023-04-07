package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.time.LocalDate;
import java.util.Date;

public class CategoryService {
    private Inventory inventory;
    public CategoryService(Inventory inventory){
        this.inventory = inventory;
    }
    public String show_data(String index){
        try {
            return inventory.show_data(index);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

}
