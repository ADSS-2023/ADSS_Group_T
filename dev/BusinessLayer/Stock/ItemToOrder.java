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
    private double cost_price;

    public ItemToOrder(String product_name, String manufacturer, int quantity, LocalDate expiry_date, double cost_price) {
        this.expiry_date = expiry_date;
        this.manufacturer = manufacturer;
        this.product_name = product_name;
        this.quantity = quantity;
        this.cost_price = cost_price;
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

    public ItemToOrder clone(){
        return new ItemToOrder(this.product_name , this.manufacturer , this.quantity , this.expiry_date , this.quantity);
    }

    public void setQuantity(int new_quantity) {
        this.quantity = new_quantity;
    }

    public double getCost_price() {
        return cost_price;
    }
}
