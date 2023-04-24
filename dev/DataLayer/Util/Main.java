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

            // Open a connection to the database
            String url = "jdbc:sqlite:dev/DataLayer/person_test.db";
            conn = DriverManager.getConnection(url);
            System.out.println("--------");
            // Use the connection here
            PersonDto person = new PersonDto("person", "shlomi", 26);
            DogDto dogDto = new DogDto("dogs","lassi","coli",5);
            DAO.insert(conn, person);
            DAO.insert(conn,dogDto);
        }
        catch (SQLException e){
            System.out.println("ERROR: "+e.getMessage());
        }

    }

}
