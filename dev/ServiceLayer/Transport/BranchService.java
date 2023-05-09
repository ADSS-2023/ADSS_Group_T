package ServiceLayer.Transport;

import BusinessLayer.Transport.BranchController;

public class BranchService {
    private BranchController branchController;
    public BranchService(BranchController branchController) {
        this.branchController = branchController;
    }

    public String getAllBranches() {
        try {
            return branchController.getAllBranches().toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String addBranch(String address, String telNumber, String contactName, int x, int y) {
        try {
            branchController.addBranch(address, telNumber, contactName, x, y);
            return "branch added successfully";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String getBranchAddress(String address) {
        try {
            return branchController.getBranch(address).getAddress();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
