package co.ksga.utils;

import co.ksga.model.service.ProductServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String dbUrl = System.getenv("DB_URL"); // Example: "jdbc:postgresql://localhost:5432/stockmanagement"
            String dbUser = System.getenv("DB_USER"); // Example: "postgres"
            String dbPassword = System.getenv("DB_PASSWORD"); // Example: "seyha"

            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException sqlException) {
            System.out.println("Problem connecting to database: " + sqlException.getMessage());
        }
        return null;

    }
}


