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
    private static Statement statement = null;
    private static Connection connection = null;
    private static ApplicationManager applicationManager = new ApplicationManager(statement);
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
            statement = connection.createStatement();
        } catch (SQLException e) {
            LogManager.printStackTrace(e.getStackTrace());
            e.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////
        ///////////CREATE TABLES/////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
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
                    " Type VARCHAR(10) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Alcohol' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Manufacturers(\n" +
                    " UUID VARCHAR(30) PRIMARY KEY,\n" +
                    " Username VARCHAR(30) NOT NULL,\n" +
                    " Company VARCHAR(100) NOT NULL\n" +
                    ");\n");
        } catch (SQLException e) {
            LogManager.println("Table 'Manufacturers' exists.", EnumWarningType.NOTE);
        }

        try {
            statement.executeUpdate("CREATE TABLE Applications(\n" +
                    " ApplicationNo VARCHAR(30) PRIMARY KEY,\n" +
                    " DateSubmitted VARCHAR(12) NOT NULL,\n" +
                    " Manufacturer VARCHAR(50) NOT NULL,\n" +
                    " ApplicantName VARCHAR(30),\n" +
                    " Email VARCHAR(100) NOT NULL,\n" +
                    " PermitNo VARCHAR(100) NOT NULL,\n" +
                    " DBAorTrade VARCHAR(100) NOT NULL,\n" +
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
            statement.executeUpdate("CREATE TABLE Users(\n" +
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

 /   /////////////////////////////////////////////////////////////////////////////////
    ///////////SEARCH ALCOHOL////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> Search(String entered, String type) {
        String query = "SELECT * FROM Alcohol WHERE BrandName = '" + entered + "' AND Type = '" + type + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            ResultSet searchAlcohol = statement.executeQuery(query);
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
            ResultSet getApplications = statement.executeQuery(query);

            for (int i = 0; i < num; i++) {
                getApplications.next();
                DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
                dataSet.addField("ApplicationNo", getApplications.getString("ApplicationNo"));
                dataSet.addField("Manufacturer", getApplications.getString("Manufacturer"));
                dataSet.addField("PermitNo", getApplications.getString("PermitNo"));
                dataSet.addField("Status", getApplications.getString("Status"));
                dataSet.addField("AlcoholType", getApplications.getString("AlcoholType"));
                dataSet.addField("AgentID", getApplications.getString("AgentID"));
                dataSet.addField("Source", getApplications.getString("Source"));
                dataSet.addField("Brand", getApplications.getString("Brand"));
                dataSet.addField("Address", getApplications.getString("Address"));
                dataSet.addField("Address2", getApplications.getString("Address2"));
                dataSet.addField("Volume", getApplications.getString("Volume"));
                dataSet.addField("ABV", getApplications.getString("ABV"));
                dataSet.addField("PhoneNo", getApplications.getString("PhoneNo"));
                dataSet.addField("AppType", getApplications.getString("AppType"));
                dataSet.addField("VintageDate", getApplications.getString("VintageDate"));
                dataSet.addField("PH", getApplications.getString("PH"));
                dataSet.addField("InboxAgent", getApplications.getString("InboxAgent"));
                dataSet.addField("ApplicantName", getApplications.getString("ApplicantName"));
                dataSet.addField("DateSubmitted", getApplications.getString("DateSubmitted"));
                dataSet.addField("DBAorTrade", getApplications.getString("DBAorTrade"));
                dataSet.addField("Email", getApplications.getString("Email"));


                dataSets.add(dataSet);
                statement.executeUpdate("UPDATE Applications SET InboxAgent = username WHERE ApplicationNo = '" + getApplications.getString("ApplicationNo") + "';");
                //applications.next();
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }
        return dataSets;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS IN AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> getApplicationsInitialAgent(String username) {
        /*String query = "SELECT * FROM Applications WHERE InboxAgent = '" + username + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            ResultSet getApplications = statement.executeQuery(query);

            while(getApplications.next()) {
                DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
                dataSet.addField("ApplicationNo", getApplications.getString("ApplicationNo"));
                dataSet.addField("Manufacturer", getApplications.getString("Manufacturer"));
                dataSet.addField("PermitNo", getApplications.getString("PermitNo"));
                dataSet.addField("Status", getApplications.getString("Status"));
                dataSet.addField("AlcoholType", getApplications.getString("AlcoholType"));
                dataSet.addField("AgentID", getApplications.getString("AgentID"));
                dataSet.addField("Source", getApplications.getString("Source"));
                dataSet.addField("Brand", getApplications.getString("Brand"));
                dataSet.addField("Address", getApplications.getString("Address"));
                dataSet.addField("Address2", getApplications.getString("Address2"));
                dataSet.addField("Volume", getApplications.getString("Volume"));
                dataSet.addField("ABV", getApplications.getString("ABV"));
                dataSet.addField("PhoneNo", getApplications.getString("PhoneNo"));
                dataSet.addField("AppType", getApplications.getString("AppType"));
                dataSet.addField("VintageDate", getApplications.getString("VintageDate"));
                dataSet.addField("PH", getApplications.getString("PH"));
                dataSet.addField("InboxAgent", getApplications.getString("InboxAgent"));
                dataSets.add(dataSet);
                //stmt.executeUpdate("UPDATE Applications SET InboxAgent = NULL WHERE ApplicationNo = '" + getApplications.getString("ApplicationNo") + "';");
                //applications.next();
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }*/
        return applicationManager.getApplicationsByAgent(username);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS FOR A MANUFACTURER///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> getApplicationsInitialManuefacturer(String username) {
        /*String query = "SELECT * FROM Applications WHERE Manufacturer = '" + username + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            ResultSet getApplications = stmt.executeQuery(query);

            while(getApplications.next()) {
                DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
                dataSet.addField("ApplicationNo", getApplications.getString("ApplicationNo"));
                dataSet.addField("Manufacturer", getApplications.getString("Manufacturer"));
                dataSet.addField("PermitNo", getApplications.getString("PermitNo"));
                dataSet.addField("Status", getApplications.getString("Status"));
                dataSet.addField("AlcoholType", getApplications.getString("AlcoholType"));
                dataSet.addField("AgentID", getApplications.getString("AgentID"));
                dataSet.addField("Source", getApplications.getString("Source"));
                dataSet.addField("Brand", getApplications.getString("Brand"));
                dataSet.addField("Address", getApplications.getString("Address"));
                dataSet.addField("Address2", getApplications.getString("Address2"));
                dataSet.addField("Volume", getApplications.getString("Volume"));
                dataSet.addField("ABV", getApplications.getString("ABV"));
                dataSet.addField("PhoneNo", getApplications.getString("PhoneNo"));
                dataSet.addField("AppType", getApplications.getString("AppType"));
                dataSet.addField("VintageDate", getApplications.getString("VintageDate"));
                dataSet.addField("PH", getApplications.getString("PH"));
                dataSet.addField("InboxAgent", getApplications.getString("InboxAgent"));
                dataSets.add(dataSet);
                //stmt.executeUpdate("UPDATE Applications SET InboxAgent = NULL WHERE ApplicationNo = '" + getApplications.getString("ApplicationNo") + "';");
                //applications.next();
            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }*/
        return applicationManager.getApplicationsByManuefacturer(username);
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATION FROM ApplicationNo////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static Application getApplicationNo(String appNo) {
        /*String query = "SELECT * FROM Applications WHERE ApplicationNo = '" + appNo + "';";
        DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
        try {
            ResultSet application = statement.executeQuery(query);
            application.next();
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
            dataSet.addField("ApplicantName", application.getString("ApplicantName"));
            dataSet.addField("DateSubmitted", application.getString("DateSubmitted"));
            dataSet.addField("DBAorTrade", application.getString("DBAorTrade"));
            dataSet.addField("Email", application.getString("Email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return ApplicationManager.getApplicationsByNumber(appNo);
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
            statement.executeUpdate("INSERT INTO Users (username, passwordHash, userType) VALUES " +
                    "('" + username + "', '" + password + "', '" + type + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET USER ID/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static String getUserType(String username) {
        String query = "SELECT * FROM Users WHERE username = '" + username + "';";
        String userType = "foo";
        DataSet dataSet = new DataSet(EnumTableType.APPLICATION);
        try {
            ResultSet user = statement.executeQuery(query);
            user.next();
            userType = user.getString("userType");
            LogManager.println("User " + username + " is type " + userType);
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
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
}
