package Main;

import PresentationLayer.MainPresentation;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception {
       MainPresentation mainPresentation = new MainPresentation();
       mainPresentation.start();
    }

}