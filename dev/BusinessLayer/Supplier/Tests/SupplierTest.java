package BusinessLayer.Supplier.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import ServiceLayer.Stock.ItemService;
import ServiceLayer.Supplier.OrderService;
import ServiceLayer.Supplier.SupplierService;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {
   public static OrderService orderService;
   public static SupplierService supplierService;
    public static ServiceFactory serviceFactory;
    public static SupplierManager supplierManager;

    @BeforeEach
    public void setUp(){
        serviceFactory = new ServiceFactory();
        supplierService=serviceFactory.supplierService;
        orderService=serviceFactory.orderService;
        supplierManager = new SupplierManager(serviceFactory);

        try {
            serviceFactory.deleteAllData();
            serviceFactory.uss.setUpDate();
            supplierManager.setUpData();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addSupplier() {
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                1, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);

        assertEquals(serviceFactory.sc.isSupplierExists(1), true);
    }
}