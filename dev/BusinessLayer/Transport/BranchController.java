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
    public boolean addBranch(String branchAddress, String telNumber, String contactName, int x, int y) {
        if (branches.containsKey(branchAddress)) {
            throw new IllegalArgumentException("branch address is taken");
        }
        else{
            try {
                if(dalDeliveryService.findBranch(branchAddress) != null)
                    throw new IllegalArgumentException("branch address is taken");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Branch branch = new Branch(branchAddress, telNumber, contactName, x, y);
        try {
            dalDeliveryService.insertBranch(branch);
            branches.put(branch.getAddress(), branch);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Branch getBranch(String branchAddress) {
        Branch b;
        if (!branches.containsKey(branchAddress)) {
            try {
                b = dalDeliveryService.findBranch(branchAddress);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(b == null)
                return null;
            branches.put(branchAddress,b);
            return b;
        }
        else
            return branches.get(branchAddress);
    }

    public LinkedHashMap<String, Branch> getAllBranches() {
        try {
            branches = dalDeliveryService.findAllBranch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return branches;
    }
}
