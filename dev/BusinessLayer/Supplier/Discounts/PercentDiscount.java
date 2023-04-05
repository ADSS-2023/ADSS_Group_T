package BusinessLayer.Supplier.Discounts;

public class PercentDiscount extends Discount {
    //in this class discount per product presented in percentage
    public PercentDiscount(int amount, float discount,boolean isPercentage) {
        super(amount, discount,isPercentage);
    }

    @Override
    public float getPriceAfterDiscount(float oldPrice) {
       return (1-(discount/100))*oldPrice;
    }

    @Override
    public String toString() {
        return "Discount Type :Price Discount{ " +
                "Amount to be discounted: " + amount +
                ", Discount: " + discount +
                ", is a Percent discount: " + isPercentage +
                '}';
    }
}
