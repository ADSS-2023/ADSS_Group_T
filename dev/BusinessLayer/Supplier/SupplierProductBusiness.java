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

    public void editProductDiscount(int productAmount, int discount){
        quantitiesAgreement.put(productAmount, discount);
    }

    public void addProductDiscount(int productAmount, int discount){
        quantitiesAgreement.put(productAmount,discount);
    }

    public void deleteProductDiscount(int productAmount, int discount){
        quantitiesAgreement.remove(productAmount,discount);
    }

    public boolean hasEnoughQuantity(int quantity){
        return maxAmount >= quantity;
    }

    public int getPriceByQuantity(int quantity){
        int maxDiscount = 0;
        for (Map.Entry<Integer, Integer> entry : quantitiesAgreement.entrySet()) {
            if(entry.getKey() <= quantity && maxDiscount <= entry.getValue())
                maxDiscount = entry.getValue();
        }
        return (1-(maxDiscount/100))*(quantity*price);
    }

    public int getPriceLimitedQuantity(int quantity){
        int quantityProvided = Math.min(maxAmount, quantity);
        int discount = 0;
        for (Map.Entry<Integer, Integer> entry : quantitiesAgreement.entrySet()) {
            if(entry.getKey() <= quantityProvided && discount <= entry.getValue())
                discount = entry.getValue();
        }
        int totalPrice = (1-(discount/100))*(quantity*price);
        return totalPrice/quantityProvided;
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
