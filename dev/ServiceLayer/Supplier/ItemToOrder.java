package ServiceLayer.Supplier;

import java.time.LocalDateTime;

public class ItemToOrder {
    private String productName;
    private String manufacturer;
    private int quantity;

    private LocalDateTime expiryDate;



    public ItemToOrder(String productName, String manufacturer, int quantity,LocalDateTime expiryDate){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.expiryDate=expiryDate;
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
}
