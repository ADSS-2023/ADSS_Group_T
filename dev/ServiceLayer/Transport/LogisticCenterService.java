package ServiceLayer.Transport;

import BusinessLayer.Transport.LogisticCenterController;

public class LogisticCenterService {
    private final LogisticCenterController logisticCenterController;
    private final TransportJsonConvert transportJsonConvert;

    public LogisticCenterService(LogisticCenterController logisticCenterController) {
        this.logisticCenterController = logisticCenterController;
        this.transportJsonConvert = new TransportJsonConvert();
    }

    public String getProductsInStock() {
        try {
            return transportJsonConvert.productAndAmountToString(logisticCenterController.getProductsInStock());
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String addTruck(int licenseNumber, String model, int weight, int maxWeight, int coolingLevel) {
        try {
            if (logisticCenterController.addTruck(licenseNumber, model, weight, maxWeight, coolingLevel))
                return "truck added to logistic center";
            else
                return "cant add truck to logistic center";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String getAddress() {
        try {
            return this.logisticCenterController.getAddress();
        }
        catch (Exception ex){
            return ex.getMessage();
        }

    }
}
