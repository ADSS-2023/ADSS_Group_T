package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class DeliveryDTO extends DTO {
    private int id;
    private String deliveryDate;
    private String departureTime;
    private int truckWeight;
    private String source;
    private int driverId;
    private int truckNumber;
    private int shippingArea;

    public DeliveryDTO(int id, String deliveryDate, String departureTime, int truckWeight, String source, int driverId, int truckNumber, int shippingArea) {
        super("Delivery");
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.source = source;
        this.driverId = driverId;
        this.truckNumber = truckNumber;
        this.shippingArea = shippingArea;
    }
}
