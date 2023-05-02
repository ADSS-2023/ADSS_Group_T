package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryDTO extends DTO {
    private int id;
    private String deliveryDate;
    private String departureTime;
    private int truckWeight;
    private String source;
    private String driverName;
    private int truckNumber;
    private String shippingArea;

    public DeliveryDTO(int id, String deliveryDate, String departureTime, int truckWeight, String source, String driverName, int truckNumber, String shippingArea) {
        super("Delivery");
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.source = source;
        this.driverName = driverName;
        this.truckNumber = truckNumber;
        this.shippingArea = shippingArea;
    }
}
