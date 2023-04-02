package BusinessLayer.Supplier.Discounts;

public abstract class Discount {
    protected int amount;
    protected int discount;
    protected  boolean isPercentage;

    public abstract int getPriceAfterDiscount(int oldPrice) ;

    public Discount(int amount,int discount,boolean isPercentage){
        this.amount=amount;
        this.discount =discount;
        this.isPercentage = isPercentage;
    }
    public void editDiscount(int amount, int discount){
        this.discount=discount;
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isPercentage() {
        return isPercentage;
    }
}
