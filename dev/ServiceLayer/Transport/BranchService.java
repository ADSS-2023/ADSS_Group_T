package ServiceLayer.Transport;

import BusinessLayer.Transport.BranchController;

public class BranchService {
    private BranchControllerD branchController;
    public BranchService(BranchController branchController) {
        this.branchController = branchController;
    }
    public String getAllBranches() {
        return branchController.getAllBranches().toString();
    }
    public String addBranch(String address,String telNumber,String contactName,int x,int y) {
        if (branchController.addBranch(address,telNumber,contactName,x,y))
            return "good";
        else
            throw new IllegalArgumentException("error in add branch");

    }

}
