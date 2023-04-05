package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

public class ItemService {
    private Inventory inventory;

    public ItemService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setDiscount(){}
    public String setMinimalAmount(int item_id,int amount){
        return inventory.set_minimal_amount(item_id,amount);

    }

}
