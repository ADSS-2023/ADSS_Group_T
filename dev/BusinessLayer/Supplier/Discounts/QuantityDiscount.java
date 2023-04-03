package BusinessLayer.Supplier.Discounts;

public class QuantityDiscount extends Discount{
    //in this class discount per product presented in shekels
    public QuantityDiscount(int amount, int discount, boolean isPercentage) {
        super(amount, discount,isPercentage);
    }

    @Override
    public int getPriceAfterDiscount(int oldPrice) {
        return oldPrice -discount;
    }
}
