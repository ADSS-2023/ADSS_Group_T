package PresentationLayer.Supplier_Stock;

import PresentationLayer.GUI.MainGui;

public class GUI_CLI_main {
    public static void main(String[] args) {
        try {
            if(args.length < 2){throw new Exception("fewer arguments supplied");}
            switch (args[0]) {
                case "GUI":
                    MainGui mainGui = new MainGui();
                    //mainGui.run(args[1]);
                case "CLI":
                    UI_General uiGeneral = new UI_General();
                    uiGeneral.run(args[1]);

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
