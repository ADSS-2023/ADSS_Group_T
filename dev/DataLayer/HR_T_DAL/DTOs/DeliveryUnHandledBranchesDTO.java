package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryUnHandledBranchesDTO extends DTO {

    private int deliveryId;
    private String branchAddress;
    private String productName;
    private int amount;

    public DeliveryUnHandledBranchesDTO(int deliveryId, String branchAddress, String productName, int amount) {
        super("DeliveryUnHandledBranches");
        this.deliveryId = deliveryId;
        this.branchAddress = branchAddress;
        this.productName = productName;
        this.amount = amount;
    }
}
