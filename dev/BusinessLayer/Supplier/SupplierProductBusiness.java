package BusinessLayer.Supplier;
import BusinessLayer.Supplier.Discounts.Discount;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierProductBusiness {
    private int supplierNum;
    private String name;
    private int productNum;
    private String manufacturer;
    private int price;
    private int maxAmount;
    private List<Discount> quantitiesAgreement;
    private LocalDateTime expiredDate;

    public SupplierProductBusiness(int supplierNum, String name, int productNum, String manufacturer, int price, int maxAmount, List<Discount> quantitiesAgreement, LocalDateTime expiredDate){
        this.supplierNum = supplierNum;
        this.name = name;
        this.productNum = productNum;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
        this.quantitiesAgreement = quantitiesAgreement;
        this.expiredDate = expiredDate;
    }

    private boolean isDiscountExists(int productAmount, int discount, boolean isPercentage){
        for(Discount dis:quantitiesAgreement) {
            if (dis.isPercentage() == isPercentage && dis.getAmount() == productAmount)
                return true;
        }
        return false;
    }

    public void editProductDiscount(int productAmount, int discount, boolean isPercentage) throws Exception {
        if(!isDiscountExists(productAmount, discount, isPercentage))
            throw new Exception("Discount not found");
        for(Discount dis:quantitiesAgreement){
            if(dis.isPercentage() == isPercentage && dis.getAmount() == productAmount)
                dis.editDiscount(productAmount, discount);
        }
    }

    public void addProductDiscount(int productAmount, int discount){
        if(isDiscountExists(productAmount, discount, isPercentage))
            throw new Exception("Discount not found");

    }

    public void deleteProductDiscount(int productAmount, int discount){
        quantitiesAgreement.remove(productAmount,discount);
    }

    public boolean hasEnoughQuantity(int quantity){
        return maxAmount >= quantity;
    }

    public int getPriceByQuantity(int quantity){
        quantity= Math.min(maxAmount, quantity);
        Discount dis = null;
        for (Discount currentDiscount : quantitiesAgreement) {
            if(currentDiscount.getAmount()<quantity)
                dis = currentDiscount;
        }
        return dis.getPriceAfterDiscount(quantity*price);
        }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getPrice() {
        return price;
    }

    public int getProductNum() {
        return productNum;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public List<Discount> getQuantitiesAgreement(){
        return quantitiesAgreement;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public int getDiscount(int quantity){
        int discount = 0;
        for () {
            if (entry.getKey() <= quantity && entry.getValue() < discount)
                discount = entry.getValue();
        }
        return discount;
    }
}
