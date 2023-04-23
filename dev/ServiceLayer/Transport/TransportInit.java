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
        ts.addDriver(1003, "Driver3", 2, 2);
        ts.addDriver(1004, "Driver4", 2, 1);

        //---------- init trucks ----------//
        ts.addTruck(2001, "Truck1", 4000 , 8000, 2,3);
        ts.addTruck(2002, "Truck2", 8000, 13000, 1,2);
        ts.addTruck(2003, "Truck3", 12500, 20000, 1,1);
        ts.addTruck(2004, "Truck4", 15000, 22000, 1,3);
        ts.addTruck(2005, "Truck5", 20000, 30000, 3,1);

        //---------- init branches ----------//
        ts.addBranch( new Branch("branch1", "000000001", "Contact B1", "Area 1"));
        ts.addBranch( new Branch("branch2", "000000002", "Contact B2", "Area 1"));
        ts.addBranch( new Branch("branch3", "000000003", "Contact B3", "Area 2"));
        ts.addBranch( new Branch("branch4", "000000004", "Contact B4", "Area 3"));



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



        String branch = "branch1";
        LinkedHashMap<String, LinkedHashMap<String, String>> products = new LinkedHashMap<>();
        LinkedHashMap<String, String> supplierProducts = new LinkedHashMap<>();
        supplierProducts.put("milk", "1");
        supplierProducts.put("cheese", "2");
        supplierProducts.put("eggs", "30");
        products.put("Tnuva", supplierProducts);
        String date = "2023-04-24";
        ts.orderDelivery(branch,products,date);

        branch = "branch2";
        products = new LinkedHashMap<>();
        supplierProducts = new LinkedHashMap<>();
        supplierProducts.put("bread", "1");
        supplierProducts.put("pita", "2");
        supplierProducts.put("cake", "30");
        products.put("Bakery",supplierProducts);
        date = "2023-04-24";
        ts.orderDelivery(branch,products,date);


    }
}
