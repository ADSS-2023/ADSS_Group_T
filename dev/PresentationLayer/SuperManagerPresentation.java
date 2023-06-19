package PresentationLayer;

import java.sql.SQLException;
import java.util.Scanner;

public class SuperManagerPresentation {

    private final TransportManagerPresentation transportManagerPresentation;
    private final HRManagerPresentation hrManagerPresentation;
    private final EmployeePresentation employeePresentation;
    private final DriverPresentation driverPresentation;
    public SuperManagerPresentation(EmployeePresentation employeePresentation,DriverPresentation driverPresentation,
                                    TransportManagerPresentation transportManagerPresentation,HRManagerPresentation hrManagerPresentation){
        this.employeePresentation = employeePresentation;
        this.driverPresentation = driverPresentation;
        this.transportManagerPresentation = transportManagerPresentation;
        this.hrManagerPresentation = hrManagerPresentation;
    }

    public void start(int id) throws SQLException {
        mainWindow(id);
    }

    private void mainWindow(int id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------ SuperManager main window -------");
            System.out.println("Please choose an option:");
            System.out.println(
                    " \n 1. register As HR Manager" +
                    " \n 2. register As Transport Manager" +
                    " \n 3. register As Employee" +
                    " \n 4. register As Driver" +
                    " \n 5. logout "
            );
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1 -> hrManagerPresentation.start();
                case 2 -> transportManagerPresentation.start();
                case 3 -> employeePresentation.start(id);
                case 4 -> driverPresentation.start();
                case 5 -> {return;}//exit
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
