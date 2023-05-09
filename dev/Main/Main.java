package Main;

import PresentationLayer.MainPresentation;
import UtilSuper.Time;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Time.getCurrDate());
        Thread.sleep(1000);
        System.out.println(Time.getCurrDate());



//       MainPresentation mainPresentation = new MainPresentation();
//       mainPresentation.start();
    }



}
