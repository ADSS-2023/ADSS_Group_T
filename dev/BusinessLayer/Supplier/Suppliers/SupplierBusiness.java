package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.PercentDiscount;
import BusinessLayer.Supplier.Discounts.NumberDiscount;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.SupplierDAO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.*;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;
import BusinessLayer.Supplier.Supplier_Util.Discounts;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;

import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class  SupplierBusiness {
    protected String supplierName;
    protected String address;
    protected int supplierNum;
    protected int bankAccountNum;
    protected HashMap<String, String> contacts;
    protected boolean selfDelivery;
    protected PaymentTerms paymentTerms;

    protected ConcurrentHashMap<Integer, SupplierProductBusiness> products;

    protected List<Discount> discountPerTotalQuantity;

    protected List<Discount> discountPerTotalPrice;

    protected SupplierDTO supplierDTO;

    protected List<SupplierContactDTO> contactDTOS;

    protected SupplierDalController supplierDalController;

    public SupplierBusiness(String supplierName, String address, int supplierNum,int bankAccountNum, HashMap<String, String> contacts, boolean selfDelivery,PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException {
        contactDTOS = new LinkedList<>();
        this.supplierName = supplierName;
        this.address = address;
        this.supplierNum = supplierNum;
        this.bankAccountNum = bankAccountNum;
        this.contacts = contacts;
        this.selfDelivery = selfDelivery;
        this.products = new ConcurrentHashMap<>();
        this.discountPerTotalQuantity = new ArrayList<>();
        this.discountPerTotalPrice = new ArrayList<>();
        this.paymentTerms=paymentTerms;
        this.supplierDalController = supplierDalController;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            SupplierContactDTO supplierContactDTO = new SupplierContactDTO(supplierNum, entry.getKey(), entry.getValue());
            supplierDalController.insert(supplierContactDTO);
            contactDTOS.add(supplierContactDTO);
        }
    }

    public SupplierBusiness(){}

//    public SupplierBusiness(SupplierDTO supplierDTO, HashMap<String, String> contacts, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController){
//        contactDTOS = new LinkedList<>();
//        this.supplierName = supplierDTO.getSupplierName();
//        this.address = supplierDTO.getAddress();
//        this.supplierNum = supplierDTO.getSupplierNum();
//        this.bankAccountNum = supplierDTO.getBankAccountNum();
//        this.contacts = contacts;
//        this.selfDelivery = selfDelivery;
//        this.products = new HashMap<>();
//        this.discountPerTotalQuantity = new ArrayList<>();
//        this.discountPerTotalPrice = new ArrayList<>();
//        this.paymentTerms=paymentTerms;
//        this.supplierDalController = supplierDalController;
//        for (Map.Entry<String, String> entry : contacts.entrySet()) {
//            SupplierContactDTO supplierContactDTO = new SupplierContactDTO(supplierNum, entry.getKey(), entry.getValue());
//            supplierDalController.insert(supplierContactDTO);
//            contactDTOS.add(supplierContactDTO);
//        }
//    }
    public SupplierBusiness(SupplierDTO supplierDTO){

    };
    public void editSupplier(String supplierName, String address, int bankAccountNum, boolean selfDelivery,PaymentTerms paymentTerms) throws SQLException {
        this.supplierName = supplierName;
        this.address = address;
        this.bankAccountNum = bankAccountNum;
        this.selfDelivery = selfDelivery;
        this.paymentTerms=paymentTerms;
        SupplierDTO newSupplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, String.valueOf(selfDelivery), this.supplierDTO.getDaysToDeliver(), paymentTerms.toString());
        supplierDalController.update(this.supplierDTO, newSupplierDTO);
        this.supplierDTO = newSupplierDTO;
    }

    public SupplierProductBusiness getProduct(String productName, String manufacturer) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            SupplierProductBusiness sp = entry.getValue();
            if (sp.getManufacturer().equals(manufacturer) && sp.getName().equals(productName))
                return sp;
        }
        return null;
    }

    public SupplierProductBusiness getProduct(int productNum){
        if(products.containsKey(productNum))
            return products.get(productNum);
        return null;
    }

    public void addProduct(int productNum, String productName, String manufacturer, float price, int maxAmount, LocalDate expiryDate) throws Exception {
        if (getProduct(productName,manufacturer) != null)
            throw new Exception("product already exists.");
        else
            products.put(productNum, new SupplierProductBusiness(supplierNum,productName,productNum, manufacturer, price, maxAmount, expiryDate, supplierDalController));
    }

    public void editProduct(String productName, String manufacturer, int price, int maxAmount, LocalDate expiryDate) throws Exception {
        if(expiryDate.isBefore(Util_Supplier_Stock.getCurrDay()))
            throw new Exception("expiry date has passed.");
        SupplierProductBusiness sp = getProduct(productName,manufacturer);
        if (sp != null) {
            sp.editProduct(supplierNum, productName, manufacturer, price, maxAmount, expiryDate);
            SupplierProductDTO newProductDTO = new SupplierProductDTO(
                    supplierNum, sp.getProductNum(), productName, manufacturer,
                    price, maxAmount,expiryDate.toString());
            supplierDalController.update(sp.getSupplierProductDTO(),newProductDTO);
        }
        else
            throw new Exception("product doesn't exist.");
    }

    public void deleteProduct(int productNum) throws Exception {
        //ToDo::make get all products from databsae
        if(!products.containsKey(productNum))
            throw new Exception("product is not exists.");
        //delete first all of product's discounts
        SupplierProductBusiness sp = products.get(productNum);
        List<Discount> discountsCopy = new ArrayList<>(sp.getQuantitiesAgreement());
        for (Discount dis:discountsCopy){
               sp.deleteProductDiscount(dis.getAmount(),dis.getDiscount(),dis.isPercentage());
            }
            products.remove(productNum);
            supplierDalController.delete(sp.getSupplierProductDTO());
    }

    public void deleteContacts () throws SQLException {
        for (SupplierContactDTO contactDTO : contactDTOS)  {
            supplierDalController.delete(contactDTO);
        }
        contacts=new HashMap<>();
        contactDTOS = new ArrayList<>();
    }

    public void editProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product doesn't exist.");
        getSupplierProduct(productNum).editProductDiscount(productAmount, discount, isPercentage);
    }

    public void addProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product doesn't exist.");
        getSupplierProduct(productNum).addProductDiscount(productAmount, discount, isPercentage);
    }

    public void deleteProductDiscount(int productNum, int productAmount, int discount, boolean isPercentage) throws Exception {
        if(getSupplierProduct(productNum) == null)
            throw new Exception("product doesn't exist.");
        getSupplierProduct(productNum).deleteProductDiscount(productAmount, discount, isPercentage);
    }

    public void editSupplierDiscount(Discounts discountEnum, int amount, int discountToChange, boolean isPercentage) throws Exception {
        if(!isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("No such discount");
        getDiscount(discountEnum,amount,isPercentage).editDiscount(amount,discountToChange);
        DiscountDTO discountDTO =null;
       DiscountDTO oldDTO = getDiscount(discountEnum,amount,isPercentage).getDiscountDTO();
        if(discountEnum == Discounts.DISCOUNT_BY_TOTAL_QUANTITY) {
            if (isPercentage)
                discountDTO = new DiscountDTO(supplierNum, amount, discountToChange, "true", "true", "true", -1);
            else
                discountDTO = new DiscountDTO(supplierNum, amount, discountToChange, "false", "true", "true", -1);
        }
        else{
            if (isPercentage)
                discountDTO = new DiscountDTO(supplierNum, amount, discountToChange, "true", "false", "true", -1);
            else
                discountDTO = new DiscountDTO(supplierNum, amount, discountToChange, "false", "false", "true", -1);
        }
        supplierDalController.update(oldDTO,discountDTO);
    }

    public void addSupplierDiscount(Discounts discountEnum, int amount, int discount,boolean isPercentage) throws Exception {
        if(isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("Discount is already exists");
        DiscountDTO discountDTO=null;
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                if(isPercentage) {
                    discountDTO = new DiscountDTO(supplierNum, amount, discount, "true", "false","true",-1);
                    discountPerTotalPrice.add(new PercentDiscount(amount, discount, true, supplierDalController, discountDTO));
                    supplierDalController.insert(discountDTO);
                }
                else{
                    discountDTO = new DiscountDTO(supplierNum, amount, discount, "false", "false","true",-1);
                    discountPerTotalPrice.add(new NumberDiscount(amount, discount, false, supplierDalController, discountDTO));
                    supplierDalController.insert(discountDTO);
                }
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                if(isPercentage) {
                    discountDTO = new DiscountDTO(supplierNum, amount, discount, "true", "true","true",-1);
                    discountPerTotalQuantity.add(new PercentDiscount(amount, discount, true, supplierDalController, discountDTO));
                    supplierDalController.insert(discountDTO);
                }
                else{
                    discountDTO = new DiscountDTO(supplierNum, amount, discount, "false", "true","true",-1);
                    discountPerTotalQuantity.add(new NumberDiscount(amount, discount, false, supplierDalController, discountDTO));
                    supplierDalController.insert(discountDTO);
                }
                break;
        }
    }

    public void deleteSupplierDiscount(Discounts discountEnum, int amount, boolean isPercentage) throws Exception {
        if(!isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("Discount doesn't Exist");
        Discount dis = getDiscount(discountEnum,amount,isPercentage);
        switch (discountEnum) {
            case DISCOUNT_BY_TOTAL_PRICE:
                        discountPerTotalPrice.remove(dis);
                        supplierDalController.delete(dis.getDiscountDTO());
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                        discountPerTotalQuantity.remove(dis);
                        supplierDalController.delete(dis.getDiscountDTO());
                break;
                }
        }


    public boolean isDiscountExist(Discounts discountEnum,int amount, boolean isPercentage) {
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                for (Discount dis : discountPerTotalPrice) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return true;
                }

                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                for (Discount dis : discountPerTotalQuantity) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return true;
                }
                break;
        }
        return false;
    }

    public Discount getDiscount(Discounts discountEnum,int amount, boolean isPercentage) throws Exception {
        if(!isDiscountExist(discountEnum,amount,isPercentage))
            throw new Exception("Discount Doesnt exist");
        switch(discountEnum){
            case DISCOUNT_BY_TOTAL_PRICE :
                for (Discount dis : discountPerTotalPrice) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                       return dis;
                }
                break;
            case DISCOUNT_BY_TOTAL_QUANTITY:
                for (Discount dis : discountPerTotalQuantity) {
                    if (dis.getAmount() == amount && dis.isPercentage() == isPercentage)
                        return dis;
                }
                break;
        }
        return null;
    }

    public SupplierProductBusiness getSupplierProduct(int productNumber){
        return products.get(productNumber);
    }

    public SupplierProductBusiness getSupplierProduct(String productName, String manufacture) throws Exception {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            if(entry.getValue().getName().equals(productName) && entry.getValue().getManufacturer().equals(manufacture))
                return entry.getValue();
        }
        throw new Exception("the product is not exists.");
    }

    public boolean isSupplierProuctExist(String productName, String manufacture) {
        for (Map.Entry<Integer, SupplierProductBusiness> entry : products.entrySet()) {
            if(entry.getValue().getName().equals(productName) && entry.getValue().getManufacturer().equals(manufacture))
               return true;
        }
        return false;
    }

    //this function gets products number in the order, and old total price and returns new price
//this function gets products number in the order, and old total price and returns new price
    public float getPriceAfterTotalDiscount(int totalProductsNum, float totalOrderPrice) {
        float discount1 = 0;
        float discount2 = 0;

        //loop for number of products discounts
        for (Discount currentDiscount : discountPerTotalQuantity) {
            if (totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice) > discount1 && currentDiscount.getAmount() <= totalProductsNum) {
                discount1 = totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice);
            }
        }
        totalOrderPrice = totalOrderPrice- discount1;
        //loop for total price discounts
        for (Discount currentDiscount : discountPerTotalPrice) {
            if (totalOrderPrice-currentDiscount.getPriceAfterDiscount(totalOrderPrice) > discount2 && currentDiscount.getAmount() <= totalOrderPrice) {
                discount2 = currentDiscount.getDiscount();
            }
        }
        return totalOrderPrice - discount2;
    }

    public abstract int findEarliestSupplyDay();

    public int getBankAccountNum() {
        return bankAccountNum;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public SupplierDTO getSupplierDTO() {
        return supplierDTO;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return supplierName;
    }

    public List<Discount> getDiscountPerTotalQuantity() {
        return discountPerTotalQuantity;
    }

    public List<Discount> getDiscountPerTotalPrice() {
        return discountPerTotalPrice;
    }

    public boolean isDelivering(){
        return selfDelivery;
    }

    public ConcurrentHashMap<Integer, SupplierProductBusiness> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "{" +
                "Supplier Name:" + supplierName + '\'' +
                ", Address: " + address + '\'' +
                ", Supplier Number: " + supplierNum +
                ", Bank Account Number: " + bankAccountNum +
                ", Payment Terms: " + paymentTerms +
                '}';
    }

    public void setProducts(ConcurrentHashMap<Integer, SupplierProductBusiness> products) {
        this.products = products;
    }

    public abstract void deleteConstantDays() throws SQLException;

    public void deleteGeneralDiscounts() throws SQLException {
        for(Discount discount : discountPerTotalPrice)
            supplierDalController.delete(discount.getDiscountDTO());
        for(Discount discount : discountPerTotalQuantity)
            supplierDalController.delete(discount.getDiscountDTO());
        this.discountPerTotalPrice = new ArrayList<>();
        this.discountPerTotalQuantity = new ArrayList<>();
    }
}
