//package ServiceLayer.Transport;
//import java.time.LocalDate;
//import java.util.Scanner;
//import java.util.LinkedHashMap;
//import BusinessLayer.Transport.*;
//import BusinessLayer.Transport.Driver.CoolingLevel;
//import BusinessLayer.Transport.Driver.LicenseType;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DeliveryManagerService {
//    private TransportService ts;
//    private  LinkedHashMap<Supplier,Integer> weightOfOrder;
//
//    public DeliveryManagerService() {
//        ts = new TransportService();
//        this.weightOfOrder = new LinkedHashMap<Supplier,Integer>();
//    }
//
//    public void init(){
//
//         //---------- init drivers ----------//
//        ts.addDriver(1001, "Driver1",LicenseType.C1,CoolingLevel.non);
//        ts.addDriver(1002, "Driver2",LicenseType.C1,CoolingLevel.freezer);
//        ts.addDriver(1003, "Driver3",LicenseType.C,CoolingLevel.fridge);
//        ts.addDriver(1004, "Driver4",LicenseType.E,CoolingLevel.non);
//
//        //---------- init trucks ----------//
//        ts.addTruck(2001, "Truck1", 4000 , 8000, LicenseType.C1,CoolingLevel.freezer);
//        ts.addTruck(2002, "Truck2", 8000, 13000, LicenseType.C1,CoolingLevel.fridge);
//        ts.addTruck(2003, "Truck3", 12500, 20000, LicenseType.C,CoolingLevel.non);
//        ts.addTruck(2004, "Truck4", 15000, 22000, LicenseType.C,CoolingLevel.freezer);
//        ts.addTruck(2005, "Truck5", 20000, 30000, LicenseType.E,CoolingLevel.non);
//
//       //---------- init branches ----------//
//        ts.addBranch( new Branch("branch1", "000000001", "Contact B1", "Area 1"));
//        ts.addBranch( new Branch("branch2", "000000002", "Contact B2", "Area 1"));
//        ts.addBranch( new Branch("branch3", "000000003", "Contact B3", "Area 2"));
//        ts.addBranch( new Branch("branch4", "000000004", "Contact B4", "Area 3"));
//
//         //---------- init suppliers ----------//
//        Supplier site_tnuva = new Supplier("Tnuva", "111111111", "Contact 1", CoolingLevel.fridge);
//        Supplier site_bakery = new Supplier("Bakery", "22222222", "Contact 2", CoolingLevel.non);
//        Supplier site_snacks = new Supplier("Snacks", "333333333", "Contact 3",CoolingLevel.non);
//        Supplier site_beverages = new Supplier("Beverages", "444444444", "Contact 4", CoolingLevel.non);
//        Supplier site_golda = new Supplier("Golda", "555555555", "Contact 5", CoolingLevel.freezer);
//
//        //---------- Create products ----------//
//        Product product_milk = new Product("milk");
//        Product product_cheese = new Product("cheese");
//        Product product_eggs = new Product("eggs");
//        Product product_coke = new Product("coke");
//        Product product_sprite = new Product("sprite");
//        Product product_fanta = new Product("fanta");
//        Product product_fuzeTea = new Product("fuzeTea");
//        Product product_chocolate = new Product("chocolate");
//        Product product_chips = new Product("chips");
//        Product product_doritos = new Product("doritos");
//        Product product_bread = new Product("bread");
//        Product product_pita = new Product("pita");
//        Product product_cake = new Product("cake");
//        Product product_mintChocolateChip = new Product("mint");
//        Product product_cookiesAntsream = new Product("cookies");
//        Product product_strawberryCheesecake = new Product("strawberry");
//
//        //---------- Create product lists for each supplier ----------//
//        ArrayList<Product> tnuvaProducts = new ArrayList<>();
//        tnuvaProducts.add(product_milk);
//        tnuvaProducts.add(product_cheese);
//        tnuvaProducts.add(product_eggs);
//        ArrayList<Product> bakeryProducts = new ArrayList<>();
//        bakeryProducts.add(product_bread);
//        bakeryProducts.add(product_pita);
//        bakeryProducts.add(product_cake);
//        ArrayList<Product> snacksProducts = new ArrayList<>();
//        snacksProducts.add(product_chocolate);
//        snacksProducts.add(product_chips);
//        snacksProducts.add(product_doritos);
//        ArrayList<Product> beveragesProducts = new ArrayList<>();
//        beveragesProducts.add(product_coke);
//        beveragesProducts.add(product_sprite);
//        beveragesProducts.add(product_fanta);
//        beveragesProducts.add(product_fuzeTea);
//        ArrayList<Product> goldaProducts = new ArrayList<>();
//        goldaProducts.add(product_mintChocolateChip);
//        goldaProducts.add(product_cookiesAntsream);
//        goldaProducts.add(product_strawberryCheesecake);
//
//        //---------- Add product lists to suppliers map ----------//
//        ts.addSupplier(site_tnuva, tnuvaProducts);
//        ts.addSupplier(site_bakery, bakeryProducts);
//        ts.addSupplier(site_snacks, snacksProducts);
//        ts.addSupplier(site_beverages, beveragesProducts);
//        ts.addSupplier(site_golda, goldaProducts);
//    }
//
//    public void start(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("------ START -------");
//        System.out.println("Please choose an option:");
//        System.out.println("1. start new pogram");
//        System.out.println("2. load old data");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//        if (choice == 1)
//            mainWindow();
//        if(choice == 2){
//            init();
//            mainWindow();
//        }
//    }
//
//     /**
//     * the main window of the system
//     */
//    public void mainWindow() {
//        Scanner scanner = new Scanner(System.in);
//        boolean running = true;
//        while (running) {
//            System.out.println(" ");
//            System.out.println("------ main window -------");
//            System.out.println("Current date: " + ts.getCurrDate());
//            System.out.println("Please choose an option:");
//            System.out.println("1. Skip day");
//            System.out.println("2. Enter new delivery");
//            System.out.println("3. Enter new driver");
//            System.out.println("4. Enter new truck");
//            System.out.println("5. Enter new supplier");
//            System.out.println("6. Enter new branch");
//            //System.out.println("7. Exit");
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//            switch (choice) {
//                case 1:
//                    skipDay();
//                    break;
//                case 2:
//                    addNewDelivery();
//                    break;
//                case 3:
//                    addNewDriver();
//                    break;
//                case 4:
//                    addNewTruck();
//                    break;
//                case 5:
//                    addNewSupplier();
//                    break;
//                case 6:
//                    addNewBranch();
//                    break;
//                // case 7:
//                //     running = false;
//                //     break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//                    break;
//            }
//        }
//    }
//
//    // option 1
//    /**
//     * skip day and let user choose way of action in case of problem
//     */
//    void skipDay() {
//        ArrayList<Integer> deliveriesWithProblems = ts.skipDay();
//        Scanner scanner = new Scanner(System.in);
//        if(deliveriesWithProblems != null){
//            for (int deliveryID : deliveriesWithProblems) {
//                System.out.println("Delivery " + deliveryID + " has problems. Please choose a solution:");
//                System.out.println("1. Switch/replace destination");
//                System.out.println("2. Change truck");
//                System.out.println("3. Remove products");
//                int choice = scanner.nextInt();
//                switch (choice) {
//                    case 1:
//                        ts.overWeightAction(deliveryID, 1);
//                        break;
//                    case 2:
//                        ts.overWeightAction(deliveryID, 2);
//                        break;
//                    case 3:
//                        ts.overWeightAction(deliveryID, 3);
//                        break;
//                    default:
//                        System.out.println("Invalid choice.");
//                        break;
//                }
//            }
//        }
//       mainWindow();
//    }
//
//    // option 2
//    /**
//     * add New Delivery to the system
//     */
//    private void addNewDelivery() {
//        if(ts.getBranches().isEmpty()){
//            System.out.println("no branches in the system, pls add at least one branch");
//            mainWindow();
//        }
//        if(ts.getSuppliers().isEmpty()){
//            System.out.println("no suppliers in the system, pls add at least one supplier");
//            mainWindow();
//        }
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the delivery details:");
//        Branch destinationSite = chooseDestinationSite(scanner);
//        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> selectedProducts = selectProducts(scanner);
//        LocalDate deliveryDate = chooseDeliveryDate(scanner);
//        ts.orderDelivery(destinationSite,selectedProducts , deliveryDate,weightOfOrder);
//    }
//
//     /**
//     * allow the user to select a destination branch
//     * @param scanner
//     * @return Branch that the user choose as desenation
//     */
//    private Branch chooseDestinationSite(Scanner scanner) {
//        System.out.println("\nPlease choose a destination site:");
//        for (int i = 0; i < ts.getBranches().size(); i++) {
//            System.out.println((i + 1) + ". " + ts.getBranches().get(i).getAddress());
//        }
//        int siteIndex = scanner.nextInt() - 1;
//        if (siteIndex < 0 || siteIndex >= ts.getBranches().size()) {
//            System.out.println("Invalid site choice.");
//            return null;
//        }
//        return ts.getBranches().get(siteIndex);
//    }
//
//      /**
//     * allow the user to select Products
//     * @param scanner
//     * @return map of supplier as key and map of products and amount of each product as value
//     */
//    private LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> selectProducts(Scanner scanner) {
//        LinkedHashMap<Supplier, LinkedHashMap<Product, Integer>> selectedProducts = new LinkedHashMap<>();
//        List<Supplier> selectedSuppliers = new ArrayList<>();
//        while (true) {
//            System.out.println("\nDo you want to add products from a supplier? (Y/N)");
//            String supplierChoice = scanner.next();
//            if (supplierChoice.equalsIgnoreCase("N")) {
//                break;
//            }
//
//            Supplier selectedSupplier = chooseSupplier(scanner, selectedSuppliers);
//            if (selectedSupplier == null) {
//                break;
//            }
//            selectedSuppliers.add(selectedSupplier);
//            LinkedHashMap<Product, Integer> supplierProducts = selectSupplierProducts(scanner, selectedSupplier);
//            if (supplierProducts.isEmpty()) {
//                break;
//            }
//            selectedProducts.put(selectedSupplier, supplierProducts);
//            System.out.println("Enter weight of products from " + selectedSupplier.getAddress() + ":");
//            int weight = scanner.nextInt();
//            if (weight <= 0) {
//                System.out.println("Invalid quantity.");
//                break;
//            }
//            this.weightOfOrder.put(selectedSupplier, weight);
//        }
//        return selectedProducts;
//    }
//
//      /**
//     * allow the user to select Products
//     * @param scanner
//     * @param selectedSupplier - The supplier from which the user will choose products
//     * @return map of product as key and amount of the product as value
//     */
//    private LinkedHashMap<Product, Integer> selectSupplierProducts(Scanner scanner, Site selectedSupplier) {
//        System.out.println("\nPlease choose products from " + selectedSupplier.getAddress() + ":");
//        List<Product> products = new ArrayList<>(ts.getSuppliers().get(selectedSupplier));
//        if (products == null || products.isEmpty()) {
//            System.out.println("No products available from " + selectedSupplier.getAddress() + ".");
//            return new LinkedHashMap<>();
//        }
//        LinkedHashMap<Product, Integer> selectedProducts = new LinkedHashMap<>();
//        while (true) {
//            int productIndex = 0;
//            for (Product product : products) {
//                System.out.println((productIndex + 1) + ". " + product.getName());
//                productIndex++;
//            }
//            int selectedProductIndex = scanner.nextInt() - 1;
//            if (selectedProductIndex < 0 || selectedProductIndex >= productIndex) {
//                System.out.println("Invalid product choice.");
//                continue;
//            }
//            int quantity;
//            while (true) {
//                System.out.println("Enter quantity:");
//                quantity = scanner.nextInt();
//                if (quantity <= 0) {
//                    System.out.println("Invalid quantity.");
//                    continue;
//                }
//                break;
//            }
//            Product selectedProduct = products.get(selectedProductIndex);
//            selectedProducts.put(selectedProduct, quantity);
//            System.out.println("Added " + quantity + " " + selectedProduct.getName() + "(s) to the order.");
//            products.remove(selectedProduct); // Remove the selected product from the list
//            if (products.isEmpty()) {
//                break;
//            }
//            System.out.println("Do you want to add more products from " + selectedSupplier.getAddress() + "? (Y/N)");
//            String choice = scanner.next();
//            if (choice.equalsIgnoreCase("N")) {
//                break;
//            }
//        }
//        return selectedProducts;
//    }
//
//     /**
//     * allow the user to select a supplier
//     * @param scanner
//     * @param selectedSuppliers - A list of suppliers from which the user has to choose one
//     * @return Supplier that the user choose
//     */
//    private Supplier chooseSupplier(Scanner scanner, List<Supplier> selectedSuppliers) {
//        System.out.println("\nPlease choose a supplier:");
//        int supplierIndex = 0;
//        for (Site supplier : ts.getSuppliers().keySet()) {
//            if (selectedSuppliers.contains(supplier) ) {
//                continue; // Skip already selected and destination site
//            }
//            System.out.println((supplierIndex + 1) + ". " + supplier.getAddress());
//            supplierIndex++;
//        }
//        if (supplierIndex == 0) {
//            System.out.println("No more suppliers available.");
//            return null;
//        }
//        int selectedSupplierIndex = scanner.nextInt() - 1;
//        if (selectedSupplierIndex < 0 || selectedSupplierIndex >= supplierIndex) {
//            System.out.println("Invalid supplier choice.");
//            return null;
//        }
//        supplierIndex = 0;
//        Supplier selectedSupplier = null;
//        for (Supplier supplier : ts.getSuppliers().keySet()) {
//            if (selectedSuppliers.contains(supplier) ) {
//                continue; // Skip already selected and destination site
//            }
//            if (supplierIndex == selectedSupplierIndex) {
//                selectedSupplier = supplier;
//                break;
//            }
//            supplierIndex++;
//        }
//        if (selectedSupplier == null ) {
//            System.out.println("Invalid supplier choice.");
//        }
//        return selectedSupplier;
//    }
//
//     /**
//     * allow the user enter date for delivery
//     * @param scanner
//     * @return LocalDate
//     */
//    private LocalDate chooseDeliveryDate(Scanner scanner) {
//        int year, month, day;
//        System.out.println("Please enter the delivery date:");
//        while (true) {
//            System.out.println("\nPlease enter the year:");
//            year = scanner.nextInt();
//            System.out.println("Please enter the month (1-12):");
//            month = scanner.nextInt();
//            if (month < 1 || month > 12) {
//                System.out.println("Invalid month.");
//                continue;
//            }
//            System.out.println("Please enter the day (1-" + LocalDate.of(year, month, 1).lengthOfMonth() + "):");
//            day = scanner.nextInt();
//            if (day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
//                System.out.println("Invalid day.");
//                continue;
//            }
//            LocalDate deliveryDate = LocalDate.of(year, month, day);
//
//            if (!ts.checkDate(deliveryDate)) {
//                System.out.println("Delivery date cannot be before today's date.");
//                continue;
//            }
//            return deliveryDate;
//        }
//    }
//
//    // option 3
//    /**
//     * add new driver to the system
//     */
//    public void addNewDriver() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the driver details:");
//        System.out.println("Please enter the driver ID:");
//        int driverId = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("Please enter the driver name:");
//        String driverName = scanner.nextLine();
//        System.out.println("Please choose the driver license type:");
//        System.out.println("1. C");
//        System.out.println("2. C1");
//        System.out.println("3. E");
//        int licenseIndex = scanner.nextInt() - 1;
//        if (licenseIndex < 0 || licenseIndex >= LicenseType.values().length) {
//            System.out.println("Invalid license choice.");
//            return;
//        }
//        LicenseType licenseType = LicenseType.values()[licenseIndex];
//        System.out.println("Please choose the cooling level:");
//        System.out.println("1. Non");
//        System.out.println("2. Fridge");
//        System.out.println("3. Freezer");
//        int coolingIndex = scanner.nextInt() - 1;
//        if (coolingIndex < 0 || coolingIndex >= CoolingLevel.values().length) {
//            System.out.println("Invalid cooling choice.");
//            return;
//        }
//        CoolingLevel coolingLevel = CoolingLevel.values()[coolingIndex];
//        if(ts.addDriver(driverId, driverName, licenseType,coolingLevel))
//            System.out.println("Driver added successfully.");
//        else
//             System.out.println("Driver not added.");
//       mainWindow();
//    }
//
//    // option 4
//     /**
//     * add new truck to the system
//     */
//    void addNewTruck() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the truck details:");
//        System.out.println("Please enter the truck's license number:");
//        int licenseNumber = scanner.nextInt();
//        System.out.println("Please enter the truck's model:");
//        String model = scanner.next();
//        System.out.println("Please enter the truck's weight:");
//        int weight = scanner.nextInt();
//        System.out.println("Please enter the truck's maximum weight:");
//        int maxWeight = scanner.nextInt();
//        System.out.println("Please choose the truck license type:");
//        System.out.println("1. C");
//        System.out.println("2. C1");
//        System.out.println("3. E");
//        int licenseIndex = scanner.nextInt() - 1;
//        if (licenseIndex < 0 || licenseIndex >= LicenseType.values().length) {
//            System.out.println("Invalid license choice.");
//            return;
//        }
//        LicenseType licenseType = LicenseType.values()[licenseIndex];
//        System.out.println("Please choose the truck cooling level:");
//        System.out.println("1. non");
//        System.out.println("2. fridge");
//        System.out.println("3. freezer");
//        int coolingIndex = scanner.nextInt() - 1;
//        if (coolingIndex < 0 || coolingIndex >= CoolingLevel.values().length) {
//            System.out.println("Invalid cooling choice.");
//            return;
//        }
//        CoolingLevel coolingLevel = CoolingLevel.values()[coolingIndex];
//        if(ts.addTruck(licenseNumber, model, weight, maxWeight, licenseType, coolingLevel))
//            System.out.println("New truck added successfully.");
//        else
//            System.out.println("truck not added.");
//       mainWindow();
//    }
//
//    // option 5
//     /**
//     * add new supplier to the system
//     */
//    public void addNewSupplier() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the supplier details:");
//        System.out.println("Please enter the supplier address:");
//        String supplierAddress = scanner.nextLine();
//        System.out.println("Please enter the supplier telephone number:");
//        String supplierTelNumber = scanner.nextLine();
//        System.out.println("Please enter the supplier contact name:");
//        String supplierContactName = scanner.nextLine();
//        System.out.println("Please choose the supplier cooling level:");
//        System.out.println("1. Non");
//        System.out.println("2. Fridge");
//        System.out.println("3. Freezer");
//        int coolingIndex = scanner.nextInt() - 1;
//        scanner.nextLine(); // consume newline character
//        if (coolingIndex < 0 || coolingIndex >= CoolingLevel.values().length) {
//            System.out.println("Invalid cooling choice.");
//            return;
//        }
//        CoolingLevel coolingLevel = CoolingLevel.values()[coolingIndex];
//        Supplier supplier = new Supplier(supplierAddress, supplierTelNumber, supplierContactName, coolingLevel);
//        ArrayList<Product> products = new ArrayList<Product>();
//        System.out.println("Please enter the product name (enter 0 to finish):");
//        String productName = scanner.nextLine();
//        while (!productName.equals("0")) {
//            Product product = new Product(productName);
//            products.add(product);
//            System.out.println("Please enter the product name (enter 0 to finish):");
//            productName = scanner.nextLine();
//        }
//        if (products.isEmpty()) {
//            System.out.println("Supplier must have at least one product");
//            return;
//        }
//        ts.addSupplier(supplier, products);
//        mainWindow();
//    }
//
//    // option 6
//     /**
//     * add new branch to the system
//     */
//    public void addNewBranch() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the branch details:");
//        System.out.println("Please enter the branch address:");
//        String branchAddress = scanner.nextLine();
//        System.out.println("Please enter the branch telephone number:");
//        String branchTelNumber = scanner.nextLine();
//        System.out.println("Please enter the branch contact name:");
//        String branchContactName = scanner.nextLine();
//        System.out.println("Please enetr the branch transport area:");
//        String branchShippingArea = scanner.nextLine();
//        Branch branch = new Branch(branchAddress, branchTelNumber, branchContactName, branchShippingArea);
//        ts.addBranch(branch);
//        mainWindow();
//    }
//
//}