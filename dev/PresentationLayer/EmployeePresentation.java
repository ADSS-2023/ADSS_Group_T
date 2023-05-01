package PresentationLayer;

import ServiceLayer.HR.EmployeeService;

import java.util.Scanner;

public class EmployeePresentation {
    private EmployeeService employeeService;
    public EmployeePresentation(EmployeeService employeeService){
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

}
