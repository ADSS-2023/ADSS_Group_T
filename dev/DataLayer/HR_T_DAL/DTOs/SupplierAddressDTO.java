package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class SupplierAddressDTO extends DTO {

    private String supplierAddress;

    private String productName;

    public SupplierAddressDTO(String supplierAddress, String productName) {
        super("SupplierAddress");
        this.supplierAddress = supplierAddress;
        this.productName = productName;
    }
}
