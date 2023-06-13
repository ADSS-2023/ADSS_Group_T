package ServiceLayer.Stock;


import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.Item;
import ServiceLayer.Supplier_Stock.Response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class InventoryService {
    private Inventory inventory;

    public InventoryService(){
        inventory = new Inventory();
    }

    /**
     * This function show the whole data about the products in the system.
     * @param
     * @return
     * @throws Exception
     */

    public Response show_data(){
        try {
            return Response.okResponse(inventory.show_data(""));
        }
        catch (Exception e) {
            return Response.errorResponse(e.getMessage());
        }
    }

    /**
     * This function returns a pointer to the inventory class.
     * @return
     */
    public Inventory get_inventory(){
        return inventory;
    }

    /**
     * This function produces a report for every input on indexes that the user put in.
     * @param categories_list
     * @return
     */
    public Response produce_inventory_report(LinkedList<String> categories_list){
        try {
            return Response.okResponse(inventory.produce_inventory_report(categories_list));
        }
        catch (Exception e) {
            return Response.errorResponse(e.getMessage());
        }
    }

    /**
     * This function sets a discount in a specific index (product = index).
     * @param product
     * @param percentageAmount
     * @param end_date_string
     * @param start_date_string
     */
    public Response set_discount(String product, double percentageAmount, String end_date_string, String start_date_string) {
        try {
            return Response.okResponse(inventory.set_discount(product , percentageAmount , end_date_string , start_date_string));

        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    /**
     * This function produces the report on the shortage items.
     * @return
     */
    public Response produce_shortage_report(){
        try {
            return Response.okResponse(inventory.produce_shortage_list());
        }
        catch (Exception e){
            return Response.errorResponse(inventory.produce_shortage_list());
        }
    }

    /**
     * This function calls to set up the system with a data
     */
    public Response setUp() {
        try {
            return Response.okResponse(inventory.setUp());
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public Response loadData() {
        try {
            return Response.okResponse(inventory.loadData());
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }


}

