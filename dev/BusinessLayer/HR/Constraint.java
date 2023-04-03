package BusinessLayer.HR;

public class Constraint {
    private String date;
    private boolean morningOrEveningl; //true-morning, false- evening
    private boolean isTemp ;
    private boolean isAprrovoed;

    public  Constraint(String date, boolean shiftType, boolean isTemp){
        this.date= date;
        this.morningOrEveningl = shiftType;
        this.isTemp =  isTemp;
        this.isAprrovoed = false;
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
