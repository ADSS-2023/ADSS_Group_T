package ServiceLayer.Transport;
import java.io.Console;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.HashMap;

import BusinessLayer.Transport.Delivery;
import BusinessLayer.Transport.LogisticsCenter;


public class Main {
    private DeliveryManagerService dmS;
    
    
    
    
        public static void main(String[] args) {
            DeliveryManagerService DMS = new DeliveryManagerService();
            DMS.init();
            DMS.start();

            
            
            
        }

        public void init() {
            this.dmS = new DeliveryManagerService();
        }

        public void start() {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println(" ");
                System.out.println("------ main window -------");
                //System.out.println("Current date: " + dmSSS.getCurDate());
                System.out.println("Please choose an option:");
                System.out.println("1. Skip day");
                System.out.println("2. Enter new delivery");
                System.out.println("3. Enter new driver");
                System.out.println("4. Enter new truck");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                
                switch (choice) {
                    case 1:
                        skipDay();
                        break;
                    case 2:
                        enterNewDelivery();
                        break;
                    case 3:
                        enterNewDriver();
                        break;
                    case 4:
                        enterNewTruck();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
        public void skipDay(){
            this.dmS.skipDay();
        }

        public void enterNewDelivery(){


        }
        public void enterNewDriver(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the driver details:");
        
            // Get the driver ID
            System.out.println("Please enter the driver ID:");
            int driverId = scanner.nextInt();
            scanner.nextLine();
        
            // Get the driver name
            System.out.println("Please enter the driver name:");
            String driverName = scanner.nextLine();
        
            // Get the driver license type
            System.out.println("Please choose the driver license type:");
            System.out.println("1. C");
            System.out.println("2. C1");
            System.out.println("3. E");
            int licenseIndex = scanner.nextInt() - 1;
            this.dmS.addNewDriver(driverId,driverName,licenseIndex);
            System.out.println("Driver added successfully.");
        
            start(); // Return to main menu

        }
        public void enterNewTruck(){

        }
    
       

        
    }



    
    
    
    




