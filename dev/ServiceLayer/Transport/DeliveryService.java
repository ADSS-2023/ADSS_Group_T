
package ServiceLayer.Transport;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import ServiceLayer.HR.Response;




import BusinessLayer.Transport.*;
import UtilSuper.EnterWeightInterface;
import UtilSuper.OverweightActionInterface;


public class DeliveryService {

    public DeliveryController deliveryController;
    private EnterWeightInterface enterWeightInterface;
    private OverweightActionInterface overweightAction;
    private TransportJsonConvert transportJsonConvert;

    public DeliveryService() {
        this.deliveryController = new DeliveryController();
        deliveryController.setEnterWeightInterface((String address, int deliveryID) -> enterWeightInterface.enterWeightFunction(address,deliveryID));
        deliveryController.setOverweightAction((int deliveryID) -> overweightAction.EnterOverweightAction(deliveryID));
        transportJsonConvert = new TransportJsonConvert();
    }

    public String orderDelivery(String branchString, LinkedHashMap<String, LinkedHashMap<String, Integer>> suppliersString,
                                String requiredDateString) {
        Response res = new Response();
        try {
            return (transportJsonConvert.suppliersAndProductsToString(transportJsonConvert.mapToFile(deliveryController.orderDelivery(branchString,suppliersString,requiredDateString))));

        }
        catch (Exception ex) {
            return ex.getMessage();
        }


    }


    public String skipDay() {
        Response res = new Response();
        try {
           deliveryController.skipDay();
            return "";
        } catch (Exception ex) {
            return ex.toString();
        }
    }



    public String replaceTruck(int deliveryID) {
        Response res = new Response();
        try {
           deliveryController.replaceTruck(deliveryID);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

//    public String replaceOrDropSite(int deliveryID) {
//        try {
//           dC.replaceOrDropSite(deliveryID);
//            return "good";
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }

    public String overWeightAction(int id, int action,String address, int weight) {
        Response res = new Response();
        try {
           deliveryController.overWeightAction(id, action,address,weight);
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getAllDeliverys() {
        Response res = new Response();
        try {
            //tc.getAllDeliverys();
            return "good";
        } catch (Exception ex) {
            return ex.toString();
        }
    }




    public String getDeliveryDetail(int deliveryID) {
        return transportJsonConvert.deliveryToString(deliveryController.getDelivery(deliveryID));
    }
    public String getLoadedProducts(int deliveryID,String address) {
        return transportJsonConvert.fileToString(deliveryController.getLoadedProducts(deliveryID,address));
    }

    public String addBranch(String address,String telNumber,String contactName,int x,int y) {
       deliveryController.addBranch(address,telNumber,contactName,x,y);
        return "D";
    }

    public void addSupplier(String supplierAddress,String telNumber,String contactName,int coolingLevel, ArrayList<String> produces,int x,int y) {

       deliveryController.addSupplier(supplierAddress,telNumber,contactName, coolingLevel, produces,x,y);
    }

    public String loadWeight(int id, String address, int weight) {
        if (deliveryController.loadWeight(id, address, weight))
            return "true";
        else
            return "false";
    }

    public void setEnterWeightInterface(EnterWeightInterface enterWeightInterface) {
        this.enterWeightInterface = enterWeightInterface;
    }

    public void setOverweightAction(OverweightActionInterface overweightAction) {
        this.overweightAction = overweightAction;
    }

    public String getAllBranches() {
        return transportJsonConvert.branchesToString(deliveryController.getAllBranches());
    }

    public String getNextDayDeatails() {
       deliveryController.getNextDayDeatails();
        return "good";
    }

    public String getSupplierProducts(String supplier) {
        return transportJsonConvert.productsToString(deliveryController.getSupplierProducts(supplier));
    }

    public String getAllSuppliersAddress() {
        return transportJsonConvert.getAllSuppliersAddress(deliveryController.getAllSuppliers());
    }

    public void initLogisticCenterController(LogisticCenterService logisticCenterService) {
        this.deliveryController.initLogisticCenterController(logisticCenterService.lcC);
    }
}


























