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
        ts.addDriver(1001, "Driver1", Driver.LicenseType.C1, Driver.CoolingLevel.non);
        ts.addDriver(1002, "Driver2", Driver.LicenseType.C1, Driver.CoolingLevel.freezer);
        ts.addDriver(1003, "Driver3", Driver.LicenseType.C, Driver.CoolingLevel.fridge);
        ts.addDriver(1004, "Driver4", Driver.LicenseType.E, Driver.CoolingLevel.non);

        //---------- init trucks ----------//
        ts.addTruck(2001, "Truck1", 4000 , 8000, Driver.LicenseType.C1, Driver.CoolingLevel.freezer);
        ts.addTruck(2002, "Truck2", 8000, 13000, Driver.LicenseType.C1, Driver.CoolingLevel.fridge);
        ts.addTruck(2003, "Truck3", 12500, 20000, Driver.LicenseType.C, Driver.CoolingLevel.non);
        ts.addTruck(2004, "Truck4", 15000, 22000, Driver.LicenseType.C, Driver.CoolingLevel.freezer);
        ts.addTruck(2005, "Truck5", 20000, 30000, Driver.LicenseType.E, Driver.CoolingLevel.non);

        //---------- init branches ----------//
        ts.addBranch( new Branch("branch1", "000000001", "Contact B1", "Area 1"));
        ts.addBranch( new Branch("branch2", "000000002", "Contact B2", "Area 1"));
        ts.addBranch( new Branch("branch3", "000000003", "Contact B3", "Area 2"));
        ts.addBranch( new Branch("branch4", "000000004", "Contact B4", "Area 3"));

        //---------- init suppliers ----------//
        Supplier site_tnuva = new Supplier("Tnuva", "111111111", "Contact 1", Driver.CoolingLevel.fridge);
        Supplier site_bakery = new Supplier("Bakery", "22222222", "Contact 2", Driver.CoolingLevel.fridge);
        Supplier site_snacks = new Supplier("Snacks", "333333333", "Contact 3", Driver.CoolingLevel.non);
        Supplier site_beverages = new Supplier("Beverages", "444444444", "Contact 4", Driver.CoolingLevel.non);
        Supplier site_golda = new Supplier("Golda", "555555555", "Contact 5", Driver.CoolingLevel.freezer);

        //---------- Create products ----------//
        Product product_milk = new Product("milk");
        Product product_cheese = new Product("cheese");
        Product product_eggs = new Product("eggs");
        Product product_coke = new Product("coke");
        Product product_sprite = new Product("sprite");
        Product product_fanta = new Product("fanta");
        Product product_fuzeTea = new Product("fuzeTea");
        Product product_chocolate = new Product("chocolate");
        Product product_chips = new Product("chips");
        Product product_doritos = new Product("doritos");
        Product product_bread = new Product("bread");
        Product product_pita = new Product("pita");
        Product product_cake = new Product("cake");
        Product product_mintChocolateChip = new Product("mint");
        Product product_cookiesAntsream = new Product("cookies");
        Product product_strawberryCheesecake = new Product("strawberry");

        //---------- Create product lists for each supplier ----------//
        ArrayList<Product> tnuvaProducts = new ArrayList<>();
        tnuvaProducts.add(product_milk);
        tnuvaProducts.add(product_cheese);
        tnuvaProducts.add(product_eggs);
        ArrayList<Product> bakeryProducts = new ArrayList<>();
        bakeryProducts.add(product_bread);
        bakeryProducts.add(product_pita);
        bakeryProducts.add(product_cake);
        ArrayList<Product> snacksProducts = new ArrayList<>();
        snacksProducts.add(product_chocolate);
        snacksProducts.add(product_chips);
        snacksProducts.add(product_doritos);
        ArrayList<Product> beveragesProducts = new ArrayList<>();
        beveragesProducts.add(product_coke);
        beveragesProducts.add(product_sprite);
        beveragesProducts.add(product_fanta);
        beveragesProducts.add(product_fuzeTea);
        ArrayList<Product> goldaProducts = new ArrayList<>();
        goldaProducts.add(product_mintChocolateChip);
        goldaProducts.add(product_cookiesAntsream);
        goldaProducts.add(product_strawberryCheesecake);

        //---------- Add product lists to suppliers map ----------//
        ts.addSupplier(site_tnuva, tnuvaProducts);
        ts.addSupplier(site_bakery, bakeryProducts);
        ts.addSupplier(site_snacks, snacksProducts);
        ts.addSupplier(site_beverages, beveragesProducts);
        ts.addSupplier(site_golda, goldaProducts);


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
