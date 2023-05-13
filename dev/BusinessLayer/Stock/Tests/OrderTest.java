package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.OrderController;
import PresentationLayer.Supplier.SupplierManager;
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
    public SupplierManager supplierManager;

    @BeforeEach
    public void setUp(){
        serviceFactory = new ServiceFactory();
        inventory = serviceFactory.inventoryService.get_inventory();
        damagedService = serviceFactory.damagedService;
        categoryService = serviceFactory.categoryService;
        inventoryService = serviceFactory.inventoryService;
        itemService = serviceFactory.itemService;
        orderController = serviceFactory.manageOrderService.getStockOrderController();
        supplierManager = new SupplierManager(serviceFactory);

        try {
            serviceFactory.deleteAllData();
            serviceFactory.uss.setUpDate();
            serviceFactory.inventoryService.setUp();
            serviceFactory.manageOrderService.set_up();
            supplierManager.setUpData();
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
        //MAY FAIL - NEED TO BE DUE TO THE CUR DAY OF THE TEST
        Map<Integer,Integer> cur_map = new HashMap<>();
        cur_map.put(4 , 100);
        try {
            orderController.createSpecialOrder(cur_map, true);
            assertEquals("---------SUNDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n" +
                    "---------MONDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tItem id: 4, item name and manufacturer name: Click Elite, amount: 100\n" +
                    "---------TUESDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n" +
                    "---------WEDNESDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n" +
                    "---------THURSDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n" +
                    "---------FRIDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n" +
                    "---------SATURDAY---------\n" +
                    "Regular orders:\n" +
                    "\tNo items to present\n" +
                    "\n" +
                    "Special orders:\n" +
                    "\tNo items to present\n",  orderController.show_all_orders());

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