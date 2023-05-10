package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.OrderController;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {
    public static InventoryService inventoryService;
    public static CategoryService categoryService;
    public static DamagedService damagedService;
    public static ItemService itemService;
    private Inventory inventory;
    public static ServiceFactory serviceFactory;
    public static OrderController orderController;

    @BeforeEach
    public void setUp(){
        serviceFactory = new ServiceFactory();
        inventory = serviceFactory.inventoryService.get_inventory();
        damagedService = serviceFactory.damagedService;
        categoryService = serviceFactory.categoryService;
        inventoryService = serviceFactory.inventoryService;
        itemService = serviceFactory.itemService;
        orderController = serviceFactory.manageOrderService.getStockOrderController();

        try {
            //serviceFactory.dataSetUp();
            inventory.setUp();
            serviceFactory.manageOrderService.set_up();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createSpecialOrder_need_to_fail(){
        Map<Integer,Integer> cur_map = new HashMap<>();
        cur_map.put(0 , 10);
        try {
            orderController.createSpecialOrder(cur_map, false);
            assertEquals("Order cannot be supplied" , "false");
        }
        catch (Exception e){
            assertEquals("\u001B[31mOrder cannot be supplied\u001B[0m", e.getMessage());
        }
    }

    @Test
    void createSpecialOrder_need_to_success(){
        //serviceFactory.supplierManager.setUpData();
        //MAKE SURE THAT YAGIL AND GOZ UPLOAD DATA!!
        Map<Integer,Integer> cur_map = new HashMap<>();
        cur_map.put(4 , 100);
        try {
            orderController.createSpecialOrder(cur_map, true);
            assertEquals("\u001B[31mOrder cannot be supplied\u001B[0m",  orderController.show_all_orders());

        }
        catch (Exception e){
            assertEquals("\u001B[31mOrder cannot be supplied\u001B[0m", e.getMessage());
        }
    }

    @Test
    void presentItemsToBePlaced(){
        try {
            assertEquals("1. Order id:12, item:3% milk, manufacturer:IDO LTD, amount:40\n" +
                    "2. Order id:1005, item:Beef Sausage, manufacturer:Zogloveck, amount:15\n", orderController.presentItemsToBePlaced());
        }
        catch (Exception e){
            assertEquals("" , "FALSE");
        }
    }

    @Test
    void placeNewArrival_removed() throws Exception {
        orderController.placeNewArrival(2 , "ile:2 , shelf:9");
        //check if the item removed from the "need to remove" list
        assertEquals("1. Order id:12, item:3% milk, manufacturer:IDO LTD, amount:40\n" , orderController.presentItemsToBePlaced());
    }

    @Test
    void placeNewArrival_added() throws Exception {
        int before_insert = inventory.get_item_by_id(5).current_amount();
        int amount_to_insert = orderController.getItems_to_place().get(1).getQuantity();
        orderController.placeNewArrival(2 , "ile:2 , shelf:9");
        int after_insert = inventory.get_item_by_id(5).current_amount();
        //check if the item added to the store (half of the amount at the data)
        assertEquals(before_insert + amount_to_insert , after_insert);
    }


}