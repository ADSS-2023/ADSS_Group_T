package Initialization;

import ServiceLayer.Transport.BranchService;

public class Branch_init {
    public static void init(BranchService branchService){

        branchService.addBranch("branch1", "000000001", "Contact B1", 1,30);
        branchService.addBranch("branch2", "000000002", "Contact B2", 30,34);
        branchService.addBranch("branch3", "000000003", "Contact B3", 11,40);
        branchService.addBranch("branch4", "000000004", "Contact B4", -41,-30);
        branchService.addBranch("branch5", "000000005", "Contact B5", 24,-50);
    }


}
