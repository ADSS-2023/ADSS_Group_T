package PresentationLayer;

import BusinessLayer.HR.User.PositionType;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import UtilSuper.Time;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import UtilSuper.Response;
public class HRManagerPresentation {

    private ShiftService shiftService;
    private EmployeeService employeeService;

    private String branch;

    public HRManagerPresentation(ShiftService shiftService, EmployeeService employeeService){
        this.shiftService = shiftService;
        this.employeeService = employeeService;
    }

    public void start(String branch) {
        this.branch = branch;
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
                                " \n 7.add shift requirements" +
                                " \n 8.exit ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> {addNewEmployee();}
                case 2 -> {notification();}
                case 3 -> {addQualification();}
                case 4 -> {ShowShiftStatus();}
                case 5 -> {addNewDriver();}
                case 6 -> {manageAssignEmployeeForShift();}
                case 7 -> {addShiftRequirements();}
                case 8 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void notification() {
        System.out.println("notification : ");
        shiftService.getNotification();
    }

    private void manageAssignEmployeeForShift() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("assign employee for shift : ");
        System.out.println("Please enter the shift details:");
        System.out.println("assign employee for shift - choose shift date");
        String date = scanner.nextLine();
        System.out.println("assign employee for shift - enter morning(m)/evening(e)");
        String shiftType = scanner.nextLine();
        shiftService.ShowShiftStatus(branch,date,shiftType);
        System.out.println("assign employee for shift - would you like to approve all? y/n");
        String ansForApproveAll = scanner.nextLine();
        if(ansForApproveAll.equals("y")){
            shiftService.assignAll(branch,date,shiftType);
        }

        else {
            while(true){
                System.out.println("assign employee for shift - Please enter the employee ID:");
                int employeeId = scanner.nextInt();
                System.out.println("assign employee for shift - choose position foe employee : "+employeeId);
                String position = scanner.nextLine();
                shiftService.assignEmployeeForShift(branch,employeeId,date,shiftType,position);
                System.out.println("assign employee for shift - would you like to assign more? y/n");
                String ansForContinue = scanner.nextLine();
                if (ansForContinue.equals("n")){return;}
            }
        }
    }

    private void addShiftRequirements() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add shift requirements");
        System.out.println("Please enter the shift details:");
        System.out.println("add shift requirements - choose shift date");
        String date = scanner.nextLine();
        System.out.println("add shift requirements - enter morning(m)/evening(e)");
        String shiftType = scanner.nextLine();
        System.out.println("add shift requirements - shift :" +date+ " , " + shiftType);
        LinkedHashMap<String,Integer> howMany = new LinkedHashMap<>();
        for (PositionType p : PositionType.values()) {
            System.out.println("how many "+ p.name() + " for that shift ?" );
            int ans_quantity = scanner.nextInt();
            howMany.put(p.name(),ans_quantity);
        }
        System.out.println(shiftService.addShiftRequirements("super",howMany,date,shiftType));
    }

    private void ShowShiftStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("shift status");
        System.out.println("Please enter the shift details:");
        System.out.println("shift status - choose shift date");
        String date = scanner.nextLine();
        System.out.println("shift status - enter morning(m)/evening(e)");
        String shiftType = scanner.nextLine();
        System.out.println(shiftService.ShowShiftStatus("super",date,shiftType));
    }

    private void addQualification() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add qualification to employee");
        System.out.println("Please enter the employee details:");
        System.out.println("Please enter the employee ID:");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the employee new Qualification from those options:" +
                "\n"+"cashier, storekeeper, security, cleaning, orderly, general_worker, shiftManager");
        String quali = scanner.nextLine();
        System.out.println(employeeService.addQualification(employeeId,quali));
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
        System.out.println(employeeService.addNewEmployee(employeeId,employeeName,employeebank,description,salary, joiningDay ,password));
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
