package PresentationLayer;

import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;

import java.util.Scanner;

public class HRManagerPresentation {

    private ShiftService shiftService;
    private EmployeeService employeeService;

    public HRManagerPresentation(ShiftService shiftService, EmployeeService employeeService){
        this.shiftService = shiftService;
        this.employeeService = employeeService;
    }

    public void start() {
        mainWindow();
    }
    private void mainWindow() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------ main window -------");
            System.out.println("Please choose an option:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                case 5 -> {}
                case 6 -> {}
                case 7 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // option 3

    /**
     * add new driver to the system
     */
    public void addNewDriver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the driver details:");
        System.out.println("Please enter the driver ID:");
        int driverId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the driver name:");
        String driverName = scanner.nextLine();
        int licenseIndex = getLicendeType();
        int coolingIndex = getCoolingLevel();
        //TODO //System.out.println(employeeService.addDriver(driverId,driverName,licenseIndex,coolingIndex));
    }

    private int getCoolingLevel(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the supplier cooling level:");
        System.out.println("1. Non");
        System.out.println("2. Fridge");
        System.out.println("3. Freezer");
        return scanner.nextInt();
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
