package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class OrderProductDTO extends DTO {
    private int orderID;
    private String productName;
    private  String manufacturer;
    private String expiryDate;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;

    private float finalPrice;

    public float getFinalPrice() {
        return finalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public String getProductName() {
        return productName;
    }

    public OrderProductDTO(int orderID, String manufacturer, String expiryDate, int productNumber, int quantity, float initialPrice, float discount, float finalPrice, String productName) {
        super("supplier_order_product");
        this.orderID = orderID;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.productName = productName;
        this.finalPrice = finalPrice;
    }
    public OrderProductDTO() {
        super("supplier_order_product");
    }
}
