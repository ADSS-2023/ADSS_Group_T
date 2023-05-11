package ServiceLayer.Transport;

import BusinessLayer.Transport.BranchController;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;

public class BranchService {
    private BranchController branchController;
    public BranchService(BranchController branchController) {
        this.branchController = branchController;
    }

    public String getAllBranches() {
        Response response = new Response();
        try {
            response.setReturnValue(TransportJsonConvert.convertCollectionToString(branchController.getAllBranches().keySet()));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String addBranch(String address, String telNumber, String contactName, int x, int y) {
        Response response = new Response();
        try {
            branchController.addBranch(address, telNumber, contactName, x, y);
            response.setReturnValue("OK");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String getBranchAddress(String address) {
        Response response = new Response();
        try {
            response.setReturnValue(branchController.getBranch(address).getAddress());
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

//    public String getAllBranches() {
//        try {
//            return TransportJsonConvert.convertCollectionToString(branchController.getAllBranches().keySet());
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }
//
//    public String addBranch(String address, String telNumber, String contactName, int x, int y) {
//        Response response = new Response();
//        try {
//            branchController.addBranch(address, telNumber, contactName, x, y);
//            response.setReturnValue("OK");
//            return ResponseSerializer.serializeToJson(response);
//
//        } catch (Exception ex) {
//            response.setErrorMessage(ex.getMessage());
//            return ResponseSerializer.serializeToJson(response);
//        }
//    }
//
//    public String getBranchAddress(String address) {
//        try {
//            return branchController.getBranch(address).getAddress();
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }
}
