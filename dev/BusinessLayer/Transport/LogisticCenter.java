package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DAOs.LogisticDAO;
import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class LogisticCenter extends Site {

    private LinkedHashMap<Product, Integer> productsInStock;
    private DalLogisticCenterService dalLogisticCenterService;

    public LogisticCenter(DalLogisticCenterService dalLogisticCenterService) {
        super("logistic center address", "0000000000", "logictic center manager", 0, 0);
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.productsInStock = new LinkedHashMap<>();
    }
    public LogisticCenter(SiteDTO siteDTO, DalLogisticCenterService dalLogisticCenterService){
        super(siteDTO.getSiteAddress(),siteDTO.getTelNumber(),siteDTO.getContactName(),siteDTO.getX(),siteDTO.getY());
        this.dalLogisticCenterService = dalLogisticCenterService;
        this.productsInStock = new LinkedHashMap<>();
    }

    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) throws Exception {
        if (getAllTrucks().containsKey(licenseNumber) || dalLogisticCenterService.findTruck(licenseNumber) != null)
            throw new RuntimeException("trucks contains licenseNumber");
        dalLogisticCenterService.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
        return true;
    }


    public void storeProducts(LinkedHashMap<Product, Integer> newSupply)  throws Exception {
        for (Map.Entry<Product, Integer> entry : newSupply.entrySet()) {
            Product key = entry.getKey();
            Integer value = entry.getValue();
            if (productsInStock.containsKey(key)) {
                int oldAmount = productsInStock.get(key);
                int newAmount = oldAmount + value;
                productsInStock.replace(key, newAmount);//product exist in stock - update amount
                dalLogisticCenterService.updateProductToStock(key.getName(), oldAmount, newAmount);
            } else {
                productsInStock.put(key, value);
                dalLogisticCenterService.insertProductToStock(key.getName(), value);
            }
        }
    }

    /**
     * load products from the stock of the logistics center
     *
     * @param requestedSupply - map of the products and amounts required to load
     * @return map of products and amounts that are not available in the logistics center stock
     */
    public LinkedHashMap<Product, Integer> loadProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) throws SQLException {
        HashSet<Product> keys = new HashSet<>(requestedSupply.keySet());
        for (Product p : keys) {
            int oldAmount = productsInStock.get(p);
            int newAmount = oldAmount - requestedSupply.get(p);
            if (productsInStock.containsKey(p) && productsInStock.get(p) >= requestedSupply.get(p)) {    //product exist in stock in the requested amount
                productsInStock.replace(p, newAmount);
                dalLogisticCenterService.updateProductToStock(p.getName(),oldAmount,newAmount);
                requestedSupply.remove(p);
                if (productsInStock.get(p) == 0){
                    productsInStock.remove(p);
                    dalLogisticCenterService.deleteProductFromStock(p.getName(),newAmount);
                }

            } else if (productsInStock.containsKey(p)) {  //product exist in stock but not in the requested amount
                requestedSupply.replace(p, requestedSupply.get(p) - productsInStock.get(p));
                productsInStock.remove(p);
                dalLogisticCenterService.deleteProductFromStock(p.getName(),oldAmount);
            }
        }
        return requestedSupply;
    }

    public LinkedHashMap<Product, Integer> getProductsInStock() throws Exception {
        productsInStock = dalLogisticCenterService.findAllProductsInStock();
        if(productsInStock == null || productsInStock.isEmpty())
            throw new Exception("no products in stock");
        return productsInStock;
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() throws Exception {
        LinkedHashMap<Integer, Truck> trucks = dalLogisticCenterService.findAllTrucks();
        if (trucks == null || trucks.isEmpty())
            throw new RuntimeException("no trucks in the system");
        return trucks;
    }

    public Truck getTruck(int licenseNumber) throws Exception {
        if(!getAllTrucks().containsKey(licenseNumber)) {
            Truck truck = dalLogisticCenterService.findTruck(licenseNumber);
            if (truck == null)
                throw new Exception("no truck in the system");
            return truck;
        }
        return getAllTrucks().get(licenseNumber);
    }
}
