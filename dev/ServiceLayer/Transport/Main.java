package ServiceLayer.Transport;

public class Main {
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



    
    
    
    




