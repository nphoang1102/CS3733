package database;

import base.EnumTableType;
import base.EnumWarningType;
import base.LogManager;
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

public class DatabaseManager {
    /**
     * Created by Evan Goldstein on 4/1/17.
     */
    private static Statement statement = null;
    private static Connection connection = null;

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CONSTRUCTOR - CONNECTS TO DATABASE////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
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
        LogManager.println("Java DB connection established!");

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////CREATE TABLES/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static void CreateTables() {

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
                    " RepID VARCHAR(30) PRIMARY KEY,\n" +
                    " PlantRegistry VARCHAR(30) NOT NULL,\n" +
                    " Source VARCHAR(30) NOT NULL,\n" +
                    " SerialNo VARCHAR(30) NOT NULL,\n" +
                    " AlcoholType VARCHAR(10) NOT NULL,\n" +
                    " Brand VARCHAR(50) NOT NULL,\n" +
                    " FancifulName VARCHAR(50),\n" +
                    " Address VARCHAR(100) NOT NULL,\n" +
                    " Address2 VARCHAR(100),\n" +
                    " Formula VARCHAR(500) NOT NULL,\n" +
                    " Grapes VARCHAR(50),\n" +
                    " WineAppelation VARCHAR(50),\n" +
                    " PhoneNo VARCHAR(20) NOT NULL,\n" +
                    " Email VARCHAR(30) NOT NULL,\n" +
                    " AdditionalInfo VARCHAR(500) NOT NULL,\n" +
                    " ABV VARCHAR(10) NOT NULL,\n" +
                    " VintageDate DATE,\n" +
                    " PH VARCHAR(10),\n" +
                    " Status ENUM('APPROVED', 'PENDING', 'REJECTED', 'SURRENDERED') NOT NULL,\n" +
                    " ApplicationNo VARCHAR(20) NOT NULL,\n" +
                    " DateOfApproval DATE,\n" +
                    " AgentName VARCHAR(30),\n" +
                    " DateOfExpiration DATE,\n" +
                    " ManufacturerUsername VARCHAR(20),\n" +
                    " AgentUsername VARCHAR(20)\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Applications' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Agents(\n" +
                    " ID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(50) NOT NULL UNIQUE,\n" +
                    " Password VARCHAR(30) NOT NULL,\n" +
                    " Name VARCHAR(50) NOT NULL,\n" +
                    " Email VARCHAR(30) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Agents' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    " UUID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(30) NOT NULL UNIQUE,\n" +
                    " Password VARCHAR(30) NOT NULL,\n" +
                    " Company VARCHAR(50) NOT NULL,\n" +
                    " Name VARCHAR(50) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Manufacturers' exists.", EnumWarningType.NOTE);
        }

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
    public static void submitApplication(String Manufacturer, String PermitNo, String Status, String AlcoholType, String AgentID, String Source, String Brand, String Address, String Address2, String Volume, String ABV, String PhoneNo, String AppType, String VintageDate, String PH, String ApplicantName, String DateSubmitted, String DBAorTrade, String Email) {
        try {
            String ApplicationNo = generateTTBID(); //TODO - Replace this with the correct method for generating Application Numbers
            String status = "PENDING";
            statement.executeUpdate("INSERT INTO Applications (ApplicationNo, Manufacturer, PermitNo, Status, AlcoholType, AgentID, Source, Brand, Address, Address2, Volume, ABV, PhoneNo, AppType, VintageDate, PH, ApplicantName, DateSubmitted, DBAorTrade, Email) VALUES " +
                    "('" + ApplicationNo + "', '" + Manufacturer + "', '" + PermitNo + "', '" + status + "', '" + AlcoholType + "', '" + AgentID + "', '" + Source + "', '" + Brand + "', '" + Address + "', '" + Address2 + "', '" + Volume + "', '" + ABV + "', '" + PhoneNo + "', '" + AppType + "', '" + VintageDate + "', '" + PH + "', '" + ApplicantName + "', '" + DateSubmitted + "', '" + DBAorTrade + "', '" + Email
                    + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GENERIC DATABASE QUERY////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryDatabase(EnumTableType table, String column, String value) {
        if(table.equals(EnumTableType.ALCOHOL)){
            return null;
        }
        else if(table.equals(EnumTableType.APPLICATION)){
            return queryApplications("SELECT * FROM Applications WHERE '" + column + "' = '" + value + "';", "");
        }
        else if(table.equals(EnumTableType.ALCOHOL)){

        }
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////APPROVE APPLICATION///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    //TODO
    public static void approveApplication(String ApplicationNum) {
        try {
            statement.executeUpdate("UPDATE Users SET status = 'APPROVED' WHERE ApplicationNo = '" + ApplicationNum + "';");
            //TODO- THIS NO LONGER MAKES SENSE SINCE WE DO NOT HAVE AGENTINBOX
            statement.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + ApplicationNum + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LinkedList<DataSet> approvedApplicationLinkedList = queryDatabase(EnumTableType.APPLICATION, "ApplicationNo", ApplicationNum);
        Application approvedApplication = (Application) approvedApplicationLinkedList.getFirst();

//        String RepID = approvedApplication.RepID;
        String PlantRegistry = approvedApplication.PlantRegistry;
        String Source = approvedApplication.Source;
        String SerialNo = approvedApplication.SerialNo;
        String AlcoholType = approvedApplication.AlcoholType;
        String Brand = approvedApplication.Brand;
        String FancifulName = approvedApplication.FanicifulName;
//        String Address = approvedApplication.Address;
//        String Address2 = approvedApplication.Address2;
//        String Formula = approvedApplication.Formula;
//        String Grapes = approvedApplication.WineAppelation;
//        String PhoneNo = approvedApplication.PhoneNo;
//        String Email = approvedApplication.Email;
//        String AppType = approvedApplication.AppType;
//        String AdditionalInfo = approvedApplication.AdditionalInfo;
        String Date = approvedApplication.Date;
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
            String TTBID = generateTTBID();
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
        for(int i = 0; i<num; i++){
            addToInbox.add((Application) applicationLinkedList.get(i));
        }
        return addToInbox;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////QUERY APPLICATIONS////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> queryApplications(String queryStr, String setAgent) {
        /*if (num == 0) {
            try {
                ResultSet count = statement.executeQuery("SELECT COUNT(*) AS total FROM Applications");
                count.next();
                num = count.getInt("total");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        LinkedList<DataSet> applicationLinkedList = new LinkedList<>();
        try {
            ResultSet getApplications = statement.executeQuery(queryStr);

            while (getApplications.next()) {
                getApplications.next();
                Application application = new Application();
                application.RepID = getApplications.getString("RepID");
                application.PlantRegistry = getApplications.getString("PlantRegistry");
                application.SerialNo = getApplications.getString("SerialNo");
                application.FanicifulName = getApplications.getString("FanicifulName");
                application.Formula = getApplications.getString("Formula");
                application.Grapes = getApplications.getString("Grapes");
                application.WineAppelation = getApplications.getString("WineAppelation");
                application.AdditionalInfo = getApplications.getString("AdditionalInfo");
                application.Date = getApplications.getString("Date");
                application.PrintName = getApplications.getString("PrintName");
                application.DateOfApproval = getApplications.getString("DateOfApproval");
                application.AgentName = getApplications.getString("AgentName");
                application.DateOfExpiration = getApplications.getString("DateOfExpiration");
                application.ManufacturerUsername = getApplications.getString("ManufacturerUsername");
                application.AgentUsername = getApplications.getString("AgentUsername");
                application.ApplicationNO = getApplications.getString("ApplicationNO");
                application.Status = getApplications.getString("Status");
                application.AlcoholType = getApplications.getString("AlcoholType");
                application.Source = getApplications.getString("Source");
                application.Brand = getApplications.getString("Brand");
                application.Address = getApplications.getString("Address");
                application.Address2 = getApplications.getString("Address2");
                application.ABV = getApplications.getString("ABV");
                application.PhoneNo = getApplications.getString("PhoneNo");
                application.AppType = getApplications.getString("AppType");
                application.VintageDate = getApplications.getString("VintageDate");
                application.PH = getApplications.getString("PH");
                application.Email = getApplications.getString("Email");
                applicationLinkedList.add(application);
                String thisApplicationNo = getApplications.getString("ApplicationNo");
                if (!setAgent.equals("")) {
                    try {
                        statement.executeUpdate("UPDATE Applications SET InboxAgent = '" + setAgent + "' WHERE ApplicationNo = '" + thisApplicationNo + "';");
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
    public static void addUser(String username, String password, String type) {
        try {
            statement.executeUpdate("INSERT INTO Users (username, passwordHash, userType) VALUES " +
                    "('" + username + "', '" + password + "', '" + type + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//TODO - Write method to look up a user
    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET USER BY USERNAME//////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

   /* public static User getUser(String username) {
        DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
        try {
            ResultSet user = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "';";);
            user.next();
            userType = user.getString("userType");
            LogManager.println("User " + username + " is type " + userType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }*/


    /*///////////////////////////////////////////////////////////////////////////////////////////////////
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
            ResultSet searchManufacturers = statement.executeQuery(query);
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
    }*/

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
