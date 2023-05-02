package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryHandledSuppliersDTO extends DTO {

    private int deliveryId;
    private String supplierAddress;
    private String productName;
    private int amount;

    public DeliveryHandledSuppliersDTO(int deliveryId, String supplierAddress, String productName, int amount) {
        super("DeliveryHandledSuppliers");
        this.deliveryId = deliveryId;
        this.supplierAddress = supplierAddress;
        this.productName = productName;
        this.amount = amount;
    }
}
