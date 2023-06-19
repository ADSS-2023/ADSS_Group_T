package Main;

import GUI.MainObject;
import PresentationLayer.MainPresentation;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            System.out.println("the system didn't get any parameters please try again ");
        }
        else if (args.length == 1) {
            if (args[0].equals("GUI")) {
                MainObject mainObject = new MainObject("");
            }
            if (args[0].equals("CLI")) {
                MainPresentation mainPresentation = new MainPresentation();
                mainPresentation.start();
            }
        }
//        else if ((args.length == 2)) {
//            if (args[0].equals("GUI") && args[1] instanceof String) {
//                MainObject mainObject = new MainObject((String)args[1]);
//            }
//            if (args[0].equals("CLI") && args[1] instanceof String){
//                MainPresentation mainPresentation = new MainPresentation((String)args[1]);
//                mainPresentation.start();
//            }
//        }
        else { System.out.println("the system didn't recognize those parameters please try again");}
        }
    }