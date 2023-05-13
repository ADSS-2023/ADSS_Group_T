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
            System.out.println(
                            " \n 1.submit shift " +
                            " \n 2.show my shift status" +
                            " \n 3.exit ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {}
                case 2 -> {showMyShifts();}
                case 3 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void submitShift(){

    }

    public void showMyShifts(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee ID:");
        int id = scanner.nextInt();
        employeeService.getListOfSubmittion(id);
    }


}
