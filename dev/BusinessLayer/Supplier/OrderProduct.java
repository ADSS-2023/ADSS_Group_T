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

    public int getQuantity() {
        return quantity;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getDiscount() {
        return discount;
    }
}
