package ServiceLayer.Transport;

public class Main {
        public static void main(String[] args) throws Exception {
            while(true)
            {
                try{
                    TransportPresentation tp = new TransportPresentation();
                    tp.start();
                }
                catch(Exception e)
                {
                    System.out.println("Error");
                    main(args);
                }
            }
        }
    }



    
    
    
    




