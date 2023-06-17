package BusinessLayer.Supplier_Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierContactDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.UserDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.UserDalController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserController {
    private LinkedList<Employee> users;
    private Employee currentUser;
    private Connection connection;

    private UserDalController userDalController;
    /**
     * Class that controls login and validation
     */
    public UserController(Connection connection, UserDalController userDalController){
        this.connection = connection;
        this.userDalController = userDalController;
        users = new LinkedList<>();
        currentUser = null;
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

    public void register(String id, Employee.Occupation occupation) throws SQLException {
            Employee employee = new Employee(id,occupation, userDalController);
    }
    public boolean isWareHouseEmployee(){
        return currentUser.isWareHouseEmployee();
    }
    public boolean isManager(){
        return currentUser.isManager();
    }
    public boolean isSupplierEmployee(){
        return currentUser.isSupplierEmployee();
    }

    public void  loadData() throws SQLException{
        List<UserDTO> userDTOS = loadUser();
        for(UserDTO userDTO: userDTOS){
            if(userDTO.getOccupation() == 1)
                users.add(new Employee(userDTO.getId(), Employee.Occupation.Manager, userDalController));
            else if(userDTO.getOccupation() == 2)
                users.add(new Employee(userDTO.getId(), Employee.Occupation.WareHouse, userDalController));
            else if(userDTO.getOccupation() == 3)
                users.add(new Employee(userDTO.getId(), Employee.Occupation.Suppliers, userDalController));
        }
    }

    public List<UserDTO> loadUser() throws SQLException {
        return userDalController.findAll("users", UserDTO.class);
    }
}
