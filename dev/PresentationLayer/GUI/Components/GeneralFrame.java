package PresentationLayer.GUI.Components;

import PresentationLayer.GUI.SupplierGUI.AllSupplierFrame;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.Response;
import ServiceLayer.Supplier_Stock.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class GeneralFrame extends JFrame {
    private ServiceFactory sf;
    private StockUI stockUI;
    private  SupplierManager supplierManager;

    public GeneralFrame(ServiceFactory sf, StockUI stockUI, SupplierManager supplierManager) {
        this.sf = sf;
        this.stockUI = stockUI;
        this.supplierManager = supplierManager;
    }

    public void run(String userChoise) {
        String[] options = {"Load data", "Empty system", "Set data to the system"};
        int action = JOptionPane.showOptionDialog(null, "Welcome to Superly inventory and supplier system", "Superly",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        try {
            if (action == 0) {
                // Read from DB
                loadData(sf);


            } else if (action == 1) {
                sf.supplierService.deleteAll();
                sf.orderService.deleteAllOrders();
                sf.deleteAllData();
            }
            else if (action == 2) {
                stockUI.deleteData();
                supplierManager.deleteAll();
                sf.uss.setUpDate();
                stockUI.setUpData();
                supplierManager.setUpData();
            }
        } catch (Exception c) {

        }
        login(sf,userChoise);


    }
    public void login(ServiceFactory sf, String userChoise) {
        String id = "";
        if (userChoise.equals("StoreManager"))
            id = "1";
        else if (userChoise.equals("Stock"))
            id = "2";
        else if (userChoise.equals("Supplier")) {
            id = "3";
        }
        Response res = sf.userService.login(id);
        if (res.isError()) {
            JOptionPane.showMessageDialog(null, "Login Failed: " + res.getErrorMassage(), "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            continueToFrame(res, sf);
        }
    }



    private static void loadData(ServiceFactory sf) {
        try {
            sf.uss.loadDate();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"failed to load data");
        }

        sf.userService.loadData();
        sf.inventoryService.loadData();
        ///TODO ask goz what need to be loaded
        sf.orderService.loadOrders();
        sf.supplierService.loadSuppliers();
        sf.manageOrderService.loadData();

    }

    private  void continueToFrame(Response res,ServiceFactory sf) {
        if (((String) res.getValue().toString()).equals("WareHouse")) {
            continueToInventory(sf);
        }
        else if(((String) res.getValue().toString()).equals("Suppliers")) {
            continueToSupplier(sf);
        }
        else {
            continueToManager(sf);
        }
    }
    public void continueToManager(ServiceFactory sf){
        ManagerFrame managerFrame = new ManagerFrame(sf);
        managerFrame.setLogOutCallBack(() -> dispose());
        managerFrame.setInventoryCallBack(()->continueToInventory(sf));
        managerFrame.setSupllierCallBack(()->continueToSupplier(sf));
        run(managerFrame);
    }
    public void continueToSupplier(ServiceFactory sf){
        AllSupplierFrame allSupplierFrame = new AllSupplierFrame(sf);
        allSupplierFrame.setLogOutCallBack(()->dispose());
        allSupplierFrame.setManagerFrameCallBack(()->continueToManager(sf));
        run(allSupplierFrame);
    }
    public void continueToInventory(ServiceFactory sf){
        StockFrame stockFrame = new StockFrame(sf);
        stockFrame.setLogOutCallBack(()-> dispose());
        stockFrame.setManagerCallBack(()->continueToManager(sf));
        run(stockFrame);
    }



    private static void run(JFrame curFrame) {
        curFrame.setVisible(true);
    }
}

