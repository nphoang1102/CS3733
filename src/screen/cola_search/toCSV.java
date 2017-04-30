package screen.cola_search;

import base.LogManager;
import base.StringUtilities;
import database.Alcohol;
import database.DataSet;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class toCSV implements IDataDownload {
    @Override
    public void downloadData(LinkedList<DataSet> result, String delimiter) {
        int index = 0;
        String[] output = new String[result.size() + 1];
        output[0] = "TTB ID" + delimiter
                + "Permit number" + delimiter
                + "Serial number" + delimiter
                + "Date approved" + delimiter
                + "Fancy name" + delimiter
                + "Brand name" + delimiter
                + "Origin" + delimiter
                + "Class" + delimiter
                + "Type" + delimiter
                + "Alcohol content" + delimiter
                + "Vintage year" + delimiter
                + "pH level";
        String outputPath = "/searchResult.csv";
        for (DataSet tempSet: result){
            Alcohol data = (Alcohol) tempSet;
            String columns = data.TTBID + delimiter
                    + data.PermitNo + delimiter
                    + data.SerialNo + delimiter
                    + data.CompletedDate + delimiter
                    + data.FancifulName + delimiter
                    + data.BrandName + delimiter
                    + data.Origin + delimiter
                    + data.Class + delimiter
                    + data.Type + delimiter
                    + data.AlcoholContent + delimiter
                    + data.VintageYear + delimiter
                    + data.PH;
            index++;
            output[index] = columns;
        }
        StringUtilities.saveData(outputPath, output);
        LogManager.println("Search result saved to /searchResult.csv");
    }
}
