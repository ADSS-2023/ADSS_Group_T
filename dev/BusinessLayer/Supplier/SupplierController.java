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
    public void addProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount, HashMap<Integer, Integer> quantitiesAgreement, LocalDateTime expiredDate){
        suppliers.get(supplierNum).addProduct(productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void editProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        suppliers.get(supplierNum).editProduct(productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void deleteProduct(int supplierNum, String productName, String manufacturer, int price, int maxAmount){
        suppliers.get(supplierNum).deleteProduct(productName, manufacturer, price, maxAmount, quantitiesAgreement, expiredDate);
    }

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        suppliers.put(supplierNum, new SupplierBusiness(name,address,supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products));
    }

    public void deleteSupplier(int supplierNum){
        suppliers.remove(supplierNum);
    }

    public void editSupplier(addSupplier(String name, String address, int supplierNum,int bankAccountNum, Map<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, Map<Integer, SupplierProductBusiness> products){
        suppliers.get(supplierNum).editSupplier(name,address, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products);
    }

    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(String productName, String manufacturer, int quantity){
        int minPrice = Integer.MAX_VALUE;
        SupplierBusiness sb = null;
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierProductBusiness sp = entry.getValue().getProduct(productName,manufacturer);
            if(sp != null && sp.isEnough(quantity)) && sp.getPriceByQuantity(quantity)< minPrice){
                minPrice = sp.getPriceByQuantity(quantity);
                sb = entry.getValue();
            }
        }
        if(sb != null)
            suppliersPerProduct.add(sb.getProduct(productName,manufacturer), quantity);
        else {
            List<Integer> suppliersIncluded = new ArrayList<>();
            boolean over = false;
            while (quantity > 0 && !over) {
                sb = null;
                int minPrice = Integer.MAX_VALUE;
                for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                    int currentPrice = entry.getValue().getProduct.getPriceLimitedQuantity(quantity);
                    if (currentPrice < minPrice && !suppliersIncluded.contains(entry.getKey())) {
                        if(sb.equals(entry.getValue()))
                            over = true;
                        minPrice = currentPrice;
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
