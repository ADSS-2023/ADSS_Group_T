package PresentationLayer.GUI;

import PresentationLayer.GUI.Components.GeneralFrame;
import ServiceLayer.Supplier_Stock.ServiceFactory;

public class MainGui {
    public static void main(String[] args) {
        ServiceFactory sf = new ServiceFactory();
        GeneralFrame generalFrame = new GeneralFrame(sf);
        generalFrame.run();
    }
}
