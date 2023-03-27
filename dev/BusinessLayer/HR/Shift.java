package BusinessLayer.HR;

import java.util.Vector;

public class Shift {
    private int shiftId ;
    private String date;
    private int manegerID;
    private String shiftType;
    private Vector<Employee> employeesList;
    private Vector<Position> positionlist;
    public Shift (int shiftId,String date,int manegerID,String shiftType,Vector<Employee> employeesList,Vector<Position> positionlist){
        this.shiftId = shiftId;
        this.date=date;
        this.shiftType = shiftType;
        this.manegerID = manegerID;
        this.employeesList = employeesList;
        this.positionlist = positionlist;
    }
}
