package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;

import java.sql.SQLException;
import java.util.LinkedHashMap;


public class LogisticCenterController {


    private final LogisticCenter logisticCenter;


    public LogisticCenterController(DalLogisticCenterService dalLogisticCenterService) throws Exception {
        SiteDTO siteDTO = dalLogisticCenterService.findLogisticCenter();
        this.logisticCenter = new LogisticCenter(siteDTO,dalLogisticCenterService);
    }

    /**
     * store products in the logistics center stocks
     *
     * @param newSupply - map with the amount for each product required to store
     */
    public void storeProducts(LinkedHashMap<Product, Integer> newSupply) throws Exception {
        logisticCenter.storeProducts(newSupply);
    }

    public LogisticCenter getLogisticCenter() {
        return logisticCenter;
    }

    /**
     * load products from the stock of the logistics center
     *
     * @param requestedSupply - map of the products and amounts required to load
     * @return map of products and amounts that are not available in the logistics center stock
     */
    public LinkedHashMap<Product, Integer> removeProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) throws SQLException {
        return logisticCenter.loadProductsFromStock(requestedSupply);
    }
    public LinkedHashMap<Product, Integer> removeFileFromStock(File file) throws SQLException {
        return logisticCenter.loadProductsFromStock(file.getProducts());

    }

    public LinkedHashMap<Product, Integer> getProductsInStock() throws Exception {
        return logisticCenter.getProductsInStock();
    }

    /**
     * add a new truck to the trucks map
     *
     * @param licenseNumber - the license number of the truck
     * @param model         - the truck model
     * @param weight        - the weight of the truck without supply
     * @param maxWeight     - max weight of the truck with supply
     * @param -             the license type required to drive the truck
     * @param coolingLevel  - the cooling level of the truck
     * @return true if the truck added successfully , and false otherwise
     */
    public boolean addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) throws Exception {
        return logisticCenter.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel);
    }

    public LinkedHashMap<Integer, Truck> getAllTrucks() throws Exception {
        return logisticCenter.getAllTrucks();
    }

    public Truck getTruck(int licenseNumber) throws Exception {
        return logisticCenter.getTruck(licenseNumber);
    }

    public String getAddress() {
        return this.logisticCenter.getAddress();
    }
}

