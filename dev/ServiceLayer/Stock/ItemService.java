package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Supplier_Stock.Response;

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
    public Response setMinimalAmount(int item_id, int amount){
        try {
            return Response.okResponse(inventory.set_minimal_amount(item_id, amount));
        }
        catch (Exception e) {
            return Response.errorResponse(e.getMessage());
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
    public Response addItem(String index,int item_id,String name,int min_amount,String manufacturer_name,double price){
        try {
            return Response.okResponse(inventory.add_item(index,item_id,name,min_amount,manufacturer_name,price));
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
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
    public Response receive_order(int order_id, int item_id, int amount, String location,LocalDate validity,double cost_price) {
        try {
            return Response.okResponse(inventory.receive_order(order_id, item_id, amount, location, validity, cost_price));
        }
        catch(Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }
    public Response move_items_to_store(int item_id,int amount){
        try {
            inventory.get_item_by_id(item_id).move_items_to_store(amount);
            return Response.okResponse("Amount has changed successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }
    public Response present_item_amount(int item_id){
        try {
            return Response.okResponse(String.format("name:%s \namount in store:%s \namount in warehouse:%s",
                    inventory.get_item_by_id(item_id).get_name(),
                    inventory.get_item_by_id(item_id).amount_store(),
                    inventory.get_item_by_id(item_id).amount_warehouse()));
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }



    }
}
