package Initialization;

import ServiceLayer.Transport.BranchService;

public class Branch_init {
    public static void init(BranchService branchService){
        branchService.addBranch("b1", "000000001", "Contact B1", 1, 30);
        branchService.addBranch("b2", "000000002", "Contact B2", 30, 34);
        branchService.addBranch("b3", "000000003", "Contact B3", 11, 40);
        branchService.addBranch("b4", "000000004", "Contact B4", -41, -30);
        branchService.addBranch("b5", "000000005", "Contact B5", 24, -50);
        branchService.addBranch("b6", "000000006", "Contact B6", -20, 10);
        branchService.addBranch("b7", "000000007", "Contact B7", 45, -23);
        branchService.addBranch("b8", "000000008", "Contact B8", -13, -7);
        branchService.addBranch("b9", "000000009", "Contact B9", 17, 5);
        branchService.addBranch("b10", "000000010", "Contact B10", 8, -12);

    }


}
