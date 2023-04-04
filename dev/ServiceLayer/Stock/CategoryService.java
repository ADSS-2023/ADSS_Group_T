package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.time.LocalDate;
import java.util.Date;

public class CategoryService {
    private Inventory inventory;
    public CategoryService(Inventory inventory){
        this.inventory = inventory;
    }
    public void setDiscount(LocalDate end_date,LocalDate start_date,double precentage){}
    public String show_data(String index){
        return inventory.show_data(index);
    }

}
