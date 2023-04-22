package BusinessLayer.Stock;

import java.time.LocalDate;

/**
 * The mutual class between inventory and suppliers that represents a product
 */
public class ItemToOrder {
    private String product_name;
    private String manufacturer;
    private int quantity;
    private LocalDate expiry_date;

    public ItemToOrder(String product_name, String manufacturer, int quantity, LocalDate expiry_date) {
        this.expiry_date = expiry_date;
        this.manufacturer = manufacturer;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public String getProductName() {
        return product_name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiry_date;
    }
}