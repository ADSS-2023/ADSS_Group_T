package Initialization;

import ServiceLayer.Transport.DeliveryService;

import java.util.LinkedHashMap;

public class Delivery_init {

    public static void initDelivery(DeliveryService deliveryService){
        // Delivery 1
        String branch1 = "branch1";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("Beef", 5);
        supplierProducts1.put("Pork", 10);
        supplierProducts1.put("Chicken", 20);
        products1.put("John's Butcher Shop", supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("Bread", 8);
        supplierProducts2.put("Cupcake", 5);
        products1.put("Sweet Buns Bakery", supplierProducts2);
        String date1 = "2023-01-02";
        deliveryService.orderDelivery(branch1, products1, date1);

// Delivery 2
        String branch2 = "branch2";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products2 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts3 = new LinkedHashMap<>();
        supplierProducts3.put("Salmon", 10);
        supplierProducts3.put("Shrimp", 5);
        supplierProducts3.put("Tuna", 3);
        products2.put("Sea to Table", supplierProducts3);
        LinkedHashMap<String, Integer> supplierProducts4 = new LinkedHashMap<>();
        supplierProducts4.put("Apple", 20);
        supplierProducts4.put("Orange", 15);
        supplierProducts4.put("Mango", 10);
        products2.put("Fresh Fruits and More", supplierProducts4);
        String date2 = "2023-01-02";
        deliveryService.orderDelivery(branch2, products2, date2);

    }
}
