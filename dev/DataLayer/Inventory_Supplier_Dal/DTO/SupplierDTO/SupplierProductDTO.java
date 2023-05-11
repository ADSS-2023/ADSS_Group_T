package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

import java.time.LocalDate;

public class SupplierProductDTO extends DTO {

    private int supplierNum;
    private int productNum;
    private String name;
    private String manufacturer;
    private float price;
    private int maxAmount;
    private String expiryDate;

    public int getSupplierNum() {
        return supplierNum;
    }

    public int getProductNum() {
        return productNum;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getPrice() {
        return price;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public SupplierProductDTO(int supplierNum, int productNum, String name, String manufacturer, float price, int maxAmount, String expiryDate) {
        super("supplier_supplier_product");
        this.supplierNum = supplierNum;
        this.productNum = productNum;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxAmount = maxAmount;
        this.expiryDate = expiryDate;
    }
    public SupplierProductDTO() {
        super("supplier_supplier_product");

    }
}
