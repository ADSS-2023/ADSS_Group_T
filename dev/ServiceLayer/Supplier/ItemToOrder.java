package ServiceLayer.Supplier;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItemToOrder {
    private String productName;
    private String manufacturer;
    private int quantity;
    private int orderId;
    private double costPrice;
    private LocalDate expiryDate;



    public ItemToOrder(String productName, String manufacturer, int quantity, LocalDate expiryDate , int orderId, double costPrice){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.expiryDate=expiryDate;
        this.orderId = orderId;
        this.costPrice=costPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

}
