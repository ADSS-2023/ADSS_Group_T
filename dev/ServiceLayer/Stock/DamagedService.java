package ServiceLayer.Stock;

import BusinessLayer.Stock.Damaged;
import BusinessLayer.Stock.Inventory;

public class DamagedService {
    private Inventory inventory;
    private Damaged damaged;
    public String produce_damaged_report(){
        return null;
    }
    public void report_damaged_item(int item_id,int order_id,int amount,String description){
        inventory.addDamagedItem(item_id, order_id, amount, description);
    }
}
