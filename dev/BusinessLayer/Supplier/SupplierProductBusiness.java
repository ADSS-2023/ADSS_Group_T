package BusinessLayer.Supplier;
import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.PrecentDiscount;
import BusinessLayer.Supplier.Discounts.QuantityDiscount;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class SupplierProductBusiness {
    private int supplierNum;
    private String name;
    private int productNum;
    private String manufacturer;
    private int price;
    private int maxAmount;
    private List<Discount> quantitiesAgreement;
    private LocalDateTime expiredDate;

    public SupplierProductBusiness(int supplierNum, String name, int productNum, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate){
        this.supplierNum = supplierNum;
        this.name = name;
        this.productNum = productNum;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
        this.quantitiesAgreement = new ArrayList<>();
        this.expiredDate = expiredDate;
    }

    private boolean isDiscountExists(int productAmount, int discount, boolean isPercentage){
        for(Discount dis:quantitiesAgreement) {
            if (dis.isPercentage() == isPercentage && dis.getAmount() == productAmount)
                return true;
        }
        return false;
    }

    private boolean isDiscountValid(int productAmount, int discount, boolean isPercentage){
        boolean valid = true;
        for(Discount dis: quantitiesAgreement) {
            if (dis.isPercentage() == isPercentage && productAmount > dis.getAmount() && discount <= dis.getDiscount()){
                valid = false;
                break;
            }
        }
        return valid;
    }

    public void editProductDiscount(int productAmount, int discount, boolean isPercentage) throws Exception {
        if(!isDiscountExists(productAmount, discount, isPercentage))
            throw new Exception("Discount not found");
        if(isDiscountValid(productAmount, discount, isPercentage)) {
            for (Discount dis : quantitiesAgreement) {
                if (dis.isPercentage() == isPercentage && dis.getAmount() == productAmount)
                    dis.editDiscount(productAmount, discount);
            }
        }
    }

    public void addProductDiscount(int productAmount, int discount, boolean isPercentage) throws Exception {
        if(isDiscountExists(productAmount, discount, isPercentage))
            throw new Exception("Discount already exists");
        if(isDiscountValid(productAmount, discount, isPercentage)) {
            if (isPercentage)
                quantitiesAgreement.add(new PrecentDiscount(productAmount, discount, true));
            else
                quantitiesAgreement.add(new QuantityDiscount(productAmount, discount, false));
        }
    }

    public void deleteProductDiscount(int productAmount, int discount, boolean isPercentage) throws Exception {
        if(!isDiscountExists(productAmount, discount, isPercentage))
            throw new Exception("Discount not found");
        Discount curr = null;
        for(Discount dis:quantitiesAgreement){
            if(dis.isPercentage() == isPercentage && dis.getAmount() == productAmount && dis.getDiscount() == discount)
                curr = dis;
        }
        quantitiesAgreement.remove(curr);
    }

    public boolean hasEnoughQuantity(int quantity){
        return maxAmount >= quantity;
    }

    public int getPriceByQuantity(int quantity){
        quantity= Math.min(maxAmount, quantity);
        int maxAmount = 0;
        Discount dis = null;
        for (Discount currentDiscount : quantitiesAgreement) {
            if(currentDiscount.getAmount() > maxAmount && quantity >= currentDiscount.getAmount()) {
                dis = currentDiscount;
                maxAmount = currentDiscount.getAmount();
            }
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

    public void editProduct(int supplierNum, String productName, int productNum, String manufacturer, int price, int maxAmount, LocalDateTime expiredDate) {
        this.supplierNum = supplierNum;
        this.productNum = productNum;
        this.name = productName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
        this.expiredDate = expiredDate;
    }
}
