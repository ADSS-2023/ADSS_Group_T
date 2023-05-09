package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
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
    void produce_inventory_report() {
        LinkedList<String> indexes = new LinkedList<>();
        // choose to report about the whole mile-products.
        indexes.add(".0");
        String actual = "inventory report:\n" +
                "\n" +
                "Category : Milk-product\n" +
                "\n" +
                "\tCategory : Cheese\n" +
                "\n" +
                "\t\tproduct:yellow cheese manufacturer:Emeck amount in store:3 amount in warehouse:2\n" +
                "\n" +
                "\tCategory : bottle milk\n" +
                "\n" +
                "\t\tproduct:3% manufacturer:IDO LTD amount in store:20 amount in warehouse:20\n" +
                "\t\tproduct:1.5% manufacturer:IDO LTD amount in store:10 amount in warehouse:10\n" +
                "\n";

        String result = inventoryService.produce_inventory_report(indexes);
        assertEquals(result, actual);
    }

    @Test
    void set_discount() {
        try {
            double preCost = inventoryService.get_inventory().get_item_by_id(2).get_price();
            inventoryService.set_discount(".0.0", 10, "2024-11-05", "2023-04-04");
            double afterCost = inventoryService.get_inventory().get_item_by_id(2).get_price();
            //checks if one of the items from the specific categories gets the discount.
            assertEquals(preCost * 0.9, afterCost);
        }
        catch (Exception e){

        }
    }

    @Test
    void produce_shortage_report() {
        itemService.setMinimalAmount(1 , 300);
        String result = inventoryService.produce_shortage_report();
        String expected = "1.5% milk, IDO LTD\n" +
                " minimal amount: 300\n" +
                "current amount: 20\n" +
                "amount to order: 280\n" +
                "-------------------------------------------\n";
        assertEquals(expected , result);
    }

    @Test
    void add_item() {
        try {
            itemService.addItem(".0.0",5, "Milky", 3, "Liran LTD", 2.0);
            assertEquals(inventory.get_item_by_id(5).get_name() , "Milky");
        }
        catch (Exception e){

        }
        }
}