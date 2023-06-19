package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class QualifiedPositionDTO extends DTO {

    private int employeeId;
    private String positionName;

    public QualifiedPositionDTO(int employeeId, String positionName) {
        super("QualifiedPosition");
        this.employeeId = employeeId;
        this.positionName = positionName;
    }

    public QualifiedPositionDTO() {
        super("QualifiedPosition");

    }
}
