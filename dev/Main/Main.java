package Main;

import PresentationLayer.HR_TransportPresentation;
import ServiceLayer.HR.Presentaition;
import UtilSuper.ServiceFactory;

public class Main {

    public static void main(String[] args) {
       HR_TransportPresentation hr_transportPresentation = new HR_TransportPresentation();
       hr_transportPresentation.start();
    }



}
