package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DTOs.SiteDTO;
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

    public Supplier(SiteDTO dto, DalDeliveryService dalDeliveryService){
        this(dto.getSiteAddress(), dto.getTelNumber(), dto.getContactName(), dto.getX(), dto.getY(),dalDeliveryService);
    }

    public void addProduct(String productName, int productCoolingLevel) throws SQLException {
        if (getAllProducts().containsKey(productName) ||
                dalDeliveryService.findProduct(productName) != null)
            throw new IllegalArgumentException("product already exist");
        dalDeliveryService.insertSupplierToProducts(address,productName);
        dalDeliveryService.addProduct(productName, productCoolingLevel);
    }

    public Product getProduct(String productName) throws SQLException {
        Product p;
        if (!products.containsKey(productName)) {
            p = dalDeliveryService.findProduct(productName);
            if (p == null)
                return null;
            products.put(productName, p);
            return p;
        }
        else
            return products.get(productName);
    }

    public LinkedHashMap<String, Product> getAllProducts() throws SQLException {
        products = dalDeliveryService.findAllProductsOfSupplier(this.address);
        return products;
    }
}

