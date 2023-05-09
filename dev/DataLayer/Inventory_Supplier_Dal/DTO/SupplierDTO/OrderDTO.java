package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

public class OrderDTO extends DTO {
    private int orderNum;
    private int supplierNum;
    private String contactName;
    private String contactNumber;
    private String orderDate;
    private String supplierAddress;
    private String destinationAddress;

    public OrderDTO( int orderNum, int supplierNum, String contactName, String contactNumber, String orderDate, String supplierAddress, String destinationAddress) {
        super("supplier_order");
        this.orderNum = orderNum;
        this.supplierNum = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.orderDate = orderDate;
        this.supplierAddress = supplierAddress;
        this.destinationAddress = destinationAddress;
    }
}
