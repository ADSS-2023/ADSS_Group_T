package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import ServiceLayer.Supplier.ItemToOrder;

import java.util.*;
import java.time.LocalDateTime;

public class SupplierController {
    HashMap<Integer, SupplierBusiness> suppliers;

    public SupplierController(){
        suppliers = new HashMap<>();
    }

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<String> constDeliveryDays, boolean selfDelivery) throws Exception {
        if(isSupplierExists(supplierNum))
            throw new Exception("supplier number is already exists.");
        suppliers.put(supplierNum, new SupplierBusiness(name, address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery));
    }

    public void deleteSupplier(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("supplier number is not exists.");
        suppliers.remove(supplierNum);
    }

    public HashMap<Integer, SupplierProductBusiness> getProducts(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("Supplier doesn't exist.");
        return suppliers.get(supplierNum).getProducts();
    }

    public SupplierBusiness findSingleSupplier(List<ItemToOrder> items) throws Exception {
        SupplierBusiness sp = null;
        float minPrice = Integer.MAX_VALUE;
        if(suppliers.isEmpty())
            throw new Exception("There are not supplier exists at all.");
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            float currentPrice = 0;
            boolean flag = true;
            for (ItemToOrder item : items) {
                if ((entry.getValue().getProduct(item.getProductName(), item.getManufacturer()) != null && entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).hasEnoughQuantity(item.getQuantity())))
                    currentPrice = currentPrice + entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).getPriceByQuantity(item.getQuantity());
                else {
                    flag = false;
                    break;
                }
            }
            if (flag && minPrice > currentPrice) {
                minPrice = currentPrice;
                sp = entry.getValue();
            }
        }
        return sp;
    }

    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(ItemToOrder item) throws Exception {
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        if(suppliers.isEmpty())
            throw new Exception("There are not supplier exists at all.");
        int quantity = item.getQuantity();
        List<SupplierProductBusiness> productList = new LinkedList<>();
        SupplierProductBusiness sp =null;
        //make list of suppliers that fully supplies the product
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            if(entry.getValue().isSupplierProuctExist(item.getProductName(), item.getManufacturer())) {
                sp = entry.getValue().getSupplierProduct(item.getProductName(), item.getManufacturer());
                if(sp.getMaxAmount()>=quantity)
                    productList.add(sp);
            }
        }
        //if there is no supplier that fully supplies, add all suppliers that supplies the product to the list
        if(productList.size()==0) {
            productList = new LinkedList<>();
            sp =null;
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                if(entry.getValue().isSupplierProuctExist(item.getProductName(), item.getManufacturer())) {
                    sp = entry.getValue().getSupplierProduct(item.getProductName(), item.getManufacturer());
                    productList.add(sp);
                }
            }
        }
       // float currentPrice = (entry.getValue().getProduct(item.getProductName(),item.getManufacturer()).getPriceByQuantity(quantity))/Math.min(entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).getMaxAmount(), quantity);

        //sort the list by the lowest price per product
        int finalQuantity = quantity;
        Collections.sort(productList, (p1, p2) -> {
            float p1Price =p1.getPriceByQuantity(finalQuantity)/Math.min(p1.getMaxAmount(), finalQuantity);
            float p2Price =p2.getPriceByQuantity(finalQuantity)/Math.min(p2.getMaxAmount(), finalQuantity);
            if(p1Price<p2Price)
                 return -1;
            if(p1Price>p2Price)
                return 1;
            return 0;
        });

        for(SupplierProductBusiness product : productList) {
            if (quantity>0) {
                int toReduce = Math.min(product.getMaxAmount(), quantity);
                suppliersPerProduct.put(product, toReduce);
                quantity = quantity - toReduce;
            }
        }
        if(quantity>0 | suppliersPerProduct.size()==0)
            throw new Exception("Order failed.\nProduct "+item.getProductName()+" manufactured by "+item.getManufacturer()+" can not be supplied.");
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

    public HashMap<Integer, SupplierBusiness> getSuppliers(){return suppliers;}

}



//    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(ItemToOrder item) throws Exception {
//        int quantity = item.getQuantity();
//        float minPrice = Integer.MAX_VALUE;
//        SupplierBusiness sb = null;
//        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
//        if(suppliers.isEmpty())
//            throw new Exception("There are not supplier exists at all.");
//        //this loop finds the cheapest supplier that supplies the product within its quantity
//        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
//            SupplierProductBusiness sp = entry.getValue().getProduct(item.getProductName(), item.getManufacturer());
//            if(sp != null && sp.hasEnoughQuantity(quantity) && (sp.getPriceByQuantity(quantity)) < minPrice){
//                minPrice = sp.getPriceByQuantity(quantity);
//                sb = entry.getValue();
//            }
//        }
//        if(sb != null) // if theres a supplier that can supply the product completely
//            suppliersPerProduct.put(sb.getProduct(item.getProductName(),item.getManufacturer()), quantity);
//
//




    /*List<Integer> suppliersIncluded = new ArrayList<>();
    boolean over = false;
            while (quantity > 0 && !over) {
                    sb = null;
                    float MinPrice = Integer.MAX_VALUE;
                    for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
        float currentPrice = (entry.getValue().getProduct(item.getProductName(),item.getManufacturer()).getPriceByQuantity(quantity))/Math.min(entry.getValue().getProduct(item.getProductName(), item.getManufacturer()).getMaxAmount(), quantity);
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
        }*/