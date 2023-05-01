package Initialization;

import ServiceLayer.Transport.DeliveryService;

import java.util.LinkedHashMap;

public class Delivery_init {

    public static void init(DeliveryService deliveryService){
        // Delivery 1
        String branch1 = "branch1";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("apples", 5);
        supplierProducts1.put("bananas", 10);
        supplierProducts1.put("grapes", 20);
        supplierProducts1.put("pears", 15);
        products1.put("fruit paradise", supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("carrots", 8);
        supplierProducts2.put("broccoli", 5);
        supplierProducts2.put("celery", 10);
        products1.put("veggie kingdom", supplierProducts2);
        String date1 = "2023-01-02";
        deliveryService.orderDelivery(branch1, products1, date1);

// Delivery 2
        String branch2 = "branch2";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products2 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts3 = new LinkedHashMap<>();
        supplierProducts3.put("chicken", 10);
        supplierProducts3.put("salmon", 5);
        supplierProducts3.put("tilapia", 3);
        supplierProducts3.put("shrimp", 8);
        products2.put("seafood palace", supplierProducts3);
        LinkedHashMap<String, Integer> supplierProducts4 = new LinkedHashMap<>();
        supplierProducts4.put("milk", 20);
        supplierProducts4.put("yogurt", 15);
        supplierProducts4.put("cheese", 10);
        products2.put("dairy delight", supplierProducts4);
        String date2 = "2023-01-02";
        deliveryService.orderDelivery(branch2, products2, date2);

    }
}
