package BusinessLayer.Supplier.Tests;

import BusinessLayer.Stock.Inventory;
import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import BusinessLayer.Supplier_Stock.ItemToOrder;
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
import java.time.LocalDate;
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

    @Test
    void addSupplierProduct(){
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        LocalDate date = LocalDate.of(2025, 10, 10);
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                1, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);
        serviceFactory.supplierService.addProduct(1, 111, "Bamba", "Osem", 10, 1000, date);
        assertEquals(serviceFactory.sc.isSupplierProductExists(1, "Bamba", "Osem"), true);
    }

    @Test
    void deleteSupplierProduct(){
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        LocalDate date = LocalDate.of(2025, 10, 10);
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                1, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);
        serviceFactory.supplierService.addProduct(1, 111, "Bamba", "Osem", 10, 1000, date);
        serviceFactory.supplierService.deleteProduct(1, 111);
        assertEquals(serviceFactory.sc.isSupplierProductExists(1, "Bamba", "Osem"), false);
    }

    @Test
    void addSupplierDiscount(){
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                1, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);
        serviceFactory.supplierService.addSupplierDiscount(1, Discounts.DISCOUNT_BY_TOTAL_QUANTITY,10,10, true);
        assertEquals(serviceFactory.sc.isSupplierDiscountExists(1, Discounts.DISCOUNT_BY_TOTAL_QUANTITY, 10, true), true);
    }

    @Test
    void addProductDiscount(){
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                1, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);
        serviceFactory.supplierService.addProduct(1, 111, "Bamba", "Osem", 10, 1000, LocalDate.of(2025, 10, 10));
        serviceFactory.supplierService.addProductDiscount(1,111,10,10 ,true);
        assertEquals(serviceFactory.sc.isProductDiscountExists(1, 111, 10, true), true);
    }
}