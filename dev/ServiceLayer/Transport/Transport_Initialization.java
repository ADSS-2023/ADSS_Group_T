package ServiceLayer.Transport;




import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Transport_Initialization {
    private DeliveryService deliveryService;
    private LogisticCenterService logisticCenterService;
    public Transport_Initialization(DeliveryService deliveryService, LogisticCenterService logisticCenterService){
        this.deliveryService = deliveryService;
        this.logisticCenterService = logisticCenterService;
    }

    public static void init_data(LogisticCenterService logisticCenterService, DeliveryService deliveryService) {
       deliveryService.initLogisticCenterController(logisticCenterService);



        //---------- init drivers ----------//
        logisticCenterService.addDriver(1001, "Driver1", 2, 1);
        logisticCenterService.addDriver(1002, "Driver2", 2, 3);
        logisticCenterService.addDriver(1003, "Driver3", 1, 2);
        logisticCenterService.addDriver(1004, "Driver4", 3, 1);

        //---------- init trucks ----------//
        logisticCenterService.addTruck(2001, "Truck1", 4000 , 8000, 3);
        logisticCenterService.addTruck(2002, "Truck2", 8000, 13000, 2);
        logisticCenterService.addTruck(2003, "Truck3", 12500, 20000, 1);
        logisticCenterService.addTruck(2004, "Truck4", 15000, 22000, 3);
        logisticCenterService.addTruck(2005, "Truck5", 20000, 30000, 1);

        //---------- init branches ----------//
        deliveryService.addBranch("branch1", "000000001", "Contact B1", 1,2);
        deliveryService.addBranch("branch2", "000000002", "Contact B2", -3,5);
        deliveryService.addBranch("branch3", "000000003", "Contact B3", 1,-5);
        deliveryService.addBranch("branch4", "000000004", "Contact B4", -3,8);
        deliveryService.addBranch("branch5", "000000005", "Contact B5", 0,3);
        deliveryService.addBranch("branch6", "000000006", "Contact B6", 0,-10);

        //---------- Add product lists to suppliers map ----------//

// Supplier 1
        ArrayList<String> productList1 = new ArrayList<>();
        productList1.add("apples");
        productList1.add("bananas");
        productList1.add("grapes");
        productList1.add("pears");
        deliveryService.addSupplier("fruit paradise", "0501", "some name", 1, productList1,3,5);

// Supplier 2
        ArrayList<String> productList2 = new ArrayList<>();
        productList2.add("carrots");
        productList2.add("broccoli");
        productList2.add("celery");
        deliveryService.addSupplier("veggie kingdom", "0502", "some name", 1, productList2,-7,5);

// Supplier 3
        ArrayList<String> productList3 = new ArrayList<>();
        productList3.add("chicken");
        productList3.add("salmon");
        productList3.add("tilapia");
        productList3.add("shrimp");
        deliveryService.addSupplier("seafood palace", "0503", "some name", 2, productList3,8,4);

// Supplier 4
        ArrayList<String> productList4 = new ArrayList<>();
        productList4.add("milk");
        productList4.add("yogurt");
        productList4.add("cheese");
        deliveryService.addSupplier("dairy delight", "0504", "some name", 2, productList4,8,-1);

// Supplier 5
        ArrayList<String> productList5 = new ArrayList<>();
        productList5.add("bread");
        productList5.add("bagels");
        productList5.add("croissants");
        productList5.add("muffins");
        deliveryService.addSupplier("bakery bliss", "0505", "some name", 1, productList5,3,0);

// Supplier 6
        ArrayList<String> productList6 = new ArrayList<>();
        productList6.add("chocolate");
        productList6.add("candy");
        productList6.add("gum");
        deliveryService.addSupplier("sweet treats", "0506", "some name", 1, productList6,-9,-9);

// Supplier 7
        ArrayList<String> productList7 = new ArrayList<>();
        productList7.add("pasta");
        productList7.add("rice");
        productList7.add("quinoa");
        productList7.add("couscous");
        deliveryService.addSupplier("carb heaven", "0507", "some name", 1, productList7,-6,4);

// Supplier 8
        ArrayList<String> productList8 = new ArrayList<>();
        productList8.add("coffee beans");
        productList8.add("tea leaves");
        deliveryService.addSupplier("caffeine fix", "0508", "some name", 1, productList8,5,8);

// Supplier 9
        ArrayList<String> productList9 = new ArrayList<>();
        productList9.add("frozen pizza");
        productList9.add("frozen vegetables");
        deliveryService.addSupplier("frozen foods galore", "0509", "some name", 3, productList9,2,8);

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

//// Delivery 3
//        String branch3 = "branch3";
//        LinkedHashMap<String, LinkedHashMap<String, Integer>> products3 = new LinkedHashMap<>();
//        LinkedHashMap<String, Integer> supplierProducts5 = new LinkedHashMap<>();
//        supplierProducts5.put("bagels", 10);
//        supplierProducts5.put("croissants", 15);
//        supplierProducts5.put("muffins", 20);
//        products3.put("bakery bliss", supplierProducts5);
//        LinkedHashMap<String, Integer> supplierProducts6 = new LinkedHashMap<>();
//        supplierProducts6.put("chocolate", 5);
//        supplierProducts6.put("candy", 10);
//        supplierProducts6.put("gum", 3);
//        products3.put("sweet treats", supplierProducts6);
//        String date3 = "2023-01-02";
//        ts.orderDelivery(branch3, products3, date3);
//
//// Delivery 4
//        String branch4 = "branch4";
//        LinkedHashMap<String, LinkedHashMap<String, Integer>> products4 = new LinkedHashMap<>();
//        LinkedHashMap<String, Integer> supplierProducts7 = new LinkedHashMap<>();
//        supplierProducts7.put("pasta", 8);
//        supplierProducts7.put("rice", 10);
//        supplierProducts7.put("quinoa", 5);
//        supplierProducts7.put("couscous", 3);
//        products4.put("carb heaven", supplierProducts7);
//        LinkedHashMap<String, Integer> supplierProducts8 = new LinkedHashMap<>();
//        supplierProducts8.put("coffee beans", 5);
//        supplierProducts8.put("tea leaves", 10);
//        products4.put("caffeine fix", supplierProducts8);
//        String date4 = "2023-01-02";
//        ts.orderDelivery(branch4, products4, date4);
//
//// Delivery 5
//        String branch6 = "branch3";
//        LinkedHashMap<String, LinkedHashMap<String, Integer>> products6 = new LinkedHashMap<>();
//        LinkedHashMap<String, Integer> supplierProducts11 = new LinkedHashMap<>();
//        supplierProducts11.put("beef", 15);
//        supplierProducts11.put("pork", 10);
//        supplierProducts11.put("chicken", 20);
//        products6.put("meat market", supplierProducts11);
//        LinkedHashMap<String, Integer> supplierProducts12 = new LinkedHashMap<>();
//        supplierProducts12.put("potatoes", 20);
//        supplierProducts12.put("onions", 15);
//        supplierProducts12.put("carrots", 10);
//        supplierProducts12.put("garlic", 5);
//        products6.put("veggie haven", supplierProducts12);
//        String date6 = "2023-01-02";
//        ts.orderDelivery(branch6, products6, date6);
//
//// Delivery 7
//        String branch7 = "branch2";
//        LinkedHashMap<String, LinkedHashMap<String, Integer>> products7 = new LinkedHashMap<>();
//        LinkedHashMap<String, Integer> supplierProducts13 = new LinkedHashMap<>();
//        supplierProducts13.put("apples", 10);
//        supplierProducts13.put("pears", 8);
//        supplierProducts13.put("oranges", 12);
//        products7.put("fruit frenzy", supplierProducts13);
//        LinkedHashMap<String, Integer> supplierProducts14 = new LinkedHashMap<>();
//        supplierProducts14.put("milk", 15);
//        supplierProducts14.put("cheese", 10);
//        supplierProducts14.put("butter", 5);
//        supplierProducts14.put("yogurt", 8);
//        products7.put("dairy delight", supplierProducts14);
//        String date7 = "2023-01-02";
//        ts.orderDelivery(branch7, products7, date7);

    }
}
