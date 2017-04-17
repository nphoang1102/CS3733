package screen.cola_search;

import base.LogManager;
import base.StringUtilities;
import javafx.collections.ObservableList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class toTSV implements IDataDownload {
    @Override
    public void downloadData(ObservableList<ColaResult> result) {
        int index = 0;
        String[] output = new String[result.size() + 1];
        output[0] = "TTB ID" + "\t"
                + "Permit number" + "\t"
                + "Serial number" + "\t"
                + "Date approved" + "\t"
                + "Fancy name" + "\t"
                + "Brand name" + "\t"
                + "Origin" + "\t"
                + "Class" + "\t"
                + "Type" + "\t"
                + "Alcohol content" + "\t"
                + "Vintage year" + "\t"
                + "pH level";
        String outputPath = "/searchResult-tab.txt";
        for (ColaResult data : result){
            index++;
            String columns = data.getId() + "\t"
                    + data.getPermit() + "\t"
                    + data.getSerial() + "\t"
                    + data.getDate() + "\t"
                    + data.getFname() + "\t"
                    + data.getName() + "\t"
                    + data.getSource() + "\t"
                    + data.getAclass() + "\t"
                    + data.getType() + "\t"
                    + data.getAlCon() + "\t"
                    + data.getYear() + "\t"
                    + data.getPh();
            output[index] = columns;
        }
        StringUtilities.saveData(outputPath, output);
        LogManager.println("Search result saved to /searchResult-tab.txt");
    }
}
