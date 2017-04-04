package database;
/**
 * Created by Evan Goldstein on 4/1/17.
 */

import base.EnumWarningType;
import base.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseManager {
    private static Statement stmt = null;
    private static Connection connection = null;

    public DatabaseManager() {
        LogManager.println("Attempting Database Connection.");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LogManager.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return;
        }
        LogManager.println("    Java DB driver registered!");
        try {
            //connection = DriverManager.getConnection("jdbc:mysql://haproxy.internal.icarusnet.me/JDBCTest?");
            connection = DriverManager.getConnection("jdbc:mysql://icarusnet.me/TTB?" +
                    "user=cadborosaurus&password=JT6N0x5dm09OgpPU");

        } catch (SQLException e) {
            LogManager.println("    Connection failed. Check output console.");
            e.printStackTrace();
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();

            return;
        }
        LogManager.println("    Java DB connection established!");

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();
        }


        ResultSet resultSet = null;

        try {
            stmt.executeUpdate("CREATE TABLE Alcohol(\n" +
                    "  TTBID REAL PRIMARY KEY,\n" +
                    "  PermitNo VARCHAR(30) NOT NULL,\n" +
                    "  SerialNo VARCHAR(30) NOT NULL,\n" +
                    "  CompletedDate DATE,\n" +
                    "  FancifulName VARCHAR(100),\n" +
                    "  BrandName VARCHAR(100) NOT NULL,\n" +
                    "  Origin INT NOT NULL,\n" +
                    "  Class INT NOT NULL,\n" +
                    "  Type VARCHAR(10) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table Alcohol exists.", EnumWarningType.NOTE);
        }

        try {
            LogManager.println("Attempting to add manufacturers table.", EnumWarningType.NOTE);
            stmt.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    "  Username VARCHAR(30) PRIMARY KEY,\n" +
                    "  Company VARCHAR(100) NOT NULL,\n" +
                    "  UUID VARCHAR(30) NOT NULL\n" +
                   ")\n");
        } catch (SQLException e) {
            LogManager.println("Table Manufacturers exists.", EnumWarningType.NOTE);
            e.printStackTrace();
        }

        //TODO - Add applications table


    }
    public void entryTest(){
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (12309847, 'FakePermitNo123', 'FakeSerial123', '2016-03-01', 'Le Fancy Le Vodka', 'Guinness', 123, 456, 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate("INSERT INTO Manufacturers (Username, Company, UUID) VALUES ('TheAlcoholic12', 'AlcoholicsAnonymous', 'FakeUUID123')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void AddEntry(long TTBID, String PermitNo, String SerialNo, String Date, String FancifulName, String BrandName, int Origin, int Class, String Type) {
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Search(String terms) {
        String query1 = "SELECT " + terms + " FROM Alcohol";
        try {
            ResultSet search = stmt.executeQuery(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void QueryAlcohol(String query){
        try {
            stmt.executeUpdate("SELECT * FROM Alcohol WHERE " + query + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}