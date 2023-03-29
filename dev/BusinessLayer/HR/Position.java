package BusinessLayer.HR;

import java.util.ArrayList;
import java.util.List;


public class Position {
    private String positionName;


    public Position(PositionType positionType){
        this.positionName = positionType.name();
    }
    public enum PositionType {
        shift_manager, cashier, storekeeper, security, cleaning, orderly, general_worker;
    }



    public String getPositionName() {
        return positionName;
    }

    public void setPositionType(PositionType positionType) {
        this.positionName = positionType.name();
    }



    public List<String> getListOfTypesPosition() {
        List<String> positionTypes = new ArrayList<>();
        for (PositionType type : PositionType.values()) {
            positionTypes.add(type.name());
        }
        return positionTypes;
    }

    public PositionType getPositionType() {
        return PositionType.valueOf(positionName);
    }
}
