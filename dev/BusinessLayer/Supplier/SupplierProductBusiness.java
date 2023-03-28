package BusinessLayer.Supplier;

import java.util.HashMap;

public class SupplierProductBusiness {
    private String name;
    private int productNum;
    private String manufacturer;
    private int price;
    private int maxAmount;
    HashMap<Integer, Integer> quantitiesAgreement;

    public SupplierProductBusiness(String name, int productNum, String manufacturer, int price, int maxAmount){
        this.name = name;
        this.productNum = productNum;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
    }

    private void editDiscount(int productAmount, int discount){
        quantitiesAgreement.put(productAmount, discount);
    }

    private void addDiscount(int productAmount, int discount){
        quantitiesAgreement.put(productAmount,discount);
    }

    private void deleteDiscount(int productAmount, int discount){
        quantitiesAgreement.remove(productAmount,discount);
    }
}
