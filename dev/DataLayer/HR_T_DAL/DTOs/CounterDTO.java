package DataLayer.HR_T_DAL.DTOs;
import DataLayer.Util.DTO;
public class CounterDTO extends DTO {
    private String counter;
    private String count;


public CounterDTO(){
    super("Counter");
}
    public CounterDTO(String counter, String count){
        super("Counter");
      this.counter = counter;
      this.count = count;
    }
    public static String getPKStatic(){return "counter";}
    public static String getTableNameStatic(){return "Counter";}

    public String getCounter() {
        return counter;
    }

    public String getCount() {
        return count;
    }
}
