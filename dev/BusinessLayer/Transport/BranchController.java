package BusinessLayer.Transport;


import java.util.LinkedHashMap;

public class BranchController {
    private final LinkedHashMap<String, Branch> branches;

    public BranchController() {
        this.branches = new LinkedHashMap<String, Branch>();
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
    public boolean addBranch(String address, String telNumber, String contactName, int x, int y) {

        if (branches.containsKey(address))
            throw new IllegalArgumentException("branch name taken");
        Branch branch = new Branch(address, telNumber, contactName, x, y);
        branches.put(branch.getAddress(), branch);
        return true;
    }

    public Branch getBranch(String branch) {
        if (!branches.containsKey(branch))
            throw new IllegalArgumentException("no such nranch ");
        else
            return branches.get(branch);
    }

    public LinkedHashMap<String, Branch> getAllBranches() {
        return branches;
    }
}
