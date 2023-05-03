package ServiceLayer.Transport;

import BusinessLayer.Transport.DeliveryController;
import UtilSuper.EnterWeightInterface;
import UtilSuper.OverweightActionInterface;

import java.util.LinkedHashMap;

public class DeliveryService {
    private final TransportJsonConvert transportJsonConvert;
    public DeliveryController deliveryController;
    private EnterWeightInterface enterWeightInterface;
    private OverweightActionInterface overweightAction;

    public DeliveryService(DeliveryController deliveryController) {
        this.deliveryController = deliveryController;
        deliveryController.setEnterWeightInterface((String address, int deliveryID) -> enterWeightInterface.enterWeightFunction(address, deliveryID));
        deliveryController.setOverweightAction((int deliveryID) -> overweightAction.EnterOverweightAction(deliveryID));
        transportJsonConvert = new TransportJsonConvert();
    }

    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
                                String requiredDateString) {
        try {
            return (transportJsonConvert.suppliersAndProductsToString(transportJsonConvert.mapToFile(deliveryController.orderDelivery(branchString, suppliersString, requiredDateString))));
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String skipDay() {
        try {
            return (transportJsonConvert.deliveryListToString(deliveryController.skipDay()));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String overWeightAction(int id, int action, String address, int weight) {
        try {
            deliveryController.overWeightAction(id, action, address, weight);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllDeliveriesDetail() {
        try {
            return (transportJsonConvert.deliveryListToString(deliveryController.getAllDeliveries().values()));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getDeliveryDetail(int deliveryID) {
        try {
            return transportJsonConvert.deliveryToString(deliveryController.getDelivery(deliveryID));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getLoadedProducts(int deliveryID, String address) {
        try {
            return transportJsonConvert.fileToString(deliveryController.getLoadedProducts(deliveryID, address));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getNextDayDetails() {
        try {
            return transportJsonConvert.deliveryListToString(deliveryController.getNextDayDeatails());
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getCurrDate() {
        try {
            return this.deliveryController.getCurrDate().toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public void setOverweightAction(OverweightActionInterface overweightAction) {
        this.overweightAction = overweightAction;
    }

}
