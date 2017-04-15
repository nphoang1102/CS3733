package screen.cola_search;

import base.LogManager;
import base.StringUtilities;
import javafx.collections.ObservableList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class toCSV implements IDataDownload {
    @Override
    public void downloadData(ObservableList<ColaResult> result) {
        int index = 0;
        String[] output = new String[result.size() + 1];
        output[0] = "TTB ID" + ","
                + "Permit number" + ","
                + "Serial number" + ","
                + "Date approved" + ","
                + "Fancy name" + ","
                + "Brand name" + ","
                + "Origin" + ","
                + "Class" + ","
                + "Type";
        String outputPath = "/searchResult.csv";
        for (ColaResult data : result){
            index++;
            String columns = data.getId() + ","
                    + data.getPermit() + ","
                    + data.getSerial() + ","
                    + data.getDate() + ","
                    + data.getFname() + ","
                    + data.getName() + ","
                    + data.getSource() + ","
                    + data.getAclass() + ","
                    + data.getType();
            output[index] = columns;
        }
        StringUtilities.saveData(outputPath, output);
        LogManager.println("Search result saved to /searchResult.csv");
    }
}
