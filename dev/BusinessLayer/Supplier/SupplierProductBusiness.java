package BusinessLayer.Supplier;
import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.Discounts.PercentDiscount;
import BusinessLayer.Supplier.Discounts.NumberDiscount;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.DiscountDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierProductDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SupplierProductBusiness {
    private int supplierNum;
    private String name;
    private int productNum;
    private String manufacturer;
    private float price;
    private int maxAmount;
    private List<Discount> quantitiesAgreement;
    private LocalDate expiryDate;

    private SupplierProductDTO supplierProductDTO;

    protected SupplierDalController supplierDalController;

    public SupplierProductBusiness(int supplierNum, String name, int productNum, String manufacturer, float price, int maxAmount, LocalDate expiryDate, SupplierDalController supplierDalController) throws SQLException {
        this.supplierNum = supplierNum;
        this.name = name;
        this.productNum = productNum;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
        this.quantitiesAgreement = new ArrayList<>();
        this.expiryDate = expiryDate;
        this.supplierDalController = supplierDalController;
        this.supplierProductDTO = new SupplierProductDTO(supplierNum, productNum, name, manufacturer, price, maxAmount, expiryDate.toString());
        supplierDalController.insert(supplierProductDTO);
    }

    public SupplierProductBusiness(SupplierProductDTO supplierProductDTO, SupplierDalController supplierDalController){
        this.quantitiesAgreement = new ArrayList<>();
        this.supplierNum = supplierProductDTO.getSupplierNum();
        this.name = supplierProductDTO.getName();
        this.productNum = supplierProductDTO.getProductNum();
        this.manufacturer = supplierProductDTO.getManufacturer();
        this.price = supplierProductDTO.getPrice();
        this.maxAmount = supplierProductDTO.getMaxAmount();
        this.supplierDalController = supplierDalController;
        this.supplierProductDTO = supplierProductDTO;
        this.expiryDate = LocalDate.parse(supplierProductDTO.getExpiryDate());
    }

    public boolean isDiscountExists(int productAmount, boolean isPercentage){
        for(Discount dis:quantitiesAgreement) {
            if (dis.isPercentage() == isPercentage && dis.getAmount() == productAmount)
                return true;
        }
        return false;
    }

    private boolean isDiscountValid(int productAmount, float discount, boolean isPercentage){
        boolean valid = true;
        for(Discount dis: quantitiesAgreement) {
            if (dis.isPercentage() == isPercentage && productAmount > dis.getAmount() && discount <= dis.getDiscount()){
                valid = false;
                break;
            }
        }
        return valid;
    }

    public void editProductDiscount(int productAmount, float newDiscount, boolean isPercentage) throws Exception {
        if(!isDiscountExists(productAmount, isPercentage))
            throw new Exception("Discount doesn't exists");
        if(!isDiscountValid(productAmount, newDiscount, isPercentage))
            throw new Exception("Discount details are not valid");
        for (Discount dis : quantitiesAgreement) {
                if (dis.isPercentage() == isPercentage && dis.getAmount() == productAmount) {
                    dis.editDiscount(productAmount, newDiscount);
                    DiscountDTO newDTO=null;
                    if(isPercentage)
                            newDTO=new DiscountDTO(supplierNum, productAmount, newDiscount, "true","false","false", productNum);
                    else
                            newDTO=new DiscountDTO(supplierNum, productAmount, newDiscount, "false","false","false", productNum);

                    supplierDalController.update(dis.getDiscountDTO(), newDTO);
                    dis.setDiscountDTO(newDTO);
                }

            }
    }

    public void addProductDiscount(int productAmount, float discount, boolean isPercentage) throws Exception {
        if(isDiscountExists(productAmount, isPercentage))
            throw new Exception("Discount already exists");
        if(!isDiscountValid(productAmount, discount, isPercentage))
            throw new Exception("Discount details are not valid");
        DiscountDTO newone=null;
            if (isPercentage){
                newone =new DiscountDTO(supplierNum, productAmount, discount, "true","false",
                        "false",productNum);
                quantitiesAgreement.add(new PercentDiscount(productAmount, discount, true, supplierDalController,newone));
                supplierDalController.insert(newone);
            }
            else {
                 newone = new DiscountDTO(supplierNum, productAmount, discount, "false","false","false", productNum);
                quantitiesAgreement.add(new NumberDiscount(productAmount, discount, false, supplierDalController,newone));
                supplierDalController.insert(newone);

            }
    }

    public void addProductDiscount(DiscountDTO productDiscountDTO) throws SQLException {
        if(productDiscountDTO.isPercentage().equals("true"))
            quantitiesAgreement.add(new PercentDiscount(productDiscountDTO, supplierDalController));
        else
            quantitiesAgreement.add(new NumberDiscount(productDiscountDTO, supplierDalController));
    }

    public void deleteProductDiscount(int productAmount, float discount, boolean isPercentage) throws Exception {
        if(!isDiscountExists(productAmount, isPercentage))
            throw new Exception("Discount doesn't exists");
        Discount curr = null;
        for(Discount dis:quantitiesAgreement){
            if(dis.isPercentage() == isPercentage && dis.getAmount() == productAmount && dis.getDiscount() == discount)
                curr = dis;
        }
        supplierDalController.delete(curr.getDiscountDTO());
        quantitiesAgreement.remove(curr);

    }

    public boolean hasEnoughQuantity(int quantity){
        return maxAmount >= quantity;
    }

    public float getPriceByQuantity(int quantity){
        quantity= Math.min(maxAmount, quantity);
        float maxDiscount = 0;
        Discount dis = null;
        for (Discount currentDiscount : quantitiesAgreement) {
            if(quantity*price-currentDiscount.getPriceAfterDiscount(quantity*price) > maxDiscount && quantity >= currentDiscount.getAmount()) {
                dis = currentDiscount;
                maxDiscount = quantity*price-currentDiscount.getPriceAfterDiscount(quantity*price);
            }
        }
            return quantity*price-maxDiscount;
        }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public float getPrice() {
        return price;
    }

    public int getProductNum() {
        return productNum;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
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

    public void editProduct(float price, int maxAmount, LocalDate expiryDate) throws SQLException {
        this.price = price;
        this.maxAmount = maxAmount;
        this.expiryDate = expiryDate;
        SupplierProductDTO newDTO = new SupplierProductDTO(supplierNum,productNum, name, manufacturer, price, maxAmount, expiryDate.toString());
        supplierDalController.update(supplierProductDTO, newDTO);
        supplierProductDTO = newDTO;
    }

    @Override
    public String toString() {
        return
                "Supplier Number: " + supplierNum +
                ", Product Name: " + name + "  " +
                ", Product Number: " + productNum +
                ", Manufacturer: " + manufacturer + "  " +
                ", Price: " + price +
                ", Max quantity in stock: " + maxAmount +
                ", expiryDate: " + expiryDate;

    }

    public SupplierProductDTO getSupplierProductDTO() {
        return supplierProductDTO;
    }
}
