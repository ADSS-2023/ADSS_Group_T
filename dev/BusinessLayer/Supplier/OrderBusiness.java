package BusinessLayer.Supplier;

import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


public class OrderBusiness {


    private int orderNum;
    private String supplierName;
    private LocalDate orderDate;
    private String supplierAddress;
    private String destinationAddress;
    private int supplierNum;
    private String contactName;
    private String contactNumber;
    private int daysToSupplied;
    private List<OrderProduct> products ;

    private OrderDTO orderDTO;

    private OrderDalController orderDalController;

    public OrderBusiness(int orderNum, String supplierName, LocalDate  orderDate,
                         String supplierAddress, String destinationAddress
            , int supplierNum, String contactName, String contactNumber,
                         List<OrderProduct> products, int daysToSupplied) {
        this.orderNum = orderNum;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        this.supplierAddress = supplierAddress;
        this.destinationAddress = destinationAddress;
        this.supplierNum = supplierNum;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.products = products;
        this.daysToSupplied = daysToSupplied;
        this.orderDTO = new OrderDTO(orderNum, supplierNum, contactName, contactNumber, orderDate.toString(), supplierAddress, destinationAddress);
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

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

    public void setDaysToSupplied(int daysToSupplied) {
        this.daysToSupplied = daysToSupplied;
    }

    public int getDaysToSupplied() {
        return daysToSupplied;
    }

    public OrderBusiness clone(int newOrderNum){
        List<OrderProduct> clonedProducts = new LinkedList<>();
        for (OrderProduct product:products)
            clonedProducts.add(product.clone());
        return new OrderBusiness(
                newOrderNum, supplierName, Util_Supplier_Stock.getCurrDay(), supplierAddress, destinationAddress,
                supplierNum,contactName,contactNumber,clonedProducts,daysToSupplied
        );
    }
    public String getSupplierName() {
        return supplierName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

}
