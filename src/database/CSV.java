package database;

import base.LogManager;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class CSV {
    String filePath;
    Reader reader;
    CSVWriter writer;
    CSVFormat csvFormat;
    Iterable<CSVRecord> records;

    public CSV(String filePath) {
        csvFormat = CSVFormat.RFC4180.withFirstRecordAsHeader();
        this.filePath = filePath;
    }
    private void createReader(){
        try {
            reader = new FileReader(filePath);
        } catch (IOException e) {
            LogManager.println("Failed to create CSVReader for " + filePath + ": " + e.getMessage()/*,EnumWarningType.ERROR*/);
        }
    }
    public void readApplication() {

    }


    //    public String [][] read(){
    public void importAlcohol() {

        try {
            records = csvFormat.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CSVRecord record : records) {
            Alcohol alcohol = new Alcohol();
            alcohol.TTBID = record.get("TTBID");
            alcohol.PermitNo = record.get("Plant Registry");
            alcohol.SerialNo = record.get("Serial Number");
            alcohol.CompletedDate = record.get("Date Approved");
            alcohol.FancifulName = record.get("Fanciful Name");
            alcohol.BrandName = record.get("Brand Name");
            alcohol.Origin = record.get("Origin");
            alcohol.Type = record.get("Type");
            alcohol.AlcoholContent = record.get("ABV");
            alcohol.VintageYear = record.get("Vintage");
            alcohol.PH = record.get("PH");
        DatabaseManager.insertAlcohol(alcohol);
        }
    }

    public void exportAlcohol(LinkedList<DataSet> list) {
        String[][] data = new String[list.size() + 1][12];
        data[0][0] = "TTBID";
        data[0][1] = "Plant Registry";
        data[0][2] = "Serial Number";
        data[0][3] = "Date Approved";
        data[0][4] = "Fanciful Name";
        data[0][5] = "Brand Name";
        data[0][6] = "Origin";
//        data[0][7] = "Class";
        data[0][7] = "Type";
        data[0][8] = "ABV";
        data[0][9] = "Vintage";
        data[0][10] = "PH";
        data[0][11] = "Application Number";
        for (int i = 1; i < list.size(); i++) {
            Alcohol alcohol = (Alcohol) list.get(i);
            data[i][0] = alcohol.TTBID;
            data[i][1] = alcohol.PermitNo;
            data[i][2] = alcohol.SerialNo;
            data[i][3] = alcohol.CompletedDate;
            data[i][4] = alcohol.FancifulName;
            data[i][5] = alcohol.BrandName;
            data[i][6] = alcohol.Origin;
//            data[i][7] = alcohol.Class;
            data[i][7] = alcohol.Type;
            data[i][8] = alcohol.AlcoholContent;
            data[i][9] = alcohol.VintageYear;
            data[i][10] = alcohol.PH;
            data[i][11] = alcohol.ApplicationNo;
        }
        export(data, ',');
    }

    public void exportApplications(LinkedList<DataSet> list) {
        String[][] data = new String[list.size() + 1][31];
        data[0][0] = "Application Number";
        data[0][1] = "Serial Number";
        data[0][2] = "Application Type";
        data[0][3] = "Application Status";
        data[0][4] = "Manufacturer Username";
        data[0][5] = "Rep Name";
        data[0][6] = "Rep ID";
        data[0][7] = "Agent Username";
        data[0][8] = "Agent Name";
        data[0][9] = "Plant Registry";
        data[0][10] = "Locality";
        data[0][11] = "Brand";
        data[0][12] = "Fanciful Name";
        data[0][13] = "Alcohol Type";
        data[0][14] = "ABV";
        data[0][15] = "Address";
        data[0][16] = "Address2";
        data[0][17] = "Formula";
        data[0][18] = "Wine Appelation";
        data[0][19] = "Vintage Date";
        data[0][20] = "Grapes";
        data[0][21] = "PH";
        data[0][22] = "Phone Number";
        data[0][23] = "Email";
        data[0][24] = "Additional Info";
        data[0][25] = "Date of Submission";
        data[0][26] = "Date of Approval";
        data[0][27] = "Date of Expiration";
        data[0][28] = "TTBID";
        data[0][29] = "Reason for Rejection";
        data[0][30] = "Revision Number";
        for (int i = 1; i < list.size(); i++) {
            Application application = (Application) list.get(i);
            data[0][0] = application.ApplicationNo;
            data[0][1] = application.SerialNo;
            data[0][2] = application.ApplicationType;
            data[0][3] = application.ApplicationStatus;
            data[0][4] = application.ManufacturerUsername;
            data[0][5] = application.RepName;
            data[0][6] = application.RepID;
            data[0][7] = application.AgentUsername;
            data[0][8] = application.AgentName;
            data[0][9] = application.PlantRegistry;
            data[0][10] = application.Locality;
            data[0][11] = application.Brand;
            data[0][12] = application.FancifulName;
            data[0][13] = application.AlcoholType;
            data[0][14] = application.ABV;
            data[0][15] = application.Address;
            data[0][16] = application.Address2;
            data[0][17] = application.Formula;
            data[0][18] = application.WineAppelation;
            data[0][19] = application.VintageDate;
            data[0][20] = application.Grapes;
            data[0][21] = application.PH;
            data[0][22] = application.PhoneNo;
            data[0][23] = application.Email;
            data[0][24] = application.AdditionalInfo;
            data[0][25] = application.DateOfSubmission;
            data[0][26] = application.DateOfApproval;
            data[0][27] = application.DateOfExpiration;
            data[0][28] = application.ApprovedTTBID;
            data[0][29] = application.ReasonForRejection;
            data[0][30] = Integer.toString(application.revisionNo);
        }
        export(data, ',');
    }

    private void export(String[][] data, char delim) {
        try {
            writer = new CSVWriter(new FileWriter(filePath), delim);
            for (int i = 0; i < data.length; i++) {
                writer.writeNext(data[i]);
            }
            writer.close();
        } catch (IOException e) {
            LogManager.println("Failed to create CSVWriter for " + filePath + ": " + e.getMessage()/*,EnumWarningType.ERROR*/);
        }

    }
}
