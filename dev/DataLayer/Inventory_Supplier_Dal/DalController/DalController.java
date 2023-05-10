package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DalController {
    private DAO dal_dao;

    private DalController(){
        dal_dao = new DAO();
        Connection connection = makeCon();
    }

    private Connection makeCon(){
        try {
            String dbFile = "C:/liran/Program/SMSRT4/ADSS/ADSS_Group_T/dev/DataLayer/stock_supplier_db.db";
            // JDBC conection parameters
            String url = "jdbc:sqlite:" + dbFile;
            // Register the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // sen it to the constructor
            // Establish the connection
            return DriverManager.getConnection(url);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insert(DTO new_dto) {
        String dbFile = "C:/liran/Program/SMSRT4/ADSS/ADSS_Group_T/dev/DataLayer/stock_supplier_db.db";

        // JDBC connection parameters
        String url = "jdbc:sqlite:" + dbFile;

        try {
            // Register the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // send it to the constructor

            // Establish the connection
            Connection connection = DriverManager.getConnection(url);
            dal_dao.insert(connection , new_dto);
            // Connection successful
            System.out.println("Connected to the database.");

            // Use the connection as needed

            // ...

            // Close the connection when done
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

}

