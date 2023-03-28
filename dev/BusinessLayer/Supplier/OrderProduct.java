package BusinessLayer.Supplier;

public class OrderProduct {
    private String productName;
    private int productNumber;
    private int quantity;
    private int initialPrice;
    private int discount;
    private int finalPrice;

    public OrderProduct(String productName, int productNumber, int quantity, int initialPrice, int discount, int finalPrice) {
        this.productName = productName;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }
}
