package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class BranchController {

    private DalDeliveryService dalDeliveryService;

    public BranchController(DalDeliveryService dalDeliveryService) {
        this.dalDeliveryService = dalDeliveryService;
    }

    /**
     * add a new branch to the branches map
     * @param branchAddress the branch to add
     * @param telNumber the branch tel number
     * @param contactName the branch contact name
     * @param x the x coordinate of the branch
     * @param y the y coordinate of the branch
     * @throws SQLException query error
     */
    public void addBranch(String branchAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        if (getAllBranches().containsKey(branchAddress) ||
                dalDeliveryService.findBranch(branchAddress) != null) {
            throw new IllegalArgumentException("branch address is taken");
        }
        dalDeliveryService.addBranch(branchAddress, telNumber, contactName, x, y);
    }

    /**
     * get branch by address
     * @param branchAddress the address of the requested branch
     * @return the requested branch, or null if it does not exist
     * @throws SQLException query error
     */
    public Branch getBranch(String branchAddress) throws SQLException {
        Branch b;
        if (!getAllBranches().containsKey(branchAddress)) {
            b = dalDeliveryService.findBranch(branchAddress);
            if(b == null)
                throw new IllegalArgumentException("no such branch");;
            return b;
        }
        else
            return getAllBranches().get(branchAddress);
    }

    /**
     * get all the branches in the system
     * @return map with keys of the branch address, and with value of the suitable branch
     * @throws SQLException query error
     */
    public LinkedHashMap<String, Branch> getAllBranches() throws SQLException {
        return dalDeliveryService.findAllBranches();
    }
}
