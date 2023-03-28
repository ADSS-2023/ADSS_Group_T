package BusinessLayer.Supplier;

import java.util.Date;
import java.util.HashMap;


public class OrderBusiness {

    private int orderNum;
    private String supplierName;
    private Date orderDate;
    private String supplierAddress;
    private String destinationAddress;
    private int supplierNum;
    private int contactName;
    private int contactNumber;
    private HashMap<Integer,OrderProduct> products ;

    public OrderBusiness(int orderNum, String supplierName, Date orderDate,
                         String supplierAddress, String destinationAddress
            ,int supplierNum, int contactName, int contactNumber,
                         HashMap<Integer, OrderProduct> products) {
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
