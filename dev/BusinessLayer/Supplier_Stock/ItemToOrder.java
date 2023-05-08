package BusinessLayer.Supplier_Stock;

import java.time.LocalDate;

/**
 * The mutual class between inventory and suppliers that represents a product
 */
public class ItemToOrder {
    private String productName;
    private String manufacturer;
    private int quantity;
    private LocalDate expiryDate;
    private double costPrice;
    private int orderId;

    public ItemToOrder(String productName, String manufacturer, int quantity, LocalDate expiryDate, int orderId , double costPrice) {
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
        this.productName = productName;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }



    public String getManufacturer() {
        return manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ItemToOrder clone(){
        return new ItemToOrder(this.productName , this.manufacturer , this.quantity , this.expiryDate , this.orderId,this.costPrice);
    }

    public void setQuantity(int new_quantity) {
        this.quantity = new_quantity;
    }

    public double getCostPrice() {
        return costPrice;
    }


    public int getOrderId() {
        return orderId;
    }
}
