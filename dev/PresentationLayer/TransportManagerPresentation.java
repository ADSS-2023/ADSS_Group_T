package PresentationLayer;

import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class TransportManagerPresentation {

    public static void start(LogisticCenterService logisticCenterService,DeliveryService deliveryService) {

    }
    private void mainWindow(LogisticCenterService logisticCenterService,DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" ");
            System.out.println("------ main window -------");
            //System.out.println("Current date: " + ts.getCurrDate());
            System.out.println("Please choose an option:");
            System.out.println("1. Skip day");
            System.out.println("2. Enter new delivery");
            System.out.println("3. Enter new driver");
            System.out.println("4. Enter new truck");
            System.out.println("5. Enter new supplier");
            System.out.println("6. Enter new branch");
            System.out.println("7. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> skipDay(deliveryService);
                case 2 -> addNewDelivery(deliveryService);
                case 3 -> addNewDriver(logisticCenterService);
                case 4 -> addNewTruck(logisticCenterService);
                case 5 -> addNewSupplier(deliveryService);
                case 6 -> addNewBranch(deliveryService);
                case 7 -> {return;}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    // option 1
    /**
     * skip day and let user choose way of action in case of problem
     */
    void skipDay(DeliveryService deliveryService) {
        System.out.println(deliveryService.getNextDayDeatails());
        System.out.println(deliveryService.skipDay());
    }

    // option 2
    /**
     * add New Delivery to the system
     */
    private void addNewDelivery(DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter delivery details:");
        String branch = getBranch(deliveryService);
        LinkedHashMap<String, LinkedHashMap<String,Integer>> suppliersAndProducts = getSuppliersAndProducts(deliveryService);
        String date = chooseDeliveryDate(scanner);
        System.out.println(deliveryService.orderDelivery(branch,suppliersAndProducts,date));
    }









    // option 3

    /**
     * add new driver to the system
     */
    public void addNewDriver(LogisticCenterService logisticCenterService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the driver details:");
        System.out.println("Please enter the driver ID:");
        int driverId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the driver name:");
        String driverName = scanner.nextLine();
        int licenseIndex = getLicendeType();
        int coolingIndex = getCoolingLevel();
        System.out.println(logisticCenterService.addDriver(driverId,driverName,licenseIndex,coolingIndex));
    }



    // option 4

    /**
     * add new truck to the system
     */
    void addNewTruck(LogisticCenterService logisticCenterService) {
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
        int coolingIndex = getCoolingLevel();
        logisticCenterService.addTruck(licenseNumber,model,weight,maxWeight,coolingIndex);

    }

    // option 5

    /**
     * add new supplier to the system
     */
    public void addNewSupplier(DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the supplier details:");
        System.out.print("Enter supplier address: ");
        String address = scanner.nextLine();
        System.out.print("Enter supplier telephone number: ");
        String telNumber = scanner.nextLine();
        System.out.print("Enter supplier contact name: ");
        String contactName = scanner.nextLine();
        System.out.print("Enter supplier X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter supplier Y coordinate: ");
        int y = scanner.nextInt();
        int coolingLevel = getCoolingLevel();
        ArrayList<String> productsList = new ArrayList<String>();
        while (true) {
            System.out.print("Please enter product name (0 to finish) ");
            String product = scanner.nextLine();
            if (product.equals("0")) {
                if (productsList.isEmpty()) {
                    System.out.println("You must enter at least one legal product name.");
                    continue;
                } else {
                    break;
                }
            }
            if (product.isEmpty()) {
                System.out.println("Product name cannot be empty. Please try again.");
                continue;
            }
            productsList.add(product);
        }
        deliveryService.addSupplier(address, telNumber, contactName,coolingLevel, productsList,x,y);
    }


    // option 6

    /**
     * add new branch to the system
     */
    public void addNewBranch(DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the delivery details:");
        System.out.print("Enter site address: ");
        String address = scanner.nextLine();
        System.out.print("Enter site telephone number: ");
        String telNumber = scanner.nextLine();
        System.out.print("Enter site contact name: ");
        String contactName = scanner.nextLine();
        System.out.print("Enter site X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter site Y coordinate: ");
        int y = scanner.nextInt();
        System.out.println(deliveryService.addBranch(address,telNumber,contactName,x,y));
    }


    private int enterWeightFunction(String address, int deliveryID,DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------- " + deliveryID + " -------");
        System.out.println("the truck in:" + address + "." +
                "\nthe folowing pruducts are loaded: " +
                "\n" + deliveryService.getLoadedProducts(deliveryID, address) +
                "\npls enter weight:");
        return scanner.nextInt();//product weight
    }

    private int enterWeightFunction(int deliveryID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("There is overweight in delivery " + deliveryID + ".");
        System.out.println("Please choose an action to handle the overweight:");
        System.out.println("1.drop site");
        System.out.println("2.replace truck");
        System.out.println("3.unload products");
        return scanner.nextInt();//overweight action
    }

    private int getCoolingLevel(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the supplier cooling level:");
        System.out.println("1. Non");
        System.out.println("2. Fridge");
        System.out.println("3. Freezer");
        int coolingLevel = scanner.nextInt();
        return coolingLevel;
    }
    private String getBranch(DeliveryService deliveryService){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter delivery details:");
        System.out.println("choose branch:");
        System.out.println(deliveryService.getAllBranches() + "\n");
        return scanner.next();
    }

    /**
     * allow the user to select a supplier
     * @param scanner
     * @param selectedSuppliers - A list of suppliers from which the user has to choose one
     * @return Supplier that the user choose
     */
    private  LinkedHashMap<String,LinkedHashMap<String,Integer>> getSuppliersAndProducts(DeliveryService deliveryService){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String,LinkedHashMap<String,Integer>> suppliersAndProducts = new LinkedHashMap<>();
        System.out.println(deliveryService.getAllSuppliersAddress());
        while (true) {
            System.out.print("Enter supplier name name (or 0 to finish): ");
            String  supplier = scanner.nextLine();
            if (supplier.equals("0") && !suppliersAndProducts.isEmpty()) {
                break;
            }
            if (suppliersAndProducts.containsKey(supplier)) {
                System.out.println(" supplier already selected. Please choose a different  supplier.");
                continue;
            }
            suppliersAndProducts.put(supplier,getSupplierProducts(supplier,deliveryService));
        }
        return suppliersAndProducts;
    }

    /**
     * allow the user to select Products
     * @param scanner
     * @param selectedSupplier - The supplier from which the user will choose products
     * @return map of product as key and amount of the product as value
     */
    private LinkedHashMap<String,Integer> getSupplierProducts(String supplier,DeliveryService deliveryService){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Integer> products = new LinkedHashMap<String, Integer>();
        System.out.println(deliveryService.getSupplierProducts(supplier));

        while (true) {
            System.out.print("Enter product name (or 0 to finish): ");
            String productName = scanner.nextLine();
            if (productName.equals("0")&& !products.isEmpty()) {
                break;
            }
            if (products.containsKey(productName)) {
                System.out.println("Product already selected. Please choose a different product.");
                continue;
            }
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume the remaining newline character
            if (quantity <= 0) {
                System.out.println("Invalid quantity. Please enter a positive number.");
                continue;
            }
            products.put(productName, quantity);
        }
        return products;
    }


    /**
     * allow the user enter date for delivery
     *
     * @param scanner
     * @return LocalDate
     */
    private String chooseDeliveryDate(Scanner scanner) {
        String year,month,day;
        System.out.println("Please enter the delivery date:");
        System.out.println("Please enter the year:");
        year = scanner.next();
        System.out.println("Please enter the month (1-12):");
        month = scanner.next();
        System.out.println("Please enter the day:");
        day = scanner.next();
        return (year + "-" + month + "-" + day);
    }
    private int getLicendeType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the driver license type:");
        System.out.println("1. C1");
        System.out.println("2. C");
        System.out.println("3. E");
        int licenseIndex = scanner.nextInt();
        return licenseIndex;
    }

}
