package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;

import java.util.Scanner;

public class Presentaition {
    private EmployeeService employeeService;
    private ShiftService shiftService;

    public Presentaition (EmployeeService employeeService , ShiftService shiftService ) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;

    }
    public void begin(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Shefa-yissaschar : please enter your ID number");
        int ans_id = input.nextInt();
        System.out.println("please enter your password");
        String ans_password = input.next();
        String type = employeeService.login(ans_id,ans_password);
        if (type.equals("m")) {//manger menu
            boolean repeat = true;
            while (repeat){
                repeat = false;
                System.out.println("" +
                        "manger menu - choose action number" +
                        " \n 1.approve constraint" +
                        " \n 2.check for shift problems" +
                        " \n 3.add restriction to employee" +
                        " \n 4.add employee qualification " +
                        " \n 5.show shift history" +
                        " \n 6.add shift requirements" +
                        " \n 7.add new employee" +
                        " \n 8.exit ");
                int chosen_num = input.nextInt();
                switch (chosen_num) {
                    case 1:
                        break;
                    case 2:
                        //shiftService.checkProblems();
                        break;
                    case 3:
                        System.out.println("add restriction to employee - enter employee id");
                        int ans_employee_id_3 = input.nextInt();
                        System.out.println("add restriction to employee - enter date");
                        String ans_date_3 = input.next();
                        System.out.println("add restriction to employee - enter morning(m)/evening(e)");
                        String ans_type_3 = input.next();
                        employeeService.addRestrictionToEmployee(ans_employee_id_3,ans_date_3,ans_type_3);
                        repeat = true;
                        break;
                    case 4:
                        System.out.println("add employee qualification - enter employee id");
                        int ans_employee_id_4 = input.nextInt();
                        System.out.println("add employee qualification - enter qualification");
                        String ans_quali_4 = input.next();
                        //employeeService.
                        break;
                    case 5:
                        //shiftService
                        break;
                    case 6:
                        //shiftService
                        break;
                    case 7:
                        System.out.println("add new employee - enter employee id");
                        int ans_employee_id_7 = input.nextInt();
                        System.out.println("add new employee - enter name");
                        String ans_name_7 = input.next();
                        System.out.println("add new employee - enter bank account");
                        String ans_ba_7 = input.next();
                        System.out.println("add new employee - joining day");
                        String ans_joiningday_7 = input.next();
                        System.out.println("add new employee - enter new password");
                        String ans_pass_7 = input.next();
                        employeeService.addNewEmployee(ans_name_7,ans_ba_7,ans_joiningday_7,ans_employee_id_7,ans_pass_7,false);
                        break;
                    case 8:
                        repeat = true;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                     }
                }
        }
        else if (type.equals("e")){//employee menu
            boolean repeat = true;
            while (repeat){
                repeat = false;
                System.out.println("employee menu - choose action number" +
                        " \n 1.add new constraint" +
                        " \n 2.watch your assigned shifts" +
                        " \n 3.watch your submitted constrains" +
                        " \n 4.exit");
                int chosen_num = input.nextInt();
                switch (chosen_num) {
                    case 1:
                        break;
                    case 2:
                        //shiftService.checkProblems();
                        break;
                    case 3:
                        System.out.println("add restriction to employee - enter employee id");
                        int ans_employee_id_3 = input.nextInt();
                        System.out.println("add restriction to employee - enter date");
                        String ans_date_3 = input.next();
                        System.out.println("add restriction to employee - enter morning(m)/evening(e)");
                        String ans_type_3 = input.next();
                        employeeService.addRestrictionToEmployee(ans_employee_id_3,ans_date_3,ans_type_3);
                        repeat = true;
                        break;
                    case 4:
                        repeat = true;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                }
            }
        }
        else System.out.println(type);
    }
}
