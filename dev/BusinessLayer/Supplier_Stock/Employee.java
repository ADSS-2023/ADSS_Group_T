package BusinessLayer.Supplier_Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.UserDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.UserDalController;

import java.sql.SQLException;

public class Employee {
    private String id;

    private UserDTO userDTO;
    Occupation occupation;
    private UserDalController userDalController;


    public Employee(String id,Occupation occupation, UserDalController userDalController) throws SQLException {
        this.id = id;
        this.occupation = occupation;
        this.userDalController = userDalController;
        if(occupation.equals(Occupation.Manager))
            this.userDTO = new UserDTO(id, 1);
        else if(occupation.equals(Occupation.WareHouse))
            this.userDTO = new UserDTO(id, 2);
        else if (occupation.equals(Occupation.Suppliers))
            this.userDTO = new UserDTO(id,3);
        userDalController.insert(userDTO);
    }

    public enum Occupation {
        WareHouse,Suppliers,Manager
    }
    public boolean isWareHouseEmployee(){
        return  occupation.equals(Occupation.WareHouse);
    }
    public boolean isManager(){
        return  occupation.equals(Occupation.Manager);
    }
    public boolean isSupplierEmployee(){
        return occupation.equals(Occupation.Suppliers);
    }

    public String getId() {
        return id;
    }
}
