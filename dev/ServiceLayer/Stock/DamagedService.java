package ServiceLayer.Stock;

import BusinessLayer.Stock.Damaged;
import BusinessLayer.Stock.Inventory;

public class DamagedService {
    private Inventory inventory;

    public DamagedService(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This function produces the report of the damaged items.
     * @return
     */
    public String produce_damaged_report(){

        try{return inventory.produce_damaged_report();}
        catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * This function adds a damaged item to the list of the damaged items.
     * @param item_id
     * @param order_id
     * @param amount
     * @param description
     * @return
     */
    public String report_damaged_item(int item_id,int order_id,int amount,String description){
        try {
            return inventory.addDamagedItem(item_id, order_id, amount, description);
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}
