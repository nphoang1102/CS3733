package database;

import base.*;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import screen.EnumScreenType;
import screen.EnumUserType;
import screen.ScreenManager;
import sun.awt.image.ImageWatched;
import sun.rmi.runtime.Log;
/*import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.TODO
import screen.EnumUserType;
import sun.awt.image.ImageWatched;
import javax.xml.crypto.Data;*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DatabaseManager {
    /**
     * Created by Evan Goldstein on 4/1/17.
     */
    private static Statement statement;
    private static Connection connection;
    private String databaseType;
    private String databaseName;
    private String databaseServer;
    private boolean derby = false; //Plz no
    private boolean mysql = false;
    protected static String endQueryLine;
//    private boolean sqlite = false;

    /////////////////////////////////////////////////////////////////////////////////
    ///////////EXCEPTIONS////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public class UserNotFoundException extends Exception {
        UserNotFoundException(String username) {
            super("User " + username + " not found");
        }
    }

    public class IncorrectPasswordException extends Exception {
        IncorrectPasswordException(String username) {
            super("Incorrect password for user " + username + "!"); //<HAL>I'm sorry Dave, but I'm afraid I can't do that.</HAL>
        }
    }

    public static class DuplicateUserException extends Exception{
        DuplicateUserException() {super("User already exists");}
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CONSTRUCTOR - CONNECTS TO DATABASE////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public DatabaseManager() {
        databaseType = "MySQL";
        databaseName = "TTB";
        databaseServer = "icarusnet.me";

        if (databaseType.toLowerCase().equals("derby")) {
            derby = true; //MAGIC!
            endQueryLine = "";
        } else if (databaseType.toLowerCase().equals("mysql")) {
            mysql = true; //MORE MAGIC!
            endQueryLine = ";";
        }
        /*else if(database.equals("sqlite") || database.equals("SQLite")){
            sqlite = true
        }*/
        else {
            LogManager.println("Unknown database type!", EnumWarningType.ERROR);
            return;
        }
       /* try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LogManager.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return;
        }*/
//        LogManager.println("    Driver registered!");
        LogManager.print("Attempting connection to " + databaseType + " database  at " + databaseServer + "... ");
        boolean noDB = true;
        int MAXTRIES = 10;
        int tries = 0;
        while (noDB) {
            try {
                if (derby) {
                    connection = DriverManager.getConnection("jdbc:derby:" + databaseName + ";create=true");
                } else if (mysql) {
                    connection = DriverManager.getConnection("jdbc:mysql://" + databaseServer + "/" + databaseName + "?" + "user=cadborosaurus&password=JT6N0x5dm09OgpPU");
                }
            /*else if(sqlite){
                connection = DriverManager.getConnection("jdbc:sqlite:TTB.db");
            }*/
                LogManager.println("Connection established!");
                noDB = false;
            } catch (SQLException e) { //BOOKER, CATCH!
                LogManager.println("");
                LogManager.print("Database connection failed. ", EnumWarningType.ERROR);
                if (derby) {
                    LogManager.println("There may already be a connection to the database."); //Derby is monogamous.
                } else if (mysql) {
                    LogManager.println("");
                }
                if (tries == MAXTRIES) {
                    LogManager.println("Giving up.", EnumWarningType.ERROR);
                    System.exit(0);
                }
                LogManager.println("Retrying...", EnumWarningType.NOTE);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
                }
                tries++;
            }

        }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {//CATCH, BOOKER!
            LogManager.printStackTrace(e.getStackTrace());
            LogManager.println("statement failed"); // （　ﾟДﾟ）
            e.printStackTrace();
        }
        //On the second day, god...
        createTables();
        //...created the tables
//        entryTest(); //Even god tests his database.

//        CSV importTest = new CSV("C:\\import.csv");
//        importTest.importAlcohol();
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CREATE TABLES/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static void createTables() {
        LogManager.println("Creating Tables: ", EnumWarningType.NOTE);
        LogManager.print("Creating alcohol table... ", EnumWarningType.NOTE);
        try {
            statement.executeUpdate("CREATE TABLE Alcohol(\n" +
                    " TTBID VARCHAR(30) PRIMARY KEY,\n" +
                    " PermitNo VARCHAR(30) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " CompletedDate VARCHAR(20),\n" +
                    " FancifulName VARCHAR(100),\n" +
                    " BrandName VARCHAR(100) NOT NULL,\n" +
                    " Class VARCHAR(50) NOT NULL,\n" +
                    " Origin VARCHAR(10) NOT NULL,\n" +
                    " AlcoholType VARCHAR(30) NOT NULL,\n" +
                    " AlcoholContent VARCHAR(30),\n" +
                    " VintageYear VARCHAR(10),\n" +
                    " PH VARCHAR(10)\n" +
                    ")" + endQueryLine);
            LogManager.println("Done.");
        } catch (SQLException e) { //BOOKERCATCH!!!!
            createTableError(e); //It was as if a million voices suddenly cried out in terror, and were suddenly silenced.
        }
        LogManager.print("Creating applications table... ", EnumWarningType.NOTE);
        try {
            statement.executeUpdate("CREATE TABLE Applications(\n" +
                    " ApplicationNo VARCHAR(20) PRIMARY KEY,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " ApplicationType VARCHAR(30) NOT NULL,\n" +
                    " ApplicationStatus VARCHAR(15) NOT NULL,\n" +
                    " CT VARCHAR(15) NOT NULL,\n" +
                    " O_R VARCHAR(15) NOT NULL,\n" +
                    " ManufacturerUsername VARCHAR(20),\n" +
                    " RepName VARCHAR(30),\n" +
                    " AgentUsername VARCHAR(30),\n" +
                    " AgentName VARCHAR(30),\n" +
                    " RepID VARCHAR(30),\n" +
                    " PlantRegistry VARCHAR(30) NOT NULL,\n" +
                    " Locality VARCHAR(30) NOT NULL,\n" +
                    " Brand VARCHAR(50) NOT NULL,\n" +
                    " FancifulName VARCHAR(50),\n" +
                    " AlcoholType VARCHAR(10) NOT NULL,\n" +
                    " ABV VARCHAR(10) NOT NULL,\n" +
                    " Address VARCHAR(100) NOT NULL,\n" +
                    " Address2 VARCHAR(100),\n" +
                    " Formula VARCHAR(500) NOT NULL,\n" +
                    " WineAppelation VARCHAR(50),\n" +
                    " VintageDate VARCHAR(4),\n" +
                    " Grapes VARCHAR(50),\n" +
                    " PH VARCHAR(10),\n" +
                    " PhoneNo VARCHAR(20) NOT NULL,\n" +
                    " Email VARCHAR(30) NOT NULL,\n" +
                    " AdditionalInfo VARCHAR(500) NOT NULL,\n" +
                    " DateOfSubmission DATE,\n" +
                    " DateOfApproval DATE,\n" +
                    " DateOfExpiration DATE,\n" +
                    " ApprovedTTBID VARCHAR(14),\n" +
                    " ReasonForRejection VARCHAR(100)\n" +
                    ")" + endQueryLine);
            LogManager.println("Done.");
        } catch (SQLException e) {
            createTableError(e);
        }
        LogManager.print("Creating agents table... ", EnumWarningType.NOTE);
        try {
            statement.executeUpdate("CREATE TABLE Agents(\n" +
                    " ID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(50) NOT NULL UNIQUE,\n" +
                    " PasswordHash VARCHAR(75) NOT NULL,\n" +
                    " FullName VARCHAR(50) NOT NULL,\n" +
                    " Email VARCHAR(30) NOT NULL,\n" +
                    " SuperAgent BOOLEAN NOT NULL,\n" +
                    " Status VARCHAR(30)\n" +
                    ")" + endQueryLine);
            LogManager.println("Done.");
        } catch (SQLException e) {
            createTableError(e); //(ノಠ ∩ಠ)ノ彡( \o°o)\
        }
        LogManager.print("Creating manufacturers table... ", EnumWarningType.NOTE);
        try {
            statement.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    " Username VARCHAR(30) PRIMARY KEY UNIQUE,\n" +
                    " PasswordHash VARCHAR(75) NOT NULL,\n" +
                    " Company VARCHAR(100) NOT NULL,\n" +
                    " FullName VARCHAR(50) NOT NULL,\n" +
                    " RepID VARCHAR(50) NOT NULL,\n" +
                    " Email VARCHAR(50) NOT NULL,\n" +
                    " PlantRegistry VARCHAR(50) NOT NULL,\n" +
                    " PhoneNo VARCHAR(20) NOT NULL,\n" +
                    " Address2 VARCHAR(50) NOT NULL,\n" +
                    " Agent VARCHAR(30),\n" +
                    " AgentDate VARCHAR(20)\n" +
                    ")" + endQueryLine);
            LogManager.println("Done.");
        } catch (SQLException e) {
            createTableError(e);
        }
    }

    private static void createTableError(Exception e) {
        if (e.getMessage().contains("already exists")) {
            LogManager.println("Already exists.");
        } else {
            LogManager.println("ERROR: " + e.getMessage());

            System.exit(0); //(╯°□°）╯︵ ┻━┻
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERIC DATABASE QUERY////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryDatabase(EnumTableType table, String column, String value) {
        value = StringUtilities.sanitize(value);
        String type = Main.screenManager.getSearchTerm();
        String value1 = value.toUpperCase();
        if (table.equals(EnumTableType.ALCOHOL)) {
            if (value.isEmpty() && type.equals("All")) {
                return queryAlcohol("SELECT * FROM Alcohol" + endQueryLine);
            } else {
                switch (type) { //funky fresh
                    case "All":
                        return queryAlcohol("SELECT * FROM Alcohol WHERE BrandName LIKE '" + value1 + "%' OR BrandName LIKE '%" + value1 + "' OR BrandName LIKE '%" + value1 + "%'" + endQueryLine);
                    case "Beer":
                        return queryAlcohol("SELECT * FROM Alcohol WHERE (AlcoholType = 'MALT BEVERAGE' AND BrandName LIKE '" + value1 + "%') OR (AlcoholType = 'MALT BEVERAGE' AND BrandName LIKE '%" + value1 + "%') OR (AlcoholType = 'MALT BEVERAGE' AND BrandName LIKE '%" + value1 + "')" + endQueryLine);
                    case "Wine":
                        return queryAlcohol("SELECT * FROM Alcohol WHERE (AlcoholType = 'WINE' AND BrandName LIKE '" + value1 + "%') OR (AlcoholType = 'WINE' AND BrandName LIKE '%" + value1 + "%') OR (AlcoholType = 'WINE' AND BrandName LIKE '%" + value1 + "')" + endQueryLine);
                    default:
                        return queryAlcohol("SELECT * FROM Alcohol WHERE (AlcoholType <> 'MALT BEVERAGE') AND (AlcoholType <> 'WINE' AND BrandName LIKE '" + value1 + "%') OR (AlcoholType <> 'MALT BEVERAGE' AND AlcoholType <> 'WINE' AND BrandName LIKE '%" + value1 + "%') OR (AlcoholType <> 'MALT BEVERAGE' AND AlcoholType <> 'WINE' AND BrandName LIKE '%" + value1 + "')" + endQueryLine);
                }
            }
        } else if (table.equals(EnumTableType.APPLICATION)) {
            return queryApplications("SELECT * FROM Applications WHERE " + column + " = '" + value + "'" + endQueryLine);
        } else if (table.equals(EnumTableType.MANUFACTURER)) {
            if (column.equals("")) {
                return queryManufacturers("SELECT * FROM Manufacturers");
            } else {
//                LogManager.println("SEARCHING FOR MANUFACTURER " + column + " = " + value);
                return queryManufacturers("SELECT * FROM Manufacturers WHERE " + column + " = '" + value + "'" + endQueryLine);
            }
        } else if (table.equals(EnumTableType.AGENT)) {
//            LogManager.println("SEARCHING FOR MANUFACTURER " + column + " = " + value);
            return queryAgents("SELECT * FROM Agents WHERE " + column + " = '" + value + "'" + endQueryLine);
        }
        //You asked for a hamburger. I...
        return null;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADVANCED SEARCH///////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    //ENTER AT YOUR OWN RISK

    public static LinkedList<DataSet> advancedSearch(String cat1, String val1, String cat2, String val2, String cat3, String val3, String cat4, String val4, String andor) {
        val1 = StringUtilities.sanitize(val1);
        val2 = StringUtilities.sanitize(val2);
        val3 = StringUtilities.sanitize(val3);
        val4 = StringUtilities.sanitize(val4);

        if (cat1.equals("BrandName") || cat1.equals("FancifulName") || cat1.equals("AlcoholType")) {
            val1 = val1.toUpperCase();
        }
        if (cat2.equals("BrandName") || cat2.equals("FancifulName") || cat2.equals("AlcoholType")) {
            val2 = val2.toUpperCase();
        }
        if (cat3.equals("BrandName") || cat3.equals("FancifulName") || cat3.equals("AlcoholType")) {
            val3 = val3.toUpperCase();
        }
        if (cat4.equals("BrandName") || cat4.equals("FancifulName") || cat4.equals("AlcoholType")) {
            val4 = val4.toUpperCase();
        }

        String query1 = "SELECT * FROM Alcohol WHERE (" + cat1 + " LIKE '" + val1 + "%' OR " + cat1 + " LIKE '%" + val1 + "' OR " + cat1 + " LIKE '%" + val1 + "%')";
        String query2 = "SELECT * FROM Alcohol WHERE (" + cat2 + " LIKE '" + val2 + "%' OR " + cat2 + " LIKE '%" + val2 + "' OR " + cat2 + " LIKE '%" + val2 + "%')";
        String query3 = "SELECT * FROM Alcohol WHERE (" + cat3 + " LIKE '" + val3 + "%' OR " + cat3 + " LIKE '%" + val3 + "' OR " + cat3 + " LIKE '%" + val3 + "%')";
        String query4 = "SELECT * FROM Alcohol WHERE (" + cat4 + " LIKE '" + val4 + "%' OR " + cat4 + " LIKE '%" + val4 + "' OR " + cat4 + " LIKE '%" + val4 + "%')";
        String combinedQuery;

        if (cat1.equals("Origin")) {
            query1 = "SELECT * FROM Alcohol WHERE " + cat1 + " = '" + val1 + "'";
        }
        if (cat2.equals("Origin")) {
            query2 = "SELECT * FROM Alcohol WHERE " + cat2 + " = '" + val2 + "'";
        }
        if (cat3.equals("Origin")) {
            query1 = "SELECT * FROM Alcohol WHERE " + cat3 + " = '" + val3 + "'";
        }
        if (cat4.equals("Origin")) {
            query1 = "SELECT * FROM Alcohol WHERE " + cat4 + " = '" + val4 + "'";
        }

        if(andor.equals("or")) {
            try {
                if (!val1.isEmpty() && val2.isEmpty() && val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1;
                } else if (!val1.isEmpty() && !val2.isEmpty() && val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1 + "UNION " + query2;
                } else if (!val1.isEmpty() && !val2.isEmpty() && !val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1 + "UNION " + query2 + "UNION " + query3;
                } else if (!val1.isEmpty() && !val2.isEmpty() && !val3.isEmpty() && !val4.isEmpty()) {
                    combinedQuery = query1 + "UNION " + query2 + "UNION " + query3 + "UNION " + query4;
                } else {
                    combinedQuery = "SELECT * FROM Alcohol";
                }
            } catch (Exception e) {
                LogManager.println("No matches found!", EnumWarningType.WARNING);
                return new LinkedList<>();
            }

            return queryAlcohol(combinedQuery);
        } else if (andor.equals("and")) {
            try {
                if (!val1.isEmpty() && val2.isEmpty() && val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1;

                } else if (!val1.isEmpty() && !val2.isEmpty() && val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1 + " AND (" + cat2 + " LIKE '" + val2 + "%' OR " + cat2 + " LIKE '%" + val2 + "' OR " + cat2 + " LIKE '%" + val2 + "%')";

                } else if (!val1.isEmpty() && !val2.isEmpty() && !val3.isEmpty() && val4.isEmpty()) {
                    combinedQuery = query1 + " AND (" + cat2 + " LIKE '" + val2 + "%' OR " + cat2 + " LIKE '%" + val2 + "' OR " + cat2 + " LIKE '%" + val2 + "%')"
                            + " AND (" + cat3 + " LIKE '" + val3 + "%' OR " + cat3 + " LIKE '%" + val3 + "' OR " + cat3 + " LIKE '%" + val3 + "%') ";

                } else if (!val1.isEmpty() && !val2.isEmpty() && !val3.isEmpty() && !val4.isEmpty()) {
                    combinedQuery = query1 + " AND (" + cat2 + " LIKE '" + val2 + "%' OR " + cat2 + " LIKE '%" + val2 + "' OR " + cat2 + " LIKE '%" + val2 + "%')"
                            + " AND (" + cat3 + " LIKE '" + val3 + "%' OR " + cat3 + " LIKE '%" + val3 + "' OR " + cat3 + " LIKE '%" + val3 + "%')"
                            + " AND (" + cat4 + " LIKE '" + val4 + "%' OR " + cat4 + " LIKE '%" + val4 + "' OR " + cat4 + " LIKE '%" + val4 + "%')";

                } else {
                    combinedQuery = "SELECT * FROM Alcohol";
                }
            } catch (Exception e) {
                LogManager.println("No matches found!", EnumWarningType.WARNING);
                return new LinkedList<>();
            }

            return queryAlcohol(combinedQuery);
        } else {

            return queryAlcohol("SELECT * FROM Alcohol");
        }

    }

    protected static void insertAlcohol(Alcohol alcohol) {
        alcohol.sanitize();
        System.out.printf("Inserting alcohol with TBID %s, BrandName %s, and Fanciful name %s.", alcohol.TTBID, alcohol.BrandName, alcohol.FancifulName + "\n");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Alcohol (" +
                    "TTBID, " +
                    "PermitNo, " +
                    "SerialNo, " +
                    "CompletedDate, " +
                    "FancifulName, " +
                    "BrandName, " +
                    "Class, " +
                    "Origin, " +
                    "AlcoholType, " +
                    "AlcoholContent, " +
                    "VintageYear, " +
                    "PH) VALUES ('" +
                    alcohol.TTBID.replaceAll("[^\\d]", "") + "', '" +
                    alcohol.PermitNo + "', '" +
                    alcohol.SerialNo + "', '" +
                    alcohol.CompletedDate + "', '" +
                    alcohol.FancifulName.toUpperCase() + "', '" +
                    alcohol.BrandName.toUpperCase() + "', '" +
                    alcohol.PH + "', '" +
                    StringUtilities.sanitize(alcohol.Origin) + "', '" +
                    alcohol.Type.toUpperCase() + "', '" +
                    alcohol.AlcoholContent + "', '" +
                    alcohol.VintageYear + "', '" +
                    alcohol.PH + "')" + DatabaseManager.endQueryLine);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LogManager.println("Failed to insert alcohol entry for TTBID " + alcohol.TTBID + ", Brandname " + alcohol.BrandName + ", and Fanciful name " + alcohol.FancifulName + ":", EnumWarningType.WARNING);
            LogManager.println(e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ALCOHOL SEARCH////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static LinkedList<DataSet> queryAlcohol(String queryStr) {
        LinkedList<DataSet> alcoholLinkedList = new LinkedList<>();
        LogManager.println("Searching alcohol table: " + queryStr, EnumWarningType.NOTE);
        int count = 1;
        try {
            ResultSet getAlcohol = statement.executeQuery(queryStr);
            while (getAlcohol.next()) {
//                System.out.println("Loading item " + count);
                Alcohol alcohol = new Alcohol(); //Bottoms up!
                alcohol.TTBID = getAlcohol.getString("TTBID");
                alcohol.PermitNo = getAlcohol.getString("PermitNo");
                alcohol.SerialNo = getAlcohol.getString("SerialNo");
                alcohol.CompletedDate = getAlcohol.getString("CompletedDate");
                alcohol.FancifulName = getAlcohol.getString("FancifulName");
                alcohol.BrandName = getAlcohol.getString("BrandName");
                alcohol.Class = getAlcohol.getString("Class");
                alcohol.Origin = getAlcohol.getString("Origin");
                alcohol.Type = getAlcohol.getString("AlcoholType");
                alcohol.AlcoholContent = getAlcohol.getString("AlcoholContent");
                alcohol.VintageYear = getAlcohol.getString("VintageYear");
                alcohol.PH = getAlcohol.getString("PH");
                alcoholLinkedList.add(alcohol);
                count++;
            }
        } catch (SQLException e) {
            LogManager.println("Search failed!" + e.getMessage(), EnumWarningType.WARNING);//Bummer, dude.
            LogManager.println("SQLState:" + e.getSQLState(), EnumWarningType.WARNING);
            return new LinkedList<>();
        }
        LogManager.println("Loaded " + count + " alcohol items!", EnumWarningType.NOTE);
        return alcoholLinkedList;
//        ヽ(´ー｀)ノ
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERATE TTBID////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static String generateTTBID() {
        return Long.toString(Math.round(Math.random() * 10000000)); //(;o;)
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SUBMIT APPLICATIONS///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void submitApplication(Application application) {
        application.sanitize();
        // OLD PARAMETERS: String Manufacturer, String PermitNo, String Status, String AlcoholType, String AgentID, String Source, String Brand, String Address, String Address2, String Volume, String ABV, String PhoneNo, String AppType, String VintageDate, String PH, String ApplicantName, String DateSubmitted, String DBAorTrade, String Email
//        application.ApprovedTTBID = generateTTBID();
//        application.ApplicationNo = application.ApprovedTTBID;
        String date = StringUtilities.getDate(); //Welcome to the new age.
        try {
            LogManager.println("Submitting new application.", EnumWarningType.NOTE);
//            String status = "PENDING";
            statement.executeUpdate("INSERT INTO Applications " +
                    "(ApplicationNo, " +
                    "SerialNo, " +
                    "ApplicationType," +
                    "ApplicationStatus," +
                    "ManufacturerUsername, " +
                    "RepName, " +
                    "AgentUsername, " +
                    "AgentName, " +
                    "RepID, " +
                    "PlantRegistry, " +
                    "Locality, " +
                    "Brand, " +
                    "FancifulName, " +
                    "AlcoholType, " +
                    "ABV, " +
                    "Address, " +
                    "Address2, " +
                    "Formula, " +
                    "WineAppelation, " +
                    "VintageDate," + //Even older than your mom.
                    "Grapes, " + //I'm sorry. I'm sure your mom is a lovely person.
                    "PH," +
                    "PhoneNo, " +
                    "Email, " +
                    "AdditionalInfo, " +
//                    "DateOfSubmission," +
//                    "DateOfApproval," +
//                    "DateOfExpiration," +
                    "ApprovedTTBID," +
                    "ReasonForRejection" +
                    ") VALUES ('"
                    + application.ApplicationNo + "', '"
                    + application.SerialNo + "', '"
                    + application.ApplicationType + "', '"
                    + application.ApplicationStatus + "', '"
                    + application.ManufacturerUsername + "', '"
                    + application.RepName + "', '"
                    + application.AgentUsername + "', '"
                    + application.AgentName + "', '"
                    + application.RepID + "', '"
                    + application.PlantRegistry + "', '"
                    + application.Locality + "', '"
                    + application.Brand + "', '"
                    + application.FancifulName + "', '"
                    + application.AlcoholType + "', '"
                    + application.ABV + "', '"
                    + application.Address + "', '"
                    + application.Address2 + "', '"
                    + application.Formula + "', '"
                    + application.WineAppelation + "', '"
                    + application.VintageDate + "', '"
                    + application.Grapes + "', '"
                    + application.PH + "', '"
                    + application.PhoneNo + "', '"
                    + application.Email + "', '"
                    + application.AdditionalInfo + "', '"
//                    + application.DateOfSubmission + "', '"
//                    + application.DateOfApproval + "', '"
//                    + application.DateOfExpiration + "', '"
                    + application.ApprovedTTBID + "', '"
                    + application.ReasonForRejection
                    + "')" + endQueryLine);
        } catch (SQLException e) {
            LogManager.println("Failed to submit new application " + application.ApplicationNo + ": " + e.getMessage()); //I cannot change the laws of physics, cap'n!

        }
        try {
            statement.executeUpdate("UPDATE Applications SET DateOfSubmission = '" + date + "' WHERE ApplicationNo = " + application.ApplicationNo + endQueryLine);
        } catch (SQLException e) {
            //ಠ_ಠ
            LogManager.print("Could not set DateOfSubmission '" + date + "' on newly submitted application " + application.ApplicationNo + ": ");
            LogManager.println(e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////MANUFACTURER QUERIES//////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static LinkedList<DataSet> queryManufacturers(String query) {
        LinkedList<DataSet> manufacturers = new LinkedList<>();
        try {
            ResultSet searchManufacturers = statement.executeQuery(query);
//            LogManager.println("queryManufacturers() has run the query: " + query, EnumWarningType.NOTE);
            while (searchManufacturers.next()) {
                String username = searchManufacturers.getString("Username");
//                LogManager.println("queryManufacturers() is adding the user " + username + " to a list that is now ", EnumWarningType.NOTE);
                UserManufacturer manufacturer = new UserManufacturer(query);
                manufacturer.Company = searchManufacturers.getString("Company");
                manufacturer.username = username;
                manufacturer.name = searchManufacturers.getString("FullName");
                manufacturer.email = searchManufacturers.getString("email");
                manufacturer.userType = EnumUserType.MANUFACTURER;
                manufacturer.Address2 = searchManufacturers.getString("Address2");
                manufacturer.Company = searchManufacturers.getString("Company"); //I have a cat.
                manufacturer.name = searchManufacturers.getString("FullName");
                manufacturer.RepID = searchManufacturers.getString("RepID");
                manufacturer.PlantRegistry = searchManufacturers.getString("PlantRegistry");
                manufacturer.PhoneNo = searchManufacturers.getString("PhoneNo");
                manufacturer.Agent = searchManufacturers.getString("Agent");
                manufacturer.AgentDate = searchManufacturers.getString("AgentDate");
                manufacturers.add(manufacturer); //One to beam up.
//                LogManager.print(manufacturers.size() + "items long.");
            }
            searchManufacturers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////AGENT QUERIES/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static LinkedList<DataSet> queryAgents(String query) {
        LinkedList<DataSet> agents = new LinkedList<>(); //That's no moon!
        try {
            ResultSet searchAgents = statement.executeQuery(query);
            LogManager.println("queryAgents() has run the query: " + query, EnumWarningType.NOTE);
            while (searchAgents.next()) {
                UserAgent agent = new UserAgent(query);
                agent.ID = searchAgents.getString("ID");
                agent.username = searchAgents.getString("Username");
                agent.PasswordHash = searchAgents.getString("PasswordHash");
                agent.name = searchAgents.getString("FullName");
                agent.email = searchAgents.getString("Email");
                agent.superAgent = searchAgents.getString("SuperAgent");
                agent.status = searchAgents.getString("Status");
                agents.add(agent); //Welcome to the obsidian order.
            }
            searchAgents.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SET AGENT STATUS//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void setAgentStatus(String username, String status) { //We're competing with facebook.

        if (status.equals("REMOVE")) {
            try {
                statement.executeUpdate("DELETE FROM Agents WHERE Username = '" + username + "' ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                statement.executeUpdate("UPDATE Agents SET " +
                        "Status = '" + status + "' " +
                        "WHERE Username = '" + username + "'" + endQueryLine);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CLEAR AGENT INBOX/////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public void clearInbox(String username) {
        try {
            statement.executeUpdate("UPDATE Applications SET " +
                    "AgentUsername = '' " +
                    "WHERE AgentUsername = '" + username + "'" + endQueryLine);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////EDIT MANUFACTURER/////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void updateManufacturer(UserManufacturer manufacturer) {
        try {
            //ResultSet searchManufacturers = statement.executeQuery("SELECT * FROM Manufacturers;");
            //LinkedList<DataSet> manufacturerLinkedList = queryDatabase(EnumTableType.MANUFACTURER, "username", userm.username);
            /*while(searchManufacturers.next()) {
                userm.username = searchManufacturers.getString("Username");
            }*/
            LogManager.print("Updating information for manufacturer" + manufacturer.username + "... ", EnumWarningType.NOTE);
            statement.executeUpdate("UPDATE Manufacturers SET " +
                    "FullName = '" + manufacturer.name + "', " + //WHOOOOO ARE YOU? OOH OHH, OOH OOH...
                    "RepID = '" + manufacturer.RepID + "', " +
                    "Email = '" + manufacturer.email + "', " +
                    "PlantRegistry = '" + manufacturer.PlantRegistry + "', " +
                    "PhoneNo = '" + manufacturer.PhoneNo + "' " +
                    "WHERE Username = '" + manufacturer.username + "'" + endQueryLine);
            LogManager.println("Success!");
        } catch (SQLException e) {
            LogManager.print("Failed. ");
            LogManager.println(e.getMessage());
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////EDIT AGENTS//////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void updateAgents(UserAgent agent) {
        try {
            LogManager.print("Updating information for agent" + agent.username + "... ", EnumWarningType.NOTE);
            statement.executeUpdate("UPDATE Agents SET " +
                    "FullName = '" + agent.name + "', " +
                    "Email = '" + agent.email + "', " +
                    "WHERE Username = '" + agent.username + "'" + endQueryLine);
            LogManager.println("Success!");
        } catch (SQLException e) {
            LogManager.print("Failed. ");
            LogManager.println(e.getMessage());
        }
    }
    

    /////////////////////////////////////////////////////////////////////////////////
    ///////////EDIT APPLICATIONS/////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void editApplication(Application application) {
        try {
            if (application.ApplicationStatus.equals("APPROVED")) {
                statement.executeUpdate("DELETE FROM Alcohol WHERE TTBID = '" + application.ApprovedTTBID + "'" + endQueryLine);
                statement.executeUpdate("DELETE FROM Applications WHERE ApplicationNo = '" + application.ApplicationNo + "'" + endQueryLine);
                submitApplication(application);
                approveApplication(application.ApplicationNo);
            } else {
                statement.executeUpdate("DELETE FROM Applications WHERE ApplicationNo = '" + application.ApplicationNo + "'" + endQueryLine);
                submitApplication(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////APPROVE APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /*public static void reapproveApplication(String ApplicationNum, String TTBID) {
        //String TTBID = generateTTBID();
        approveApplication(ApplicationNum, TTBID);
    }
    public static void approveNewApplication(String ApplicationNum) {
        //String TTBID = generateTTBID();
        approveApplication(ApplicationNum, TTBID);
    }*/

    public static void approveApplication(String ApplicationNum) {

        try {
            statement.executeUpdate("UPDATE Applications SET ApplicationStatus = 'APPROVED' WHERE ApplicationNo = '" + ApplicationNum + "'" + endQueryLine);
            statement.executeUpdate("UPDATE Applications SET AgentUsername = NULL WHERE ApplicationNo = '" + ApplicationNum + "'" + endQueryLine);
            //statement.executeUpdate("UPDATE Applications SET AgentName = '" + Main.getUser().name + "' WHERE ApplicationNo = '" + ApplicationNum + "'" + endQueryLine);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LinkedList<DataSet> approvedApplicationLinkedList = queryDatabase(EnumTableType.APPLICATION, "ApplicationNo", ApplicationNum);
        if( approvedApplicationLinkedList != null && !approvedApplicationLinkedList.isEmpty()) { //THERE. ARE. FOUR! LIGHTS!
            Application approvedApplication = (Application) approvedApplicationLinkedList.getFirst();
            String ApplicationNo = approvedApplication.ApplicationNo;
            String SerialNo = approvedApplication.SerialNo;
            String ApplicationType = approvedApplication.ApplicationType;
            String ApplicationStatus = approvedApplication.ApplicationStatus;
            String ManufacturerUsername = approvedApplication.ManufacturerUsername;
            String RepName = approvedApplication.RepName;
            String AgentUsername = approvedApplication.AgentUsername;
            String AgentName = approvedApplication.AgentName;
            String RepID = approvedApplication.RepID;
            String PlantRegistry = approvedApplication.PlantRegistry;
            String Locality = approvedApplication.Locality;
            String Brand = approvedApplication.Brand;
            String FancifulName = approvedApplication.FancifulName; //Fancy feast is delicious.
            String AlcoholType = approvedApplication.AlcoholType; // WHAT ARE THEY SELLING??
            String ABV = approvedApplication.ABV;
            String Address = approvedApplication.Address;//Is this the real life?
            String Address2 = approvedApplication.Address2;//Is this just fantasy?
            String Formula = approvedApplication.Formula;
            String WineAppelation = approvedApplication.WineAppelation;//Caught in a landslide
            String VintageDate = approvedApplication.VintageDate;//NO ESCAPE FROM REALITY...
            String Grapes = approvedApplication.Grapes;
            String PH = approvedApplication.PH; //THEY'RE SELLING CHOCOLATE!!!
            String PhoneNo = approvedApplication.PhoneNo;
            String Email = approvedApplication.Email;
            String AdditionalInfo = approvedApplication.AdditionalInfo;
            String DateOfSubmission = approvedApplication.DateOfSubmission;
            String CompletedDate = StringUtilities.getDate();
            String DateOfExpiration = approvedApplication.DateOfExpiration;
            String TTBID = approvedApplication.ApprovedTTBID;
            String ReasonForRejection = approvedApplication.ReasonForRejection;
            String Class = "";

            try {
                LogManager.println("INSERTING THINGS NOW!!!");
                statement.executeUpdate("INSERT INTO Alcohol (" +
                        "TTBID, " +
                        "PermitNo, " +
                        "SerialNo, " +
                        "CompletedDate, " +
                        "FancifulName, " +
                        "BrandName, " +
                        "Class, " +
                        "Origin, " +
                        "AlcoholType, " +
                        "AlcoholContent, " +
                        "VintageYear, " +
                        "PH) VALUES ('" +
                        TTBID + "', '" +
                        PlantRegistry + "', '" +
                        SerialNo + "', '" +
                        CompletedDate + "', '" +
                        FancifulName.toUpperCase() + "', '" +
                        Brand.toUpperCase() + "', '" +
                        PH + "', '" +
                        Locality + "', '" +
                        AlcoholType + "', '" +
                        ABV + "', '" +
                        VintageDate + "', '" +
                        PH + "')" + endQueryLine);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //HACK

    /////////////////////////////////////////////////////////////////////////////////
    ///////////REJECT APPLICATION////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void rejectApplication(String ApplicationNo, String reasonForRejection, String status) {
        try {
            statement.executeUpdate("UPDATE Applications SET ApplicationStatus = '" + status + "' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            statement.executeUpdate("UPDATE Applications SET ReasonForRejection = '" + reasonForRejection + "' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            statement.executeUpdate("UPDATE Applications SET AgentUsername = NULL WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            //stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            LogManager.println("could not remove application", EnumWarningType.ERROR); //I'm sorry Dave, but I'm afraid I can't do that.
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////FORWARD APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public void forwardApplication(String ApplicationNo, String AgentUsername) {

        UserAgent tempMan;
        LinkedList<DataSet> tempMans = queryDatabase(EnumTableType.AGENT, "Username", AgentUsername);
        tempMan = (UserAgent) tempMans.getFirst();


        try {
            statement.executeUpdate("UPDATE Applications SET AgentUsername = '" + AgentUsername + "' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            statement.executeUpdate("UPDATE Applications SET AgentName = '" + tempMan.name + "' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
        } catch (SQLException e) {
            LogManager.println("agent does not exist", EnumWarningType.ERROR); //I'm sorry Dave, but I'm afraid I can't do that.
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SURRENDER APPLICATION////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void surrenderApplication(String ApplicationNo, String reasonForRejection) {
        try {
            statement.executeUpdate("UPDATE Applications SET ApplicationStatus = 'SURRENDERED' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
//            statement.executeUpdate("UPDATE Applications SET ReasonForRejection = '" + reasonForRejection + "' WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            statement.executeUpdate("UPDATE Applications SET AgentUsername = NULL WHERE ApplicationNo = '" + ApplicationNo + "'" + endQueryLine);
            //stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            LogManager.println("Failed to surrender application. ", EnumWarningType.ERROR); //I'm sorry Dave, but I'm afraid I can't do that.
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS IN AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> getApplicationsByAgent(String agent) {
//        LinkedList<Application> dataSets = new LinkedList<>();
        return queryApplications("SELECT * FROM Applications WHERE AgentUsername = '" + agent + "';");
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD APPLICATIONS TO AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> addApplicationToInbox(String type, String username, int num) {
        LinkedList<DataSet> applicationLinkedList = queryApplications("SELECT * FROM Applications WHERE AlcoholType = '" + type + "' AND ApplicationStatus = 'PENDING' AND AgentUsername = '';");
        LinkedList<Application> addToInbox = new LinkedList<>();

        //counters
        int i = 0;
        int j = -1;

        boolean isFirst = true;//checks if it is the first time through the linked list

        while (i < num) { //makes sure that the inbox never exceeds 10 applications
            try {
                //increaces the counter of linked list
                j++;
                Application tempApp = (Application) applicationLinkedList.get(j); //gets one of the applications from all applications
                LinkedList<DataSet> tempMans = queryDatabase(EnumTableType.MANUFACTURER, "Username", tempApp.ManufacturerUsername);//gets the manufacturer for the specific application
                UserManufacturer tempMan = (UserManufacturer) tempMans.getFirst(); //gets the current date
                String curDate = StringUtilities.getDate();
                LogManager.println(curDate);
                if (!tempMan.AgentDate.equals(curDate)) {
                    tempMan.Agent = "";
                }
                //check if that manufacturer has a specific agent on the day and adds it to the inbox
                if (tempMan.Agent.equals(username) || (tempMan.Agent.equals("") && !isFirst)) {
                    addToInbox.add((Application) applicationLinkedList.get(j));
                    try {
                        //sets the applications agent as the agents username who was there
                        statement.executeUpdate("UPDATE Applications SET AgentUsername = '" + username + "' WHERE ApplicationNo = '" + tempApp.ApplicationNo + "'" + endQueryLine);
                        statement.executeUpdate("UPDATE Applications SET AgentName = '" + Main.getUser().name + "' WHERE ApplicationNo = '" + tempApp.ApplicationNo + "'" + endQueryLine);

                        //sets the manufacturers agent and day to the agent username and date passed in
                        statement.executeUpdate("UPDATE Manufacturers SET Agent = '" + username + "' WHERE Username = '" + tempMan.username + "'" + endQueryLine);
                        statement.executeUpdate("UPDATE Manufacturers SET AgentDate = '" + curDate + "' WHERE Username = '" + tempMan.username + "'" + endQueryLine);

                        //increments the number of applications added to the inbox
                        i++;
                    } catch (SQLException e) {
                        LogManager.println("Error setting agent on application " + ((Application) applicationLinkedList.get(i)).ApplicationNo + " !", EnumWarningType.ERROR);
                    }
                    //checks if the manufacturer doesnt have an agent
                }
                /*else if(tempMan.Agent.equals("")){
                    //adds the application to the inbox on the second pass
                    if(!isFirst){
                        //same as above
                        addToInbox.add((Application) applicationLinkedList.get(i));
                        try {
                            statement.executeUpdate("UPDATE Applications SET AgentUsername = '" + username + "' WHERE ApplicationNo = '" + tempApp.ApplicationNo + "'" + endQueryLine);
                            statement.executeUpdate("UPDATE Manufacturers SET Agent = '" + username + "' WHERE Username = '" + tempMan.Agent + "'" + endQueryLine);
                            i++;
                        } catch (SQLException e) {
                            LogManager.println("Error setting agent on application " + ((Application) applicationLinkedList.get(i)).ApplicationNo + " !", EnumWarningType.ERROR);
                        }
                    }
                }*/
                //if the user has an agent it does not add it to the inbox
            } catch (Exception e) {
                //list of applications changed
                if (isFirst) {
                    //makes the while loop continue and start at the begining of the list
                    isFirst = false;
                    j = -1;
                } else {//If your hand touches metal...
                    //the list ran out of entries twice so break the loop cause nothings left
                    //...I swear by my pretty floral bonnet, I will end you.
                    break;
                }
            }
        }

        return addToInbox; //YOU'VE GOT MAIL!!!!
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////QUERY APPLICATIONS////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static LinkedList<DataSet> queryApplications(String queryStr) {
        LinkedList<DataSet> applicationLinkedList = new LinkedList<>();
        try {
            ResultSet getApplications = statement.executeQuery(queryStr);

            while (getApplications.next()) {
                Application application = new Application();
                application.ApplicationNo = getApplications.getString("ApplicationNo");
                application.SerialNo = getApplications.getString("SerialNo");
                application.ApplicationType = getApplications.getString("ApplicationType");
                application.ApplicationStatus = getApplications.getString("ApplicationStatus");
                application.ManufacturerUsername = getApplications.getString("ManufacturerUsername");
                application.RepName = getApplications.getString("RepName");
                application.AgentUsername = getApplications.getString("AgentUsername");
                application.AgentName = getApplications.getString("AgentName");
                application.RepID = getApplications.getString("RepID");
                application.PlantRegistry = getApplications.getString("PlantRegistry");
                application.Locality = getApplications.getString("Locality");
                application.Brand = getApplications.getString("Brand");
                application.FancifulName = getApplications.getString("FancifulName");
                application.AlcoholType = getApplications.getString("AlcoholType");
                application.ABV = getApplications.getString("ABV");
                application.Address = getApplications.getString("Address");
                application.Address2 = getApplications.getString("Address2");
                application.Formula = getApplications.getString("Formula");
                application.WineAppelation = getApplications.getString("WineAppelation");
                application.VintageDate = getApplications.getString("VintageDate");
                application.Grapes = getApplications.getString("Grapes");
                application.PH = getApplications.getString("PH");
                application.PhoneNo = getApplications.getString("PhoneNo");
                application.Email = getApplications.getString("Email");
                application.AdditionalInfo = getApplications.getString("AdditionalInfo");
                application.DateOfSubmission = getApplications.getString("DateOfSubmission");
                application.DateOfApproval = getApplications.getString("DateOfApproval");
                application.DateOfExpiration = getApplications.getString("DateOfExpiration");
                application.ApprovedTTBID = getApplications.getString("ApprovedTTBID");
                application.ReasonForRejection = getApplications.getString("ReasonForRejection");
                applicationLinkedList.add(application);
//                String thisApplicationNo = getApplications.getString("ApplicationNo");
/*                if (!setAgent.equals("")) {
                    try {
                        statement.executeUpdate("UPDATE Applications SET AgentUsername = '" + setAgent + "' WHERE ApplicationNo = '" + thisApplicationNo + "';");
                    } catch (SQLException e) {
                        LogManager.println("Error setting agent on application " + thisApplicationNo + " !", EnumWarningType.ERROR);
                    }
                }*/
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>(); //I married me a powerful, ugly creature!
        }

        return applicationLinkedList;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD USERS/////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void addUser(User user, String password, EnumUserType userType) throws DuplicateUserException{
//        String table = "Agents";
//        boolean Super = false;
        try {
            //if(userType.equals(EnumUserType.SUPER_AGENT)){table = "Agents"; Super = true;}
            if (userType.equals(EnumUserType.AGENT) || userType.equals(EnumUserType.SUPER_AGENT)) {
                try {
                    UserAgent agent = (UserAgent) user;
                    String SuperAgent = "false";
                    if (userType.equals(EnumUserType.SUPER_AGENT)) {
                        SuperAgent = "true";
                    }
                    String status = "pending";
                    statement.executeUpdate("INSERT INTO Agents" + " (ID, username, PasswordHash, FullName, Email, SuperAgent, Status) VALUES " +
                            "('" + agent.ID + "',  '" + agent.username + "', '" + PasswordStorage.createHash(password) + "', '" + agent.name + "', '" + agent.email + "', '" + SuperAgent + "', '" + status + "')");
                } catch (PasswordStorage.CannotPerformOperationException e) {
                    e.printStackTrace();
                }catch (MySQLIntegrityConstraintViolationException e){
                    throw new DuplicateUserException();
                }
            }
            if (userType.equals(EnumUserType.MANUFACTURER)) {
                UserManufacturer manufacturer = (UserManufacturer) user;

                try {
                    statement.executeUpdate("INSERT INTO Manufacturers" + " (Username, PasswordHash, Company, FullName, RepID, Email, PlantRegistry, PhoneNo, Address2, Agent, AgentDate) VALUES " +
                            "('" + manufacturer.username + "', '" + PasswordStorage.createHash(password) + "', '', '', '', '', '', '', '', '', '')");

                } catch (PasswordStorage.CannotPerformOperationException e) {
                    e.printStackTrace();
                } catch (MySQLIntegrityConstraintViolationException e){
                    throw new DuplicateUserException();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////LOG IN////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public User login(String username, String password) throws UserNotFoundException, IncorrectPasswordException, PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        ResultSet user;
        try {
            LogManager.print("Searching for an agent called " + username + "... ", EnumWarningType.NOTE);
            //user = statement.executeQuery("SELECT * FROM Agents WHERE username = '" + username + "';");

            user = statement.executeQuery("SELECT * FROM Agents WHERE username = '" + username + "'" + endQueryLine);

            if (user.next()) {

                //Create the Agent object from database information
                UserAgent agent = new UserAgent(user.getString("FullName"), username, user.getString("Email"), user.getString("ID"), user.getString("SuperAgent"), user.getString("Status"));

                LogManager.println("Found!");

//                tryPassword(username, password, user.getString("PasswordHash"));
                if (PasswordStorage.verifyPassword(password, user.getString("PasswordHash"))) {
                    return agent;
                } else {
                    throw new IncorrectPasswordException(username);
                }
            } else {
                LogManager.println("not found.");
                LogManager.print("Searching for a manufacturer called " + username + "... ", EnumWarningType.NOTE);

                ResultSet manufacturerRS = statement.executeQuery("SELECT * FROM Manufacturers WHERE Username = '" + username + "'" + endQueryLine);
                LinkedList<DataSet> manufacturerLinkedList = new LinkedList<>();
                if (manufacturerRS.next()) {
                    manufacturerLinkedList = queryDatabase(EnumTableType.MANUFACTURER, "Username", username);
                    LogManager.println("Found!");
//                    System.out.println("setting manufacturer");
                    UserManufacturer manufacturer = (UserManufacturer) manufacturerLinkedList.getFirst();
                    /*try {
//                        tryPassword(username, password, user.getString("PasswordHash"));
                    } catch (Exception e) {
                        LogManager.println(e.getMessage(), EnumWarningType.ERROR);
                    }*/
                    String passwordHash = "";
//                    System.out.println("Getting password hash");
                    try {
                        manufacturerRS = statement.executeQuery("SELECT * FROM Manufacturers WHERE Username = '" + username + "'" + endQueryLine);
                        manufacturerRS.next();
                        passwordHash = manufacturerRS.getString("PasswordHash");
                    } catch (Exception e) {
                        System.out.println("Failed to get password hash! " + e.getMessage());
                    }
                    System.out.println(passwordHash);
//                    String passwordHash = "";
//                    System.out.println("checking password");
                    if (PasswordStorage.verifyPassword(password, passwordHash)) {
//                        System.out.println("Password good");
                        return manufacturer;
                    } else {
//                        System.out.println("Password bad");
                        throw new IncorrectPasswordException(username);
                    }
                } else {
                    LogManager.println("User " + username + " not found.", EnumWarningType.WARNING);
                    throw new UserNotFoundException(username);
                }

            }

        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
        }
        return null;
    }

    private boolean tryPassword(String username, String password, String correctHash) throws IncorrectPasswordException, PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        //try {
        if (PasswordStorage.verifyPassword(password, correctHash)) {
            LogManager.println("Incorrect password entered for " + username);
            return true;
        } else {
            return false;
        }
//        } catch (PasswordStorage.CannotPerformOperationException e) {
//            LogManager.println("Password operation failed for " + username + ".", EnumWarningType.ERROR);
//            throw new PasswordStorage.CannotPerformOperationException("Password operation failed for user " + username + ".");
//        } catch (PasswordStorage.InvalidHashException e) {
//            //LogManager.printStackTrace(e.getStackTrace());
//            LogManager.println("Invalid stored has for " + username + ".", EnumWarningType.ERROR);
//            throw new PasswordStorage.InvalidHashException("Invalid stored hash for " + username + ".");
//        }

    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////TESTS/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public void entryTest() {
        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('06110001000003', 'ID-I-15001', '060028', '2016-04-01', 'ROSADO', 'LO BRUJO', '52', '80', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('07362001000194', 'BWN-NC-15051', '070049', '2016-04-01', 'VENI VIDI VICI', 'INCORVAIA', '30', '80', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('16047001000297', 'IL-I-340', '160033', '2016-04-04', 'POULSARD VIEILLES VIGNES', 'DOMAINE ROLET PERE ET FILS', '51', '80', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('05348001000134', 'BR-MO-ANH-1', '05B645', '2005-12-21', '', 'BUDWEISER', '29', '901', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('06054001000120', 'BR-MO-ANH-1', '06B722', '2006-10-06', '', 'BUDWEISER', '29', '901', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('04267001000046', 'BR-MO-ANH-1', '04B554', '2004-10-19', '', 'BUDWEISER', '09', '901', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('07319001000233', 'BR-TX-ANH-1', '07B886', '2007-11-21', '', 'BUDWEISER', '44', '901', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('06243001000024', 'BR-MA-BOS-1', '06048C', '2015/05/28', 'HONEY PORTER', 'SAMUEL ADAMS', '26', '906', 'Beer')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('12104001000339', 'BR-MA-BOS-1', '12039U', '2012/04/23', 'OLD KENTUCKY STYLE', 'SAMUEL ADAMS', '24', '906', 'Beer')"); //Why are you looking down here? There's nothing to see here.
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('12076001000564', 'BWC-CA-5099', '12020A', '2012/04/04', '', 'BERINGER', '01', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('12089001000135', 'TPWBH-CA-20114', '1204TP', '2012/04/17', '', 'BERINGER', '70', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('09351001000078', 'FL-I-540', '093836', '2009/12/21', '', 'CAVIT', '50', '80', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('10155001000193', 'FL-I-540', '104091', '2006/12/23', '', 'CAVIT', '50', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('62987272654101', 'FL-I-540', '654813', '2006/12/23', '', 'CAVIT', '50', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('62987272654101', 'FL-I-540', '9873331', '2006/12/23', '', 'CAVIT', '50', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('00568612654101', 'FL-I-540', '03452983', '2007/12/23', '', 'CAVIT', '50', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('62987272654101', 'FL-I-540', '104091', '2006/12/23', '', 'CAVIT', '50', '81', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('10096001000193', 'BW-CA-5920', '10LC08', '2010/04/15', 'MAGGY HAWK NO. 5', 'LA CREMA', '01', '88', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('10096001000196', 'BW-CA-5920', '10LC08', '2010/04/05', 'MAGGY HAWK', 'LA CREMA', '01', '88', 'Wine')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//You're still here?
//It's over.
//Go home.

//...Go.