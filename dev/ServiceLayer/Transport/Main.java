package ServiceLayer.Transport;

import PresentationLayer.MainPresentation;

public class Main {
    public static void main(String[] args) throws Exception {
        while(true)
        {
            try{
                MainPresentation mainPresentation = new MainPresentation();
                mainPresentation.start();
            }
            catch(Exception e)
            {
                System.out.println("Error");
                main(args);
            }
        }
    }
}



    
    
    
    




