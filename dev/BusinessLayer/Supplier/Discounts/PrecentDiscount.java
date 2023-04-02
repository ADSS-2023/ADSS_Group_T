package BusinessLayer.Supplier.Discounts;

public class PrecentDiscount extends Discount {
    //in this class discount per product presented in percentage
    public PrecentDiscount(int amount, int discount,boolean isPercentage) {
        super(amount, discount,isPercentage);
    }

    @Override
    public int getPriceAfterDiscount(int oldPrice) {
       return (1-(discount/100))*oldPrice;
    }
}
