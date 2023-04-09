package ServiceLayer.Transport;

public class Main {
    private DeliveryManagerService dmS;
        public static void main(String[] args) throws Exception {

            while(true)
            {
                try{
                    DeliveryManagerService DMS = new DeliveryManagerService();
                    DMS.start();
                }
                catch(Exception e)
                {
                    System.out.println("Error");
                    main(args);

                }
            }
        }
    }



    
    
    
    




