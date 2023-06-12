package ServiceLayer.HR;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.ShiftController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.Time;

public class ShiftService {
    public ShiftController shiftController ;

    public ShiftService(ShiftController shiftController){
        this.shiftController = shiftController;
    }

    public String addShiftRequirements(String branch, LinkedHashMap<String, Integer> howMany, String date, String shiftType) {
        Response response = new Response();
        try {
            boolean bool = !shiftType.equals("e");
            shiftController.addRequirements(branch, Time.stringToLocalDate(date), bool, howMany);
            response.setReturnValue("succeed");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String getNotification() {
        Response response = new Response();
        try {
            HashMap<LocalDate, String> noti = shiftController.getNotifications(LocalDate.now(), LocalDate.now().plusDays(1));
            StringBuilder st = new StringBuilder();
            for (LocalDate date : noti.keySet()) {
                st.append(noti.get(date)).append("\n");
            }
            response.setReturnValue(st.toString());
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String ShowShiftStatus(String branch, String date, String shiftType) {
        Response response = new Response();
        try {
            boolean bool = !shiftType.equals("e");
            response.setReturnValue(shiftController.showShiftStatus(branch, Time.stringToLocalDate(date), bool));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }


    public String assignEmployeeForShift(String branch, int ans_id, String ans_date, String ans_type, String position) {
        Response response = new Response();
        try {
            boolean bool = !ans_type.equals("e");
            response.setReturnValue(shiftController.assignEmployeeForShift(branch, ans_id, Time.stringToLocalDate(ans_date), bool, position));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String assignAll(String branch, String ans_date, String ans_type) {
        Response response = new Response();
        try {
            boolean bool = !ans_type.equals("e");
            response.setReturnValue(shiftController.assignAll(branch, Time.stringToLocalDate(ans_date), bool));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String addDriverReq(String date, String licenseType, String cooling) {
        Response response = new Response();
        try {
            shiftController.addDriverRequirement(Time.stringToLocalDate(date), getByString(licenseType), getcoolingByString(cooling));
            response.setReturnValue("succeed");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
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





}
