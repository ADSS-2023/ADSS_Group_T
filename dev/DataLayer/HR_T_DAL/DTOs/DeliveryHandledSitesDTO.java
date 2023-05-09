package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryHandledSitesDTO extends DTO {

    private int deliveryId;
    private String siteAddress;
    private String productName;
    private int amount;

    public DeliveryHandledSitesDTO(int deliveryId, String siteAddress, String productName, int amount) {
        super("DeliveryHandledSites");
        this.deliveryId = deliveryId;
        this.siteAddress = siteAddress;
        this.productName = productName;
        this.amount = amount;
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
