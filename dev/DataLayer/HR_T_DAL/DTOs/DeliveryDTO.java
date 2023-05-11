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
        super(getTableNameStatic());
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.departureTime = departureTime;
        this.truckWeight = truckWeight;
        this.source = source;
        this.driverId = driverId;
        this.truckNumber = truckNumber;
        this.shippingArea = shippingArea;
    }
    public  DeliveryDTO(){
        super(getTableNameStatic());
    }

    public static String getTableNameStatic() {
        return "Delivery";
    }

    public int getId() {
        return id;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getTruckWeight() {
        return truckWeight;
    }

    public String getSource() {
        return source;
    }

    public int getDriverId() {
        return driverId;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public int getShippingArea() {
        return shippingArea;
    }
}
