package Initialization;

import ServiceLayer.Transport.SupplierService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Suppliers_init {

    public static void initSupplierProducts(SupplierService supplierService){

// Supplier 1 - Butcher Shop
        LinkedHashMap<String,Integer> productMap1 = new LinkedHashMap<String, Integer>();
        productMap1.put("Beef", 2);
        productMap1.put("Pork", 2);
        productMap1.put("Chicken", 1);
        supplierService.addProducts("s1", productMap1);

// Supplier 2 - Bakery
        LinkedHashMap<String,Integer> productMap2 = new LinkedHashMap<String, Integer>();
        productMap2.put("Bread", 1);
        productMap2.put("Cupcake", 1);
        productMap2.put("Croissant", 1);
        supplierService.addProducts("s2", productMap2);

// Supplier 3 - Seafood Market
        LinkedHashMap<String,Integer> productMap3 = new LinkedHashMap<String, Integer>();
        productMap3.put("Salmon", 2);
        productMap3.put("Shrimp", 3);
        productMap3.put("Tuna", 2);
        String supplierAddress3 = "Sea to Table";
        supplierService.addProducts("s3", productMap3);

// Supplier 4 - Fruit Stand
        LinkedHashMap<String,Integer> productMap4 = new LinkedHashMap<String, Integer>();
        productMap4.put("Apple", 1);
        productMap4.put("Mango", 2);
        productMap4.put("Orange", 1);
        supplierService.addProducts("s4", productMap4);

// Supplier 5 - Green Grocer
        LinkedHashMap<String,Integer> productMap5 = new LinkedHashMap<String, Integer>();
        productMap5.put("Broccoli", 2);
        productMap5.put("Carrots", 1);
        productMap5.put("Kale", 2);
        supplierService.addProducts("s5", productMap5);

// Supplier 6 - Wine and Spirits
        LinkedHashMap<String,Integer> productMap6 = new LinkedHashMap<String, Integer>();
        productMap6.put("Red Wine", 2);
        productMap6.put("Whiskey", 3);
        productMap6.put("Tequila", 2);
        supplierService.addProducts("s6", productMap6);

// Supplier 7 - Deli
        LinkedHashMap<String,Integer> productMap7 = new LinkedHashMap<String, Integer>();
        productMap7.put("Ham", 1);
        productMap7.put("Turkey", 1);
        productMap7.put("Pastrami", 2);
        supplierService.addProducts("s7", productMap7);

// Supplier 8 - Asian Market
        LinkedHashMap<String,Integer> productMap8 = new LinkedHashMap<String, Integer>();
        productMap8.put("Sushi", 2);
        productMap8.put("Ramen", 2);
        productMap8.put("Tofu", 1);
        supplierService.addProducts("s8", productMap8);

// Supplier 9 - Coffee Roastery
        LinkedHashMap<String,Integer> productMap9 = new LinkedHashMap<String, Integer>();
        productMap9.put("Espresso", 1);
        productMap9.put("Latte", 1);
        productMap9.put("Cold Brew", 2);
        supplierService.addProducts("s9", productMap9);

    }
}
