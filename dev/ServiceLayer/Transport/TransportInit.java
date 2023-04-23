package ServiceLayer.Transport;

import BusinessLayer.Transport.Branch;
import BusinessLayer.Transport.Driver;
import BusinessLayer.Transport.Product;
import BusinessLayer.Transport.Supplier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransportInit {
    private TransportService ts;
    public TransportInit(TransportService ts){
        this.ts = ts;
    }
    public void init(){

        //---------- init drivers ----------//
        ts.addDriver(1001, "Driver1", 2, 1);
        ts.addDriver(1002, "Driver2", 2, 3);
        ts.addDriver(1003, "Driver3", 1, 2);
        ts.addDriver(1004, "Driver4", 3, 1);

        //---------- init trucks ----------//
        ts.addTruck(2001, "Truck1", 4000 , 8000, 3);
        ts.addTruck(2002, "Truck2", 8000, 13000, 2);
        ts.addTruck(2003, "Truck3", 12500, 20000, 1);
        ts.addTruck(2004, "Truck4", 15000, 22000, 3);
        ts.addTruck(2005, "Truck5", 20000, 30000, 1);

        //---------- init branches ----------//
        ts.addBranch( new Branch("branch1", "000000001", "Contact B1", "Area 1"));
        ts.addBranch( new Branch("branch2", "000000002", "Contact B2", "Area 1"));
        ts.addBranch( new Branch("branch3", "000000003", "Contact B3", "Area 2"));
        ts.addBranch( new Branch("branch4", "000000004", "Contact B4", "Area 2"));
        ts.addBranch( new Branch("branch5", "000000005", "Contact B5", "Area 3"));
        ts.addBranch( new Branch("branch6", "000000006", "Contact B6", "Area 3"));


        //---------- Add product lists to suppliers map ----------//

// Supplier 1
        ArrayList<String> productList1 = new ArrayList<>();
        productList1.add("apples");
        productList1.add("bananas");
        productList1.add("grapes");
        productList1.add("pears");
        ts.addSupplier("fruit paradise", "0501", "some name", 1, productList1);

// Supplier 2
        ArrayList<String> productList2 = new ArrayList<>();
        productList2.add("carrots");
        productList2.add("broccoli");
        productList2.add("celery");
        ts.addSupplier("veggie kingdom", "0502", "some name", 1, productList2);

// Supplier 3
        ArrayList<String> productList3 = new ArrayList<>();
        productList3.add("chicken");
        productList3.add("salmon");
        productList3.add("tilapia");
        productList3.add("shrimp");
        ts.addSupplier("seafood palace", "0503", "some name", 2, productList3);

// Supplier 4
        ArrayList<String> productList4 = new ArrayList<>();
        productList4.add("milk");
        productList4.add("yogurt");
        productList4.add("cheese");
        ts.addSupplier("dairy delight", "0504", "some name", 2, productList4);

// Supplier 5
        ArrayList<String> productList5 = new ArrayList<>();
        productList5.add("bread");
        productList5.add("bagels");
        productList5.add("croissants");
        productList5.add("muffins");
        ts.addSupplier("bakery bliss", "0505", "some name", 1, productList5);

// Supplier 6
        ArrayList<String> productList6 = new ArrayList<>();
        productList6.add("chocolate");
        productList6.add("candy");
        productList6.add("gum");
        ts.addSupplier("sweet treats", "0506", "some name", 1, productList6);

// Supplier 7
        ArrayList<String> productList7 = new ArrayList<>();
        productList7.add("pasta");
        productList7.add("rice");
        productList7.add("quinoa");
        productList7.add("couscous");
        ts.addSupplier("carb heaven", "0507", "some name", 1, productList7);

// Supplier 8
        ArrayList<String> productList8 = new ArrayList<>();
        productList8.add("coffee beans");
        productList8.add("tea leaves");
        ts.addSupplier("caffeine fix", "0508", "some name", 1, productList8);

// Supplier 9
        ArrayList<String> productList9 = new ArrayList<>();
        productList9.add("frozen pizza");
        productList9.add("frozen vegetables");
        ts.addSupplier("frozen foods galore", "0509", "some name", 3, productList9);

// Delivery 1
        String branch1 = "branch1";
        LinkedHashMap<String, LinkedHashMap<String, String>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("apples", "5");
        supplierProducts1.put("bananas", "10");
        supplierProducts1.put("grapes", "20");
        supplierProducts1.put("pears", "15");
        products1.put("fruit paradise", supplierProducts1);
        LinkedHashMap<String, String> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("carrots", "8");
        supplierProducts2.put("broccoli", "5");
        supplierProducts2.put("celery", "10");
        products1.put("veggie kingdom", supplierProducts2);
        String date1 = "2023-04-24";
        ts.orderDelivery(branch1, products1, date1);

// Delivery 2
        String branch2 = "branch2";
        LinkedHashMap<String, LinkedHashMap<String, String>> products2 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts3 = new LinkedHashMap<>();
        supplierProducts3.put("chicken", "10");
        supplierProducts3.put("salmon", "5");
        supplierProducts3.put("tilapia", "3");
        supplierProducts3.put("shrimp", "8");
        products2.put("seafood palace", supplierProducts3);
        LinkedHashMap<String, String> supplierProducts4 = new LinkedHashMap<>();
        supplierProducts4.put("milk", "20");
        supplierProducts4.put("yogurt", "15");
        supplierProducts4.put("cheese", "10");
        products2.put("dairy delight", supplierProducts4);
        String date2 = "2023-04-24";
        ts.orderDelivery(branch2, products2, date2);

// Delivery 3
        String branch3 = "branch3";
        LinkedHashMap<String, LinkedHashMap<String, String>> products3 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts5 = new LinkedHashMap<>();
        supplierProducts5.put("bagels", "10");
        supplierProducts5.put("croissants", "15");
        supplierProducts5.put("muffins", "20");
        products3.put("bakery bliss", supplierProducts5);
        LinkedHashMap<String, String> supplierProducts6 = new LinkedHashMap<>();
        supplierProducts6.put("chocolate", "5");
        supplierProducts6.put("candy", "10");
        supplierProducts6.put("gum", "3");
        products3.put("sweet treats", supplierProducts6);
        String date3 = "2023-04-24";
        ts.orderDelivery(branch3, products3, date3);

// Delivery 4
        String branch4 = "branch4";
        LinkedHashMap<String, LinkedHashMap<String, String>> products4 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts7 = new LinkedHashMap<>();
        supplierProducts7.put("pasta", "8");
        supplierProducts7.put("rice", "10");
        supplierProducts7.put("quinoa", "5");
        supplierProducts7.put("couscous", "3");
        products4.put("carb heaven", supplierProducts7);
        LinkedHashMap<String, String> supplierProducts8 = new LinkedHashMap<>();
        supplierProducts8.put("coffee beans", "5");
        supplierProducts8.put("tea leaves", "10");
        products4.put("caffeine fix", supplierProducts8);
        String date4 = "2023-04-24";
        ts.orderDelivery(branch4, products4, date4);

// Delivery 5
        String branch8 = "branch4";
        LinkedHashMap<String, LinkedHashMap<String, String>> products8 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts15 = new LinkedHashMap<>();
        supplierProducts15.put("pasta", "10");
        supplierProducts15.put("tomato sauce", "12");
        supplierProducts15.put("olive oil", "5");
        supplierProducts15.put("garlic", "3");
        products8.put("italian essentials", supplierProducts15);
        LinkedHashMap<String, String> supplierProducts16 = new LinkedHashMap<>();
        supplierProducts16.put("chocolate", "8");
        supplierProducts16.put("candy", "15");
        supplierProducts16.put("gum", "5");
        products8.put("sweet spot", supplierProducts16);
        String date8 = "2023-05-01";
        ts.orderDelivery(branch8, products8, date8);

// Delivery 6
        String branch6 = "branch3";
        LinkedHashMap<String, LinkedHashMap<String, String>> products6 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts11 = new LinkedHashMap<>();
        supplierProducts11.put("beef", "15");
        supplierProducts11.put("pork", "10");
        supplierProducts11.put("chicken", "20");
        products6.put("meat market", supplierProducts11);
        LinkedHashMap<String, String> supplierProducts12 = new LinkedHashMap<>();
        supplierProducts12.put("potatoes", "20");
        supplierProducts12.put("onions", "15");
        supplierProducts12.put("carrots", "10");
        supplierProducts12.put("garlic", "5");
        products6.put("veggie haven", supplierProducts12);
        String date6 = "2023-04-29";
        ts.orderDelivery(branch6, products6, date6);

// Delivery 7
        String branch7 = "branch2";
        LinkedHashMap<String, LinkedHashMap<String, String>> products7 = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts13 = new LinkedHashMap<>();
        supplierProducts13.put("apples", "10");
        supplierProducts13.put("pears", "8");
        supplierProducts13.put("oranges", "12");
        products7.put("fruit frenzy", supplierProducts13);
        LinkedHashMap<String, String> supplierProducts14 = new LinkedHashMap<>();
        supplierProducts14.put("milk", "15");
        supplierProducts14.put("cheese", "10");
        supplierProducts14.put("butter", "5");
        supplierProducts14.put("yogurt", "8");
        products7.put("dairy delight", supplierProducts14);
        String date7 = "2023-04-30";
        ts.orderDelivery(branch7, products7, date7);

    }
}
