package Initialization;

import ServiceLayer.Transport.SupplierService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Suppliers_init {

    public static void init(SupplierService supplierService){

// Supplier 1 - Butcher Shop
        LinkedHashMap<String,Integer> productMap1 = new LinkedHashMap<String, Integer>();
        productMap1.put("Beef", 2);
        productMap1.put("Pork", 2);
        productMap1.put("Chicken", 1);
        String supplierAddress1 = "John's Butcher Shop";
        supplierService.addSupplier(supplierAddress1, "0601", "John", 4, 3);
        supplierService.addProducts(supplierAddress1, productMap1);

// Supplier 2 - Bakery
        LinkedHashMap<String,Integer> productMap2 = new LinkedHashMap<String, Integer>();
        productMap2.put("Bread", 1);
        productMap2.put("Cupcake", 1);
        productMap2.put("Croissant", 1);
        String supplierAddress2 = "Sweet Buns Bakery";
        supplierService.addSupplier(supplierAddress2, "0602", "Sophie", 6, -5);
        supplierService.addProducts(supplierAddress2, productMap2);

// Supplier 3 - Seafood Market
        LinkedHashMap<String,Integer> productMap3 = new LinkedHashMap<String, Integer>();
        productMap3.put("Salmon", 2);
        productMap3.put("Shrimp", 3);
        productMap3.put("Tuna", 2);
        String supplierAddress3 = "Sea to Table";
        supplierService.addSupplier(supplierAddress3, "0603", "Tom", 1, 2);
        supplierService.addProducts(supplierAddress3, productMap3);

// Supplier 4 - Fruit Stand
        LinkedHashMap<String,Integer> productMap4 = new LinkedHashMap<String, Integer>();
        productMap4.put("Apple", 1);
        productMap4.put("Mango", 2);
        productMap4.put("Orange", 1);
        String supplierAddress4 = "Fresh Fruits and More";
        supplierService.addSupplier(supplierAddress4, "0604", "Liz", -8, 0);
        supplierService.addProducts(supplierAddress4, productMap4);

// Supplier 5 - Green Grocer
        LinkedHashMap<String,Integer> productMap5 = new LinkedHashMap<String, Integer>();
        productMap5.put("Broccoli", 2);
        productMap5.put("Carrots", 1);
        productMap5.put("Kale", 2);
        String supplierAddress5 = "Organic Greens";
        supplierService.addSupplier(supplierAddress5, "0605", "Alex", -5, 6);
        supplierService.addProducts(supplierAddress5, productMap5);

// Supplier 6 - Wine and Spirits
        LinkedHashMap<String,Integer> productMap6 = new LinkedHashMap<String, Integer>();
        productMap6.put("Red Wine", 2);
        productMap6.put("Whiskey", 3);
        productMap6.put("Tequila", 2);
        String supplierAddress6 = "Spirits Unlimited";
        supplierService.addSupplier(supplierAddress6, "0606", "Mike", -7, -8);
        supplierService.addProducts(supplierAddress6, productMap6);

// Supplier 7 - Deli
        LinkedHashMap<String,Integer> productMap7 = new LinkedHashMap<String, Integer>();
        productMap7.put("Ham", 1);
        productMap7.put("Turkey", 1);
        productMap7.put("Pastrami", 2);
        String supplierAddress7 = "Gourmet Deli";
        supplierService.addSupplier(supplierAddress7, "0607", "Karen", 9, -3);
        supplierService.addProducts(supplierAddress7, productMap7);

// Supplier 8 - Asian Market
        LinkedHashMap<String,Integer> productMap8 = new LinkedHashMap<String, Integer>();
        productMap8.put("Sushi", 2);
        productMap8.put("Ramen", 2);
        productMap8.put("Tofu", 1);
        String supplierAddress8 = "Tokyo Market";
        supplierService.addSupplier(supplierAddress8, "0608", "Yuki", 5, -7);
        supplierService.addProducts(supplierAddress8, productMap8);

// Supplier 9 - Coffee Roastery
        LinkedHashMap<String,Integer> productMap9 = new LinkedHashMap<String, Integer>();
        productMap9.put("Espresso", 1);
        productMap9.put("Latte", 1);
        productMap9.put("Cold Brew", 2);
        String supplierAddress9 = "Brewed Awakening";
        supplierService.addSupplier(supplierAddress9, "0609", "Chris", 1, 9);
        supplierService.addProducts(supplierAddress9, productMap9);

// Supplier 10 - Gourmet Market
        LinkedHashMap<String,Integer> productMap10 = new LinkedHashMap<String, Integer>();
        productMap10.put("Truffle Oil", 3);
        productMap10.put("Artisan Cheese", 2);
        productMap10.put("Foie Gras", 3);
        String supplierAddress10 = "Gourmet Finds";
        supplierService.addSupplier(supplierAddress10, "0610", "Maria", -4, 5);
        supplierService.addProducts(supplierAddress10, productMap10);



    }
}
