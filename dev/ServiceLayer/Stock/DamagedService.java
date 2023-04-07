package ServiceLayer.Stock;

import BusinessLayer.Stock.Damaged;
import BusinessLayer.Stock.Inventory;

public class DamagedService {
    private Inventory inventory;

    public DamagedService(Inventory inventory) {
        this.inventory = inventory;
    }

    public String produce_damaged_report(){

        try{return inventory.produce_damaged_report();}
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String report_damaged_item(int item_id,int order_id,int amount,String description){
        try {
            return inventory.addDamagedItem(item_id, order_id, amount, description);
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}
