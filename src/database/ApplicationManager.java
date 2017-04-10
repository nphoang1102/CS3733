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
    ///////////GET APPLICATIONS IN AGENT'S INBOX/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<DataSet> getApplicationsInitialAgent(String username) {
        String query = "SELECT * FROM Applications WHERE InboxAgent = '" + username + "';";
        LinkedList<DataSet> dataSets = new LinkedList<>();
        try {
            getApplications = statement.executeQuery(query);
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
        }
        return dataSets;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////GET APPLICATIONS FOR A MANUFACTURER///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public static LinkedList<Application> getForManuefacturer(String manufacturerUserName) {
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
