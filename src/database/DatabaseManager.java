package database;

import base.EnumTableType;
import base.EnumWarningType;
import base.LogManager;
import base.Main;
import screen.EnumUserType;
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

import static com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmIndexes.UUID;

public class DatabaseManager {
    /**
     * Created by Evan Goldstein on 4/1/17.
     */
    private static Statement statement;
    private static Connection connection = null;

    /////////////////////////////////////////////////////////////////////////////////
    ///////////EXCEPTIONS////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public class UserNotFoundException extends Exception {
        String username;

        public UserNotFoundException(String username) {
            super("User " + username + " not found");
        }
    }

    public class IncorrectPasswordException extends Exception {
        String username;

        public IncorrectPasswordException(String username) {
            super("Incorrect password for user " + username + "!");
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////CONSTRUCTOR - CONNECTS TO DATABASE////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public DatabaseManager() {
        LogManager.println("Attempting Database Connection.");
       /* try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LogManager.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return;
        }*/
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
        LogManager.println("Java DB connection established!");

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
            LogManager.println("statement failed");
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CREATE TABLES/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void createTables() {

        try {
            statement.executeUpdate("CREATE TABLE Alcohol(\n" +
                    " TTBID VARCHAR(30) PRIMARY KEY,\n" +
                    " PermitNo VARCHAR(30) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " CompletedDate DATE,\n" +
                    " FancifulName VARCHAR(100),\n" +
                    " BrandName VARCHAR(100) NOT NULL,\n" +
                    " Origin VARCHAR(10) NOT NULL,\n" +
                    " Class VARCHAR(10) NOT NULL,\n" +
                    " Type VARCHAR(10) NOT NULL,\n" +
                    " AlcoholContent VARCHAR(30),\n" +
                    " VintageYear VARCHAR(10),\n" +
                    " PH VARCHAR(10)\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Alcohol' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Applications(\n" +
                    " ApplicationNo VARCHAR(20) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " ApplicationType VARCHAR(30) NOT NULL,\n" +
                    " ApplicationStatus ENUM('APPROVED', 'PENDING', 'REJECTED', 'SURRENDERED') NOT NULL,\n" +
                    " ManufacturerUsername VARCHAR(20),\n" +
                    " AgentName VARCHAR(30),\n" +
                    " AgentUsername VARCHAR(30),\n" +
                    " RepID VARCHAR(30) PRIMARY KEY,\n" +
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
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Applications' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Agents(\n" +
                    " ID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(50) NOT NULL UNIQUE,\n" +
                    " PasswordHash VARCHAR(30) NOT NULL,\n" +
                    " Name VARCHAR(50) NOT NULL,\n" +
                    " Email VARCHAR(30) NOT NULL,\n" +
                    " SuperAgent BOOLEAN NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Agents' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    " Username VARCHAR(30) PRIMARY KEY,\n" +
                    " Password VARCHAR(30) NOT NULL,\n" +
                    " Company VARCHAR(100) NOT NULL,\n" +
                    " Name VARCHAR(50) NOT NULL,\n" +
                    " RepID VARCHAR(50) NOT NULL,\n" +
                    " Email VARCHAR(50) NOT NULL,\n" +
                    " PlantRegistry VARCHAR(50) NOT NULL,\n" +
                    " PhoneNo VARCHAR(20) NOT NULL,\n" +
                    " Address2 VARCHAR(50) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Manufacturers' exists.", EnumWarningType.NOTE);
        }

    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERIC DATABASE QUERY////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryDatabase(EnumTableType table, String column, String value) {
        String type = Main.screenManager.getSearchTerm();
        LogManager.println("type = " + type);
        if (table.equals(EnumTableType.ALCOHOL)) {
            if (value.isEmpty() && type == "All") {
                return queryAlcohol("SELECT * FROM Alcohol;");
            } else {
                if (type == "All") {
                    return queryAlcohol("SELECT * FROM Alcohol WHERE BrandName LIKE '" + value + "%' OR BrandName LIKE '%" + value + "' OR BrandName LIKE '%" + value + "%';");
                } else if (type == "Beer") {
                    return queryAlcohol("SELECT * FROM Alcohol WHERE Type = 'Beer' AND BrandName LIKE '" + value + "%';");
                } else if (type == "Wine") {
                    return queryAlcohol("SELECT * FROM Alcohol WHERE Type = 'Wine' AND BrandName LIKE '" + value + "%';");
                } else {
                    return queryAlcohol("SELECT * FROM Alcohol WHERE Type <> 'Beer' AND Type <> 'Wine' AND BrandName LIKE '" + value + "%';");
                }
            }
        } else if (table.equals(EnumTableType.APPLICATION)) {
            return queryApplications("SELECT * FROM Applications WHERE '" + column + "' = '" + value + "';", "");
        } else if (table.equals(EnumTableType.MANUFACTURER)) {
            if (column.equals("")) {
                return queryManufacturers("SELECT * FROM Manufacturers");
            } else {
                LogManager.println("SEARCHING FOR MANUFACTURER " + column + " = " + value);
                return queryManufacturers("SELECT * FROM Manufacturers WHERE " + column + " = '" + value + "';");
            }
        }
        return null;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////ALCOHOL SEARCH////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryAlcohol(String queryStr) {

        LinkedList<DataSet> alcoholLinkedList = new LinkedList<>();

        try {
            ResultSet getAlcohol = statement.executeQuery(queryStr);

            while (getAlcohol.next()) {
                Alcohol alcohol = new Alcohol();
                alcohol.TTBID = getAlcohol.getString("TTBID");
                alcohol.PermitNo = getAlcohol.getString("PermitNo");
                alcohol.SerialNo = getAlcohol.getString("SerialNo");
                alcohol.CompletedDate = getAlcohol.getString("CompletedDate");
                alcohol.FancifulName = getAlcohol.getString("FancifulName");
                alcohol.BrandName = getAlcohol.getString("BrandName");
                alcohol.Origin = getAlcohol.getString("Origin");
                alcohol.Class = getAlcohol.getString("Class");
                alcohol.Type = getAlcohol.getString("Type");
                alcohol.AlcoholContent = getAlcohol.getString("AlcoholContent");
                alcohol.VintageYear = getAlcohol.getString("VintageYear");
                alcohol.PH = getAlcohol.getString("PH");
                alcoholLinkedList.add(alcohol);
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the alcohol table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }

        return alcoholLinkedList;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERATE TTBID////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static String generateTTBID() {
        return Long.toString(Math.round(Math.random() * 10000000));
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////SUBMIT APPLICATIONS///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void submitApplication(Application application) {
        // OLD PARAMETERS: String Manufacturer, String PermitNo, String Status, String AlcoholType, String AgentID, String Source, String Brand, String Address, String Address2, String Volume, String ABV, String PhoneNo, String AppType, String VintageDate, String PH, String ApplicantName, String DateSubmitted, String DBAorTrade, String Email
        try {
            String ApplicationNo = generateTTBID(); //TODO - Replace this with the correct method for generating Application Numbers
            String status = "PENDING";
            statement.executeUpdate("INSERT INTO Applications " +
                    "(ApplicationNo, " +
                    "SerialNo, " +
                    "ApplicationType," +
                    "ApplicationStatus," +
                    "ManufacturerUsername, " +
                    "AgentName, " +
                    "AgentUsername, " +
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
                    "VintageDate," +
                    "Grapes, " +
                    "PH," +
                    "PhoneNo, " +
                    "Email, " +
                    "AdditionalInfo, " +
                    "DateOfSubmission," +
                    "DateOfApproval," +
                    "DateOfExpiration," +
                    "ApprovedTTBID," +
                    "ReasonForRejection" +
                    ") VALUES " +
                    "('"
                    + application.ApplicationNo + "', '"
                    + application.SerialNo + "', '"
                    + application.ApplicationType + "', '"
                    + application.ApplicationStatus + "', '"
                    + application.ManufacturerUsername + "', '"
                    + application.AgentName + "', '"
                    + application.AgentUsername + "', '"
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
                    + application.DateOfSubmission + "', '"
                    + application.DateOfApproval + "', '"
                    + application.DateOfExpiration + "', '"
                    + application.ApprovedTTBID + "', '"
                    + application.ReasonForRejection
                    + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////MANUFACTURER QUERIES//////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public static LinkedList<DataSet> queryManufacturers(String query) {
        LinkedList<DataSet> manufacturers = new LinkedList<>();
        try {
            ResultSet searchManufacturers = statement.executeQuery(query);
            LogManager.println("queryManufacturers() has run the query: " + query, EnumWarningType.NOTE);
            while (searchManufacturers.next()) {
                String username = searchManufacturers.getString("Username");
                LogManager.println("queryManufacturers() is adding the user " + username + " to a list that is now ", EnumWarningType.NOTE);
                UserManufacturer manufacturer = new UserManufacturer(query);
                manufacturer.Company = searchManufacturers.getString("Company");
                manufacturer.username = username;
                manufacturer.name = searchManufacturers.getString("name");
                manufacturer.email = searchManufacturers.getString("email");
                manufacturer.userType = EnumUserType.MANUFACTURER;
                manufacturer.Address2 = searchManufacturers.getString("Address2");
                manufacturer.Company = searchManufacturers.getString("Company");
                manufacturer.name = searchManufacturers.getString("Name");
                manufacturer.RepID = searchManufacturers.getString("RepID");
                manufacturer.PlantRegistry = searchManufacturers.getString("PlantRegistry");
                manufacturer.PhoneNo = searchManufacturers.getString("PhoneNo");
                manufacturers.add(manufacturer);
                LogManager.print( manufacturers.size() + "items long.");
            }
            searchManufacturers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////APPROVE APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void approveApplication(String ApplicationNum) {
        String TTBID = generateTTBID();
        try {
            statement.executeUpdate("UPDATE Users SET status = 'APPROVED' WHERE ApplicationNo = '" + ApplicationNum + "';");
            statement.executeUpdate("UPDATE Applications SET AgentUsername = NULL WHERE ApplicationNo = '" + ApplicationNum + "';");
            statement.executeUpdate("UPDATE Applications SET ApprovedTTBID = '" + TTBID + "' WHERE ApplicationNo = '" + ApplicationNum + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LinkedList<DataSet> approvedApplicationLinkedList = queryDatabase(EnumTableType.APPLICATION, "ApplicationNo", ApplicationNum);
        Application approvedApplication = (Application) approvedApplicationLinkedList.getFirst();
        //TODO - Double check fields
        String PlantRegistry = approvedApplication.PlantRegistry;
        String Source = approvedApplication.Locality;
        String SerialNo = approvedApplication.SerialNo;
        String AlcoholType = approvedApplication.AlcoholType;
        String Brand = approvedApplication.Brand;
        String FancifulName = approvedApplication.FancifulName;
        String Date = approvedApplication.DateOfApproval;
//        String PrintName = approvedApplication.PrintName;
//        String ABV = approvedApplication.ABV;
//        String VintageDate = approvedApplication.VintageDate;
//        String PH = approvedApplication.PH;
//        String Status = approvedApplication.Status;
//        String ApplicationNo = approvedApplication.ApplicationNO;
//        String DateOfApproval = approvedApplication.DateOfApproval;
//        String AgentName = approvedApplication.AgentName;
//        String DateOfExpiration = approvedApplication.DateOfExpiration;
//        String ManufacturerUsername = approvedApplication.ManufacturerUsername;
//        String AgentUsername = approvedApplication.AgentUsername;
//        String Address = approvedApplication.Address;
//        String Address2 = approvedApplication.Address2;
//        String Formula = approvedApplication.Formula;
//        String Grapes = approvedApplication.WineAppelation;
//        String PhoneNo = approvedApplication.PhoneNo;
//        String Email = approvedApplication.Email;
//        String AppType = approvedApplication.AppType;
//        String AdditionalInfo = approvedApplication.AdditionalInfo;

        try {
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('" + TTBID + "', '" + PlantRegistry + "', '" + SerialNo + "', '" + Date + "', '" + FancifulName + "', '" + Brand + "', '" + Source + "', '" + AlcoholType + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////REJECT APPLICATION////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void rejectApplication(String ApplicationNo) {//GET REKKKDDDDD!
        try {
            statement.executeUpdate("UPDATE Users SET status = 'REJECTED' WHERE ApplicationNo = '" + ApplicationNo + "';");
            statement.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + ApplicationNo + "';");
            //stmt.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES (" + TTBID + " " + PermitNo + " " + SerialNo + " " + Date + " " + FancifulName + " " + BrandName + " " + Origin + " " + Class + " " + Type + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS IN AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> getApplicationsByAgent(String agent) {
        LinkedList<Application> dataSets = new LinkedList<>();
        return queryApplications("SELECT * FROM Applications WHERE AgentUsername = '" + agent + "';", "");
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD APPLICATIONS TO AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> addApplicationToInbox(String type, String username, int num) {
        LinkedList<DataSet> applicationLinkedList = queryApplications("SELECT * FROM Applications WHERE AlcoholType = '" + type + "';", username);
        LinkedList<Application> addToInbox = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            try{
                addToInbox.add((Application) applicationLinkedList.get(i));
            }catch (Exception e){
                LogManager.println("No Applications found", EnumWarningType.WARNING);
            }
        }
        return addToInbox;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////QUERY APPLICATIONS////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private static LinkedList<DataSet> queryApplications(String queryStr, String setAgent) {
        LinkedList<DataSet> applicationLinkedList = new LinkedList<>();
        try {
            ResultSet getApplications = statement.executeQuery(queryStr);

            while (getApplications.next()) {
                //getApplications.next();
                Application application = new Application();
                application.ApplicationNo = getApplications.getString("ApplicationNo");
                application.SerialNo = getApplications.getString("SerialNo");
                application.ApplicationType = getApplications.getString("ApplicationType");
                application.ApplicationStatus = getApplications.getString("ApplicationStatus");
                application.ManufacturerUsername = getApplications.getString("ManufacturerUsername");
                application.AgentName = getApplications.getString("AgentName");
                application.AgentUsername = getApplications.getString("AgentUsername");
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
                String thisApplicationNo = getApplications.getString("ApplicationNo");
                if (!setAgent.equals("")) {
                    try {
                        statement.executeUpdate("UPDATE Applications SET AgentUsername = '" + setAgent + "' WHERE ApplicationNo = '" + thisApplicationNo + "';");
                    } catch (SQLException e) {
                        LogManager.println("Error setting agent on application " + thisApplicationNo + " !", EnumWarningType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }

        return applicationLinkedList;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////ADD USERS/////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void addUser(User user, String password, EnumUserType userType) {
        String table = "Agents";
        boolean Super = false;
        try {
            //if(userType.equals(EnumUserType.SUPER_AGENT)){table = "Agents"; Super = true;}
            if (userType.equals(EnumUserType.AGENT) || userType.equals(EnumUserType.SUPER_AGENT)) {
                try {
                    UserAgent agent = (UserAgent) user;
                    String SuperAgent = "false";
                    if (userType.equals(EnumUserType.SUPER_AGENT)) {
                        SuperAgent = "true";
                    }
                    statement.executeUpdate("INSERT INTO Agents" + " (ID, username, PasswordHash, Name, Email, SuperAgent) VALUES " +
                            "('" + agent.ID + "',  '" + agent.username + "', '" + PasswordStorage.createHash(password) + "', '" + agent.name + "', '" + agent.email + "', '" + SuperAgent + "')");
                } catch (PasswordStorage.CannotPerformOperationException e) {
                    e.printStackTrace();
                }
            }
            if (userType.equals(EnumUserType.MANUFACTURER)) {
                UserManufacturer manufacturer = (UserManufacturer) user;
                try {
                    statement.executeUpdate("INSERT INTO Manufacturers" + " (Username, Password, Company, Name, RepID, Email, PlantRegistry, PhoneNo, Address2) VALUES " +
                            "( '" + manufacturer.username + "', '" + PasswordStorage.createHash(password) + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "', '" + "" + "')");

                } catch (PasswordStorage.CannotPerformOperationException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET USER BY USERNAME//////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public User login(String username, String password) throws UserNotFoundException, IncorrectPasswordException, PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        ResultSet user;
        try {
            user = statement.executeQuery("SELECT * FROM Agents WHERE username = '" + username + "';");
            if (user.next()) {
                UserAgent agent = new UserAgent(user.getString("name"), username, user.getString("email"), user.getString("ID"), "false");
                LogManager.println("User " + username + " is an agent");
                tryPassword(username, password, user.getString("PasswordHash"));
                return agent;
            } else {
                //user = statement.executeQuery("SELECT * FROM Manufacturers WHERE username = '" + username + "';");
                LinkedList<DataSet> manufacturerLinkedList = queryDatabase(EnumTableType.MANUFACTURER, "Username", username);
                if (!manufacturerLinkedList.isEmpty()) {
                    UserManufacturer manufacturer = (UserManufacturer) manufacturerLinkedList.get(0);
                    LogManager.println("User " + username + " is an agent");
                    tryPassword(username, password, user.getString("PasswordHash"));
                    return manufacturer;
                } else {
                    LogManager.println("User " + username + " not found.", EnumWarningType.WARNING);
                    //throw new UserNotFoundException(username);
                }

            }

        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
        }
        return null;
    }

    private boolean tryPassword(String username, String password, String correctHash) throws IncorrectPasswordException, PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        try {
            if (PasswordStorage.verifyPassword(password, correctHash)) {
                return true;
            } else {
                throw new IncorrectPasswordException(username);
            }
        } catch (PasswordStorage.CannotPerformOperationException e) {
            LogManager.println("Invalid stored password hash for user " + username + ".", EnumWarningType.ERROR);
            throw new PasswordStorage.CannotPerformOperationException("Invalid stored password hash for user " + username + ".");
        } catch (PasswordStorage.InvalidHashException e) {
            //LogManager.printStackTrace(e.getStackTrace());
            LogManager.println("Password hash validation failed for " + username + ".", EnumWarningType.ERROR);
            throw new PasswordStorage.InvalidHashException("Password hash validation failed for user " + username + ".");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////TESTS/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /*public void entryTest() {
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
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('12104001000339', 'BR-MA-BOS-1', '12039U', '2012/04/23', 'OLD KENTUCKY STYLE', 'SAMUEL ADAMS', '24', '906', 'Beer')");
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
        try {
            statement.executeUpdate("INSERT INTO Manufacturers (UUID, Username, Company) VALUES ('FakeUUID123', 'TheAlcoholic12', 'AlcoholicsAnonymous')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Manufacturers (UUID, Username, Company) VALUES ('UUID239', 'User123', 'RealCompany')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate("INSERT INTO Users (username, passwordHash, userType) VALUES ('user123', 'password123', 'Agent')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
