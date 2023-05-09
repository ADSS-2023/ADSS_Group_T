package BusinessLayer.Stock.Tests;

import static org.junit.jupiter.api.Assertions.*;
import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DamagedTest {

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
            e.getMessage();
        }
    }

    @Test
    public void testAddDamagedItem() {
        try {
            int before_amount = inventory.get_item_by_id(1).amount_store();
            damagedService.report_damaged_item( 1, 120,3, "Damaged during transit");
            assertEquals(inventory.get_item_by_id(1).amount_store(), before_amount - 3);
        }
        catch (Exception e){

        }

    }

    @Test
    public void testProduceDamagedReport() {
        damagedService.report_damaged_item( 1, 120,3, "Damaged during transit");

        try {
            String result = damagedService.produce_damaged_report();
            // Check if the returned report is not empty and contains the correct item details
            assertNotNull(result);
            assertEquals(result, "Item name : 1.5% , Item ID : 1 , Amount : 3 , Description : Damaged during transit");
        } catch (Exception e) {
            fail("produce_damaged_report() threw an exception: " + e.getMessage());
        }
    }

}

