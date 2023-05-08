package PresentationLayer;

import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class TransportManagerPresentation {
    private final LogisticCenterService logisticCenterService;
    private final DeliveryService deliveryService;
    private final SupplierService supplierService;
    private final BranchService branchService;

    public TransportManagerPresentation(LogisticCenterService logisticCenterService, DeliveryService deliveryService, SupplierService supplierService, BranchService branchService) {
        this.logisticCenterService = logisticCenterService;
        this.deliveryService = deliveryService;
        this.supplierService = supplierService;
        this.branchService = branchService;
    }

    public void start() {
        mainWindow();
    }

    private void mainWindow() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" ");
            System.out.println("------ main window -------");
            System.out.println("Current date: " + deliveryService.getCurrDate());
            System.out.println("Please choose an option:");
            System.out.println("1. Skip day");
            System.out.println("2. Enter new delivery");
            System.out.println("3. Enter new truck");
            System.out.println("4. Enter new supplier");
            System.out.println("5. Enter new branch");
            System.out.println("6. Show logistic center products");
            System.out.println("7. show all Deliveries");
            System.out.println("8. add new products to supplier");
            System.out.println("9. ");
            System.out.println("10. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> skipDay();
                case 2 -> addNewDelivery();
                case 3 -> addNewTruck();
                case 4 -> addNewSupplier();
                case 5 -> addNewBranch();
                case 6 -> showProductsInStock();
                case 7 -> showAllDeliveries();
                case 8 -> addNewSupplierProducts();
                case 9 -> {
                }
                case 10 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // option 1

    /**
     * skip day and let user choose way of action in case of problem
     */
    private void skipDay() {
        System.out.println(deliveryService.getNextDayDetails());
        System.out.println(deliveryService.skipDay());
    }

    // option 2
    /**
     * add New Delivery to the system
     */
    private void addNewDelivery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter delivery details:");
        String destination = enterDeliveryDestination();
        LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersAndProducts = enterSuppliersAndProducts(deliveryService);
        String date = enterDeliveryDate(scanner);
        System.out.println(deliveryService.orderDelivery(destination, suppliersAndProducts, date));
    }

    // option 3
    /**
     * add new truck to the system
     */
    private void addNewTruck() {
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
        int coolingIndex = enterCoolingLevel();
        logisticCenterService.addTruck(licenseNumber, model, weight, maxWeight, coolingIndex);
    }

    // option 4
    /**
     * add new supplier to the system
     */
    private void addNewSupplier() {
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
        supplierService.addSupplier(address, telNumber, contactName, x, y);
    }

    // option 5
    /**
     * add new branch to the system
     */
    private void addNewBranch() {
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
        System.out.println(branchService.addBranch(address, telNumber, contactName, x, y));
    }

    // option 6
    private void showProductsInStock() {
        System.out.println(logisticCenterService.getProductsInStock());
    }

    //option 7
    private void showAllDeliveries() {
        System.out.println(this.deliveryService.showAllDeliveries());
    }

    //option 8
    private void addNewSupplierProducts() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Integer> products = new LinkedHashMap<>();
        System.out.println("Please enter supplier from the list:");
        supplierService.getAllSuppliers();
        String supplier = scanner.nextLine();

        int coolingLevel = 0;
        while (true) {
            System.out.print("Enter product name (enter 0 to finish): ");
            String productName = scanner.nextLine();
            if (productName.equals("0")) {
                break;
            }
            while (true) {
                System.out.print("Enter cooling level (1 - non, 2 - fridge, 3 - freezer): ");
                try {
                    coolingLevel = Integer.parseInt(scanner.nextLine());
                    if (coolingLevel >= 1 && coolingLevel <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid cooling level. Please enter a number between 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }
            products.put(productName, coolingLevel);
        }

        supplierService.addProducts(supplier, products);
    }




    //enter Data from keyboard
    private int enterCoolingLevel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the supplier cooling level:");
        System.out.println("1. Non");
        System.out.println("2. Fridge");
        System.out.println("3. Freezer");
        return scanner.nextInt();
    }

    private String enterDeliveryDestination() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter delivery details:");
        System.out.println("choose destination:");
        System.out.println(logisticCenterService.getAddress());
        System.out.println(branchService.getAllBranches() + "\n");
        return scanner.next();
    }

    /**
     * allow the user to select a supplier
     *
     * @param scanner
     * @param selectedSuppliers - A list of suppliers from which the user has to choose one
     * @return Supplier that the user choose
     */
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> enterSuppliersAndProducts(DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersAndProducts = new LinkedHashMap<>();
        System.out.println(supplierService.getAllSuppliers());
        while (true) {
            System.out.print("Enter supplier name name (or 0 to finish): ");
            String supplier = scanner.nextLine();
            if (supplier.equals("0") && !suppliersAndProducts.isEmpty()) {
                break;
            }
            if (suppliersAndProducts.containsKey(supplier)) {
                System.out.println(" supplier already selected. Please choose a different  supplier.");
                continue;
            }
            suppliersAndProducts.put(supplier, enterSupplierProducts(supplier, deliveryService));
        }
        return suppliersAndProducts;
    }

    /**
     * allow the user to select Products
     *
     * @param scanner
     * @param selectedSupplier - The supplier from which the user will choose products
     * @return map of product as key and amount of the product as value
     */
    private LinkedHashMap<String, Integer> enterSupplierProducts(String supplier, DeliveryService deliveryService) {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Integer> products = new LinkedHashMap<String, Integer>();
        System.out.println(supplierService.getSupplierProducts(supplier));

        while (true) {
            System.out.print("Enter product name (or 0 to finish): ");
            String productName = scanner.nextLine();
            if (productName.equals("0") && !products.isEmpty()) {
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
    private String enterDeliveryDate(Scanner scanner) {
        String year, month, day;
        System.out.println("Please enter the delivery date:");
        System.out.println("Please enter the year:");
        year = scanner.next();
        System.out.println("Please enter the month (1-12):");
        month = scanner.next();
        System.out.println("Please enter the day:");
        day = scanner.next();
        return (year + "-" + month + "-" + day);
    }

    //CallBack-Functions:
    public int enterWeightFunction(String address, int deliveryID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------- " + deliveryID + " -------");
        System.out.println("the truck in:" + address + "." +
                "\nthe folowing pruducts are loaded: " +
                "\n" + deliveryService.getLoadedProducts(deliveryID, address) +
                "\npls enter weight:");
        return scanner.nextInt();//products weight
    }

    public int enterOverWeightAction(int deliveryID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("There is overweight in delivery " + deliveryID + ".");
        System.out.println("Please choose an action to handle the overweight:");
        System.out.println("1.drop site");
        System.out.println("2.replace truck");
        System.out.println("3.unload products");
        return scanner.nextInt();//overweight action
    }

}
