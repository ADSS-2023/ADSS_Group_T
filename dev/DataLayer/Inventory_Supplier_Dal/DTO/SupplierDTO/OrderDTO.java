package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDTO extends DTO {
    private int orderNum;
    private int supplierNum;
    private String supplierName;
    private String contactName;
    private String contactNumber;
    private String orderDate;
    private String supplierAddress;
    private String destinationAddress;
    private boolean orderSupplied;
    private int daysToDeliver;
    private String constantDay;

    public OrderDTO(int orderNum, int supplierNum, String contactName, String contactNumber, String orderDate,
                    String supplierAddress, String destinationAddress, boolean orderSupplied, int daysToDeliver, String constantDay) {
        super("supplier_order");
        this.orderNum = orderNum;
        this.supplierNum = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.orderDate = orderDate;
        this.supplierAddress = supplierAddress;
        this.destinationAddress = destinationAddress;
        this.orderSupplied = orderSupplied;
        this.daysToDeliver = daysToDeliver;
        this.constantDay = constantDay;
    }

    public OrderDTO() {
        super("supplier_order");
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public boolean isOrderSupplied() {
        return orderSupplied;
    }

    public int getDaysToDeliver() {
        return daysToDeliver;
    }

    public String getConstantDay() {
        return constantDay;
    }


}
