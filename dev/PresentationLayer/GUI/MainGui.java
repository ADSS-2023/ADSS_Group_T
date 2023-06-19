package PresentationLayer.GUI;

import PresentationLayer.GUI.Components.GeneralFrame;
import PresentationLayer.Stock.StockUI;
import PresentationLayer.Supplier.SupplierManager;
import ServiceLayer.Supplier_Stock.ServiceFactory;

public class MainGui {

    public MainGui(){

    }

    public void run(String user){
                ServiceFactory sf = new ServiceFactory();
                SupplierManager supplierManager = new SupplierManager(sf);
                StockUI stockUI = new StockUI(sf);
                GeneralFrame generalFrame = new GeneralFrame(sf, stockUI, supplierManager);
                generalFrame.run(user);
        }
    }

