package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Suppliers.ConstantSupplier;
import BusinessLayer.Supplier.Suppliers.OccasionalSupplier;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import BusinessLayer.Supplier.Util.PaymentTerms;

import java.time.DayOfWeek;
import java.util.*;

public class SupplierController {
    HashMap<Integer, SupplierBusiness> suppliers;

    public SupplierController(){
        suppliers = new HashMap<>();
    }

    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<DayOfWeek> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms, int daysToDeliver) throws Exception {
        if(isSupplierExists(supplierNum))
            throw new Exception("supplier number is already exists.");
        if(constDeliveryDays.isEmpty())
            suppliers.put(supplierNum, new OccasionalSupplier(name, address, supplierNum, bankAccountNum, contacts, daysToDeliver, selfDelivery, paymentTerms));
        else{
            suppliers.put(supplierNum, new ConstantSupplier(name, address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, paymentTerms));
        }
    }

    public void deleteSupplier(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("supplier number doesn't exist.");
        suppliers.remove(supplierNum);

    }

    public HashMap<Integer, SupplierProductBusiness> getProducts(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("Supplier doesn't exist.");
        return suppliers.get(supplierNum).getProducts();
    }

    public HashMap<SupplierProductBusiness,Integer> findUrgentSuppliers(ItemToOrder item) throws Exception {
        List<SupplierBusiness> suppliersList = (List<SupplierBusiness>) suppliers.values();
        //edit here - make the list to contain suppliers that can supply full quantity required only
        //if those are not exists - split the order of the item between the possible suppliers


        Collections.sort(suppliersList, (sp1, sp2) -> {
            int sp1daysOfDelivery =sp1.findEarliestSupplyDay();
            float sp2daysOfDelivery =sp2.findEarliestSupplyDay();
            if(sp1daysOfDelivery<sp2daysOfDelivery)
                return -1;
            if(sp1daysOfDelivery>sp2daysOfDelivery)
                return 1;
            return 0;
        });
        int earliestSupply = suppliersList.get(0).findEarliestSupplyDay();
        List<SupplierBusiness> fastestSuppliers = new ArrayList<>();
        List<SupplierBusiness> finalsSuppliers = new ArrayList<>();
        for(SupplierBusiness sb : suppliersList){
            if(sb.isSupplierProuctExist(item.getProductName(), item.getManufacturer()) && sb.findEarliestSupplyDay() == earliestSupply) {
                if(sb.getProduct(item.getProductName(),item.getManufacturer()).hasEnoughQuantity(item.getQuantity()))
                    finalsSuppliers.add(sb);
                else
                    fastestSuppliers.add(sb);
            }
        }
        if(!finalsSuppliers.isEmpty())
            suppliersList = finalsSuppliers;
        else
            suppliersList = fastestSuppliers;

        Collections.sort(suppliersList, (sp1, sp2) -> {
            SupplierProductBusiness p1 = sp1.getProduct(item.getProductName(), item.getManufacturer());
            SupplierProductBusiness p2 = sp2.getProduct(item.getProductName(), item.getManufacturer());
            float p1Price =p1.getPriceByQuantity(item.getQuantity())/Math.min(p1.getMaxAmount(), item.getQuantity());
            float p2Price =p2.getPriceByQuantity(item.getQuantity())/Math.min(p2.getMaxAmount(), item.getQuantity());
            if(p1Price<p2Price)
                return -1;
            if(p1Price>p2Price)
                return 1;
            return 0;
        });
        List<SupplierProductBusiness> productList = new LinkedList<>();
        for (SupplierBusiness sp: suppliersList ) {
            if(sp.isSupplierProuctExist(item.getProductName(), item.getManufacturer()))
                productList.add(sp.getSupplierProduct(item.getProductName(), item.getManufacturer()));
        }
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();

        int quantity = item.getQuantity();
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

    /**
     *
     * @param items - list of items to order
     * @param isRegular - if the order required is a constant one.
     * @return - a single supplier that supplies all products completely,if exists
     * @throws Exception
     */
    public SupplierBusiness findSingleSupplier(List<ItemToOrder> items, boolean isRegular) throws Exception {
        SupplierBusiness sp = null;
        float minPrice = Integer.MAX_VALUE;
        if (suppliers.isEmpty())
            throw new Exception("There is not supplier exists at all.");
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierBusiness spCurr = entry.getValue();
            if ((isRegular && spCurr instanceof ConstantSupplier) || !isRegular) {//if the isRegular is true, then find suppliers with constant days only.
                float currentPrice = 0;
                boolean flag = true;
                for (ItemToOrder item : items) {
                    if ((spCurr.getProduct(item.getProductName(), item.getManufacturer()) != null && spCurr.getProduct(item.getProductName(), item.getManufacturer()).hasEnoughQuantity(item.getQuantity())))
                        currentPrice = currentPrice + spCurr.getProduct(item.getProductName(), item.getManufacturer()).getPriceByQuantity(item.getQuantity());
                    else {
                        flag = false;
                        break;
                    }
                }

                if (flag && minPrice > currentPrice) {
                    minPrice = currentPrice;
                    sp = spCurr;
                }
            }
        }
        return sp;
    }

    /**
     *
     * @param item - to order
     * @return map of products business to quntities that will be ordered
     * @throws Exception
     */
    public HashMap<SupplierProductBusiness, Integer> findSuppliersProduct(ItemToOrder item,boolean isRegular) throws Exception {
        HashMap<SupplierProductBusiness, Integer> suppliersPerProduct = new HashMap<>();
        if(suppliers.isEmpty())
            throw new Exception("There are not supplier exists at all.");
        int quantity = item.getQuantity();
        List<SupplierProductBusiness> productList = new LinkedList<>();
        SupplierProductBusiness sp;

        //make list of suppliers that fully supplies the product
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            SupplierBusiness spCurr = entry.getValue();
            if ((isRegular && spCurr instanceof ConstantSupplier) || !isRegular) {//if the isRegular is true, then find suppliers with constant days only.
                if (spCurr.isSupplierProuctExist(item.getProductName(), item.getManufacturer())) {
                    sp = spCurr.getSupplierProduct(item.getProductName(), item.getManufacturer());
                    if (sp.getMaxAmount() >= quantity)
                        productList.add(sp);
                }
            }
        }
        //if there is no supplier that fully supplies, add all suppliers that supplies the product to the list
        if(productList.size()==0) {
            productList = new LinkedList<>();
            sp =null;
            for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
                SupplierBusiness spCurr = entry.getValue();
                if ((isRegular && spCurr instanceof ConstantSupplier) || !isRegular) {//if the isRegular is true, then find suppliers with constant days only.
                    if (spCurr.isSupplierProuctExist(item.getProductName(), item.getManufacturer())) {
                        sp = spCurr.getSupplierProduct(item.getProductName(), item.getManufacturer());
                        productList.add(sp);
                    }
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