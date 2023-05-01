package BusinessLayer.Transport;

import BusinessLayer.User;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.List;

public class TransportManager extends User {
    public TransportManager(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String description, int salary, String joiningDay, String password, UserType userType) {
        super(id, employeeName, bankAccount, qualifiedPositions, description, salary, joiningDay, password, userType);
    }
}
