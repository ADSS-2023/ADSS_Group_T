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
                            " \n 1.submmit shift " +
                            " \n 2.show my shift status" +
                            " \n 3.exit ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }





    }

    public String addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password){return null;}

    public String getListOfSubmittion(int id){
        return "0";}

}
