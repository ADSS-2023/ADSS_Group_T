package DataLayer.Inventory_Supplier_Dal.DTO;

import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

public class UserDTO extends DTO {

    private String id;
    private int occupation;


    public String getId() {
        return id;
    }

    public int getOccupation() {
        return occupation;
    }

    public UserDTO(String id, int occupation){
        super("users");
        this.id = id;
        this.occupation = occupation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }
}
