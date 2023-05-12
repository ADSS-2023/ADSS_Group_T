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
                            " \n 1.add new Employee" +
                            " \n 2.notification" +
                            " \n 3.add employee qualification " +
                            " \n 4.show shift status" +
                            " \n 5.add new driver" +
                            " \n 6.manage assign Employee for shift" +
                            " \n 7.submit shift for employee" +
                            " \n 8.submit shift for driver" +
                            " \n 9.add shift requirements" +
                            " \n 10.exit ");
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

    public String addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password){return null;}

    public String getListOfSubmittion(int id){
        return "0";}

}
