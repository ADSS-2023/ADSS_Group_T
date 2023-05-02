package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class Position extends DTO {

    private String positionName;

    public Position(String positionName) {
        super("Position");
        this.positionName = positionName;
    }
}
