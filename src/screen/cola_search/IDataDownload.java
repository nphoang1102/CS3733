package screen.cola_search;

import javafx.collections.ObservableList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public interface IDataDownload {
    public void downloadData(ObservableList<ColaResult> result, String delimiter);
}
