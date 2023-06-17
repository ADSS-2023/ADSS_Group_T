package BusinessLayer.Supplier_Stock;

import java.sql.Connection;
import java.util.LinkedList;

public class UserController {
    private LinkedList<Employee> users;
    private Employee currentUser;
    private Connection connection;
    /**
     * Class that controls login and validation
     */
    public UserController(Connection connection){
        users = new LinkedList<>();
        currentUser = null;
        this.connection = connection;
    }
    /**
     * Login with unique id
     * @param id
     * @return true if the action succeeded
     * @throws Exception
     */
    public String login(String id) throws Exception {
        boolean found = false;
        for (Employee e : users) {
            if (e.getId().equals(id)) {
                found = true;
                currentUser = e;
                break;
            }
        }
        if (!found)
            throw new Exception("No such id in the system");
        return currentUser.occupation.toString();
    }
    public boolean isWareHouseEmployee(){

        return currentUser.isWareHouseEmployee();
    }
    public boolean isManager(){
        return currentUser.isManager();
    }
    public boolean isSupplierEmployee(){ return currentUser.isSupplierEmployee(); }

    public void  loadData(){
        users.add(new Employee("123", Employee.Occupation.WareHouse));
        users.add(new Employee("2",Employee.Occupation.Suppliers));
    }
}
