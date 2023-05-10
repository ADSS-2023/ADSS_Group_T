package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.Util.Util;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


class ItemTest {

    public static InventoryService inventoryService;
    public static CategoryService categoryService;
    public static DamagedService damagedService;
    public static ItemService itemService;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        try {
            inventoryService = new InventoryService();
            categoryService = new CategoryService(inventoryService.get_inventory());
            damagedService = new DamagedService(inventoryService.get_inventory());
            itemService = new ItemService(inventoryService.get_inventory());
            inventory = inventoryService.get_inventory();
            inventory.setUp();
        }
        catch (Exception e){

        }
    }


    @Test
    void setMin_amount(){
        try {
            String afterUpdate = itemService.setMinimalAmount(2 , 7);
            assertEquals(inventory.get_item_by_id(2).get_name() + " new minimal amount:7" , afterUpdate);
        }
        catch (Exception e){
        }
    }

    @Test
    void receive_order(){
        try {
            int amountBefore = inventory.get_item_by_id(0).amount_store();
            itemService.receive_order(156,0,20,"ile 5 shelf 15", Util.stringToDate("2023-05-25") , 2.20);
            int amountAfter = inventory.get_item_by_id(0).amount_store();
            assertEquals(amountBefore + 10 ,amountAfter);
        }
        catch (Exception e){

        }

    }

    @Test
    void makeItemFromData(){

    }
}