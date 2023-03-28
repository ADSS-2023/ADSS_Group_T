package ServiceLayer.Supplier;

public class ItemToOrder {
    String productName;
    String manufacturer;
    int quantity;

    public ItemToOrder(String productName, String manufacturer, int quantity){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
    }
}
