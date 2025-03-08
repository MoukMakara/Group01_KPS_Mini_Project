package co.ksga.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/stock_management",
                    "postgres",
                    "Admin"
            );
        }catch (SQLException sqlException){
            System.out.println("problem connecting to database");
        }
        return null;
    }

}
