package BusinessLayer.HR;

public class Constraint {
    private String date;

    private String shiftType;
    private boolean isTemp ;

    private boolean morningOrEveningl; //true-morning, false- evening
    private boolean isTemp ;

    public  Constraint(String date, boolean shiftType, boolean isTemp){
        this.date= date;
        this.morningOrEveningl = shiftType;
        this.isTemp =  isTemp;
    }

    public String getDate(){
        return  date;
    }

    public boolean getShiftType(){
        return  morningOrEveningl;
    }

    public boolean isTemp(){
        return  isTemp;
    }
}
