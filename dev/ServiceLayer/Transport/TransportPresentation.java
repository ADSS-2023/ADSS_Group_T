package ServiceLayer.Transport;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.LinkedHashMap;
import BusinessLayer.Transport.*;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;

import java.util.ArrayList;
import java.util.List;

public class TransportPresentation {
    private TransportService ts;
    private TransportInit ti;

    public TransportPresentation() {
        ts = new TransportService();
        ti = new TransportInit(ts);
    }

    public void start(){
        this.callback();

        Scanner scanner = new Scanner(System.in);
        System.out.println("------ START -------");
        System.out.println("Please choose an option:");
        System.out.println("1. start new pogram");
        System.out.println("2. load old data");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1)
            mainWindow();
        if(choice == 2){
            ti.init();
            mainWindow();
        }
    }

    /**
     * the main window of the system
     */
    public void mainWindow() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
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
                    addNewSupplier();
                    break;
                case 6:
                    addNewBranch();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // option 1
    /**
     * skip day and let user choose way of action in case of problem
     */
    void skipDay() {
        ts.skipDay();
    }






    // option 2
    /**
     * add New Delivery to the system
     */
    private void addNewDelivery() {

    }


      /**
     * allow the user to select Products
     * @param scanner
     * @param selectedSupplier - The supplier from which the user will choose products
     * @return map of product as key and amount of the product as value
     */


     /**
     * allow the user to select a supplier
     * @param scanner
     * @param selectedSuppliers - A list of suppliers from which the user has to choose one
     * @return Supplier that the user choose
     */


     /**
     * allow the user enter date for delivery
     * @param scanner
     * @return LocalDate
     */
    private LocalDate chooseDeliveryDate(Scanner scanner) {
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

            return deliveryDate;
        }
    }

    // option 3
    /**
     * add new driver to the system
     */
    public void addNewDriver() {

    }

    // option 4
     /**
     * add new truck to the system
     */
    void addNewTruck() {

    }

    // option 5
     /**
     * add new supplier to the system
     */
    public void addNewSupplier() {

    }

    // option 6
     /**
     * add new branch to the system
     */
    public void addNewBranch() {

    }
    public void enterWeight(){}
    public void callback(){
        ts.tc.setListener(new TransportController.Listener() {
            public int enterWeightCallBack(String address, int id) {

                Scanner scanner = new Scanner(System.in);
                System.out.println("the truck in:" + address + "." +
                            "\nthe folowing pruducts are loaded: " +
                            "\n" + ts.getLoadedProducts(id, address) +
                            "\npls enter weight:");
                int productsWeight = scanner.nextInt();
                return productsWeight;
            }
        });
    }

}

//                String result =  ts.loadWeight(id,address,weight);
//                if (result.equals("false")) {
//                    System.out.println("Delivery " + id + " has problems. Please choose a solution:");
//                    System.out.println("1. Switch/replace destination");
//                    System.out.println("2. Change truck");
//                    System.out.println("3. Remove products");
//                    int choice = scanner.nextInt();
//                    switch (choice) {
//                        case 1:
//                            ts.overWeightAction(id, 1,address,weight);
//                            break;
//                        case 2:
//                            ts.overWeightAction(id, 2,address,weight);
//                            break;
//                        case 3:
//                            ts.overWeightAction(id, 3,address,weight);
//                            break;
//                        default:
//                            System.out.println("Invalid choice.");
//                            break;
//                    }
//                }
