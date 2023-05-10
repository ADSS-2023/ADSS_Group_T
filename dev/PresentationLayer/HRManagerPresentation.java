package PresentationLayer;

import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import UtilSuper.Time;

import java.util.HashMap;
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
            System.out.println(
                                " \n 1.add new Employee" +
                                " \n 2.notification" +
                                " \n 3.add employee qualification " +
                                " \n 4.show shift status" +
                                " \n 5.add new driver" +
                                " \n 6.update employee" +
                                " \n 7.update driver" +
                                " \n 8.manage assign Employee for shift" +
                                " \n 9.submit shift for employee" +
                                " \n 10.submit shift for driver" +
                                " \n 11.add shift requirements" +
                                " \n 12.exit ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {addNewEmployee();}
                case 2 -> {notification();}
                case 3 -> {addQualification();}
                case 4 -> {ShowShiftStatus1();}
                case 5 -> {addNewDriver();}
                case 6 -> {updateEmployeeDetails1();}
                case 7 -> {updateDriverDetails1();}
                case 8 -> {}
                case 9 -> {assignEmployeeForShift1();}
                case 10 -> {assignEmployeeForShift1();}
                case 11 -> {addShiftRequirements1();}
                case 12 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void notification() {
    }

    private void assignEmployeeForShift1() {

    }

    private void addShiftRequirements1() {
    }

    private void updateDriverDetails1() {
    }

    private void updateEmployeeDetails1() {

    }

    private void ShowShiftStatus1() {
    }

    private void addQualification() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee details:");
        System.out.println("Please enter the employee ID:");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the employee new Qualification from those options:" +
                "\n"+"cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager");
        String quali = scanner.nextLine();
        employeeService.addQualification(employeeId,quali);
    }

    private void addNewEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee details:");
        System.out.println("Please enter the employee ID:");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the employee name:");
        String employeeName = scanner.nextLine();
        System.out.println("Please enter the employee bank account:");
        String employeebank = scanner.nextLine();
        System.out.println("Please enter the employee description:");
        String description = scanner.nextLine();
        System.out.println("Please enter the employee joining day:");
        String joiningDay = scanner.nextLine();
        System.out.println("Please enter the employee password:");
        String password = scanner.nextLine();
        System.out.println("Please enter the employee salary:");
        int salary = scanner.nextInt();
        employeeService.addNewEmployee(employeeId,employeeName,employeebank,description,salary, Time.stringToLocalDate(joiningDay),password);
    }

    // option 3
    /**
     * add new driver to the system
     */
    public void addNewDriver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the driver details:");
        System.out.println("Please enter the driver ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the driver name:");
        String Name = scanner.nextLine();
        System.out.println("Please enter the driver bank account:");
        String bank = scanner.nextLine();
        System.out.println("Please enter the driver description:");
        String description = scanner.nextLine();
        System.out.println("Please enter the driver joining day:");
        String joiningDay = scanner.nextLine();
        System.out.println("Please enter the driver password:");
        String password = scanner.nextLine();
        System.out.println("Please enter the driver salary:");
        int salary = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the driver cooling level by number: (1)=non (2)=fridge (3)=freezer");
        int coolinglevel = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the driver license type : C1 , C , E " );
        String licenseType = scanner.nextLine();
        employeeService.addNewDriver(id,Name,bank,description,salary,joiningDay,password,licenseType,coolinglevel);
    }
}
