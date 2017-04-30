package screen.cola_search;

import database.DataSet;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public interface IDataDownload {
    public void downloadData(LinkedList<DataSet> result, String delimiter);
}
