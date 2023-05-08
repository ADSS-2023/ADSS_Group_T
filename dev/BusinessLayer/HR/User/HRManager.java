package BusinessLayer.HR.User;

import java.time.LocalDate;
import java.util.List;

public class HRManager extends User {
    public HRManager(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, LocalDate joiningDay, String password) {
        super(id, employeeName, bankAccount, description, salary, joiningDay, password,UserType.HRManager);
    }
}
