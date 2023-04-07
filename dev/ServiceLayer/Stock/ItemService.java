package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;

import java.time.LocalDate;

public class ItemService {
    private Inventory inventory;

    public ItemService(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This function sets the minimal amount of items in the specific item.
     * @param item_id
     * @param amount
     * @return
     */
    public String setMinimalAmount(int item_id,int amount){
        return inventory.set_minimal_amount(item_id,amount);

    }

    /**
     * Add new item to the system
     * @param index
     * @param item_id
     * @param name
     * @param min_amount
     * @param manufacturer_name
     * @param price
     */
    public void addItem(String index,int item_id,String name,int min_amount,String manufacturer_name,double price){
        try {
            inventory.add_item(index,item_id,name,min_amount,manufacturer_name,price);
        }
        catch (Exception e){
            e.getMessage();
        }

    }

    /**
     * this methode receives a new amount of a specific item by order id.
     * @param order_id
     * @param item_id
     * @param amount
     * @param location
     * @param validity
     * @param cost_price
     */
    public void receive_order(int order_id, int item_id, int amount, String location,LocalDate validity,double cost_price) {
        inventory.receive_order(order_id,item_id,amount,location,validity,cost_price);
    }
}
