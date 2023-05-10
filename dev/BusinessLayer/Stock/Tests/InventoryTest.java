package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
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
            //serviceFactory.dataSetUp();
            inventory.setUp();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
                "\t\tid:2, product:yellow cheese, manufacturer:Emeck, amount in store:3, amount in warehouse:3\n" +
                "\n" +
                "\tCategory : bottle milk\n" +
                "\n" +
                "\t\tid:1, product:1.5% milk, manufacturer:IDO LTD, amount in store:5, amount in warehouse:5\n" +
                "\t\tid:0, product:3% milk, manufacturer:IDO LTD, amount in store:10, amount in warehouse:10\n" +
                "\n" +
                "\tCategory : chocolate\n" +
                "\n" +
                "\t\tid:4, product:Click, manufacturer:Elite, amount in store:10, amount in warehouse:10\n" +
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
            System.out.println(e.getMessage());
        }
    }

    @Test
    void produce_shortage_report() {
        itemService.setMinimalAmount(1 , 300);
        String result = inventoryService.produce_shortage_report();
        String expected = "1.5% milk, IDO LTD\n" +
                " minimal amount: 300, current amount: 10, amount to order: 290\n" +
                "-------------------------------------------\n";
        assertEquals(expected , result);
    }

    @Test
    void add_item() {
        try {
            itemService.addItem(".0.0", 3, "Milky", 3, "Liran LTD", 2.0);
            assertEquals(inventory.get_item_by_id(3).get_name(), "Milky");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}