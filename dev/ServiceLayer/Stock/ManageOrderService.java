package ServiceLayer.Stock;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.OrderController;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier_Stock.Response;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ManageOrderService {
    private OrderController orderController;


    public ManageOrderService() {
    }

    /**
     * This function gets an input from the user that includes id of item , day of week , new amount that
     * required for the specific item.
     * @param id
     * @param day
     * @param new_amount
     * @return
     */
    public Response editRegularOrder(int id , DayOfWeek day , int new_amount){
        try {
            orderController.editRegularOrder(id , day , new_amount);
            return Response.okResponse("Order edited successfully");
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }

    }
    /**
     * Receives a map that holds item id and amount to be ordered
     * @param items_quantity
     * @return
     */
    public Response createRegularOrder(Map<Integer,Integer> items_quantity){
        try {
            return Response.okResponse(orderController.createRegularOrder(items_quantity));
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }

    }

    /**
     * This method send an order to suppliers which will be supplied one time as a special order.
     * @param items_quantity a map that maps item id to the desired amount.
     * @param isUrgent boolean flag to indicate whether the order priority is arrival.
     * @return string that indicates whether the action succeeded
     */
    public Response createSpecialOrder(Map<Integer,Integer> items_quantity,boolean isUrgent){
        try{
            orderController.createSpecialOrder(items_quantity,isUrgent);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("Order received successfully");
    }

    public Response nextDay() {
        try {
            this.orderController.nextDay();
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("Next Day functions succeed");
    }

    /**
     * Receive new order that arrived to the store
     * @param newOrder all the new arrivals
     */
    public Response receiveOrders(List<ItemToOrder> newOrder){
        try{
            orderController.receiveOrders(newOrder);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("Order received successfully");
    }

    /**
     * Presents all the items that hasn't been placed yet
     * @return
     */
    public Response presentItemsToBePlaced(){
        try {
            return Response.okResponse(orderController.presentItemsToBePlaced());
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    /**
     * place new arrival in store by index in waiting list
     * @param index
     * @param location
     * @return
     */
    public Response placeNewArrival(int index,String location){
        try{
            orderController.placeNewArrival(index,location);
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("Item has been placed successfully");
    }

    public Response presentItemsById(DayOfWeek cur_day) {
        try{
            return Response.okResponse(orderController.presentItemsByDay(cur_day));
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }
    public Response set_up()  {
        try {
            orderController.loadWaitingItems();
            orderController.loadOrderedItems();
            orderController.set_up_waiting_items();
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
        return Response.okResponse("set up succeed");
    }

    public void setOrderController(Inventory inv, OrderService orderService, InventoryDalController inventoryDalController) {

        this.orderController = new OrderController(inv,orderService,inventoryDalController);
        orderController.setInventoryDalController(inventoryDalController);
    }

    public Response show_all_orders() {
        try{
            return Response.okResponse(orderController.show_all_orders());
        }
        catch(Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }

    public OrderController getStockOrderController() {
        return orderController;
    }

    public Response show_new_items() {
        try {
            return Response.okResponse(orderController.show_new_items());
        }
        catch (Exception e){
            return Response.errorResponse(e.getMessage());
        }
    }
    public Response loadData(){
        try {
            orderController.loadData();
            return Response.okResponse("Action succeeded");
        }
        catch (Exception e){
           return Response.errorResponse(e.getMessage());
        }

    }
}
