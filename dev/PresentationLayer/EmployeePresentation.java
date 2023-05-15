package PresentationLayer;

import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.Time;

import java.util.Scanner;

public class EmployeePresentation {
    private EmployeeService employeeService;
    private BranchService branchService;
    private int id;
    public EmployeePresentation(EmployeeService employeeService, BranchService branchService){
        this.branchService = branchService;
        this.employeeService = employeeService;
    }
    public void start(int id) {
        this.id = id;
        mainWindow();
    }
    private void mainWindow() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------ Employee main window -------");
            System.out.println("Please choose an option:");
            System.out.println(
                            " \n 1.submmit shift " +
                            " \n 2.logout ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {submitShift();}
                case 2 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void submitShift(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please choose shift date");
        String date = scanner.nextLine();
        System.out.println("please choose morning(m)/evening(e)");
        String shiftType = scanner.nextLine();
        System.out.println("choose Branch from the list:");
        Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
        if (response.isError())
            System.out.println(response.getErrorMessage());
        else {
            System.out.println(response.getReturnValue());
        }
        String branch = scanner.nextLine();
        System.out.println(employeeService.submitShiftForEmployee(branch,id, Time.stringToLocalDate(date),shiftType));
    }

//    public void showMyShifts(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the employee ID:");
//        int id = scanner.nextInt();
//        employeeService.getListOfSubmittion(id);
//    }

}
