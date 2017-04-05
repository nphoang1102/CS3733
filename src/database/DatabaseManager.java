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
import java.util.LinkedList;

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


        //INSERTING TABLES
        try {
            stmt.executeUpdate("CREATE TABLE Alcohol(\n" +
                    " TTBID REAL PRIMARY KEY,\n" +
                    " PermitNo VARCHAR(30) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " CompletedDate DATE,\n" +
                    " FancifulName VARCHAR(100),\n" +
                    " BrandName VARCHAR(100) NOT NULL,\n" +
                    " Origin INT NOT NULL,\n" +
                    " Class INT NOT NULL,\n" +
                    " Type VARCHAR(10) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Alcohol' exists.", EnumWarningType.NOTE);
        }

        try {
            stmt.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    " UUID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(30) NOT NULL,\n" +
                    " Company VARCHAR(100) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Manufacturers' exists.", EnumWarningType.NOTE);
        }

        try {
            stmt.executeUpdate("CREATE TABLE Applications(\n" +
                    " ApplicationNo BIGINT PRIMARY KEY,\n" +
                    " Manufacturer VARCHAR(100) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Applications' exists.", EnumWarningType.NOTE);
        }

        try {
            stmt.executeUpdate("CREATE TABLE Users(\n" +
                    " username BIGINT PRIMARY KEY,\n" +
                    " passwordHash VARCHAR(100) NOT NULL,\n" +
                    " userType ENUM('AGENT','MANUFACTURER') NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Users' exists.", EnumWarningType.NOTE);
        }

    }

    public void entryTest() {
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (12309847, 'FakePermitNo123', 'FakeSerial123', '2016-03-01', 'Le Fancy Le Vodka', 'Guinness', 123, 456, 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate("INSERT INTO Manufacturers (UUID, Username, Company) VALUES ('FakeUUID123', 'TheAlcoholic12', 'AlcoholicsAnonymous')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.executeUpdate("INSERT INTO Manufacturers (UUID, Username, Company) VALUES ('UUID239', 'User123', 'RealCompany')");
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
            search.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryAlcohol(String query) {
        String query2 = "SELECT * FROM Alcohol WHERE " + query + ")";
        try {
            ResultSet searchAlcohol = stmt.executeQuery(query2);
            searchAlcohol.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<DataSet> queryManufactures() {
        return queryManufacturers("SELECT * FROM Manufactures");
    }

    public static LinkedList<DataSet> queryManufactures(String manufacturer) {
        return queryManufacturers("SELECT * FROM Manufactures WHERE Company = " + manufacturer);
    }

    public static LinkedList<DataSet> queryManufactures(LinkedList<String> manufacturers) {
        String query = "SELECT * FROM Manufactures WHERE";
        for (String m : manufacturers) {
            query = query + " Company = " + m + " OR";
        }
        query = query + " Company = END";
        return queryManufacturers(query);
    }

    public static LinkedList<DataSet> queryManufacturers(String entry) {
        String query = entry;
        LinkedList<DataSet> data = new LinkedList<>();
        try {
            ResultSet searchManufacturers = stmt.executeQuery(query);
            while (searchManufacturers.next()) {
                String UUID = searchManufacturers.getString("UUID");
                String username = searchManufacturers.getString("Username");
                String company = searchManufacturers.getString("Company");
                DataSet dataSet = new DataSet(EnumTableType.MANUFACTURER);
                dataSet.addField("UUID", UUID);
                dataSet.addField("Username", username);
                dataSet.addField("Company", company);
                data.add(dataSet);
                /*
                LogManager.println("UUID: " + UUID);
                LogManager.println("Username: " + username);
                LogManager.println("Company: " + company);
                LogManager.println("");*/
            }
            searchManufacturers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}


