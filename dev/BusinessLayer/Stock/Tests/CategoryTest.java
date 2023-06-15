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
import java.security.spec.ECField;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
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
    }

    @Test
    void present_categories_on_empty_data() {
        String output = "";
        try {
            output = inventory.present_names();
        }
        catch (Exception c){
            output = c.getMessage();
        }
        assertEquals("No categories", output);
    }

    @Test
    void present_categories() {
        try {
            inventory.setUp();
            String output = inventory.present_names();
            assertEquals("1 : Milk-product, 2 : Meat-product", output);
        }
        catch (Exception e){
            e.getMessage();
        }

    }

    @Test
    void add_new_main_category(){
        try {
            inventory.setUp();
            inventory.add_category("" , "New_test_products");
            String output = inventory.present_names();
            assertEquals("1 : Milk-product, 2 : Meat-product, 3 : New_test_products", output);
        }
        catch (Exception e){
            e.getMessage();
        }
    }


}

