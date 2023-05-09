package BusinessLayer.Supplier;

import DataLayer.Inventory_Supplier_Dal.DAO.SupplierDAO.OrderProductDAO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.OrderProductDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.OrderDalController;

import java.sql.SQLException;
import java.time.LocalDate;

public class OrderProduct {
    private String productName;
    private String manufacturer;
    private LocalDate expiryDate;
    private int productNumber;
    private int quantity;
    private float initialPrice;
    private float discount;
    private float finalPrice;

    private OrderProductDTO orderProductDTO;

    private OrderDalController orderDalController;

    public OrderProduct(String productName, int productNumber, int quantity, float initialPrice, float discount, float finalPrice, String manufacturer, LocalDate expiryDate, OrderProductDTO orderDTO) {
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.orderProductDTO = orderDTO;
    }

    public void setOrderProductDTO(OrderProductDTO orderProductDTO) throws SQLException {
        this.orderDalController.update(orderProductDTO, orderProductDTO);
        this.orderProductDTO = orderProductDTO;
    }

    public OrderProduct clone(){
        return new OrderProduct(
                productName, productNumber, quantity, initialPrice, discount, finalPrice,
                manufacturer, expiryDate, orderProductDTO);
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

    @Override
    public String toString() {
        return
                "productName: " + productName + '\'' +
                ", productNumber: " + productNumber +
                ", quantity: " + quantity +
                ", initialPrice: " + initialPrice +
                ", discount: " + discount +
                ", finalPrice: " + finalPrice + "\n";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
