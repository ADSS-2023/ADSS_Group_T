package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class PositionDTO extends DTO {

    private String positionName;

    public PositionDTO(String positionName) {
        super("Position");
        this.positionName = positionName;
    }
}
