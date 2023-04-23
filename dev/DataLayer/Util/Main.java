package DataLayer.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");

            // Open a connection to the database
            String url = "jdbc:sqlite:DataLayer/person_test.db";
            conn = DriverManager.getConnection(url);
            System.out.println("--------");
            // Use the connection here
            PersonDto person = new PersonDto("person", "ido", 26);
            DAO.insert(conn, person);
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println("ERROR: "+e.getMessage());
        }

    }

}
