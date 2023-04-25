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
    public void begin() {
        boolean exit_2 = true;
        Scanner input = new Scanner(System.in);
        int main_checker = 1000000;
        while (main_checker != 0) {
            System.out.println("MAIN MENU : please enter your ID number OR 0 to end session");
            int ans_id = input.nextInt();
            if (ans_id == 0){
                main_checker = 0;
            }
            else{
                System.out.println("please enter your password");
                String ans_password = input.next();
                String type = employeeService.login(ans_id,ans_password);
                if (type.equals("m")) {//manger menu
                    boolean repeat_2 = true;
                    while (repeat_2){
                        repeat_2 = false;
                        System.out.println("" +
                                "manger menu - choose action number" +
                                " \n 1.approve shift" +
                                " \n 2.add restriction to employee" +
                                " \n 3.add employee qualification " +
                                " \n 4.show shift history" +
                                " \n 5.add shift requirements" +
                                " \n 6.add new employee" +
                                " \n 7.exit ");
                        int chosen_num = input.nextInt();
                        switch (chosen_num) {

                            case 1:
                                System.out.println("Approve shift - enter date");
                                String ans_date_2 = input.next();
                                System.out.println("Approve shift - enter morning(m)/evening(e)");
                                String ans_type_2 = input.next();
                                System.out.println(shiftService.ShowShiftStatus(ans_date_2, ans_type_2));

                                System.out.println("\n 1. To approve the shift - enter 0");
                                System.out.println("\n 2. To approve submission for employee - enter id");

                                String ans_approve_2_1 = input.next();

                                if (ans_approve_2_1.equals("0")) {
                                    String ans_isLegal_2 = shiftService.approveShift(ans_date_2, ans_type_2);
                                    if (ans_isLegal_2.equals("0"))
                                        System.out.println("The shift has been successfully approved");
                                    else {
                                        System.out.println(ans_isLegal_2 + "\n ");
                                        System.out.println("\n 1. Enforce the approval anyway - enter 0");
                                        System.out.println("\n 2. To approve submission for employee - enter id");
                                        String ans_approve_2_2 = input.next();
                                        if (ans_approve_2_2.equals("0"))
                                            shiftService.approveShiftAnyWay(ans_date_2, ans_type_2);

                                        else {
                                            int id = Integer.parseInt(ans_approve_2_2);
                                            System.out.println("\n 3. To approve submission for employee - enter Position");
                                            String ans_position = input.next();
                                            System.out.println(shiftService.assignEmployeeForShift(id, ans_date_2, ans_type_2, ans_position));
                                        }
                                    }
                                }
                                else {
                                    int id = Integer.parseInt(ans_approve_2_1);
                                    System.out.println("\n 2. To approve submission for employee - enter Position");
                                    String ans_position = input.next();
                                    System.out.println(shiftService.assignEmployeeForShift(id, ans_date_2, ans_type_2, ans_position));
                                }
                                break;

                            case 2:
                                System.out.println("add restriction to employee - enter employee id");
                                int ans_employee_id_3 = input.nextInt();
                                System.out.println("add restriction to employee - enter date");
                                String ans_date_3 = input.next();
                                System.out.println("add restriction to employee - enter morning(m)/evening(e)");
                                String ans_type_3 = input.next();
                                if (employeeService.addRestrictionToEmployee(ans_employee_id_3,ans_date_3,ans_type_3) == null)
                                    System.out.print(ans_employee_id_3 + " restricted " + ans_date_3 + " successfully \n");
                                else
                                    System.out.print("restricted not approve");
                                break;
                            case 3:
                                System.out.println("add employee qualification - enter employee id");
                                int ans_employee_id_4 = input.nextInt();
                                System.out.println("add employee qualification - enter qualification");
                                String ans_quali_4 = input.next();
                                employeeService.addQualification(ans_employee_id_4,ans_quali_4);
                                break;
                            case 4:
                                System.out.println("show shift history - enter date");
                                String ans_date_5 = input.next();
                                System.out.println("show shift history - enter morning(m)/evening(e)");
                                String ans_type_5 = input.next();
                                System.out.println(shiftService.shiftHistory(ans_date_5,ans_type_5));
                                break;
                            case 5:
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
                            case 6:
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
                            case 7:
                                exit_2 = false;
                                break;
                            default:
                                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                                break;
                        }
                        repeat_2 = exit_2;
                    }
                }
                else if (type.equals("e")){//employee menu
                    boolean repeat = true;
                    while (repeat){
                        repeat = false;
                        System.out.println("employee menu - choose action number" +
                                " \n 1.submit shift " +
                                " \n 2.watch your submitted constrains" +
                                " \n 3.watch your assigned shifts" +
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
                                shiftService.addNewSubmittedPositionByEmployee(ans_id,ans_date_1,ans_type_1,ans_isTemp_1);
                                break;
                            case 2:
                                System.out.println(employeeService.getListOfSubmittedConstraints(ans_id));
                                break;
                            case 3:
                                System.out.println(employeeService.getListOfAssignedShifts(ans_id));
                                break;
                            case 4:
                                exit_2 = false;
                                break;
                            default:
                                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                                break;
                        }
                        repeat = exit_2;
                    }
                }
                else System.out.println(type);
            }
        }
    }
}