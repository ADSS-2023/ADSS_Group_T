package BusinessLayer.Supplier.Discounts;

public abstract class Discount {
    protected int amount;
    protected float discount;
    protected  boolean isPercentage;

    public abstract float getPriceAfterDiscount(float oldPrice) ;

    public Discount(int amount,float discount,boolean isPercentage){
        this.amount=amount;
        this.discount =discount;
        this.isPercentage = isPercentage;
    }
    public void editDiscount(int amount, float discount){
        this.discount=discount;
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public float getDiscount() {
        return discount;
    }

    public boolean isPercentage() {
        return isPercentage;
    }
}
