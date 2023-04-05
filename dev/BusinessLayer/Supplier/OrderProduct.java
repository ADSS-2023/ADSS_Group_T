package BusinessLayer.Supplier;

public class OrderProduct {
    private String productName;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;
    private float finalPrice;

    public OrderProduct(String productName, int productNumber, int quantity, float initialPrice, float discount, float finalPrice) {
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

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "{" +
                "productName='" + productName + '\'' +
                ", productNumber=" + productNumber +
                ", quantity=" + quantity +
                ", initialPrice=" + initialPrice +
                ", discount=" + discount +
                ", finalPrice=" + finalPrice +
                '}'+'\n';
    }
}
