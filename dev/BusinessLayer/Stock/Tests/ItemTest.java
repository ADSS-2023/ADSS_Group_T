package BusinessLayer.Stock.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Stock.Util.Util;
import PresentationLayer.Supplier.SupplierManager;
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
    public static ServiceFactory serviceFactory;
    public SupplierManager supplierManager;

    @BeforeEach
    public void setUp(){
        serviceFactory = new ServiceFactory();
        inventory = serviceFactory.inventoryService.get_inventory();
        damagedService = serviceFactory.damagedService;
        categoryService = serviceFactory.categoryService;
        inventoryService = serviceFactory.inventoryService;
        itemService = serviceFactory.itemService;
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
    void setMin_amount(){
        try {
            String afterUpdate = (String) itemService.setMinimalAmount(2 , 2).getValue();
            assertEquals(inventory.get_item_by_id(2).get_name() + " new minimal amount:2" , afterUpdate);
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