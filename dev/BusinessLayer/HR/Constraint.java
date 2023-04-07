package BusinessLayer.HR;

import UtilSuper.PositionType;

public class Constraint {
    private String date;
    private boolean morningOrEveningl; //true-morning, false- evening
    private boolean isTemp ;
    private PositionType position;
    private boolean isAssigned;

    public  Constraint(String date, boolean shiftType, boolean isTemp){
        this.date= date;
        this.morningOrEveningl = shiftType;
        this.isTemp =  isTemp;
        this.isAssigned = false;
    }

    public boolean isMorningOrEveningl() {
        return morningOrEveningl;
    }

    public PositionType getPosition() {
        return position;
    }

    public String getPositionByString() {
        return position.name();
    }

    public boolean isAssigned() {
        return isAssigned;
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
