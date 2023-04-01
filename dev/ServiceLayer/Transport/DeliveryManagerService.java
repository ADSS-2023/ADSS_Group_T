
package ServiceLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Set;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryManagerService {
    private DeliveryController dc;
    private List<Site> sites;
    private HashMap<Site,List<Product>> suppliers;
    
    public DeliveryManagerService() {
        dc = new DeliveryController();
        //sites = new ArrayList<>();
       // suppliers = new HashMap<>();
        
       

    }
    public void init(){
         //---------- init drivers ----------//
        dc.addDriver(1001, "Driver1",LicenseType.C1,CoolingLevel.non);
        dc.addDriver(1002, "Driver2",LicenseType.C1,CoolingLevel.freezer);
        dc.addDriver(1003, "Driver3",LicenseType.C,CoolingLevel.fridge);
        dc.addDriver(1004, "Driver4",LicenseType.E,CoolingLevel.non);

        


        //---------- init trucks ----------//
        dc.addTruck(2001, "Truck1", 4000 , 8000, LicenseType.C1,CoolingLevel.freezer);
        dc.addTruck(2002, "Truck2", 8000, 13000, LicenseType.C1,CoolingLevel.fridge);
        dc.addTruck(2003, "Truck3", 12500, 20000, LicenseType.C,CoolingLevel.non);
        dc.addTruck(2004, "Truck4", 15000, 22000, LicenseType.C,CoolingLevel.freezer);
        dc.addTruck(2005, "Truck5", 20000, 30000, LicenseType.E,CoolingLevel.non);

       //---------- init branches ----------//
        dc.addBranch( new Branch("branch1", "000000001", "Contact B1", "Area 1"));
        dc.addBranch( new Branch("branch2", "000000002", "Contact B2", "Area 1"));
        dc.addBranch( new Branch("branch3", "000000003", "Contact B3", "Area 2"));
        dc.addBranch( new Branch("branch4", "000000004", "Contact B4", "Area 3"));
        

        
         //---------- init suppliers ----------//
        Site site_tnuva = new Supplier("Tnuva", "111111111", "Contact 1", CoolingLevel.fridge);
        Site site_bakery = new Supplier("Beverages", "22222222", "Contact 2", CoolingLevel.non);
        Site site_snacks = new Supplier("Snacks", "333333333", "Contact 3",CoolingLevel.non);
        Site site_beverages = new Supplier("Beverages", "444444444", "Contact 4", CoolingLevel.non);
        Site site_golda = new Supplier("Golda", "555555555", "Contact 5", CoolingLevel.freezer);
    
       
        
        

        // Create products
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
        Product product_mintChocolateChip = new Product("mint chocolate chip ice cream");
        Product product_cookiesAndCream = new Product("cookies and cream ice cream");
        Product product_strawberryCheesecake = new Product("strawberry cheesecake ice cream");
     

        // Create product lists for each supplier
        List<Product> tnuvaProducts = new ArrayList<>();
        tnuvaProducts.add(product_milk);
        tnuvaProducts.add(product_cheese);
        tnuvaProducts.add(product_eggs);
        List<Product> bakeryProducts = new ArrayList<>();
        bakeryProducts.add(product_bread);
        bakeryProducts.add(product_pita);
        bakeryProducts.add(product_cake);
        List<Product> snacksProducts = new ArrayList<>();
        snacksProducts.add(product_chocolate);
        snacksProducts.add(product_chips);
        snacksProducts.add(product_doritos);
        List<Product> beveragesProducts = new ArrayList<>();
        beveragesProducts.add(product_coke);
        beveragesProducts.add(product_sprite);
        beveragesProducts.add(product_fanta);
        beveragesProducts.add(product_fuzeTea);
        List<Product> goldaProducts = new ArrayList<>();
        goldaProducts.add(product_mintChocolateChip);
        goldaProducts.add(product_cookiesAndCream);
        goldaProducts.add(product_strawberryCheesecake);
       

        // Add product lists to suppliers map

        dc.addSupplier(site_tnuva, tnuvaProducts);
        dc.addSupplier(site_bakery, bakeryProducts);
        dc.addSupplier(site_snacks, snacksProducts);
        dc.addSupplier(site_beverages, beveragesProducts);
        dc.addSupplier(site_golda, goldaProducts);


        //if we add more its not update!!!!!!!!!!1!!!!!!
        this.sites = dc.getSites();
        this.suppliers = dc.getSuppliers();



    }
    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(" ");
            System.out.println("------ main window -------");
            System.out.println("Current date: " + dc.getCurrDate());
            System.out.println("Please choose an option:");
            System.out.println("1. Skip day");
            System.out.println("2. Enter new delivery");
            System.out.println("3. Enter new driver");
            System.out.println("4. Enter new truck");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            switch (choice) {
                case 1:
                    skipDay();
                    break;
                case 2:
                    addNewDelivery();
                    break;
                case 3:
                    addNewDriver();
                    break;
                case 4:
                    addNewTruck();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    // option 1
    void skipDay() {
        sites = dc.getSites();


        ArrayList<Delivery> deliveriesWithProblems = dc.skipDay();
        Scanner scanner = new Scanner(System.in);
        for (Delivery delivery : deliveriesWithProblems) {
            System.out.println("Delivery " + delivery.getId() + " has problems. Please choose a solution:");
            System.out.println("1. Switch/replace destination");
            System.out.println("2. Change truck");
            System.out.println("3. Remove products");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    dc.handleProblem(delivery.getId(), "switch/replace");
                    break;
                case 2:
                    dc.handleProblem(delivery.getId(), "change truck");
                    break;
                case 3:
                    dc.handleProblem(delivery.getId(), "remove product");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        
        start(); 
    }
    

    // option 2
    private void addNewDelivery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the delivery details:");
        Site destinationSite = chooseDestinationSite(scanner);
        HashMap<Site, HashMap<Product, Integer>> selectedProducts = selectProducts(scanner, destinationSite);
        LocalDate deliveryDate = chooseDeliveryDate(scanner);
        
    }
    
    private Site chooseDestinationSite(Scanner scanner) {
        System.out.println("\nPlease choose a destination site:");
        for (int i = 0; i < sites.size(); i++) {
            System.out.println((i + 1) + ". " + sites.get(i).getAddress());
        }
        int siteIndex = scanner.nextInt() - 1;
        if (siteIndex < 0 || siteIndex >= sites.size()) {
            System.out.println("Invalid site choice.");
            return null;
        }
        return sites.get(siteIndex);
    }
    
    private HashMap<Site, HashMap<Product, Integer>> selectProducts(Scanner scanner, Site destinationSite) {
        HashMap<Site, HashMap<Product, Integer>> selectedProducts = new HashMap<>();
        List<Site> selectedSuppliers = new ArrayList<>();
        HashMap<Site,Integer> weightOfOrder = new HashMap<>();
    
        while (true) {
            System.out.println("\nDo you want to add products from a supplier? (Y/N)");
            String supplierChoice = scanner.next();
            if (supplierChoice.equalsIgnoreCase("N")) {
                break;
            }
    
            Site selectedSupplier = chooseSupplier(scanner, selectedSuppliers, destinationSite);
            if (selectedSupplier == null) {
                break;
            }

            selectedSuppliers.add(selectedSupplier);
    
            HashMap<Product, Integer> supplierProducts = selectSupplierProducts(scanner, selectedSupplier);
            if (supplierProducts.isEmpty()) {
                break;
            }
    
            selectedProducts.put(selectedSupplier, supplierProducts);
    
            System.out.println("Enter weight of products from " + selectedSupplier.getAddress() + ":");
            int weight = scanner.nextInt();
            if (weight <= 0) {
                System.out.println("Invalid quantity.");
                break;
            }
            weightOfOrder.put(selectedSupplier, weight);
        }
    
        return selectedProducts;
    }
    
    

    private HashMap<Product, Integer> selectSupplierProducts(Scanner scanner, Site selectedSupplier) {
        System.out.println("\nPlease choose products from " + selectedSupplier.getAddress() + ":");
        List<Product> products = suppliers.get(selectedSupplier);
        if (products == null || products.isEmpty()) {
            System.out.println("No products available from " + selectedSupplier.getAddress() + ".");
            return new HashMap<>();
        }
        HashMap<Product, Integer> selectedProducts = new HashMap<>();
        while (true) {
            int productIndex = 0;
            for (Product product : products) {
                System.out.println((productIndex + 1) + ". " + product.getName());
                productIndex++;
            }
            int selectedProductIndex = scanner.nextInt() - 1;
            if (selectedProductIndex < 0 || selectedProductIndex >= productIndex) {
                System.out.println("Invalid product choice.");
                continue;
            }
            int quantity;
            while (true) {
                System.out.println("Enter quantity:");
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Invalid quantity.");
                    continue;
                }
                break;
            }
            Product selectedProduct = products.get(selectedProductIndex);
            selectedProducts.put(selectedProduct, quantity);
            System.out.println("Added " + quantity + " " + selectedProduct.getName() + "(s) to the order.");
            products.remove(selectedProduct); // Remove the selected product from the list
            if (products.isEmpty()) {
                break;
            }
            System.out.println("Do you want to add more products from " + selectedSupplier.getAddress() + "? (Y/N)");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
        return selectedProducts;
    }
    

    private Site chooseSupplier(Scanner scanner, List<Site> selectedSuppliers, Site destinationSite) {
        System.out.println("\nPlease choose a supplier:");
        int supplierIndex = 0;
        for (Site supplier : this.suppliers.keySet()) {
            if (selectedSuppliers.contains(supplier) || supplier.equals(destinationSite)) {
                continue; // Skip already selected and destination site
            }
            System.out.println((supplierIndex + 1) + ". " + supplier.getAddress());
            supplierIndex++;
        }
        if (supplierIndex == 0) {
            System.out.println("No more suppliers available.");
            return null;
        }
    
        int selectedSupplierIndex = scanner.nextInt() - 1;
        if (selectedSupplierIndex < 0 || selectedSupplierIndex >= supplierIndex) {
            System.out.println("Invalid supplier choice.");
            return null;
        }
    
        supplierIndex = 0;
        Site selectedSupplier = null;
        for (Site supplier : suppliers.keySet()) {
            if (selectedSuppliers.contains(supplier) || supplier.equals(destinationSite)) {
                continue; // Skip already selected and destination site
            }
            if (supplierIndex == selectedSupplierIndex) {
                selectedSupplier = supplier;
                break;
            }
            supplierIndex++;
        }
        if (selectedSupplier == null ) {
            System.out.println("Invalid supplier choice.");
        }
    
        return selectedSupplier;
    }

    private LocalDate chooseDeliveryDate(Scanner scanner) {
        // Get the delivery date
        int year, month, day;
        System.out.println("Please enter the delivery date:");
        while (true) {
            System.out.println("\nPlease enter the year:");
            year = scanner.nextInt();
            System.out.println("Please enter the month (1-12):");
            month = scanner.nextInt();
            if (month < 1 || month > 12) {
                System.out.println("Invalid month.");
                continue;
            }
            System.out.println("Please enter the day (1-" + LocalDate.of(year, month, 1).lengthOfMonth() + "):");
            day = scanner.nextInt();
            if (day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
                System.out.println("Invalid day.");
                continue;
            }
            LocalDate deliveryDate = LocalDate.of(year, month, day);
    
            if (!dc.checkDate(deliveryDate)) {
                System.out.println("Delivery date cannot be before today's date.");
                continue;
            }
    
            return deliveryDate;
        }
    }
    






    



    
    
    // option 3
    public void addNewDriver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the driver details:");
    
        // Get the driver ID
        System.out.println("Please enter the driver ID:");
        int driverId = scanner.nextInt();
        scanner.nextLine();
    
        // Get the driver name
        System.out.println("Please enter the driver name:");
        String driverName = scanner.nextLine();
    
        // Get the driver license type
        System.out.println("Please choose the driver license type:");
        System.out.println("1. C");
        System.out.println("2. C1");
        System.out.println("3. E");
        int licenseIndex = scanner.nextInt() - 1;
        if (licenseIndex < 0 || licenseIndex >= LicenseType.values().length) {
            System.out.println("Invalid license choice.");
            return;
        }
        LicenseType licenseType = LicenseType.values()[licenseIndex];

        
        System.out.println("Please choose the cooling level:");
        System.out.println("1. Non");
        System.out.println("2. Fridge");
        System.out.println("3. Freezer");
        int coolingIndex = scanner.nextInt() - 1;
        if (coolingIndex < 0 || coolingIndex >= CoolingLevel.values().length) {
            System.out.println("Invalid cooling choice.");
            return;
        }
        CoolingLevel coolingLevel = CoolingLevel.values()[coolingIndex];
        
    
        // Create the driver object
        if(dc.addDriver(driverId, driverName, licenseType,coolingLevel))
            System.out.println("Driver added successfully.");
        else
             System.out.println("Driver not added.");
        
        start(); // Return to main menu
    }



    // option 5
    void addNewTruck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the truck details:");
        
        System.out.println("Please enter the truck's license number:");
        int licenseNumber = scanner.nextInt();
    
        System.out.println("Please enter the truck's model:");
        String model = scanner.next();
    
        System.out.println("Please enter the truck's weight:");
        int weight = scanner.nextInt();
    
        System.out.println("Please enter the truck's maximum weight:");
        int maxWeight = scanner.nextInt();
    
        System.out.println("Please choose the truck license type:");
        System.out.println("1. C");
        System.out.println("2. C1");
        System.out.println("3. E");
        int licenseIndex = scanner.nextInt() - 1;
        if (licenseIndex < 0 || licenseIndex >= LicenseType.values().length) {
            System.out.println("Invalid license choice.");
            return;
        }
        LicenseType licenseType = LicenseType.values()[licenseIndex];
    
        System.out.println("Please choose the truck cooling level:");
        System.out.println("1. non");
        System.out.println("2. fridge");
        System.out.println("3. freezer");
        int coolingIndex = scanner.nextInt() - 1;
        if (coolingIndex < 0 || coolingIndex >= CoolingLevel.values().length) {
            System.out.println("Invalid cooling choice.");
            return;
        }
        CoolingLevel coolingLevel = CoolingLevel.values()[coolingIndex];

        if(dc.addTruck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel))
            System.out.println("New truck added successfully.");
        else
            System.out.println("truck not added.");
        start(); // return to main menu
    }















public String addNewTruck(int licenseNumber, String model, int weight,int maxWeight, int licenseIndex  ){
    return "not implemented yet";
}
public String addNewDriver(int id, String name, int licenseIndex){
    return "not implemented yet";
} 
public String addNewOrder(int id, String name, int licenseIndex){
    return "not implemented yet";
} 



    
    

    // /**
    //  * @return DeliveryController return the dc
    //  */
    // public DeliveryController getDc() {
    //     return dc;
    // }

    // /**
    //  * @param dc the dc to set
    //  */
    // public void setDc(DeliveryController dc) {
    //     this.dc = dc;
    // }

    // /**
    //  * @return LocalDate return the curDay
    //  */
    // public LocalDate getCurDay() {
    //     return curDay;
    // }

    // /**
    //  * @param curDay the curDay to set
    //  */
    // public void setCurDay(LocalDate curDay) {
    //     this.curDay = curDay;
    // }

    // /**
    //  * @return LogisticsCenter return the lc
    //  */
    // public LogisticsCenter getLc() {
    //     return lc;
    // }

    // /**
    //  * @param lc the lc to set
    //  */
    // public void setLc(LogisticsCenter lc) {
    //     this.lc = lc;
    // }

    // /**
    //  * @return List<Delivery> return the deliveries
    //  */
    // public List<Delivery> getDeliveries() {
    //     return deliveries;
    // }

    // /**
    //  * @param deliveries the deliveries to set
    //  */
    // public void setDeliveries(List<Delivery> deliveries) {
    //     this.deliveries = deliveries;
    // }

    // /**
    //  * @return List<Driver> return the drivers
    //  */
    // public List<Driver> getDrivers() {
    //     return drivers;
    // }

    // /**
    //  * @param drivers the drivers to set
    //  */
    // public void setDrivers(List<Driver> drivers) {
    //     this.drivers = drivers;
    // }

    // /**
    //  * @return List<Truck> return the trucks
    //  */
    // public List<Truck> getTrucks() {
    //     return trucks;
    // }

    // /**
    //  * @param trucks the trucks to set
    //  */
    // public void setTrucks(List<Truck> trucks) {
    //     this.trucks = trucks;
    // }

    // /**
    //  * @return ArrayList<Site> return the sites
    //  */
    // public ArrayList<Site> getSites() {
    //     return sites;
    // }

    // /**
    //  * @param sites the sites to set
    //  */
    // public void setSites(ArrayList<Site> sites) {
    //     this.sites = sites;
    // }

    // /**
    //  * @return ArrayList<Product> return the products
    //  */
    // public ArrayList<Product> getProducts() {
    //     return products;
    // }

    // /**
    //  * @param products the products to set
    //  */
    // public void setProducts(ArrayList<Product> products) {
    //     this.products = products;
    // }

    // /**
    //  * @return HashMap<Site,List<Product>> return the suppliers
    //  */
    // public HashMap<Site,List<Product>> getSuppliers() {
    //     return suppliers;
    // }

    // /**
    //  * @param suppliers the suppliers to set
    //  */
    // public void setSuppliers(HashMap<Site,List<Product>> suppliers) {
    //     this.suppliers = suppliers;
    // }

}





    


//     private void addNewDelivery() {

//         //suppliers = dc.getSuppliers();


//         Scanner scanner = new Scanner(System.in);
//         System.out.println("Please enter the delivery details:");
    
//         // Get the destination site
//         System.out.println("\nPlease choose a destination site:");
//         for (int i = 0; i < sites.size(); i++) {
//             System.out.println((i + 1) + ". " + sites.get(i).getName());
//         }
//         int siteIndex = scanner.nextInt() - 1;
//         if (siteIndex < 0 || siteIndex >= sites.size()) {
//             System.out.println("Invalid site choice.");
//             return;
//         }
//         Site destinationSite = sites.get(siteIndex);
    
//         // Select supplier and product
//         HashMap<Site, HashMap<Product, Integer>> selectedProducts = new HashMap<>();
// List<Site> selectedSuppliers = new ArrayList<>();
//         HashMap<Site,Integer> weightOfOrder = new HashMap<>();

// while (true) {
//     System.out.println("\nDo you want to add products from a supplier? (Y/N)");
//     String supplierChoice = scanner.next();
//     if (supplierChoice.equalsIgnoreCase("N")) {
//         break;
//     }
//     System.out.println("\nPlease choose a supplier:");
//     int supplierIndex = 0;
//     for (Site supplier : this.suppliers.keySet()) {
//         if (selectedSuppliers.contains(supplier) || supplier.equals(destinationSite)) {
//             continue; // Skip already selected and destination site
//         }
//         System.out.println((supplierIndex + 1) + ". " + supplier.getName());
//         supplierIndex++;
//     }
//     if (supplierIndex == 0) {
//         System.out.println("No more suppliers available.");
//         break;
//     }
//     int selectedSupplierIndex = scanner.nextInt() - 1;
//     if (selectedSupplierIndex < 0 || selectedSupplierIndex >= supplierIndex) {
//         System.out.println("Invalid supplier choice.");
//         continue;
//     }
//     supplierIndex = 0;
//     Site selectedSupplier = null;
//     for (Site supplier : suppliers.keySet()) {
//         if (selectedSuppliers.contains(supplier) || supplier.equals(destinationSite)) {
//             continue; // Skip already selected and destination site
//         }
//         if (supplierIndex == selectedSupplierIndex) {
//             selectedSupplier = supplier;
//             break;
//         }
//         supplierIndex++;
//     }
//     if (selectedSupplier == null ) {
//         System.out.println("Invalid supplier choice.");
//         continue;
//     }
//     selectedSuppliers.add(selectedSupplier);
//     HashMap<Product, Integer> supplierProducts = new HashMap<>();
//     List<Product> selectedProductsList = new ArrayList<>();
//     while (true) {
//         System.out.println("\nPlease choose a product from the list of Schlutz products:");
//         for (int i = 0; i < this.suppliers.get(selectedSupplier).size(); i++) {
//             Product product = this.suppliers.get(selectedSupplier).get(i);
//             if (selectedProductsList.contains(product)) {
//                 continue; // Skip already selected products
//             }
//             System.out.println((i + 1) + ". " + product.getName());
//         }
//         if (selectedProductsList.size() == this.suppliers.get(selectedSupplier).size()) {
//             System.out.println("You have already selected all products from " + selectedSupplier.getName() + ".");
//             System.out.println("Enter weight of products from " + selectedSupplier.getName() + ":");
//             int weight= scanner.nextInt();
//             if (weight <= 0) {
//                 System.out.println("Invalid quantity.");
//                 continue;
//             }
//             weightOfOrder.put(selectedSupplier, weight);
//             break;
//         }
//         int productIndex = scanner.nextInt() - 1;
//         if (productIndex < 0 || productIndex >= this.suppliers.get(selectedSupplier).size()) {
//             System.out.println("Invalid product choice.");
//             continue;
//         }
//         Product selectedProduct = this.suppliers.get(selectedSupplier).get(productIndex);
//         if (selectedProductsList.contains(selectedProduct)) {
//             System.out.println("You have already selected this product.");
//             continue;
//         }
//         System.out.println("Enter quantity for product " + selectedProduct.getName() + ":");
//         int quantity = scanner.nextInt();
//         if (quantity <= 0) {
//             System.out.println("Invalid quantity.");
//             continue;
//         }
//         supplierProducts.put(selectedProduct, quantity);
//         selectedProductsList.add(selectedProduct);
//         System.out.println("\nDo you want to add more products from " + selectedSupplier.getName() + "? (Y/N)");
//         String moreProductsChoice = scanner.next();
//         if (moreProductsChoice.equalsIgnoreCase("N")) {
//             System.out.println("Enter weight of products from " + selectedSupplier.getName() + ":");
//             int weight= scanner.nextInt();
//             if (weight <= 0) {
//                 System.out.println("Invalid quantity.");
//                 continue;
//             }
//             weightOfOrder.put(selectedSupplier, weight);
//             break;
//         }
//     }
//     selectedProducts.put(selectedSupplier, supplierProducts);
// }

      
    

    
    //     // Get the delivery date
    //     int year, month, day;
    //     System.out.println("Please enter the delivery date:");
    //     while (true) {
    //         System.out.println("\nPlease enter the year:");
    //         year = scanner.nextInt();
    //         System.out.println("Please enter the month (1-12):");
    //         month = scanner.nextInt();
    //         if (month < 1 || month > 12) {
    //             System.out.println("Invalid month.");
    //             continue;
    //         }
    //         System.out.println("Please enter the day (1-" + LocalDate.of(year, month, 1).lengthOfMonth() + "):");
    //         day = scanner.nextInt();
    //         if (day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
    //             System.out.println("Invalid day.");
    //             continue;
    //         }
    //         LocalDate deliveryDate = LocalDate.of(year, month, day);
    
    //         if (!dc.checkDate(deliveryDate)) {
    //             System.out.println("Delivery date cannot be before today's date.");
    //             continue;
    //         }
    
    //         // Order the delivery
    //         boolean success = dc.orderDelivery(destinationSite, selectedProducts, deliveryDate,null);
    //         // Print result to user and return to start
    //         if (success) {
    //             System.out.println("Delivery ordered successfully.");
    //         } else {
    //             System.out.println("Failed to order delivery.");
    //         }
    //         start();
    //         break;
    //     }
    
    //     // Do something with destinationSite, productQuantities, and deliveryDate
    
    //     start(); // Return to main menu
    // }
    