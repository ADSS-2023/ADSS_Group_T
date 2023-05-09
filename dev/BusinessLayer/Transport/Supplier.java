package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DTOs.SupplierDTO;
import DataLayer.HR_T_DAL.DalService.DalDeliveryService;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Supplier extends Site {

    private LinkedHashMap<String, Product> products;

    private DalDeliveryService dalDeliveryService;

    public Supplier(String address, String telNumber, String contactName, int x, int y,DalDeliveryService dalDeliveryService) {
        super(address, telNumber, contactName, x, y);
        this.products = new LinkedHashMap<String, Product>();
        this.dalDeliveryService = dalDeliveryService;
    }

    public Supplier(SupplierDTO dto,DalDeliveryService dalDeliveryService){
        this(dto.getSupplierAddress(), dto.getTelNumber(), dto.getContactName(), dto.getX(), dto.getY(),dalDeliveryService);
    }

    public void addProduct(String productName, int productCoolingLevel) {
        if (products.containsKey(productName))
            throw new IllegalArgumentException("product already exist");
        else {
            try {
                if(dalDeliveryService.findProduct(productName) != null)
                    throw new IllegalArgumentException("product already exist");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                dalDeliveryService.insertSupplierToProducts(address,productName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Product product = new Product(productName, productCoolingLevel);
            products.put(productName, product);
        }
    }

    public Product getProduct(String productName){
        Product p;
        if (!products.containsKey(productName))
            try {
                p = dalDeliveryService.findProduct(productName);
                if(p==null)
                    return null;
                products.put(productName,p);
                return p;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        else{
            return products.get(productName);
        }
    }

    public LinkedHashMap<String, Product> getAllProducts() {
        try {
            products = dalDeliveryService.findAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}

