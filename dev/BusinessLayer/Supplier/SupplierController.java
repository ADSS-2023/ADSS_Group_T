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

    public void addSupplier(String name, String address, int supplierNum,int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products, HashMap<Integer, Integer> discountPerTotalQuantity, HashMap<Integer, Integer> discountPerTotalPrice){
        suppliers.put(supplierNum, new SupplierBusiness(name, address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products, discountPerTotalQuantity, discountPerTotalPrice));
    }

    public void deleteSupplier(int supplierNum){
        suppliers.remove(supplierNum);
    }

    public HashMap<Integer, SupplierProductBusiness> getProducts(int supplierNum){
        return suppliers.get(supplierNum).getProducts();
    }

    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(String productName, String manufacturer, int quantity){
        int minPrice = Integer.MAX_VALUE;
        SupplierBusiness sb = null;
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierProductBusiness sp = entry.getValue().getProduct(productName,manufacturer);
            if(sp != null && sp.hasEnoughQuantity(quantity) && (sp.getPriceByQuantity(quantity)) < minPrice){
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

    public boolean isSupplierExists(int supplierNum){
        return suppliers.containsKey(supplierNum);
    }
    public SupplierBusiness getSupplier(int supplierNum){
        if(isSupplierExists(supplierNum))
             return  suppliers.get(supplierNum);
        else return null;
    }
}
