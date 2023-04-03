package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import ServiceLayer.Supplier.ItemToOrder;

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

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, HashMap<String, Integer> contacts, List<String> constDeliveryDays, boolean selfDelivery, HashMap<Integer, SupplierProductBusiness> products){
        suppliers.put(supplierNum, new SupplierBusiness(name, address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, products));
    }

    public void deleteSupplier(int supplierNum){
        suppliers.remove(supplierNum);
    }

    public HashMap<Integer, SupplierProductBusiness> getProducts(int supplierNum){
        return suppliers.get(supplierNum).getProducts();
    }

    public SupplierBusiness findSingleSupplier(List<ItemToOrder> items){
        SupplierBusiness sp = null;
        int minPrice = Integer.MAX_VALUE;
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            int currentPrice = 0;
            boolean flag = true;
            for (ItemToOrder item : items) {
                if ((entry.getValue().getProduct(item.getProductName(), item.getManufacturer()) != null && entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).hasEnoughQuantity(item.getQuantity())))
                    currentPrice = currentPrice + entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).getPriceByQuantity(item.getQuantity());
                else
                    flag = false;
            }
            if (flag && minPrice > currentPrice) {
                minPrice = currentPrice;
                sp = entry.getValue();
            }
        }
        return sp;
    }

    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(ItemToOrder item){
        int quantity = item.getQuantity();
        int minPrice = Integer.MAX_VALUE;
        SupplierBusiness sb = null;
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierProductBusiness sp = entry.getValue().getProduct(item.getProductName(), item.getManufacturer());
            if(sp != null && sp.hasEnoughQuantity(quantity) && (sp.getPriceByQuantity(quantity)) < minPrice){
                minPrice = sp.getPriceByQuantity(quantity);
                sb = entry.getValue();
            }
        }
        if(sb != null)
            suppliersPerProduct.put(sb.getProduct(item.getProductName(),item.getManufacturer()), quantity);
        else {
            List<Integer> suppliersIncluded = new ArrayList<>();
            boolean over = false;
            while (quantity > 0 && !over) {
                sb = null;
                int MinPrice = Integer.MAX_VALUE;
                for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                    int currentPrice = (entry.getValue().getProduct(item.getProductName(),item.getManufacturer()).getPriceByQuantity(quantity))/Math.min(entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).getMaxAmount(), quantity);
                    if (currentPrice < MinPrice && !suppliersIncluded.contains(entry.getKey())) {
                        if(sb.equals(entry.getValue()))
                            over = true;
                        MinPrice = currentPrice;
                        sb = entry.getValue();
                    }
                }
                if(sb != null) {
                    suppliersIncluded.add(sb.getSupplierNum());
                    SupplierProductBusiness sp = sb.getProduct(item.getProductName(),item.getManufacturer());
                    quantity = (quantity - Math.min(quantity,sp.getMaxAmount()));
                    suppliersPerProduct.put(sp,Math.min(quantity,sp.getMaxAmount()));
                }
            }
        }
        return suppliersPerProduct;
    }

    public boolean isSupplierExists(int supplierNum){
        return suppliers.containsKey(supplierNum);
    }
    public SupplierBusiness getSupplier(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("Supplier Does Not Exists");
        return suppliers.get(supplierNum);

    }
}
