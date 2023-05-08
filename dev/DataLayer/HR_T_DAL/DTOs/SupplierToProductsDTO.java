package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class SupplierToProductsDTO extends DTO {

    private String supplierAddress;

    private String productName;

    public SupplierToProductsDTO(String supplierAddress, String productName) {
        super("SupplierToProducts");
        this.supplierAddress = supplierAddress;
        this.productName = productName;
    }
}
