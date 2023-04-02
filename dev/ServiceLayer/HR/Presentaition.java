package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;

import java.util.Scanner;

public class Presentaition {
    private EmployeeService employeeService;
    private ShifService shifService;

    public Presentaition (EmployeeService employeeService , ShifService shifService ) {
        this.employeeService = employeeService;
        this.shifService = shifService;

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
            while (repeat)
                repeat = false;
                System.out.println("" +
                        "manger menu - choose action number" +
                        " \n 1.approve constraint" +
                        " \n 2.check for shift problems" +
                        " \n 3.add restriction to employee" +
                        " \n 4.add employee qualification " +
                        " \n 5.show shift history" +
                        " \n 6.add shift requirements" +
                        " \n 7.exit ");
            int chosen_num = input.nextInt();
            switch (chosen_num) {
                case 1:
                    break;
                case 2:
                    //shifService.checkProblems();
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
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    repeat = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    break;
            }
        }
        else if (type.equals("e")){//employee menu
            System.out.println("employee menu - choose action number" +
                    " \n 1.add new constraint" +
                    " \n 2.watch your assigned shifts" +
                    " \n 3.watch your submitted constrains" +
                    " \n 4.exit");
        }
        else System.out.println(type);

    }
}
