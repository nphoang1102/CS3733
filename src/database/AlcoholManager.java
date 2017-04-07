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

/**
 * Created by Daniel Yun on 4/6/2017.
 */
public class AlcoholManager {

    private static Statement stmt = null;
    private static Connection connection = null;

    public AlcoholManager() {

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
                    " Type VARCHAR(10) NOT NULL,\n" +
                    " AlcoholContent VARCHAR(20) NOT NULL,\n" +
                    " VintageYear NUMBER,\n" +
                    " PH VARCHAR(10)\n" +
                    ");");
        } catch (SQLException e) {
            LogManager.println("Table 'Alcohol' exists.", EnumWarningType.NOTE);
        }
    }

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
                String AlcoholContent = searchAlcohol.getString("AlcoholContent");
                String VintageYear = searchAlcohol.getString("VintageYear");
                String PH = searchAlcohol.getString("PH");
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
                dataSet.addField("AlcoholContent", AlcoholContent);
                dataSet.addField("VintageYear", VintageYear);
                dataSet.addField("PH", PH);
                dataSets.add(dataSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSets;
    }
}
