package DataLayer.HR_T_DAL.DTOs;
import DataLayer.Util.DTO;

import java.time.LocalTime;

public class TimeDTO extends DTO {

    private String currTime;

    public TimeDTO(String currTime){
        super("Time");
        this.currTime = currTime;
    }
    public static String getPKStatic(){return "time";}
    public static String getTableNameStatic(){return "Time";}
    public static int getIDStatic(){return 0;}

    public String getTime() {
        return this.currTime;
    }
}
