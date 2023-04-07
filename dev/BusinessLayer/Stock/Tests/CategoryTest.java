package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    public static InventoryService inventoryService;
    public static CategoryService categoryService;
    public static DamagedService damagedService;
    public static ItemService itemService;
    private Inventory inventory;
    @BeforeEach
    public void setUp() {
        inventoryService = new InventoryService();
        categoryService = new CategoryService(inventoryService.get_inventory());
        damagedService = new DamagedService(inventoryService.get_inventory());
        itemService = new ItemService(inventoryService.get_inventory());
        inventory = inventoryService.get_inventory();
        inventory.setUp();
    }

    @Test
    void add_category() {

    }


}

