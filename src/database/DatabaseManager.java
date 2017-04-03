/**
 * Created by Evan Goldstein on 4/1/17.
 */

import base.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseManager {
    public static void DatabaseManager() {
        LogManager.println("Attempting Database Connection.");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LogManager.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return;
        }
        System.out.println("    Java DB driver registered!");
        Connection connection = null;
        try {
            //connection = DriverManager.getConnection("jdbc:mysql://haproxy.internal.icarusnet.me/JDBCTest?");
            connection = DriverManager.getConnection("jdbc:mysql://haproxy.internal.icarusnet.me:3306/JDBCTest?" + "user=jdbc");
        } catch (SQLException e) {
            System.out.println("    Connection failed. Check output console.");
            e.printStackTrace();
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();

            return;
        }
        System.out.println("    Java DB connection established!");
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();
        }
    }

    public static void AddEntery() {

    }

    public static void Search(String terms) {

    }
}