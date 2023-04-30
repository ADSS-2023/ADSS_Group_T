package BusinessLayer.Supplier.Discounts;

public class NumberDiscount extends Discount{
    //in this class discount per product presented in shekels
    public NumberDiscount(int amount, float discount, boolean isPercentage) {
        super(amount, discount,isPercentage);
    }

    @Override
    public String toString() {
        return "Amount to be discounted: " + amount +
                ", Discount: " + discount +
                ", is a Percent discount: " + isPercentage +
                '}';
    }

    @Override
    public float getPriceAfterDiscount(float oldPrice) {
        return oldPrice -discount;
    }
}
