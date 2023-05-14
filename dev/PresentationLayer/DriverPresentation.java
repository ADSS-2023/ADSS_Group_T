
package PresentationLayer;

import ServiceLayer.HR.EmployeeService;
import UtilSuper.Time;

import java.util.Scanner;

public class DriverPresentation {
    private EmployeeService employeeService;
    public DriverPresentation(EmployeeService employeeService){

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
                    " \n 1.submit shift" + " \n 2.logout ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {submitShift();}
                case 2 -> {return;}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void submitShift(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("please choose shift date");
        String date = scanner.nextLine();
        System.out.println(employeeService.submitShiftForDriver(Time.stringToLocalDate(date),id));
    }
}
