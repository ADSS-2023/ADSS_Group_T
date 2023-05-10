package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryUnHandledSitesDTO extends DTO {

    private int deliveryId;
    private String siteAddress;
    private String productName;

    private int fileId;
    private int amount;

    public DeliveryUnHandledSitesDTO(int deliveryId, String siteAddress, String productName, int fileId,int amount) {
        super("DeliveryUnHandledSites");
        this.deliveryId = deliveryId;
        this.siteAddress = siteAddress;
        this.productName = productName;
        this.fileId = fileId;
        this.amount = amount;
    }

    public DeliveryUnHandledSitesDTO() {
    }

    public int getFileId() {
        return fileId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }
}
