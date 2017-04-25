package screen.cola_search;

import base.LogManager;
import base.StringUtilities;
import javafx.collections.ObservableList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class toCSV implements IDataDownload {
    @Override
    public void downloadData(ObservableList<ColaResult> result, String delimiter) {
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
        for (ColaResult data : result){
            index++;
            String columns = data.getId() + delimiter
                    + data.getPermit() + delimiter
                    + data.getSerial() + delimiter
                    + data.getDate() + delimiter
                    + data.getFname() + delimiter
                    + data.getName() + delimiter
                    + data.getSource() + delimiter
                    + data.getAclass() + delimiter
                    + data.getType() + delimiter
                    + data.getAlCon() + delimiter
                    + data.getYear() + delimiter
                    + data.getPh();
            output[index] = columns;
        }
        StringUtilities.saveData(outputPath, output);
        LogManager.println("Search result saved to /searchResult.csv");
    }
}
