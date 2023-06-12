package BusinessLayer.Supplier_Stock;

import java.util.LinkedList;

public class UserController {
    private LinkedList<Employee> users;
    private Employee currentUser;
    /**
     * Class that controls login and validation
     */
    public UserController(){
        users = new LinkedList<>();
        currentUser = null;
    }
    /**
     * Login with unique id
     * @param id
     * @return true if the action succeeded
     * @throws Exception
     */
    public boolean login(String id) throws Exception {
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
        return true;
    }
    public boolean isWareHouseEmployee(){
        return currentUser.isWareHouseEmployee();
    }
    public boolean isManager(){
        return currentUser.isManager();
    }
    public boolean isSupplierEmployee(){ return currentUser.isSupplierEmployee(); }
}
