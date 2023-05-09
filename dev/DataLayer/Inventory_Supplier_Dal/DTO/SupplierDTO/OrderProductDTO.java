package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class OrderProductDTO extends DTO {
    private int orderID;
    private  String manufacturer;
    private String expiryDate;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;
    private String productName;

    public OrderProductDTO(int orderID, String manufacturer, String expiryDate, int productNumber, int quantity, float initialPrice, float discount, String productName) {
        super("supplier_order_product");
        this.orderID = orderID;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.productName = productName;
    }
}
