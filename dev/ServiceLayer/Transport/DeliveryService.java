package ServiceLayer.Transport;

import BusinessLayer.Transport.DeliveryController;
import UtilSuper.EnterWeightInterface;
import UtilSuper.EnterOverWeightInterface;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;

import java.util.LinkedHashMap;

public class DeliveryService {
    public DeliveryController deliveryController;
    private EnterWeightInterface enterWeightInterface;
    private EnterOverWeightInterface enterOverWeightInterface;
    private final TransportJsonConvert transportJsonConvert;

    public DeliveryService(DeliveryController deliveryController) {
        this.deliveryController = deliveryController;
        deliveryController.setEnterWeightInterface((String address, int deliveryID) -> enterWeightInterface.enterWeightFunction(address, deliveryID));
        deliveryController.setOverweightAction((int deliveryID) -> enterOverWeightInterface.EnterOverweightAction(deliveryID));
        transportJsonConvert = new TransportJsonConvert();
    }

    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
                                String requiredDateString) {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.suppliersAndProductsToString(
                    transportJsonConvert.mapToFile(deliveryController.orderDelivery(branchString, suppliersString, requiredDateString))));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String skipDay() {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.deliveryListToString(deliveryController.skipDay()));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String getDeliveryDetail(int deliveryID) {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.deliveryToString(deliveryController.getDelivery(deliveryID)));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String getLoadedProducts(int deliveryID, String address) {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.fileToString(deliveryController.getLoadedProducts(deliveryID, address)));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String getNextDayDetails() {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.deliveryListToString(deliveryController.getNextDayDeatails()));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String getCurrDateDetails() {
        try {
            Response response = new Response();
            response.setReturnValue(this.deliveryController.getCurrDateDetails().toString());
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public void setEnterOverWeightInterface(EnterOverWeightInterface enterOverWeightInterface) {
        this.enterOverWeightInterface = enterOverWeightInterface;
    }

    public String getCurrDate() {
        try {
            Response response = new Response();
            response.setReturnValue(this.deliveryController.getCurrDate().toString());
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

    public String showAllDeliveries() {
        try {
            Response response = new Response();
            response.setReturnValue(transportJsonConvert.deliveryListToString(this.deliveryController.getAllDeliveries().values()));
            return ResponseSerializer.serializeToJson(response);
        } catch (Exception ex) {
            Response response = new Response();
            response.setErrorMessage(ex.getMessage());
            return ResponseSerializer.serializeToJson(response);
        }
    }

//    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
//                                String requiredDateString) {
//        try {
//            return (transportJsonConvert.suppliersAndProductsToString(transportJsonConvert.mapToFile(deliveryController.orderDelivery(branchString, suppliersString, requiredDateString))));
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }
//
//    public String skipDay() {
//        try {
//            return (transportJsonConvert.deliveryListToString(deliveryController.skipDay()));
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//
//    public String getDeliveryDetail(int deliveryID) {
//        try {
//            return transportJsonConvert.deliveryToString(deliveryController.getDelivery(deliveryID));
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String getLoadedProducts(int deliveryID, String address) {
//        try {
//            return transportJsonConvert.fileToString(deliveryController.getLoadedProducts(deliveryID, address));
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String getNextDayDetails() {
//        try {
//            return transportJsonConvert.deliveryListToString(deliveryController.getNextDayDeatails());
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public String getCurrDateDetails() {
//        try {
//            return this.deliveryController.getCurrDateDetails().toString();
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
//        this.enterWeightInterface = enterWeightInterface;
//    }
//
//    public void setEnterOverWeightInterface(EnterOverWeightInterface enterOverWeightInterface) {
//        this.enterOverWeightInterface = enterOverWeightInterface;
//    }
//
//    public String getCurrDate() {
//        try {
//            return this.deliveryController.getCurrDate().toString();
//        }
//        catch (Exception exception){
//            return exception.toString();
//        }
//    }
//
//    public String showAllDeliveries() {
//        try {
//            return transportJsonConvert.deliveryListToString(this.deliveryController.getAllDeliveries());
//        }
//        catch (Exception exception){
//            return exception.toString();
//        }
//    }

}
