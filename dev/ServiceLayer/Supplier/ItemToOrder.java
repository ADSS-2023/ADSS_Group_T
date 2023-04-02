package ServiceLayer.Supplier;

public class ItemToOrder {
    private String productName;
    private String manufacturer;
    private int quantity;

    public ItemToOrder(String productName, String manufacturer, int quantity){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
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
