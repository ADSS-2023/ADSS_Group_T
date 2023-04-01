package BusinessLayer.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class SupplierController {
    HashMap<Integer, SupplierBusiness> suppliers;

    public SupplierController(){
        suppliers = new HashMap<>();
    }
    public void addProduct(int supplierNum,int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        suppliers.get(supplierNum).addProduct(productNum,productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void editProduct(int supplierNum, int productNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        suppliers.get(supplierNum).editProduct(productNum, productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void deleteProduct(int supplierNum, int productNum){
        suppliers.get(supplierNum).deleteProduct(productNum);
    }

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products){
        suppliers.put(supplierNum, new SupplierBusiness(name,address,supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products));
    }

    public void deleteSupplier(int supplierNum){
        suppliers.remove(supplierNum);
    }

    public void editSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products){
        suppliers.get(supplierNum).editSupplier(name,address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products);
    }
    public void editDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        suppliers.get(vendorNum).editDiscount(productNum, productAmount, discount);
    }

    public void addDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        suppliers.get(vendorNum).addDiscount(productNum, productAmount, discount);
    }

    public void deleteDiscount(int vendorNum, int productNum, int productAmount, int discount) {
        suppliers.get(vendorNum).deleteDiscount(productNum, productAmount, discount);
    }
    public HashMap<Integer, SupplierProductBusiness> getProducts(int vendorNum){
        return suppliers.get(vendorNum).getProducts();
    }

    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(String productName, String manufacturer, int quantity){
        int minPrice = Integer.MAX_VALUE;
        SupplierBusiness sb = null;
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierProductBusiness sp = entry.getValue().getProduct(productName,manufacturer);
            if(sp != null && sp.isEnough(quantity) && (sp.getPriceByQuantity(quantity)) < minPrice){
                minPrice = sp.getPriceByQuantity(quantity);
                sb = entry.getValue();
            }
        }
        if(sb != null)
            suppliersPerProduct.put(sb.getProduct(productName,manufacturer), quantity);
        else {
            List<Integer> suppliersIncluded = new ArrayList<>();
            boolean over = false;
            while (quantity > 0 && !over) {
                sb = null;
                int MinPrice = Integer.MAX_VALUE;
                for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                    int currentPrice = entry.getValue().getProduct(productName,manufacturer).getPriceLimitedQuantity(quantity);
                    if (currentPrice < MinPrice && !suppliersIncluded.contains(entry.getKey())) {
                        if(sb.equals(entry.getValue()))
                            over = true;
                        MinPrice = currentPrice;
                        sb = entry.getValue();
                    }
                }
                if(sb != null) {
                    suppliersIncluded.add(sb.getSupplierNum());
                    SupplierProductBusiness sp = sb.getProduct(productName,manufacturer);
                    quantity = quantity - Math.min(quantity,sp.getMaxAmount());
                    suppliersPerProduct.put(sp,Math.min(quantity,sp.getMaxAmount()));
                }
            }
        }
        return suppliersPerProduct;
    }
}
