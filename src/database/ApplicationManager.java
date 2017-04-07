package database;

import base.EnumTableType;
import base.EnumWarningType;
import base.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by Evan Goldstein on 4/6/17.
 */
public class ApplicationManager {

    private static ResultSet getApplications = null;
    private static Statement statement = null;
    public ApplicationManager(Statement statement){
        this.statement = statement;
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
            statement.executeUpdate("UPDATE Users SET status = 'APPROVED' WHERE ApplicationNo = '" + ApplicationNo + "';");
            statement.executeUpdate("UPDATE Applications SET AgentInbox = NULL WHERE ApplicationNo = '" + ApplicationNo + "';");
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
            statement.executeUpdate("INSERT INTO Alcohol (TTBID, PermitNo, SerialNo, CompletedDate, FancifulName, BrandName, Origin, Class, Type) VALUES ('" + TTBID + "', '" + PermitNo + "', '" + SerialNo + "', '" + Date + "', '" + FancifulName + "', '" + BrandName + "', '" + Origin + "', '" + Class + "', '" + Type + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////REJECT/ APPLICATION///////////////////////////////////////////////////
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
    public static LinkedList<Application> getApplicationsByAgent(String agent) {
        LinkedList<Application> dataSets = new LinkedList<>();
        return runQuery("SELECT * FROM Applications WHERE InboxAgent = '" + agent + "';", 0,"");
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS FOR A MANUFACTURER///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> getApplicationsByManuefacturer(String manufacturerUserName) {
        return runQuery("SELECT * FROM Applications WHERE Manufacturer = '" + manufacturerUserName + "';", 0, "");
    }


    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATION FROM ApplicationNo////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static Application getApplicationsByNumber(String appNo) {
        LinkedList<Application> applicationLinkedList = runQuery("SELECT * FROM Applications WHERE ApplicationNo = '" + appNo + "';", 1, "");
        Application application = applicationLinkedList.get(0);
        return application;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> getApplications(String type, int num, String username) {
        String query = "SELECT * FROM Applications WHERE AlcoholType = '" + type + "';";
        return runQuery("SELECT * FROM Applications WHERE AlcoholType = '" + type + "';", num, username);

    }

    public static LinkedList<Application> runQuery(String queryStr, int num, String agent){
        if(num==0){
            try {
                ResultSet count = statement.executeQuery("SELECT COUNT(*) AS total FROM");
                count.next();
                num = count.getInt("total");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LinkedList<Application> applicationLinkedList = new LinkedList<>();
        try {
            ResultSet getApplications = statement.executeQuery(queryStr);

            for (int i = 0; i < num; i++) {
                getApplications.next();
                Application application = new Application();
                application.ApplicationNo = getApplications.getString("ApplicationNo");
                application.Manufacturer = getApplications.getString("Manufacturer");
                application.PermitNo = getApplications.getString("PermitNo");
                application.Status = getApplications.getString("Status");
                application.AlcoholType = getApplications.getString("AlcoholType");
                application.AgentID = getApplications.getString("AgentID");
                application.Source = getApplications.getString("Source");
                application.Brand = getApplications.getString("Brand");
                application.Address = getApplications.getString("Address");
                application.Address2 = getApplications.getString("Address2");
                application.Volume = getApplications.getString("Volume");
                application.ABV = getApplications.getString("ABV");
                application.PhoneNo = getApplications.getString("PhoneNo");
                application.AppType = getApplications.getString("AppType");
                application.VintageDate = getApplications.getString("VintageDate");
                application.PH = getApplications.getString("PH");
                application.InboxAgent = getApplications.getString("InboxAgent");
                application.ApplicantName = getApplications.getString("ApplicantName");
                application.DateSubmitted = getApplications.getString("DateSubmitted");
                application.DBAorTrade = getApplications.getString("DBAorTrade");
                application.Email = getApplications.getString("Email");
                applicationLinkedList.add(application);

            }
        } catch (SQLException e) {
            LogManager.println("Empty result set! Is the applications table empty?", EnumWarningType.WARNING);
            return new LinkedList<>();
        }

        if(!agent.equals("")){
            try {
                statement.executeUpdate("UPDATE Applications SET InboxAgent = '" + agent + "' WHERE ApplicationNo = '" + getApplications.getString("ApplicationNo") + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return applicationLinkedList;
    }

}
