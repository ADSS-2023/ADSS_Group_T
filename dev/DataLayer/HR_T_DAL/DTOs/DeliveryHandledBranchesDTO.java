package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryHandledBranchesDTO extends DTO {

    private int deliveryId;
    private String branchAddress;
    private String productName;
    private int amount;

    public DeliveryHandledBranchesDTO(int deliveryId, String branchAddress, String productName, int amount) {
        super("DeliveryHandledBranches");
        this.deliveryId = deliveryId;
        this.branchAddress = branchAddress;
        this.productName = productName;
        this.amount = amount;
    }
}
