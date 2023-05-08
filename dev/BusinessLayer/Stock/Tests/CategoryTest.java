package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    public static InventoryService inventoryService;
    public static CategoryService categoryService;
    public static DamagedService damagedService;
    public static ItemService itemService;
    private Inventory inventory;
    public static ServiceFactory serviceFactory;

    @BeforeEach
    public void setUp() {
        serviceFactory = new ServiceFactory();
        inventory = serviceFactory.inventoryService.get_inventory();
    }

    @Test
    void present_categories_on_empty_data() {
        String output = inventory.present_names();
        assertEquals("No categories", output);
    }

    @Test
    void present_categories() {
        //serviceFactory.inventory.setUp();
        String output = inventory.present_names();
        assertEquals("1 : Milk-product, 2 : Meat-product", output);
    }

    //HALF - WORK
    @Test
    void add_category(){
        try {
            //inventory.setUp();
            inventory.add_category("", "Liran555");
            inventory.add_category(".0", "Liran52"); // fail because of name of index
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

