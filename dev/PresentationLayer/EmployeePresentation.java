package PresentationLayer;

import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;

import java.util.Scanner;

public class EmployeePresentation {
    private EmployeeService employeeService;
    private ShiftService shiftService;
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
                case 1 -> {submitShift();}
                case 2 -> {showMyShifts();}
                case 3 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void submitShift(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee ID:");
        int id = scanner.nextInt();
        System.out.println("please choose shift date");
        String date = scanner.nextLine();
        System.out.println("please choose morning(m)/evening(e)");
        String shiftType = scanner.nextLine();
        System.out.println("choose Branch from the list:");
        String branch = scanner.nextLine();
        System.out.println(shiftService.ShowShiftStatus(branch,date,shiftType));
        employeeService.submitAsUser(id,date,shiftType,branch);
    }

    public void showMyShifts(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the employee ID:");
        int id = scanner.nextInt();
        employeeService.getListOfSubmittion(id);
    }

}
