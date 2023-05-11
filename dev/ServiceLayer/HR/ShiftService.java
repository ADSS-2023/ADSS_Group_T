package ServiceLayer.HR;

import BusinessLayer.HR.ShiftController;

import java.time.LocalDate;
import java.util.HashMap;

public class ShiftService {
    public ShiftController shiftController ;

    public ShiftService(ShiftController shiftController){
        this.shiftController = shiftController;
    }

//    public String addShiftRequirements(String branch,  HashMap<String,Integer> howMany , String date , String shiftType){
//        Response res = new Response();
//        try
//        {
//            boolean bool = true;
//            if (shiftType.equals("e"))
//                bool = false;
//           shiftController.addRequirements(branch, date, bool,  howMany);
//        }
//        catch (Exception ex){
//            return ex.getMessage();
//        }
//        return "succeed";
//    }

    public String getNotification (){
        Response res = new Response();
        try
        {
           HashMap<LocalDate,String> noti = shiftController.getNotifications();
            for (LocalDate date : noti.keySet()) {

            }
        }
        catch (Exception ex){
            return ex.getMessage();
        }
        return "succeed";
    }




//    public String ShowShiftStatus(String branch, String date , String shiftType){
//        Response res = new Response();
//        try
//        {
//            boolean bool = true;
//            if (shiftType.equals("e"))
//                bool = false;
//            return shiftController.showShiftStatus(branch, date, bool);
//        }
//        catch (Exception ex){
//        }
//        return null;
//    }

//    public String assignEmployeeForShift(String branch,  int ans_id, String ans_date, String ans_type, String position){
//        Response res = new Response();
//        try
//        {
//            boolean bool = true;
//            if (ans_type.equals("e"))
//                bool = false;
//            return shiftController.assignEmployeeForShift(branch, ans_id, ans_date, bool, position  );
//        }
//        catch (Exception ex){
//        }
//        return null;
//    }

//    public String assignAll(String branch,  String ans_date, String ans_type){
//        Response res = new Response();
//        try
//        {
//            boolean bool = true;
//            if (ans_type.equals("e"))
//                bool = false;
//            return shiftController.assignAll(branch,  ans_date, bool);
//        }
//        catch (Exception ex){
//        }
//        return null;
//    }









//    public String submitShift(String branch, int id, String date, String type) {
//        Response res = new Response();
//        try {
//            boolean boolType = true;
//            if (type.equals("e"))
//                boolType = false;
//            boolean boolTemp = true;
//            if (temp.equals("p"))
//                boolTemp = false;
//            shiftController.submitShift(id,date,boolType,boolTemp);
//        } catch (Exception ex) {
//        }
//        return null;
//    }


}
