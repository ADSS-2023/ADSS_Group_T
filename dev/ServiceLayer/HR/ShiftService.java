package ServiceLayer.HR;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.ShiftController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

import UtilSuper.Response;
import UtilSuper.Time;

public class ShiftService {
    public ShiftController shiftController ;

    public ShiftService(ShiftController shiftController){
        this.shiftController = shiftController;
    }

    public String addShiftRequirements(String branch, LinkedHashMap<String, Integer> howMany , String date , String shiftType){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (shiftType.equals("e"))
                bool = false;
           shiftController.addRequirements(branch, Time.stringToLocalDate(date), bool,  howMany);
        }
        catch (Exception ex){
            return ex.getMessage();
        }
        return "succeed";
    }

    public String getNotification (){
        Response res = new Response();
        String st = "";
        try
        {
           HashMap<LocalDate,String> noti = shiftController.getNotifications(LocalDate.now(), LocalDate.now().plusDays(1));
            for (LocalDate date : noti.keySet()) {
                st += noti.get(date).toString();
            }
        }
        catch (Exception ex){
            return ex.getMessage();
        }
        return st;
    }

    public String ShowShiftStatus(String branch, String date , String shiftType){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (shiftType.equals("e"))
                bool = false;
            return shiftController.showShiftStatus(branch, Time.stringToLocalDate(date), bool);
        }
        catch (Exception ex){
        }
        return null;
    }

    public String assignEmployeeForShift(String branch,  int ans_id, String ans_date, String ans_type, String position){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (ans_type.equals("e"))
                bool = false;
            return shiftController.assignEmployeeForShift(branch, ans_id, Time.stringToLocalDate(ans_date), bool, position  );
        }
        catch (Exception ex){
        }
        return null;
    }

    public String assignAll(String branch,  String ans_date, String ans_type){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (ans_type.equals("e"))
                bool = false;
            return shiftController.assignAll(branch, Time.stringToLocalDate(ans_date) , bool);
        }
        catch (Exception ex){
        }
        return null;
    }

    public String addDriverReq(String date , String licenseType ,String cooling  ){
        Response res = new Response();
        try
        {
         shiftController.addDriverRequirement(Time.stringToLocalDate(date),getByString(licenseType),getcoolingByString(cooling));
        }
        catch (Exception ex){
        }
        return null;
    }
    public static Driver.LicenseType getByString (String licenseType ) {
        if (licenseType.equals("C1"))
            return Driver.LicenseType.C1;
        if (licenseType.equals("C"))
            return Driver.LicenseType.C;
        if (licenseType.equals("E"))
            return Driver.LicenseType.E;
        else
            return Driver.LicenseType.C1;
    }

    public static Driver.CoolingLevel getcoolingByString (String coolingLevel) {
        if (coolingLevel.equals("non"))
            return Driver.CoolingLevel.non;
        if (coolingLevel.equals("fridge"))
            return Driver.CoolingLevel.fridge;
        if (coolingLevel.equals("freezer"))
            return Driver.CoolingLevel.freezer;
        else
            return Driver.CoolingLevel.non;
    }


//    public String submitShift(String branch, int id, String date, String type) {
//        Response res = new Response();
//        try {
//            boolean boolType = true;
//            if (type.equals("e"))
//                boolType = false;
//            boolean boolTemp = true;
//            shiftController.submitShift(id,date,boolType,boolTemp);
//        } catch (Exception ex) {
//        }
//        return null;
//    }


}
