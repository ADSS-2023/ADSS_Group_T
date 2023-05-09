package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class BranchController {
    private LinkedHashMap<String, Branch> branches;
    private DalDeliveryService dalDeliveryService;

    public BranchController(DalDeliveryService dalDeliveryService) {
        this.branches = new LinkedHashMap<String, Branch>();
        this.dalDeliveryService = dalDeliveryService;
    }

    /**
     * add a new branch to the branches map
     *
     * @param address     - the branch to add
     * @param telNumber   - the branch tel number
     * @param contactName - the branch contact name
     * @param x           - the x coordinate of the branch
     * @param y           - the y coordinate of the branch
     * @return true if the branch added successfully , and false otherwise
     */
    public void addBranch(String branchAddress, String telNumber, String contactName, int x, int y) throws SQLException {
        if (branches.containsKey(branchAddress) ||
                dalDeliveryService.findBranch(branchAddress) != null) {
            throw new IllegalArgumentException("branch address is taken");
        }
        Branch branch = new Branch(branchAddress, telNumber, contactName, x, y);
        dalDeliveryService.insertBranch(branch);
        branches.put(branch.getAddress(), branch);
    }

    public Branch getBranch(String branchAddress) throws SQLException {
        Branch b;
        if (!branches.containsKey(branchAddress)) {
            b = dalDeliveryService.findBranch(branchAddress);
            if(b == null)
                return null;
            branches.put(branchAddress,b);
            return b;
        }
        else
            return branches.get(branchAddress);
    }

    public LinkedHashMap<String, Branch> getAllBranches() throws SQLException {
        branches = dalDeliveryService.findAllBranch();
        return branches;
    }
}
