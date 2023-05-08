package BusinessLayer.Stock.Tests;

import static org.junit.jupiter.api.Assertions.*;
import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DamagedTest {

    public static InventoryService inventoryService;
    public static CategoryService categoryService;
    public static DamagedService damagedService;
    public static ItemService itemService;
    private Inventory inventory;
    public static ServiceFactory serviceFactory;

    @BeforeEach
    public void setUp(){
        serviceFactory = new ServiceFactory();
        inventory = serviceFactory.inventoryService.get_inventory();
        damagedService = serviceFactory.damagedService;
        categoryService = serviceFactory.categoryService;
        inventoryService = serviceFactory.inventoryService;
        itemService = serviceFactory.itemService;
        try {
            serviceFactory.dataSetUp();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }    }

    @Test
    public void testAddDamagedItem() {
        int before_amount = inventory.get_item_by_id(1).amount_store();
        damagedService.report_damaged_item( 1, 120,3, "Damaged during transit");
        assertEquals(inventory.get_item_by_id(1).amount_store(), before_amount - 3);
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

