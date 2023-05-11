package BusinessLayer.Supplier;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.NumberDiscount;
import BusinessLayer.Supplier.Discounts.PercentDiscount;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier.Suppliers.ConstantSupplier;
import BusinessLayer.Supplier.Suppliers.OccasionalSupplier;
import BusinessLayer.Supplier.Suppliers.SupplierBusiness;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.*;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupplierController {
    private ConcurrentHashMap<Integer, SupplierBusiness> suppliers;
    private Connection connection;
    private SupplierDalController supplierDalController;
    public SupplierController(Connection connection, SupplierDalController supplierDalController) throws Exception {
        suppliers = new ConcurrentHashMap<>();
        this.connection = connection;
        this.supplierDalController = supplierDalController;
        //loadSuppliers();
    }

    public void loadSuppliers() throws Exception {
        List<SupplierDTO> supplierDTOS = supplierDalController.findAll("supplier_supplier", SupplierDTO.class);
        for (SupplierDTO supplierDTO : supplierDTOS) {
            List<SupplierContactDTO> supplierContactDTOS = loadSupplierContacts(supplierDTO.getSupplierNum());
            HashMap<String, String> contacts = new HashMap<>();
            for(SupplierContactDTO contactDTO : supplierContactDTOS)
                contacts.put(contactDTO.getContactName(), contactDTO.getContactNumber());
            List<ConstDeliveryDaysDTO> constDeliveryDaysDTOS = loadConstDeliveryDays(supplierDTO.getSupplierNum());
            List<DayOfWeek> days = new ArrayList<>();
            for(ConstDeliveryDaysDTO constDeliveryDaysDTO : constDeliveryDaysDTOS)
                days.add(DayOfWeek.of(constDeliveryDaysDTO.getDay()));
            List<SupplierProductDTO> productDTOS = loadSupplierProducts(supplierDTO.getSupplierNum());
            ConcurrentHashMap<Integer, SupplierProductBusiness> products = new ConcurrentHashMap<>();
            for(SupplierProductDTO supplierProductDTO :  productDTOS){
                products.put(supplierProductDTO.getProductNum(), new SupplierProductBusiness(supplierProductDTO, supplierDalController));
            }
            List<DiscountDTO> productDiscountDTOS = loadDiscounts(supplierDTO.getSupplierNum());
            for(DiscountDTO productDiscountDTO : productDiscountDTOS)
                if(!Boolean.parseBoolean(productDiscountDTO.IsSupplierDiscount()))
                    products.get(productDiscountDTO.getProductNum()).addProductDiscount(productDiscountDTO);
            List<DiscountDTO> supplierDiscountDTOS = loadDiscounts(supplierDTO.getSupplierNum());
            List<Discount> discountPerTotalQuantity = new ArrayList<>();
            List<Discount> discountPerTotalPrice = new ArrayList<>();
            for(DiscountDTO supplierDiscountDTO : supplierDiscountDTOS) {
                if (Boolean.parseBoolean(supplierDiscountDTO.IsSupplierDiscount())){
                    if (Boolean.parseBoolean(supplierDiscountDTO.getIsTotalAmount())) {
                        if (Boolean.parseBoolean(supplierDiscountDTO.isPercentage()))
                            discountPerTotalQuantity.add(new PercentDiscount(supplierDiscountDTO, supplierDalController));
                        else
                            discountPerTotalQuantity.add(new NumberDiscount(supplierDiscountDTO, supplierDalController));
                    } else {
                        if (Boolean.parseBoolean(supplierDiscountDTO.isPercentage()))
                            discountPerTotalPrice.add(new PercentDiscount(supplierDiscountDTO, supplierDalController));
                        else
                            discountPerTotalPrice.add(new NumberDiscount(supplierDiscountDTO, supplierDalController));
                    }
                }
            }
            if(supplierDTO.getDaysToDeliver() == -1)
                suppliers.put(supplierDTO.getSupplierNum(), new ConstantSupplier(supplierDTO,contacts, products, days, supplierDalController, discountPerTotalQuantity, discountPerTotalPrice));
            else
                suppliers.put(supplierDTO.getSupplierNum(), new OccasionalSupplier(supplierDTO, contacts, products, supplierDTO.getDaysToDeliver(), supplierDalController, discountPerTotalQuantity, discountPerTotalPrice));
        }
    }

    public List<SupplierContactDTO> loadSupplierContacts(int supplierNum) throws SQLException {
        return supplierDalController.findAllOfCondition("supplier_supplier_contact", "supplierNum", supplierNum, SupplierContactDTO.class);
    }

    public List<ConstDeliveryDaysDTO> loadConstDeliveryDays(int supplierNum) throws SQLException {
        return supplierDalController.findAllOfCondition("supplier_const_delivery_days", "supplierNum", supplierNum, ConstDeliveryDaysDTO.class);
    }

    public List<SupplierProductDTO> loadSupplierProducts(int supplierNum) throws SQLException {
        return supplierDalController.findAllOfCondition("supplier_supplier_product", "supplierNum", supplierNum, SupplierProductDTO.class);
    }

    public List<DiscountDTO> loadDiscounts(int supplierNum) throws SQLException {
        return supplierDalController.findAllOfCondition("supplier_discount", "supplierNum", supplierNum, DiscountDTO.class);
    }


    public void addSupplier(String name, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<DayOfWeek> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms, int daysToDeliver) throws Exception {
        if(isSupplierExists(supplierNum))
            throw new Exception("supplier number is already exists.");
        if(constDeliveryDays.isEmpty()) {
            suppliers.put(supplierNum, new OccasionalSupplier(name, address, supplierNum, bankAccountNum, contacts, daysToDeliver, selfDelivery, paymentTerms, supplierDalController));
        }
        else{
            suppliers.put(supplierNum, new ConstantSupplier(name, address, supplierNum, bankAccountNum, contacts, constDeliveryDays, selfDelivery, paymentTerms, supplierDalController));
        }
    }

    public void deleteSupplier(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("supplier number doesn't exist.");
        SupplierBusiness sp = getSupplier(supplierNum);
        sp.deleteContacts();
        sp.deleteConstantDays();
        sp.deleteGeneralDiscounts();
        ConcurrentHashMap<Integer, SupplierProductBusiness> products_ = sp.getProducts();
        for (Map.Entry<Integer, SupplierProductBusiness> entry : sp.getProducts().entrySet())
            sp.deleteProduct(entry.getKey());
        supplierDalController.delete(getSupplier(supplierNum).getSupplierDTO());
        suppliers.remove(supplierNum);
    }


    public ConcurrentHashMap<Integer, SupplierProductBusiness> getProducts(int supplierNum) throws Exception {
        if(!isSupplierExists(supplierNum))
            throw new Exception("Supplier doesn't exist.");
        return suppliers.get(supplierNum).getProducts();
    }

    public HashMap<SupplierProductBusiness,Integer> findUrgentSuppliers(ItemToOrder item) throws Exception {
        List<SupplierBusiness> suppliersList = new LinkedList<>();
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers.entrySet()) {
            suppliersList.add(entry.getValue());
        }
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

    public static PaymentTerms stringToPaymentTerms(String paymentTerms){
        switch (paymentTerms){
            case "SHOTEF_PLUS_30":
                return PaymentTerms.SHOTEF_PLUS_30;
            case "SHOTEF_PLUS_45":
                return PaymentTerms.SHOTEF_PLUS_45;
            case "SHOTEF_PLUS_60":
                return PaymentTerms.SHOTEF_PLUS_60;
            case "SHOTEF_PLUS_90":
                return PaymentTerms.SHOTEF_PLUS_90;
        }
        return null;
    }

    public ConcurrentHashMap<Integer, SupplierBusiness> getSuppliers(){return suppliers;}

    public void deleteAll() throws Exception {
        ConcurrentHashMap<Integer, SupplierBusiness> suppliers_ = suppliers;
        for (Map.Entry<Integer, SupplierBusiness> entry : suppliers_.entrySet())
            deleteSupplier(entry.getKey());
        suppliers = new ConcurrentHashMap<>();
    }
}