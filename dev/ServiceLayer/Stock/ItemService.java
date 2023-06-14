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
        try {
            return inventory.set_minimal_amount(item_id, amount);
        }
        catch (Exception e) {
            return e.getMessage();
        }
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
    public void addItem(String index,int item_id,String name,int min_amount,String manufacturer_name,double price) {
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
    public String receive_order(int order_id, int item_id, int amount, String location,LocalDate validity,double cost_price) {
        try {
            return inventory.receive_order(order_id, item_id, amount, location, validity, cost_price);
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
    public String move_items_to_store(int item_id,int amount){
        try {
            inventory.get_item_by_id(item_id).move_items_to_store(amount);
            return "Amount has changed successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public String present_item_amount(int item_id){
        try {
            return  String.format("name:%s \namount in store:%s \namount in warehouse:%s",
                    inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).amount_store(),
                    inventory.get_item_by_id(item_id).amount_warehouse());
        }
        catch (Exception e){
            return e.getMessage();
        }



    }
}
