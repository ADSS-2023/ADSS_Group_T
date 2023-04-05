package ServiceLayer.Stock;

import BusinessLayer.Stock.Damaged;
import BusinessLayer.Stock.Inventory;

public class DamagedService {
    private Inventory inventory;
    private Damaged damaged;

    public DamagedService(Inventory inventory) {
        this.inventory = inventory;
    }

    public String produce_damaged_report(){
        return null;
    }
    public String report_damaged_item(int item_id,int order_id,int amount,String description){
        return inventory.addDamagedItem(item_id, order_id, amount, description);
    }
}
