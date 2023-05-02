package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryUnHandledSuppliersDTO extends DTO {

    private int deliveryId;
    private String supplierAddress;
    private String productName;
    private int amount;

    public DeliveryUnHandledSuppliersDTO(int deliveryId, String supplierAddress, String productName, int amount) {
        super("DeliveryUnHandledSuppliers");
        this.deliveryId = deliveryId;
        this.supplierAddress = supplierAddress;
        this.productName = productName;
        this.amount = amount;
    }
}
