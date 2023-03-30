
package ServiceLayer.Transport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryManagerService {
    private LocalDate curDay;
    private LogisticsCenter lc;

    private List<Delivery> deliveries;
    private List<Driver> drivers;
    private List<Truck> trucks;
    private ArrayList<Site> sites;
    private List<Product> products;
    
    

    public DeliveryManagerService() {
        sites = new ArrayList<>();
        products = new ArrayList<>();

        curDay = LocalDate.of(2023, 1, 1);
        //---------- create Drivers ----------//
        Driver driver1 = new Driver(1001, "Driver1",LicenseType.C1);
        Driver driver2 = new Driver(1002,"Driver2", LicenseType.C);
        Driver driver3 = new Driver(1003,"Driver3", LicenseType.C);
        Driver driver4 = new Driver(1004,"Driver4", LicenseType.E);
        HashMap<Integer,Driver> hashMapDrivers = new HashMap<Integer,Driver>();
        hashMapDrivers.put(1001, driver1);
        hashMapDrivers.put(1002, driver2);
        hashMapDrivers.put(1003, driver3);

         //---------- create Trucks ----------//
        Truck truck1 = new Truck(2001, "Truck1", 2000, 4000, LicenseType.C1);
        Truck truck2 = new Truck(2002, "Truck2", 3000, 6000, LicenseType.C);
        Truck truck3 = new Truck(2003, "Truck3", 4500, 9000, LicenseType.E);
        HashMap<Integer, Truck> hashMapTrucks = new HashMap<Integer, Truck>();
        hashMapTrucks.put(2001, truck1);
        hashMapTrucks.put(2002, truck2);
        hashMapTrucks.put(2003, truck3);

         //---------- create Products ----------//
        Product product1 = new Product("Product 1");
        Product product2 = new Product("Product 2");
        Product product3 = new Product("Product 3");
        Product product4 = new Product("Product 4");
        Product product5 = new Product("Product 5");
        Product product6 = new Product("Product 6");

        HashMap<Product,Integer> hashMapProducts = new HashMap<Product,Integer>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);

        //---------- create Sites ----------//
        Site site1 = new Site("Address 1", "123456789", "Contact 1", "Area 1");
        Site site2 = new Site("Address 2", "987654321", "Contact 2", "Area 2");
        Site site3 = new Site("Address 3", "555555555", "Contact 3", "Area 3");
        sites.add(site1);
        sites.add(site2);
        sites.add(site3);
        //---------- create Files ----------//
        File file1 = new File(1);
        file1.addProduct(product1, 10);
        file1.addProduct(product2, 20);
        File file2 = new File(2);
        file2.addProduct(product3, 15);

        //---------- create Deliveries ----------//
        
        //hashMapDeliveries.put(site1, file1);
        //hashMapDeliveries.put(site2, file2);
       // HashMap<Site, File> destinations2 = new HashMap<>();
        //destinations2.put(site3, file1);
        //Delivery delivery1 = new Delivery(1, LocalDate.of(2023, 1, 1), LocalTime.of(8, 0), 5000, destinations1, site1, "Driver1", 1001);
        //Delivery delivery2 = new Delivery(2, LocalDate.of(2023, 1, 2), LocalTime.of(9, 0), 8000, destinations2, site3, "Driver2", 1002);

        this.lc = new LogisticsCenter(hashMapTrucks,hashMapProducts,hashMapDrivers);
        
        
    }
    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(" ");
            System.out.println("------ main window -------");
            System.out.println("Current date: " + curDay);
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
    
    void skipDay() {
        // Do something
        this.curDay = this.curDay.plusDays(1);
        start(); // Return to main menu
    }
    
   
    private void addNewDelivery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the delivery details:");
        // Get the destination site
    
        System.out.println("Please choose a destination site:");
        for (int i = 0; i < sites.size(); i++) {
            System.out.println((i+1) + ". " + sites.get(i).getName());
        }
        int siteIndex = scanner.nextInt() - 1;
        if (siteIndex < 0 || siteIndex >= sites.size()) {
            System.out.println("Invalid site choice.");
            return;
        }
        Site destinationSite = sites.get(siteIndex);
    
        Map<Product, Integer> productQuantities = new HashMap<>();
        while (productQuantities.size() < products.size()) {
            System.out.println("Please choose a product:");
            for (int i = 0; i < products.size(); i++) {
                if (productQuantities.containsKey(products.get(i))) {
                    continue; // Skip products that have already been chosen
                }
                System.out.println((i+1) + ". " + products.get(i).getName());
            }
            int productIndex = scanner.nextInt() - 1;
            if (productIndex < 0 || productIndex >= products.size() || productQuantities.containsKey(products.get(productIndex))) {
                System.out.println("Invalid product choice.");
                continue;
            }
            System.out.println("Please enter the quantity:");
            int quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }
            productQuantities.put(products.get(productIndex), quantity);
            System.out.println("Do you want to add another product? (y/n)");
            String answer = scanner.next();
            while (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Invalid input. Please enter 'y' or 'n':");
                answer = scanner.next();
            }
            if (answer.equals("n")) {
                break;
            }
        }
    
        // Get the delivery date
        int year, month, day;
        while (true) {
            System.out.println("Please enter the year:");
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
    
          
            if (deliveryDate.isBefore(this.curDay)) {
                System.out.println("Delivery date cannot be before today's date.");
                continue;
            }
            boolean success = lc.addNewOrder(destinationSite, productQuantities, deliveryDate);

            // Print result to user and return to start
            if (success) {
                System.out.println("Delivery ordered successfully.");
            } else {
                System.out.println("Failed to order delivery.");
            }
            start();
            break;
        }
    
        // Do something with destinationSite, productQuantities, and deliveryDate
    
        start(); // Return to main menu
    }
    
    public void addNewDriver(String id,String name, String license ){

    }
    
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
    
        // Create the driver object
        Driver newDriver = new Driver(driverId, driverName, licenseType);
        drivers.add(newDriver);
    
        System.out.println("Driver added successfully.");
    
        start(); // Return to main menu
    }
    
    void addNewTruck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the truck details:");
    
        // Get license number
        System.out.println("Please enter the truck's license number:");
        int licenseNumber = scanner.nextInt();
    
        // Get model
        System.out.println("Please enter the truck's model:");
        String model = scanner.next();
    
        // Get weight
        System.out.println("Please enter the truck's weight:");
        int weight = scanner.nextInt();
    
        // Get max weight
        System.out.println("Please enter the truck's maximum weight:");
        int maxWeight = scanner.nextInt();
    
        // Get license type
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
    
        // Get cooling level
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
    
        // Create and add truck to logistics company
        Truck newTruck = new Truck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel);
        lc.addNewTruck(newTruck);
    
        System.out.println("New truck added successfully.");
        start(); // Return to main menu
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



    
    
}