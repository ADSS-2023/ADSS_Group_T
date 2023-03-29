package BusinessLayer.HR;

import java.util.Vector;

public class Employee {
    private int id ;
    private String employeeName;
    private String bankAccount ;
    private Vector<Position> qualifedPositions;
    private Vector<Constraint> constraints;
    private int salary;
    private String joiningDay;

    public Employee (int id,String employeeName,String bankAccount,Vector<Position> qualifedPositions,String joiningDay) {
        this.id=id;
        this.bankAccount = bankAccount;
        this.joiningDay = joiningDay;
        this.employeeName = employeeName;
        this.qualifedPositions = qualifedPositions;
    }

    public void addConstraint(Constraint constraint){

    }


}
