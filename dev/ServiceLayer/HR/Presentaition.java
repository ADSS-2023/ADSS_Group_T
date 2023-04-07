package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import UtilSuper.PositionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Presentaition {
    private EmployeeService employeeService;
    private ShiftService shiftService;

    public Presentaition (EmployeeService employeeService , ShiftService shiftService ) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;

    }
    public void begin(){
        boolean exit = true;
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
                        " \n 2.approve shift" +
                        " \n 3.add restriction to employee" +
                        " \n 4.add employee qualification " +
                        " \n 5.show shift history" +
                        " \n 6.add shift requirements" +
                        " \n 7.add new employee" +
                        " \n 8.exit ");
                int chosen_num = input.nextInt();
                switch (chosen_num) {
                    case 2:
                        System.out.println("Approve shift - enter date");
                        String ans_date_2 = input.next();
                        System.out.println("Approve shift - enter morning(m)/evening(e)");
                        String ans_type_2 = input.next();
                        System.out.println(shiftService.ShowShiftStatus(ans_date_2,ans_type_2));

                        System.out.println("\n 1. To approve the shift - enter 0");
                        System.out.println("\n 2. To approve submission for emploteey - enter id");
                        String ans_approve_2_1 = input.next();
                        if (ans_approve_2_1.equals("0")){
                            String ans_isLegal_2 =  shiftService.approveShift(ans_date_2,ans_type_2);
                            if (ans_approve_2_1.equals("0"))
                                System.out.println("The shift has been successfuly approved");
                            else{
                                System.out.println(ans_isLegal_2 + "\n ");
                                System.out.println("\n 1. Enforce the approvement anyway - enter 0");
                                System.out.println("\n 2. To approve submission for employee - enter id");
                                String ans_approve_2_2 = input.next();
                                if (ans_approve_2_2.equals("0"))
                                    shiftService.approveShiftAnyWay(ans_date_2,ans_type_2);
                                else{
                                    int id = Integer.parseInt(ans_approve_2_2);
                                    System.out.println("\n 2. To approve submission for employee - enter Position");
                                    String ans_position= input.next();
                                    System.out.println(shiftService.assignEmployeeForShift(id,  ans_date_2, ans_type_2, ans_position));
                                }
                            }
                        }
                        else{
                            String ans_approve_2_2 = input.next();
                            int id = Integer.parseInt(ans_approve_2_2);
                            System.out.println("\n 2. To approve submission for employee - enter Position");
                            String ans_position= input.next();
                            System.out.println(shiftService.assignEmployeeForShift(id,  ans_date_2, ans_type_2, ans_position));
                        }
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
                        employeeService.addQualification(ans_employee_id_4,ans_quali_4);
                        break;
                    case 5:
                        System.out.println("show shift history - enter date");
                        String ans_date_5 = input.next();
                        System.out.println("show shift history - enter morning(m)/evening(e)");
                        String ans_type_5 = input.next();
                        System.out.println(shiftService.shiftHistory(ans_date_5,ans_type_5));
                        break;
                    case 6:
                        System.out.println("add shift requirements - choose shift date");
                        String ans_date_6 = input.next();
                        System.out.println("add shift requirements - enter morning(m)/evening(e)");
                        String ans_mORe_6 = input.next();
                        System.out.println("add shift requirements - shift :"+ans_date_6+ " , " + ans_mORe_6);
                        HashMap<String,Integer> howMany = new HashMap<>();
                        for (PositionType p : PositionType.values()) {
                            System.out.println("how many "+ p.name() + " for that shift ?" );
                            int ans_quantity = input.nextInt();
                            howMany.put(p.name(),ans_quantity);
                        }
                        System.out.println(shiftService.addShiftRequirements(howMany,ans_date_6,ans_mORe_6));
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
                        exit = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                     }
                repeat = exit;
                }
        }
        else if (type.equals("e")){//employee menu
            boolean repeat = true;
            while (repeat){
                repeat = false;
                System.out.println("employee menu - choose action number" +
                        " \n 1.submit shift " +
                        " \n 2.watch your assigned shifts" +
                        " \n 3.watch your submitted constrains" +
                        " \n 4.exit");
                int chosen_num = input.nextInt();
                switch (chosen_num) {
                    case 1:
                        System.out.println("submit shift - enter date");
                        String ans_date_1 = input.next();
                        System.out.println("submit shift - enter morning(m)/evening(e)");
                        String ans_type_1 = input.next();
                        System.out.println("submit shift - enter temp(t)/permanent(p)");
                        String ans_isTemp_1 = input.next();
                        System.out.println("submit shift - enter position");
                        String ans_position_1 = input.next();
                        shiftService.addNewSubmittedPositionByEmployee(ans_id,ans_date_1,ans_type_1,ans_isTemp_1,ans_position_1);
                        repeat = true;
                        break;
                    case 2:
                        System.out.println(employeeService.getListOfSubmittedConstraints(ans_id));
                        repeat = true;
                        break;
                    case 3:
                        System.out.println(employeeService.getListOfAssignedShifts(ans_id));
                        repeat = true;
                        break;
                    case 4:
                        repeat = true;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 4.");
                        break;
                }
                repeat = true;
            }
        }
        else System.out.println(type);
    }
}
