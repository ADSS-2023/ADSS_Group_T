
package PresentationLayer;

import ServiceLayer.HR.EmployeeService;

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
            int choice = scanner.nextInt();
            System.out.println(
                            " \n 1.submit shift" +
                            " \n 2.exit ");
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {submitShift();}
                case 2 -> {return;}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void submitShift(){

    }
}
