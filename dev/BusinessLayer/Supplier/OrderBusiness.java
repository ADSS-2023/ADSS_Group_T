package BusinessLayer.Supplier;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class OrderBusiness {

    private int orderNum;
    private String supplierName;
    private LocalDateTime orderDate;
    private String supplierAddress;
    private String destinationAddress;
    private int supplierNum;
    private String contactName;
    private int contactNumber;
    private List<OrderProduct> products ;

    public OrderBusiness(int orderNum, String supplierName, LocalDateTime  orderDate,
                         String supplierAddress, String destinationAddress
            ,int supplierNum, String contactName, int contactNumber,
                       List<OrderProduct> products) {
        this.orderNum = orderNum;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        this.supplierAddress = supplierAddress;
        this.destinationAddress = destinationAddress;
        this.supplierNum = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.products = products;
    }



}
