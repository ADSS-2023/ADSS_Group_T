package BusinessLayer.Supplier;

import java.time.LocalDate;

public class OrderProduct {
    private String productName;
    private String manufacturer;
    private LocalDate expiryDate;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;
    private float finalPrice;



    public OrderProduct(String productName, int productNumber, int quantity, float initialPrice, float discount, float finalPrice) {
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getProductName() {
        return productName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getFinalPrice() {
        return finalPrice;
    }


    public float getInitialPrice() {
        return initialPrice;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return
                "productName: " + productName + '\'' +
                ", productNumber: " + productNumber +
                ", quantity: " + quantity +
                ", initialPrice: " + initialPrice +
                ", discount: " + discount +
                ", finalPrice: " + finalPrice + "\n";
    }
}
