package BusinessLayer.Supplier;

import java.time.LocalDateTime;
import java.util.List;


public class OrderBusiness {

    private final int orderNum;
    private String supplierName;
    private LocalDateTime orderDate;
    private String supplierAddress;
    private String destinationAddress;
    private int supplierNum;
    private String contactName;
    private String contactNumber;

    public List<OrderProduct> getProducts() {
        return products;
    }

    private List<OrderProduct> products ;

    @Override
    public String toString() {
        String s = "";
        for (OrderProduct order:products) {
            s=s+" "+order.toString();
        }
        return
                "Order Number: " + orderNum +
                ",Supplier Number: "+supplierNum+
                ",Products: " +"\n"+ s+"\n";

    }

    public OrderBusiness(int orderNum, String supplierName, LocalDateTime  orderDate,
                         String supplierAddress, String destinationAddress
            , int supplierNum, String contactName, String contactNumber,
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
