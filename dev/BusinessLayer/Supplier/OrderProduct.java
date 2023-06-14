package BusinessLayer.Supplier;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderProductDAO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.OrderProductDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.OrderDalController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderProduct {
    private String productName;
    private String manufacturer;
    private LocalDate expiryDate;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;
    private float finalPrice;

    public OrderProductDTO orderProductDTO;

    private OrderDalController orderDalController;

    public OrderProduct(String productName, int productNumber, int quantity, float initialPrice, float discount, float finalPrice, String manufacturer, LocalDate expiryDate, OrderProductDTO orderProductDTO, OrderDalController orderDalController) {
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.orderProductDTO = orderProductDTO;
        this.orderDalController=orderDalController;
    }

    public OrderProduct(OrderProductDTO orderProductDTO, OrderDalController orderDalController) {
        this.productName = orderProductDTO.getProductName();
        this.manufacturer = orderProductDTO.getManufacturer();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expiryDate = LocalDate.parse(orderProductDTO.getExpiryDate(), formatter);

        this.productNumber = orderProductDTO.getProductNumber();
        this.quantity = orderProductDTO.getQuantity();
        this.initialPrice = orderProductDTO.getInitialPrice();
        this.discount = orderProductDTO.getDiscount();
        this.finalPrice = orderProductDTO.getFinalPrice();
        this.orderProductDTO = orderProductDTO;
    }

    public void setOrderProductDTO(OrderProductDTO orderProductDTO) throws SQLException {
        this.orderDalController.insert( orderProductDTO);
        this.orderProductDTO = orderProductDTO;
    }

    public OrderProduct clone(){
        return new OrderProduct(
                productName, productNumber, quantity, initialPrice, discount, finalPrice,
                manufacturer, expiryDate, orderProductDTO,orderDalController);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getProductName() {
        return productName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getFinalPrice() {
        return finalPrice;
    }


    public void setInitialPrice(float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public OrderProductDTO getOrderProductDTO() {
        return orderProductDTO;
    }

    @Override
    public String toString() {
        return
                "productName: " + productName  +
                " |productNumber: " + productNumber +
                " |quantity: " + quantity +
                " |initialPrice: " + initialPrice +
                " |discount: " + discount +
                " |finalPrice: " + finalPrice + "\n";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
