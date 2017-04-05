/**
 * Created by Evan Goldstein on 4/1/17.
 */
package database;

import base.EnumTableType;
import base.EnumWarningType;
import base.LogManager;
import com.sun.org.apache.xpath.internal.operations.Or;
import screen.EnumUserType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedList;

public class DatabaseManager {
    private static Statement stmt = null;
    private static Connection connection = null;

    public DatabaseManager() {

        /////////////////////////////////////////////////////////////////////////////////
        ///////////CONNECT TO DATABASE///////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
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

        /////////////////////////////////////////////////////////////////////////////////
        ///////////CREATE TABLES/////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        try {
            stmt.executeUpdate("CREATE TABLE Alcohol(\n" +
                    " TTBID VARCHAR(30) PRIMARY KEY,\n" +
                    " PermitNo VARCHAR(30) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " CompletedDate DATE,\n" +
                    " FancifulName VARCHAR(100),\n" +
                    " BrandName VARCHAR(100) NOT NULL,\n" +
                    " Origin VARCHAR(10) NOT NULL,\n" +
                    " Class VARCHAR(10) NOT NULL,\n" +
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
                    " ApplicationNo VARCHAR(30) PRIMARY KEY,\n" +
                    " Manufacturer VARCHAR(50) NOT NULL,\n" +
                    " PermitNo VARCHAR(100) NOT NULL,\n" +
                    " Status ENUM('APPROVED','DENIED','PENDING') NOT NULL,\n" +
                    " AlcoholType VARCHAR(50) NOT NULL,\n" +
                    " AgentID VARCHAR(20) NOT NULL,\n" +
                    " Source VARCHAR(100) NOT NULL,\n" +
                    " Brand VARCHAR(50) NOT NULL,\n" +
                    " Address VARCHAR(100) NOT NULL,\n" +
                    " Address2 VARCHAR(100) NOT NULL,\n" +
                    " Volume VARCHAR(100) NOT NULL,\n" +
                    " ABV VARCHAR(10) NOT NULL,\n" +
                    " PhoneNo VARCHAR(20) NOT NULL,\n" +
                    " AppType VARCHAR(100) NOT NULL,\n" +
                    " VintageDate VARCHAR(30),\n" +
                    " PH VARCHAR(10),\n" +
                    " InboxAgent VARCHAR(20)\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Applications' exists.", EnumWarningType.NOTE);
        }

        try {
            stmt.executeUpdate("CREATE TABLE Users(\n" +
                    " username VARCHAR(100) PRIMARY KEY,\n" +
                    " passwordHash VARCHAR(100) NOT NULL,\n" +
                    //" agentInbox ARRAY(100) NOT NULL,\n" +
                    //" userType ENUM('AGENT','MANUFACTURER') NOT NULL\n" +
                    " userType VARCHAR(20) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Users' exists.", EnumWarningType.NOTE);
        }

    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////TESTS/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public void entryTest() {
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('12309847', 'PermitNo123', 'FakeSerial123', '2016-03-01', 'Le Fancy Le Vodka', 'Guinness', '123', '456', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('98765432', 'PermitNo321', 'Serial65', '2016-02-01', 'Le Beer', 'Beermakers', '456', '80', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('682732423', 'PermitNo333', 'Seria8080', '2015-12-12', 'Le Drinky', 'Winemakers', '902', '44', 'Wine')");
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

        try {
            stmt.executeUpdate("INSERT INTO Users (username, passwordHash, userType) VALUES ('user123', 'password123', 'Agent')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD ENTRY/////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /*public static void AddEntry(long TTBID, String PermitNo, String SerialNo, String Date, String FancifulName, String BrandName, int Origin, int Class, String Type) {
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SEARCH ALCOHOL////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> Search(String entered, String type) {
        String query = "SELECT * FROM Alcohol WHERE BrandName = '" + entered + "' AND Type = '" + type + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            ResultSet searchAlcohol = stmt.executeQuery(query);
            while (searchAlcohol.next()) {
                String TTBID = searchAlcohol.getString("TTBID");
                String PermitNo = searchAlcohol.getString("PermitNo");
                String SerialNo = searchAlcohol.getString("SerialNo");
                String CompletedDate = searchAlcohol.getString("CompletedDate");
                String FancifulName = searchAlcohol.getString("FancifulName");
                String BrandName = searchAlcohol.getString("BrandName");
                String Origin = searchAlcohol.getString("Origin");
                String Class = searchAlcohol.getString("Class");
                String Type = searchAlcohol.getString("Type");
                DataSet dataSet = new DataSet(EnumTableType.ALCOHOL);
                dataSet.addField("TTBID", TTBID);
                dataSet.addField("PermitNo", PermitNo);
                dataSet.addField("SerialNo", SerialNo);
                dataSet.addField("CompletedDate", CompletedDate);
                dataSet.addField("FancifulName", FancifulName);
                dataSet.addField("BrandName", BrandName);
                dataSet.addField("Origin", Origin);
                dataSet.addField("Class", Class);
                dataSet.addField("Type", Type);
                dataSets.add(dataSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSets;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> getApplications(String type, int num, String username) {
        String query = "SELECT * FROM Applications WHERE AlcoholType = '" + type + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            ResultSet applications = stmt.executeQuery(query);

            for (int i = 0; i < num; i++) {
                DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
                dataSet.addField("ApplicationNo", applications.getString("ApplicationNo"));
                dataSet.addField("Manufacturer", applications.getString("Manufacturer"));
                dataSet.addField("PermitNo", applications.getString("PermitNo"));
                dataSet.addField("Status", applications.getString("Status"));
                dataSet.addField("AlcoholType", applications.getString("AlcoholType"));
                dataSet.addField("AgentID", applications.getString("AgentID"));
                dataSet.addField("Source", applications.getString("Source"));
                dataSet.addField("Brand", applications.getString("Brand"));
                dataSet.addField("Address", applications.getString("Address"));
                dataSet.addField("Address2", applications.getString("Address2"));
                dataSet.addField("Volume", applications.getString("Volume"));
                dataSet.addField("ABV", applications.getString("ABV"));
                dataSet.addField("PhoneNo", applications.getString("PhoneNo"));
                dataSet.addField("AppType", applications.getString("AppType"));
                dataSet.addField("VintageDate", applications.getString("VintageDate"));
                dataSet.addField("PH", applications.getString("PH"));
                dataSet.addField("InboxAgent", applications.getString("InboxAgent"));
                dataSets.add(dataSet);
                stmt.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + applications.getString("ApplicationNo") + "';");
                applications.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSets;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATION FROM ApplicationNo////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static DataSet getApplicationNo(String appNo) {
        String query = "SELECT * FROM Applications WHERE ApplicationNo = '" + appNo + "';";
        DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
        try {
            ResultSet application = stmt.executeQuery(query);
            dataSet.addField("ApplicationNo", application.getString("ApplicationNo"));
            dataSet.addField("Manufacturer", application.getString("Manufacturer"));
            dataSet.addField("PermitNo", application.getString("PermitNo"));
            dataSet.addField("Status", application.getString("Status"));
            dataSet.addField("AlcoholType", application.getString("AlcoholType"));
            dataSet.addField("AgentID", application.getString("AgentID"));
            dataSet.addField("Source", application.getString("Source"));
            dataSet.addField("Brand", application.getString("Brand"));
            dataSet.addField("Address", application.getString("Address"));
            dataSet.addField("Address2", application.getString("Address2"));
            dataSet.addField("Volume", application.getString("Volume"));
            dataSet.addField("ABV", application.getString("ABV"));
            dataSet.addField("PhoneNo", application.getString("PhoneNo"));
            dataSet.addField("AppType", application.getString("AppType"));
            dataSet.addField("VintageDate", application.getString("VintageDate"));
            dataSet.addField("PH", application.getString("PH"));
            dataSet.addField("InboxAgent", application.getString("InboxAgent"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SUBMIT APPLICATIONS///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void submitApplication(String ApplicationNo, String Manufacturer, String PermitNo, String Status, String AlcoholType, String AgentID, String Source, String Brand, String Address, String Address2, String Volume, String ABV, String PhoneNo, String AppType, String VintageDate, String PH) {
        try {
            stmt.executeUpdate("INSERT INTO Applications (ApplicationNo, Manufacturer, PermitNo, Status, AlcoholType, AgentID, Source, Brand, Address, Address2, Volume, ABV, PhoneNo, AppType, VintageDate, PH) VALUES " +
                    "('" + ApplicationNo + "', '" + PermitNo + "', '" + AlcoholType + "', '" + AgentID + "', '" + Source + "', '" + Brand + "', '" + Address + "', '" + Address2 + "', '" + Volume + "', '" + ABV + "', '" + PhoneNo + "', '" + AppType + "', '" + VintageDate + "', '" + PH + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERATE TTBID////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static String generateTTBID() {
        String id = Long.toString(Math.round(Math.random() * 10000000));
        return id;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////APPROVE APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void approveApplication(String ApplicationNo) {
        try {
            stmt.executeUpdate("UPDATE Users SET status = 'APPROVED' WHERE ApplicationNo = '" + ApplicationNo + "';");
            stmt.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + ApplicationNo + "';");
            //stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataSet approvedApplication = getApplicationNo(ApplicationNo);
        String TTBID = generateTTBID();
        String PermitNo = approvedApplication.getValueForKey("PermitNo");
        String SerialNo = approvedApplication.getValueForKey("SerialNo");
        String Date = approvedApplication.getValueForKey("CompletedDate");
        String FancifulName = approvedApplication.getValueForKey("FancifulName");
        String BrandName = approvedApplication.getValueForKey("BrandName");
        String Origin = approvedApplication.getValueForKey("Origin");
        String Class = approvedApplication.getValueForKey("Class");
        String Type = approvedApplication.getValueForKey("Type");
        try {
            stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('" + TTBID + "', '" + PermitNo + "', '" + SerialNo + "', '" + Date + "', '" + FancifulName + "', '" + BrandName + "', '" + Origin + "', '" + Class + "', '" + Type + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////APPROVE APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void rejectApplication(String ApplicationNo) {//GET REKKKDDDDD!
        try {
            stmt.executeUpdate("UPDATE Users SET status = 'REJECTED' WHERE ApplicationNo = '" + ApplicationNo + "';");
            stmt.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + ApplicationNo + "';");
            //stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void queryAlcohol(String query) {
        String query2 = "SELECT * FROM Alcohol WHERE " + query + ")";
        try {
            ResultSet searchAlcohol = stmt.executeQuery(query2);
            searchAlcohol.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD USERS/////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void addUser(String username, String password, String type) {
        try {
            stmt.executeUpdate("INSERT INTO Users (username, passwordHash, userType) VALUES " +
                    "('" + username + "', '" + password + "', '" + type + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET USER TYPE/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static String getUserType(String username) {
        String query = "SELECT * FROM Users WHERE username = '" + username + "';";
        String userType = null;
        try {
            ResultSet users = stmt.executeQuery(query);
            userType = users.getString("userType");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////MANUFACTURER QUERIES//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryManufactures() {
        return queryManufacturers("SELECT * FROM Manufacturers");
    }

    public static LinkedList<DataSet> queryManufactures(String manufacturer) {
        return queryManufacturers("SELECT * FROM Manufacturers WHERE Company = '" + manufacturer + "';");
    }

    public static LinkedList<DataSet> queryManufactures(LinkedList<String> manufacturers) {
        String query = "SELECT * FROM Manufacturers WHERE";
        for (String m : manufacturers) {
            query = query + " Company = '" + m + "' OR";
        }
        query = query + " Company = 'END'";
        return queryManufacturers(query);
    }

    public static LinkedList<DataSet> queryManufacturers(String entry) {
        String query = entry;
        LinkedList<DataSet> dataSets = new LinkedList<>();
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
                dataSets.add(dataSet);

                LogManager.println("UUID: " + UUID);
                LogManager.println("Username: " + username);
                LogManager.println("Company: " + company);
                LogManager.println("");
            }
            searchManufacturers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSets;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
}
