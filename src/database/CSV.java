package database;

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

    public void readApplication() {

    }

    public void readAlcohol() {
//        String [] [] csv = read();

    }

    //    public String [][] read(){
    public void read() {
        try {
            reader = new FileReader(filePath);
        } catch (IOException e) {
            System.out.println("Failed to create CSVReader for " + filePath + ": " + e.getMessage()/*,EnumWarningType.ERROR*/);
        }

        try {
            records = csvFormat.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CSVRecord record : records) {
            System.out.printf("A = %s, B = %s, C = %s, D = %s", record.get("A"), record.get("B"), record.get("C"), record.get("D"));
            System.out.println();
        }
    }

    public void writeAlcohol(LinkedList<DataSet> list) {
        String[][] data = new String[list.size() + 1][13];
        data[0][0] = "TTBID";
        data[0][1] = "Permit Number";
        data[0][2] = "Serial Number";
        data[0][3] = "Date Approved";
        data[0][4] = "Fanciful Name";
        data[0][5] = "Brand Name";
        data[0][6] = "Origin";
        data[0][7] = "Class";
        data[0][8] = "Type";
        data[0][9] = "ABV";
        data[0][10] = "Vintage";
        data[0][11] = "PH";
        data[0][12] = "Application Number";
        for (int i = 1; i < list.size(); i++) {
            Alcohol alcohol = (Alcohol) list.get(i);
            data[i][0] = alcohol.TTBID;
            data[i][1] = alcohol.PermitNo;
            data[i][2] = alcohol.SerialNo;
            data[i][3] = alcohol.CompletedDate;
            data[i][4] = alcohol.FancifulName;
            data[i][5] = alcohol.BrandName;
            data[i][6] = alcohol.Origin;
            data[i][7] = alcohol.Class;
            data[i][8] = alcohol.Type;
            data[i][9] = alcohol.AlcoholContent;
            data[i][10] = alcohol.VintageYear;
            data[i][11] = alcohol.PH;
            data[i][12] = alcohol.ApplicationNo;
        }
        write(data, ',');
    }

    private void write(String[][] data, char delim) {
        try {
            writer = new CSVWriter(new FileWriter(filePath), delim);
            for (int i = 0; i < data.length; i++) {
                writer.writeNext(data[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to create CSVWriter for " + filePath + ": " + e.getMessage()/*,EnumWarningType.ERROR*/);
        }

    }
}
